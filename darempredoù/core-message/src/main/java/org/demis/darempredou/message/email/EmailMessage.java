package org.demis.darempredou.message.email;

import org.demis.darempredou.message.Message;

import java.util.ArrayList;
import java.util.List;

public class EmailMessage implements Message {

    private byte[] binaryContent = null;

    private EmailAddress from;

    private List<EmailRecipient> recipients = new ArrayList<EmailRecipient>();

    private String subject;

    public EmailMessage(byte[] binaryContent) {
        this.binaryContent = binaryContent;
    }

    public byte[] getBinaryContent() {
        return binaryContent;
    }

    public void setBinaryContent(byte[] binaryContent) {
        this.binaryContent = binaryContent;
    }

    public EmailAddress getFrom() {
        return from;
    }

    public void setFrom(EmailAddress from) {
        this.from = from;
    }

    public List<EmailRecipient> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<EmailRecipient> recipients) {
        this.recipients = recipients;
    }

    public void addRecipient(EmailRecipient recipient) {
        this.recipients.add(recipient);
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
