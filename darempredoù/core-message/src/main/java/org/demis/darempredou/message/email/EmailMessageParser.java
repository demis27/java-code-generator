package org.demis.darempredou.message.email;

import org.apache.commons.lang.StringUtils;
import org.demis.darempredou.message.BinaryMessage;
import org.demis.darempredou.message.Message;
import org.demis.darempredou.message.MessageParser;
import org.demis.darempredou.message.MessageParserConfiguration;
import org.demis.darempredou.message.email.helper.EmailParserHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class EmailMessageParser implements MessageParser {

    private final Logger logger = LoggerFactory.getLogger(EmailMessageParser.class);

    @Override
    public Message parse(MessageParserConfiguration configuration, BinaryMessage binaryMessage) {
        EmailMessage message = new EmailMessage(binaryMessage.getBinaryContent());

        List<EmailHeader> headers = new ArrayList<EmailHeader>();

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(message.getBinaryContent());
        BufferedReader reader = new BufferedReader(new InputStreamReader(byteArrayInputStream));
        String line = null;
        EmailPart currentPart = null;
        EmailPart topPart = new EmailPart();
        boolean readHeader = false;
        boolean readContent = false;
        boolean readTopHeader = true;
        currentPart = topPart;

        try {
            while ((line = reader.readLine()) != null) {
                if (readTopHeader && StringUtils.isEmpty(line)) {
                    readTopHeader = false;
                    EmailParserHelper.processEmailHeaders(headers, message);
                    EmailParserHelper.processEmailPartHeaders(headers, currentPart);
                    headers.clear();
                    if (!topPart.isMultipart()) {
                        readContent = true;
                    }
                }
                else if (readTopHeader) {
                    EmailParserHelper.readHeaderLine(headers, line);
                }
                else {
                    if (EmailParserHelper.detectBoundaryBegin(currentPart, line)) {
                        EmailPart part = new EmailPart();
                        readHeader = true;
                        if (!EmailParserHelper.extractBoundary(line).equals(currentPart.getBoundary())) {
                            currentPart = currentPart.getParent();
                        }
                        currentPart.addPart(part);
                        currentPart = part;
                    }
                    else if (EmailParserHelper.detectBoundaryEnd(currentPart, line)) {
                        currentPart = currentPart.getParent();
                        readContent = false;
                    }
                    else if (readHeader) {
                        if (StringUtils.isEmpty(line)) {
                            EmailParserHelper.processEmailHeaders(headers, message);
                            EmailParserHelper.processEmailPartHeaders(headers, currentPart);
                            headers.clear();
                            readContent = true;
                            readHeader = false;
                        }
                        else {
                            EmailParserHelper.readHeaderLine(headers, line);
                        }
                    }
                    else if (readContent) {
                        currentPart.addContent(line);
                    }
                }
            }

            if (topPart.getContentType().startsWith("multipart/alternative")) {
                EmailParserHelper.readMultipartAlternative(topPart, message);
            }
            else if (topPart.getContentType().startsWith("multipart/mixed")) {
                for (EmailPart apart : topPart.getParts()) {
                    if (apart.getContentType().startsWith("multipart/alternative")) {
                        EmailParserHelper.readMultipartAlternative(apart, message);
                    }
                    else if (apart.getContentDisposition() != null && apart.getContentDisposition().equals("attachment")) {
                        EmailParserHelper.readAttachment(apart, message);
                    }
                    else {
                        EmailParserHelper.createTextOrHtml(message, apart);
                    }
                }
            }
            else if (topPart.getContentType().startsWith("multipart/related")) {
                for (EmailPart apart : topPart.getParts()) {
                    if (apart.getContentType().startsWith("multipart/alternative")) {
                        EmailParserHelper.readMultipartAlternative(apart, message);
                    }
                    else if (apart.getContentDisposition() != null && apart.getContentDisposition().equals("attachment")) {
                        EmailParserHelper.readAttachment(apart, message);
                    }
                    else {
                        EmailParserHelper.readAttachment(apart, message);
                    }
                }
            }
            else {
                EmailParserHelper.createTextOrHtml(message, topPart);
            }

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return message;
    }


}
