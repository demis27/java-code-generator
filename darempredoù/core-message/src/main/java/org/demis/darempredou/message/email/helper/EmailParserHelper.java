package org.demis.darempredou.message.email.helper;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.net.QuotedPrintableCodec;
import org.apache.commons.lang.StringUtils;
import org.demis.darempredou.message.email.*;
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
            headers.get(headers.size() - 1).Binary(line.trim().replace("\t", ""));
        }
        else {
            String headerName = line.substring(0, line.indexOf(":")) ;
            String headerValue = line.substring(line.indexOf(":") + 1) ;

            EmailHeader header = new EmailHeader(headerName.trim(), headerValue.trim());
            headers.add(header);
        }
    }

    public static void processEmailPartHeaders(List<EmailHeader> headers, EmailPart part) {
        for (EmailHeader header: headers) {
            if ("content-type".equals(header.getName().toLowerCase())) {
                part.setContentType(header.getBinaryValue().trim().substring(0, header.getBinaryValue().indexOf(";")).trim().replace(";", ""));
                if (header.getBinaryValue().indexOf("charset") > 0) {
                    String endOf = header.getBinaryValue().substring(header.getBinaryValue().indexOf(";"));
                    part.setCharset(endOf.substring(endOf.indexOf("charset=") + "charset=".length()).replace("\"", ""));
                }
                if (header.getBinaryValue().indexOf("boundary") > 0) {
                    part.setBoundary(EmailParserHelper.extractBoundary(header.getBinaryValue()));
                }

            }
            else if ("content-transfer-encoding".equals(header.getName().toLowerCase())) {
                part.setContentTransferEncoding(header.getBinaryValue().trim());
            }
            else if ("content-disposition".equals(header.getName().toLowerCase())) {
                part.processContentDisposition(header.getBinaryValue());
            }
            else if ("content-id".equals(header.getName().toLowerCase())) {
                part.setContentId(header.getBinaryValue().replace("<", "").replace(">", "").trim());
            }
        }
        part.addHeaders(headers);
    }

    public static void processEmailHeaders(List<EmailHeader> headers, EmailMessage message) {
        for (EmailHeader header: headers) {
            if ("from".equals(header.getName().toLowerCase())) {
                message.setFrom(getAddress(header.getBinaryValue()));
                header.setValue((parseEmailNameRecipient(header.getBinaryValue()) + " <" + parseEmailAddressRecipient(header.getBinaryValue()) + ">").trim());
            }
            else if ("to".equals(header.getName().toLowerCase())) {
                message.addRecipient(getRecipient(header.getBinaryValue(), "to"));
            }
            else if ("cc".equals(header.getName().toLowerCase())) {
                message.addRecipient(getRecipient(header.getBinaryValue(), "cc"));
            }
            else if ("subject".equals(header.getName().toLowerCase())) {
                message.setSubject(EmailParserHelper.decodeAllNonASCIIHeaderValue(header.getBinaryValue().trim()));
            }
        }
        message.addHeaders(headers);
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

    public static String decodeNonASCIIHeaderValue(String entry) {
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

    public static boolean detectBoundaryBegin(EmailPart currentPart, String line) {
        return (line.toLowerCase().startsWith("--")
                && !line.toLowerCase().endsWith("--")
                && (line.contains(currentPart.getBoundary()) || (currentPart.getParent() != null && line.contains(currentPart.getParent().getBoundary())))) ;
    }

    public static boolean detectBoundaryEnd(EmailPart currentPart, String line) {
        return (line.toLowerCase().startsWith("--")
                && line.toLowerCase().endsWith("--")
                && (line.contains(currentPart.getBoundary()) || (currentPart.getParent() != null && line.contains(currentPart.getParent().getBoundary())))) ;
    }

    public static String extractBoundary(String line) {
        // if boundary is in declaration: boundary=<boundary number>
        if (line.contains("boundary=")) {
            String boundary = line.trim().replace("\t", "");
            boundary = boundary.substring(boundary.indexOf("boundary=") + "boundary=".length()).trim();
            boundary = boundary.replace("\"", "");
            if (boundary.indexOf("=") == boundary.length() -1) {
                boundary = boundary.substring(0, boundary.length() -1);
            }

            return boundary;
        } // if is juste boundary number: --<boundary number> or --<boundary number>--
        else if (line.trim().startsWith("--")) {
            String boundary = line.trim().substring(2);
            if (boundary.endsWith("--")) {
                boundary = boundary.substring(0, boundary.length() - 2);
            }
            return boundary;
        }
        return "";
    }

    public static void readMultipartAlternative(EmailPart part, EmailMessage emailMessage) {
        for (EmailPart apart : part.getParts()) {
            createTextOrHtml(emailMessage, apart);
        }
    }

    public static void createTextOrHtml(EmailMessage emailMessage, EmailPart part) {

        String contentValue = null ;
        if (part.getContentTransferEncoding().equals("7bit")) {
            contentValue = part.getContent();
        }
        else if (part.getContentTransferEncoding().equals("8bit")) {
            contentValue = part.getContent();
        }
        else if (part.getContentTransferEncoding().equals("base64")) {
            contentValue = new String(new Base64().decode(part.getContent().getBytes()));
        }
        else if (part.getContentTransferEncoding().equals("quoted-printable")) {
            try {
                contentValue = EmailParserHelper.decodeQuotedPrintableText(part.getCharset(), part.getContents());
            } catch (DecoderException ex) {
                logger.error("error", ex);
            }
        }

        if (part.getContentType().startsWith("text/plain")) {
            emailMessage.setTextPart(contentValue);
        }
        else if (part.getContentType().startsWith("text/html")) {
            emailMessage.setHtmlPart(contentValue);
        }
    }

    public static String decodeQuotedPrintableText(String charset, List<String> contents) throws DecoderException {
        QuotedPrintableCodec codec = new QuotedPrintableCodec(charset.toUpperCase());
        StringBuffer encodedContent = new StringBuffer("");
        StringBuffer decodeContent = new StringBuffer("");
        for (String contentLine : contents) {
            if (!(contentLine.length() > 0 && contentLine.charAt(contentLine.length() - 1) == '=')) {
                encodedContent.append(contentLine);
                decodeContent.append(codec.decode(encodedContent.toString()));
                decodeContent.append("\n");
                encodedContent = new StringBuffer("");
            } else {
                encodedContent.append(contentLine.substring(0, contentLine.length() - 1));
            }
        }
        return decodeContent.toString();
    }

    public static void readAttachment(EmailPart apart, EmailMessage emailMessage) {
        EmailAttachment attachment = new EmailAttachment();
        if (apart.getContentTransferEncoding().equals("base64")) {
            attachment.setBinaryContent(new Base64().decode(apart.getContent().getBytes()));
        }
        else if (apart.getContentTransferEncoding().equals("7bit")) {
            attachment.setBinaryContent(apart.getContent().getBytes());
        }
        else if (apart.getContentTransferEncoding().equals("8bit")) {
            attachment.setBinaryContent(apart.getContent().getBytes());
        }
        else if (apart.getContentTransferEncoding().equals("quoted-printable")) {
            try {
                attachment.setBinaryContent((new QuotedPrintableCodec(apart.getCharset())).decode(apart.getContent().getBytes()));
            } catch (DecoderException ex) {
                logger.error("error", ex);
            }
        }
        attachment.setFileName(apart.getFilename());
        attachment.setFileContentType(apart.getContentType());
        attachment.setFileSize(attachment.getBinaryContent().length);
        attachment.setContentId(apart.getContentId());
        emailMessage.addAttachment(attachment);
    }

}
