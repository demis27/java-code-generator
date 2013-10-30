package org.demis.darempredou.message.email;

import org.apache.commons.lang.StringUtils;
import org.demis.darempredou.message.Message;

import java.util.ArrayList;
import java.util.List;

public class EmailMessage implements Message {

    private byte[] binaryContent = null;

    private EmailAddress from;

    private List<EmailRecipient> recipients = new ArrayList<EmailRecipient>();

    private String subject;

    private String htmlPart;

    private String textPart;

    private List<EmailAttachment> attachments = new ArrayList<EmailAttachment>();

    private List<EmailHeader> headers = new ArrayList<EmailHeader>();

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

    public List<EmailRecipient> getRecipients(String type) {
        if (StringUtils.isEmpty(type)) {
            return getRecipients();
        }
        ArrayList<EmailRecipient> result = new ArrayList<EmailRecipient>();
        for (EmailRecipient recipient: recipients) {
            if (type.equals(recipient.getType())) {
                result.add(recipient);
            }
        }
        return result;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getHtmlPart() {
        return htmlPart;
    }

    public void setHtmlPart(String htmlPart) {
        this.htmlPart = htmlPart;
    }

    public String getTextPart() {
        return textPart;
    }

    public void setTextPart(String textPart) {
        this.textPart = textPart;
    }

    public void addAttachment(EmailAttachment attachment) {
        attachments.add(attachment);
    }

    public List<EmailAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<EmailAttachment> attachments) {
        this.attachments = attachments;
    }

    public List<EmailHeader> getHeaders() {
        return headers;
    }

    public void setHeaders(List<EmailHeader> headers) {
        this.headers = headers;
    }

    public void addHeaders(List<EmailHeader> headers) {
        this.headers.addAll(headers);
    }

    public EmailHeader getHeader(String name) {
        if (name == null || name.trim().length() == 0 || headers == null || headers.size() == 0) {
            return null;
        }
        for (EmailHeader header: headers) {
            if (name.equals(header.getName())) {
                return header;
            }
        }
        return null;
    }
}
