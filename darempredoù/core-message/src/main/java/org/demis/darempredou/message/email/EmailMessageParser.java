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
        boolean readHeader = false;
        boolean readContent = false;
        boolean readTopHeader = true;

        try {
            while ((line = reader.readLine()) != null) {
                if (readTopHeader && StringUtils.isEmpty(line)) {
                    readTopHeader = false;
                    EmailParserHelper.processEmailHeaders(headers, message);
                    //if (!topPart.isMultipart()) {
                    //    readContent = true;
                    //}
                }
                else if (readTopHeader) {
                    EmailParserHelper.readHeaderLine(headers, line);
                }
                else {
                }
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return message;
    }


}
