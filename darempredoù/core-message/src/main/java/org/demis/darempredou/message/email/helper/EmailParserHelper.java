package org.demis.darempredou.message.email.helper;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.net.QuotedPrintableCodec;
import org.apache.commons.lang.StringUtils;
import org.demis.darempredou.message.email.EmailAddress;
import org.demis.darempredou.message.email.EmailHeader;
import org.demis.darempredou.message.email.EmailMessage;
import org.demis.darempredou.message.email.EmailRecipient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;

public class EmailParserHelper {

    private static final Logger logger = LoggerFactory.getLogger(EmailParserHelper.class);

    public static EmailRecipient getRecipient(String line, String headerName) {
        String headerValue = line.trim();
        EmailRecipient recipient = new EmailRecipient();
        // email
        recipient.setEmail(parseEmailAddressRecipient(headerValue));
        // name
        String name = parseEmailNameRecipient(headerValue);
        if (!StringUtils.isEmpty(name)) {
            recipient.setName(name);
        }
        // header name
        recipient.setType(headerName);

        return recipient;
    }

    public static EmailAddress getAddress(String line) {
        String headerValue = line.trim();
        EmailAddress address = new EmailAddress();
        // email
        address.setEmail(parseEmailAddressRecipient(headerValue));
        // name
        String name = parseEmailNameRecipient(headerValue);
        if (!StringUtils.isEmpty(name)) {
            address.setName(name);
        }

        return address;
    }
    public static String parseEmailAddressRecipient(String email) {
        if (!email.contains("<")) {
            return email;
        }
        // find email address
        StringTokenizer tokenizer = new StringTokenizer(email.substring(email.indexOf("<")), "<>");
        if (tokenizer.hasMoreTokens()) {
            return tokenizer.nextToken();
        }
        return email;
    }

    public static String parseEmailNameRecipient(String email) {
        if (!email.contains("<")) {
            return null;
        }
        // find email address
        String beforeAddress = email.substring(0, email.indexOf("<")).trim();
        beforeAddress = beforeAddress.replace('_', ' ');
        // extract charset
        if (beforeAddress.contains("=?")) {
            try {
                String charset = "UTF-8";
                String name = "";
                String encoding = "Q";
                if (beforeAddress.contains("=?")) {
                    String tmp = beforeAddress.substring(beforeAddress.indexOf("=?") + "=?".length());
                    charset = tmp.substring(0, tmp.indexOf("?"));
                    tmp = tmp.substring(tmp.indexOf("?") + 1);
                    encoding = tmp.substring(0, 1);
                    tmp = tmp.substring(tmp.indexOf("?") + 1);
                    name = tmp.substring(0, tmp.length() - 2);
                    if ("Q".equals(encoding)) {
                        beforeAddress = (new QuotedPrintableCodec(charset)).decode(name);
                    } else if ("B".equals(encoding)) {
                        beforeAddress = new String(new Base64().decode(name.getBytes()));
                    }
                }
            } catch (DecoderException ex) {
                logger.warn("Error", ex);
            }
        }
        beforeAddress = beforeAddress.replace("\"", "");
        return beforeAddress;
    }

    public static void readHeaderLine(List<EmailHeader> headers, String line) {
        if (line.charAt(0) == ' ' || line.charAt(0) == '\t') {
            headers.get(headers.size() - 1).Binary(" " + line.trim().replace("\t", ""));
        }
        else {
            String headerName = line.substring(0, line.indexOf(":")) ;
            String headerValue = line.substring(line.indexOf(":") + 1) ;

            EmailHeader header = new EmailHeader(headerName.trim(), headerValue.trim());
            headers.add(header);
        }
    }

    public static void processEmailHeaders(List<EmailHeader> headers, EmailMessage message) {
        for (EmailHeader header: headers) {
            if ("from".equals(header.getName().toLowerCase())) {
                message.setFrom(getAddress(header.getValue()));
            }
            else if ("to".equals(header.getName().toLowerCase())) {
                message.addRecipient(getRecipient(header.getValue(), "to"));
            }
            else if ("cc".equals(header.getName().toLowerCase())) {
                message.addRecipient(getRecipient(header.getValue(), "cc"));
            }
            else if ("subject".equals(header.getName().toLowerCase())) {
                message.setSubject(EmailParserHelper.decodeAllNonASCIIHeaderValue(header.getValue().trim()));
            }
        }
    }

    public static String decodeAllNonASCIIHeaderValue(String entry) {
        String result = entry;

        if (result.indexOf("=?") >= 0) {
            while (result.indexOf("=?") >= 0) {
                result = decodeNonASCIIHeaderValue(result);
            }
        }
        return result;
    }

    private static String decodeNonASCIIHeaderValue(String entry) {
        String result = "";

        if (entry.indexOf("=?") >= 0) {
            try {
                String charset = "UTF-8";
                String name = "";
                String encoding = "Q";
                result = entry.substring(0,entry.indexOf("=?"));
                if (entry.indexOf("=?") >= 0) {
                    String tmp = entry.substring(entry.indexOf("=?") + "=?".length());
                    charset = tmp.substring(0, tmp.indexOf("?"));
                    tmp = tmp.substring(tmp.indexOf("?") + 1);
                    encoding = tmp.substring(0, 1);
                    tmp = tmp.substring(tmp.indexOf("?") + 1);
                    name = tmp.substring(0, tmp.indexOf("?="));
                    if ("Q".equals(encoding)) {
                        result += (new QuotedPrintableCodec(charset)).decode(name.replace("_", " "));
                    } else if ("B".equals(encoding)) {
                        result += new String(new Base64().decode(name.getBytes()));
                    }
                    if (tmp.indexOf("?=") >= 0) {
                        result += tmp.substring(tmp.indexOf("?=") + 2);
                    }
                }
            } catch (DecoderException ex) {
                logger.warn("Error", ex);
            }
        }
        else {
            result = entry;
        }

        return result;
    }
}
