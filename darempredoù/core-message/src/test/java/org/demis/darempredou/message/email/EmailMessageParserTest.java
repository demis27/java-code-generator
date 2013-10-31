package org.demis.darempredou.message.email;

import org.demis.darempredou.message.BinaryMessage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

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

    @Test
    public void testTextAndHtmlMessageWithAccent() throws IOException {

        BinaryMessage message = getMessage("rawemail-4.eml");
        EmailMessageParser parser = new EmailMessageParser();
        EmailMessage emailMessage = (EmailMessage)(parser.parse(null, message));
        Assert.assertEquals("test html avec accent", emailMessage.getSubject());
        Assert.assertNotNull(emailMessage.getHtmlPart());
        Assert.assertNotNull(emailMessage.getTextPart());

        Assert.assertEquals(1, emailMessage.getRecipients().size());
        Assert.assertEquals("demis27@gmail.com", emailMessage.getFrom().getEmail());
        Assert.assertEquals("Stéphane Kermabon", emailMessage.getFrom().getName());
        Assert.assertEquals("dwarf-test-extern@demis27.net", emailMessage.getRecipients("to").get(0).getEmail());
    }

    @Test
    public void testAttachment() throws IOException {

        BinaryMessage message = getMessage("rawemail-5.eml");
        EmailMessageParser parser = new EmailMessageParser();
        EmailMessage emailMessage = (EmailMessage)(parser.parse(null, message));
        Assert.assertEquals("test piece jointe text", emailMessage.getSubject());
        Assert.assertNotNull(emailMessage.getHtmlPart());
        Assert.assertNotNull(emailMessage.getTextPart());

        Assert.assertEquals(1, emailMessage.getRecipients().size());
        Assert.assertEquals("demis27@gmail.com", emailMessage.getFrom().getEmail());
        Assert.assertEquals("Stéphane Kermabon", emailMessage.getFrom().getName());
        Assert.assertEquals("dwarf-test-extern@demis27.net", emailMessage.getRecipients("to").get(0).getEmail());

        List<EmailAttachment> attachments = emailMessage.getAttachments();
        Assert.assertEquals(1, attachments.size());
        EmailAttachment attachment = attachments.get(0);

        Assert.assertEquals("refactor-todo.txt", attachment.getFileName());
        Assert.assertEquals(77, attachment.getFileSize());
    }

    @Test
    public void testHtmlAndTextFromYahoo() throws IOException {

        BinaryMessage message = getMessage("rawemail-6.eml");
        EmailMessageParser parser = new EmailMessageParser();
        EmailMessage emailMessage = (EmailMessage)(parser.parse(null, message));
        Assert.assertEquals("test yahoo html", emailMessage.getSubject());
        Assert.assertNotNull(emailMessage.getHtmlPart());
        Assert.assertNotNull(emailMessage.getTextPart());

        Assert.assertEquals(1, emailMessage.getRecipients().size());
        Assert.assertEquals("kermabon.stephane@yahoo.fr", emailMessage.getFrom().getEmail());
        Assert.assertEquals("Kermabon Stéphane", emailMessage.getFrom().getName());
        Assert.assertEquals("dwarf-test-extern@demis27.net", emailMessage.getRecipients("to").get(0).getEmail());
    }

    @Test
    public void testTextFromYahoo() throws IOException {

        BinaryMessage message = getMessage("rawemail-7.eml");
        EmailMessageParser parser = new EmailMessageParser();
        EmailMessage emailMessage = (EmailMessage)(parser.parse(null, message));
        Assert.assertEquals("test yahoo texte", emailMessage.getSubject());
        Assert.assertNull(emailMessage.getHtmlPart());
        Assert.assertNotNull(emailMessage.getTextPart());

        Assert.assertEquals(1, emailMessage.getRecipients().size());
        Assert.assertEquals("kermabon.stephane@yahoo.fr", emailMessage.getFrom().getEmail());
        Assert.assertEquals("Kermabon Stéphane", emailMessage.getFrom().getName());
        Assert.assertEquals("dwarf-test-extern@demis27.net", emailMessage.getRecipients("to").get(0).getEmail());
    }

    @Test
    public void testAttachmentFromYahoo() throws IOException {

        BinaryMessage message = getMessage("rawemail-8.eml");
        EmailMessageParser parser = new EmailMessageParser();
        EmailMessage emailMessage = (EmailMessage)(parser.parse(null, message));
        Assert.assertEquals("test yahoo piece jointe", emailMessage.getSubject());
        Assert.assertNotNull(emailMessage.getHtmlPart());
        Assert.assertNotNull(emailMessage.getTextPart());

        Assert.assertEquals(1, emailMessage.getRecipients().size());
        Assert.assertEquals("kermabon.stephane@yahoo.fr", emailMessage.getFrom().getEmail());
        Assert.assertEquals("Kermabon Stéphane", emailMessage.getFrom().getName());
        Assert.assertEquals("dwarf-test-extern@demis27.net", emailMessage.getRecipients("to").get(0).getEmail());

        List<EmailAttachment> attachments = emailMessage.getAttachments();
        Assert.assertEquals(2, attachments.size());

        EmailAttachment attachment = attachments.get(0);
        Assert.assertEquals("reponse-24830.txt", attachment.getFileName());
        Assert.assertEquals(12860, attachment.getFileSize());

        attachment = attachments.get(1);
        Assert.assertEquals("request_description.gif", attachment.getFileName());
        Assert.assertEquals(62748, attachment.getFileSize());
    }

    @Test
    public void testTextWithoutAccentByThunderbird() throws IOException {

        BinaryMessage message = getMessage("rawemail-9.eml");
        EmailMessageParser parser = new EmailMessageParser();
        EmailMessage emailMessage = (EmailMessage)(parser.parse(null, message));
        Assert.assertEquals("test thunderbird sans accent - html", emailMessage.getSubject());
        Assert.assertNull(emailMessage.getHtmlPart());
        Assert.assertNotNull(emailMessage.getTextPart());

        Assert.assertEquals(1, emailMessage.getRecipients().size());
        Assert.assertEquals("stephane@eptica.com", emailMessage.getFrom().getEmail());
        Assert.assertEquals("stephane kermabon", emailMessage.getFrom().getName());
        Assert.assertEquals("dwarf-test-extern@demis27.net", emailMessage.getRecipients("to").get(0).getEmail());
    }

    @Test
    public void testTextWithAccentByThunderbird() throws IOException {

        BinaryMessage message = getMessage("rawemail-10.eml");
        EmailMessageParser parser = new EmailMessageParser();
        EmailMessage emailMessage = (EmailMessage)(parser.parse(null, message));
        Assert.assertEquals("test thunderbird html - avec des accents", emailMessage.getSubject());
        Assert.assertNull(emailMessage.getHtmlPart());
        Assert.assertNotNull(emailMessage.getTextPart());

        Assert.assertEquals(1, emailMessage.getRecipients().size());
        Assert.assertEquals("stephane@eptica.com", emailMessage.getFrom().getEmail());
        Assert.assertEquals("stephane kermabon", emailMessage.getFrom().getName());
        Assert.assertEquals("dwarf-test-extern@demis27.net", emailMessage.getRecipients("to").get(0).getEmail());
    }

    @Test
    public void testTextByExchangeV6_5() throws IOException {

        BinaryMessage message = getMessage("rawemail-11.eml");
        EmailMessageParser parser = new EmailMessageParser();
        EmailMessage emailMessage = (EmailMessage)(parser.parse(null, message));
        Assert.assertEquals("Un bien bel objet", emailMessage.getSubject());
        Assert.assertNull(emailMessage.getHtmlPart());
        Assert.assertNotNull(emailMessage.getTextPart());

        Assert.assertEquals(1, emailMessage.getRecipients().size());
        Assert.assertEquals("pbanwarth@rayonnance.fr", emailMessage.getFrom().getEmail());
        Assert.assertEquals("Philippe Banwarth", emailMessage.getFrom().getName());
        Assert.assertEquals("dwarf-test-extern@demis27.net", emailMessage.getRecipients("to").get(0).getEmail());

    }

    @Test
    public void testTextAndHtmlByExchangeV6_5() throws IOException {

        BinaryMessage message = getMessage("rawemail-12.eml");
        EmailMessageParser parser = new EmailMessageParser();
        EmailMessage emailMessage = (EmailMessage)(parser.parse(null, message));
        Assert.assertEquals("Issue 0001886", emailMessage.getSubject());
        Assert.assertNotNull(emailMessage.getHtmlPart());
        Assert.assertNotNull(emailMessage.getTextPart());

        Assert.assertEquals(1, emailMessage.getRecipients().size());
        Assert.assertEquals("pbanwarth@rayonnance.fr", emailMessage.getFrom().getEmail());
        Assert.assertEquals("Philippe Banwarth", emailMessage.getFrom().getName());
        Assert.assertEquals("dwarf-test-extern@demis27.net", emailMessage.getRecipients("to").get(0).getEmail());
    }

    @Test
    public void testRichTextAndHtmlByExchangeV6_5() throws IOException {

        BinaryMessage message = getMessage("rawemail-13.eml");
        EmailMessageParser parser = new EmailMessageParser();
        EmailMessage emailMessage = (EmailMessage)(parser.parse(null, message));
        Assert.assertEquals("Du texte enrichi", emailMessage.getSubject());
        Assert.assertNotNull(emailMessage.getHtmlPart());
        Assert.assertNotNull(emailMessage.getTextPart());

        Assert.assertEquals(1, emailMessage.getRecipients().size());
        Assert.assertEquals("pbanwarth@rayonnance.fr", emailMessage.getFrom().getEmail());
        Assert.assertEquals("Philippe Banwarth", emailMessage.getFrom().getName());
        Assert.assertEquals("dwarf-test-extern@demis27.net", emailMessage.getRecipients("to").get(0).getEmail());
    }

    @Test
    public void testAttachmentByExchangeV6_5() throws IOException {

        BinaryMessage message = getMessage("rawemail-14.eml");
        EmailMessageParser parser = new EmailMessageParser();
        EmailMessage emailMessage = (EmailMessage)(parser.parse(null, message));
        Assert.assertEquals("Une moyenne pj", emailMessage.getSubject());
        Assert.assertNotNull(emailMessage.getHtmlPart());
        Assert.assertNotNull(emailMessage.getTextPart());

        Assert.assertEquals(1, emailMessage.getRecipients().size());
        Assert.assertEquals("pbanwarth@rayonnance.fr", emailMessage.getFrom().getEmail());
        Assert.assertEquals("Philippe Banwarth", emailMessage.getFrom().getName());
        Assert.assertEquals("dwarf-test-extern@demis27.net", emailMessage.getRecipients("to").get(0).getEmail());

        List<EmailAttachment> attachments = emailMessage.getAttachments();
        Assert.assertEquals(1, attachments.size());
        EmailAttachment attachment = attachments.get(0);
    }

    @Test
    public void testAttachmentByGandiMail() throws IOException {

        BinaryMessage message = getMessage("rawemail-15.eml");
        EmailMessageParser parser = new EmailMessageParser();
        EmailMessage emailMessage = (EmailMessage)(parser.parse(null, message));
        Assert.assertEquals("test deux pieces jointes", emailMessage.getSubject());
        Assert.assertNull(emailMessage.getHtmlPart());
        Assert.assertNotNull(emailMessage.getTextPart());
        Assert.assertTrue(emailMessage.getTextPart().indexOf("é à ï") > 0);

        Assert.assertEquals(1, emailMessage.getRecipients().size());
        Assert.assertEquals("dwarf-test@demis27.net", emailMessage.getFrom().getEmail());
        Assert.assertNull(emailMessage.getFrom().getName());
        Assert.assertEquals("dwarf-test-extern@demis27.net", emailMessage.getRecipients("to").get(0).getEmail());

        List<EmailAttachment> attachments = emailMessage.getAttachments();
        Assert.assertEquals(2, attachments.size());

        EmailAttachment attachment = attachments.get(0);
        Assert.assertEquals("add_logical_volume.txt", attachment.getFileName());
        Assert.assertEquals(166, attachment.getFileSize());

        attachment = attachments.get(1);
        Assert.assertEquals("LWC-application-conf.xml.zip", attachment.getFileName());
        Assert.assertEquals(6705, attachment.getFileSize());
    }

    @Test
    public void testAttachmentByRoundCubeWebmail() throws IOException {

        BinaryMessage message = getMessage("rawemail-20.eml");
        EmailMessageParser parser = new EmailMessageParser();
        EmailMessage emailMessage = (EmailMessage)(parser.parse(null, message));
        Assert.assertEquals(emailMessage.getSubject(), "[Sofinco] : Erreur lors d'une suppression de requête dans les requêtes archivées");
        Assert.assertNotNull(emailMessage.getHtmlPart());
        Assert.assertNotNull(emailMessage.getTextPart());

        Assert.assertEquals(1, emailMessage.getRecipients().size());
        Assert.assertEquals("timothee.lecoustre@ceritek.com", emailMessage.getFrom().getEmail());
        Assert.assertNull(emailMessage.getFrom().getName());
        Assert.assertEquals("support.fr@eptica.com", emailMessage.getRecipients("to").get(0).getEmail());

        List<EmailAttachment> attachments = emailMessage.getAttachments();
        Assert.assertEquals(1, attachments.size());

        EmailAttachment attachment = attachments.get(0);
        Assert.assertEquals("Copie de eptica-20090618-054525.zip", attachment.getFileName());
        Assert.assertEquals(56030, attachment.getFileSize());
        Assert.assertEquals("application/x-zip-compressed", attachment.getFileContentType());
    }

    @Test
    public void testHtmlByMicrosoftOfficeOutlook12() throws IOException {

        BinaryMessage message = getMessage("rawemail-30.eml");
        EmailMessageParser parser = new EmailMessageParser();
        EmailMessage emailMessage = (EmailMessage)(parser.parse(null, message));

        Assert.assertEquals(emailMessage.getSubject(), "[Eptica] [Sofinco] : Erreur lors d'une suppression de requête dans les requêtes archivées [29576-1245324752]");
        Assert.assertNotNull(emailMessage.getHtmlPart());
        Assert.assertNotNull(emailMessage.getTextPart());

        Assert.assertEquals("timothee.lecoustre@ceritek.com", emailMessage.getFrom().getEmail());
        Assert.assertEquals("Timothée Lecoustre", emailMessage.getFrom().getName());
        Assert.assertEquals("support.fr@eptica.com", emailMessage.getRecipients("to").get(0).getEmail());
        Assert.assertEquals("tdescroix@sofinco.fr", emailMessage.getRecipients("cc").get(0).getEmail());
    }

    @Test
    public void testTextAndHtmlByMicrosoftOfficeOutlook11() throws IOException {

        BinaryMessage message = getMessage("rawemail-31.eml");
        EmailMessageParser parser = new EmailMessageParser();
        EmailMessage emailMessage = (EmailMessage)(parser.parse(null, message));

        Assert.assertEquals(emailMessage.getSubject(), "[Decathlon] Notification par email du transfert d'une requête d'un agent à un autre agent");
        Assert.assertNotNull(emailMessage.getHtmlPart());
        Assert.assertNotNull(emailMessage.getTextPart());

        Assert.assertEquals("marc.vidal@eptica.com", emailMessage.getFrom().getEmail());
        Assert.assertEquals("Marc Vidal", emailMessage.getFrom().getName());
        Assert.assertEquals("support.fr@eptica.com", emailMessage.getRecipients("to").get(0).getEmail());
        Assert.assertEquals("'Support Eptica'", emailMessage.getRecipients("to").get(0).getName());
    }

    @Test
    public void testSubjectWithAccentAndUnderscoreByGmail() throws IOException {

        BinaryMessage message = getMessage("rawemail-40.eml");
        EmailMessageParser parser = new EmailMessageParser();
        EmailMessage emailMessage = (EmailMessage)(parser.parse(null, message));


        Assert.assertEquals("_sujet_avec_underscore_et_accent_é_", emailMessage.getSubject());
        Assert.assertNotNull(emailMessage.getHtmlPart());
        Assert.assertNotNull(emailMessage.getTextPart());

        Assert.assertEquals("demis27@gmail.com", emailMessage.getFrom().getEmail());
        Assert.assertEquals("Stéphane Kermabon",emailMessage.getFrom().getName());
        Assert.assertEquals("dwarf-test-extern@demis27.net", emailMessage.getRecipients("to").get(0).getEmail());
    }

    @Test
    public void testTextAndHtmlByTheBat() throws IOException {

        BinaryMessage message = getMessage("rawemail-50.eml");
        EmailMessageParser parser = new EmailMessageParser();
        EmailMessage emailMessage = (EmailMessage)(parser.parse(null, message));


        Assert.assertEquals("Shed weight now and enjoy the process", emailMessage.getSubject());
        Assert.assertNotNull(emailMessage.getHtmlPart());
        Assert.assertNotNull(emailMessage.getTextPart());

        Assert.assertEquals("miqejingsfew@ejings.com", emailMessage.getFrom().getEmail());
        Assert.assertEquals("Michelle Babb", emailMessage.getFrom().getName());
        Assert.assertEquals("boutique@bayardweb.net", emailMessage.getRecipients("to").get(0).getEmail());
    }

    @Test
    public void testTextAndHtmlAndAttachmentByOutlookExpress6_0() throws IOException {

        BinaryMessage message = getMessage("rawemail-60.eml");
        EmailMessageParser parser = new EmailMessageParser();
        EmailMessage emailMessage = (EmailMessage)(parser.parse(null, message));

        Assert.assertEquals("Software At Low Pr1ce", emailMessage.getSubject());
        Assert.assertNotNull(emailMessage.getHtmlPart());
        Assert.assertNotNull(emailMessage.getTextPart());

        Assert.assertEquals("sadjebmusic.com@nemesystems.com", emailMessage.getFrom().getEmail());
        Assert.assertEquals("Orlando Young", emailMessage.getFrom().getName());
        Assert.assertEquals("boutique@bayardweb.net", emailMessage.getRecipients("to").get(0).getEmail());

        List<EmailAttachment> attachments = emailMessage.getAttachments();
        Assert.assertEquals(1, attachments.size());

        EmailAttachment attachment = attachments.get(0);
        Assert.assertEquals(13112, attachment.getFileSize());
        Assert.assertEquals("image/gif", attachment.getFileContentType());
        Assert.assertEquals("pic094.gif@07580871.63513118", attachment.getContentId());
    }

    @Test
    public void testTextByCaramail() throws IOException {

        BinaryMessage message = getMessage("rawemail-70.eml");
        EmailMessageParser parser = new EmailMessageParser();
        EmailMessage emailMessage = (EmailMessage)(parser.parse(null, message));
    }

    @Test
    public void testTextByMediaNetV2() throws IOException {

        BinaryMessage message = getMessage("rawemail-80.eml");
        EmailMessageParser parser = new EmailMessageParser();
        EmailMessage emailMessage = (EmailMessage)(parser.parse(null, message));

    }

    @Test
    public void testLotusNotes() throws IOException {

        BinaryMessage message = getMessage("rawemail-90.eml");
        EmailMessageParser parser = new EmailMessageParser();
        EmailMessage emailMessage = (EmailMessage)(parser.parse(null, message));

        Assert.assertEquals("Note de frais", emailMessage.getSubject());
        Assert.assertNotNull(emailMessage.getHtmlPart());
        Assert.assertNotNull(emailMessage.getTextPart());

        Assert.assertEquals("ghislaine.m.lunion@gsk.com", emailMessage.getFrom().getEmail());
        Assert.assertEquals("dwarf-test-extern@demis27.net", emailMessage.getRecipients("to").get(0).getEmail());

        List<EmailAttachment> attachments = emailMessage.getAttachments();
        Assert.assertEquals(2, attachments.size());

        EmailAttachment attachment = attachments.get(0);
        Assert.assertEquals("application/vnd.ms-excel", attachment.getFileContentType());
        Assert.assertEquals("feuille de congés.xls", attachment.getFileName());

        EmailAttachment attachment2 = attachments.get(1);
        Assert.assertEquals("application/vnd.ms-excel", attachment2.getFileContentType());
        Assert.assertEquals("Note de Frais.xls", attachment2.getFileName());
    }
}
