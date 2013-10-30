package org.demis.darempredou.message.email;

import org.demis.darempredou.message.BinaryMessage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class EmailMessageParserTest {


    private static BinaryMessage getMessage(String ressourceName) throws IOException {
        InputStream stream = EmailMessageParser.class.getResourceAsStream(ressourceName);
        BinaryMessage message = new BinaryMessage();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        StringBuffer buffer = new StringBuffer("");
        String line = null;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
            buffer.append("\n");
        }
        message.setBinaryContent(buffer.toString().getBytes());
        return message;
    }

    @Test
    public void htmlMessageWithAccent() throws IOException {

        BinaryMessage message = getMessage("rawemail-1.eml");
        EmailMessageParser parser = new EmailMessageParser();
        EmailMessage emailMessage = (EmailMessage)(parser.parse(null, message));
        String expectedContent = "message text pure? Avec des accents é\n"
                + "\n"
                + "--\n"
                + "Avant d'imprimer cet email, réfléchissez à l'impact sur l'environnement, merci\n"
                + "\n"
                + "Kermabon Démis Stéphane\n"
                + "demis27@demis27.net\n";


        Assert.assertEquals("test-text", emailMessage.getSubject());
        Assert.assertNull(emailMessage.getHtmlPart());
        Assert.assertNotNull(emailMessage.getTextPart());
        Assert.assertEquals(emailMessage.getTextPart(), expectedContent);

        Assert.assertNotNull(emailMessage.getHeader("From"));
        Assert.assertEquals(emailMessage.getHeader("From").getValue(), "Stéphane Kermabon <demis27@gmail.com>");

        Assert.assertEquals(emailMessage.getRecipients().size(), 1);
        Assert.assertEquals(emailMessage.getFrom().getEmail(), "demis27@gmail.com");
        Assert.assertEquals(emailMessage.getFrom().getName(), "Stéphane Kermabon");
        Assert.assertEquals(emailMessage.getRecipients("to").get(0).getEmail(), "dwarf-test-extern@demis27.net");
    }

    @Test
    public void textMessageWithoutAccent() throws IOException {

        BinaryMessage message = getMessage("rawemail-2.eml");
        EmailMessageParser parser = new EmailMessageParser();
        EmailMessage emailMessage = (EmailMessage)(parser.parse(null, message));
        Assert.assertEquals("test text sans accents", emailMessage.getSubject());
        Assert.assertNull(emailMessage.getHtmlPart());
        Assert.assertNotNull(emailMessage.getTextPart());

        Assert.assertEquals(1, emailMessage.getRecipients().size());
        Assert.assertEquals("demis27@gmail.com", emailMessage.getFrom().getEmail());
        Assert.assertEquals("Stéphane Kermabon", emailMessage.getFrom().getName());
        Assert.assertEquals("dwarf-test-extern@demis27.net", emailMessage.getRecipients("to").get(0).getEmail());
    }

    @Test
    public void textAndHtmlMessageWithoutAccent() throws IOException {

        BinaryMessage message = getMessage("rawemail-3.eml");
        EmailMessageParser parser = new EmailMessageParser();
        EmailMessage emailMessage = (EmailMessage)(parser.parse(null, message));
        Assert.assertEquals("test html sans accent", emailMessage.getSubject());
        Assert.assertNotNull(emailMessage.getHtmlPart());
        Assert.assertNotNull(emailMessage.getTextPart());

        Assert.assertEquals(1, emailMessage.getRecipients().size());
        Assert.assertEquals("demis27@gmail.com", emailMessage.getFrom().getEmail());
        Assert.assertEquals("Stéphane Kermabon", emailMessage.getFrom().getName());
        Assert.assertEquals("dwarf-test-extern@demis27.net", emailMessage.getRecipients("to").get(0).getEmail());
    }
}
