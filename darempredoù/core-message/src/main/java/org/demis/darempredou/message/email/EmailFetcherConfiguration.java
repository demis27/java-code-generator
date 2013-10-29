package org.demis.darempredou.message.email;

import org.demis.darempredou.domain.MailServer;
import org.demis.darempredou.domain.Mailbox;
import org.demis.darempredou.message.MessageFetcherConfiguration;


public class EmailFetcherConfiguration implements MessageFetcherConfiguration {

    private MailServer mailServer = null;

    private Mailbox mailbox = null;

    private boolean deleteAfterFetch = false;

    private int maxFetchedMessage = 100;

    public EmailFetcherConfiguration() {
        // no op
    }

    public EmailFetcherConfiguration(MailServer server, Mailbox mailbox) {
        this.mailServer = server;
        this.mailbox = mailbox;
    }

    public MailServer getMailServer() {
        return mailServer;
    }

    public void setMailServer(MailServer mailServer) {
        this.mailServer = mailServer;
    }

    public Mailbox getMailbox() {
        return mailbox;
    }

    public void setMailbox(Mailbox mailbox) {
        this.mailbox = mailbox;
    }

    public boolean isDeleteAfterFetch() {
        return deleteAfterFetch;
    }

    public void setDeleteAfterFetch(boolean deleteAfterFetch) {
        this.deleteAfterFetch = deleteAfterFetch;
    }

    public int getMaxFetchedMessage() {
        return maxFetchedMessage;
    }

    public void setMaxFetchedMessage(int maxFetchedMessage) {
        this.maxFetchedMessage = maxFetchedMessage;
    }
}
