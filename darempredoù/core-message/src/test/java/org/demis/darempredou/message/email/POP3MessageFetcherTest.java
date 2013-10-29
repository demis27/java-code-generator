package org.demis.darempredou.message.email;

import org.demis.darempredou.domain.MailServer;
import org.demis.darempredou.domain.Mailbox;
import org.demis.darempredou.message.BinaryMessage;
import org.demis.darempredou.message.MessageFetcher;
import org.demis.darempredou.message.MessageFetcherException;
import org.junit.AfterClass;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class POP3MessageFetcherTest {

    private MailServer popServer = null;

    private Mailbox mailbox = null;

    private EmailFetcherConfiguration configuration = null;

    @BeforeClass
    protected void setUp() throws Exception {
        popServer = new MailServer();
        popServer.setHost("access.mail.gandi.net");
        popServer.setPort(110);
        popServer.setProtocol(1);

        mailbox = new Mailbox();
        mailbox.setLogin("darempredou@demis27.net");
        mailbox.setEmailAddress("darempredou@demis27.net");
        mailbox.setPassword("Boussois27&");

        configuration = new EmailFetcherConfiguration(popServer, mailbox);
    }

    @Test
    public void testSimpleFetch() throws MessageFetcherException {
        MessageFetcher fetcher = new POP3MessageFetcher();
        configuration.setDeleteAfterFetch(false);

        BinaryMessage[] messages = fetcher.fetch(configuration);
        Assert.assertNotNull(messages);
        Assert.assertEquals(1, messages.length);

        Assert.assertEquals(messages[0].getBinaryContent().length, 3104);
    }

    @AfterClass
    protected void tearDown() throws Exception {
    }
}
