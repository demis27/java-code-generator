package org.demis.darempredou.message.email;

import org.apache.commons.lang.StringUtils;
import org.demis.darempredou.message.email.helper.EmailParserHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class EmailPart {

    private static final Logger logger = LoggerFactory.getLogger(EmailPart.class);

    private String contentType = null;

    private String charset = null;

    private String contentDisposition = null;

    private String filename = null;

    private String contentTransferEncoding = null;

    private List<EmailPart> parts = new ArrayList<EmailPart>();

    private EmailPart parent = null;

    private String boundary = "no boundary id";

    private List<String> contents = new ArrayList<String>();

    private String content = null;

    private String contentId = null;

    private List<EmailHeader> headers = new ArrayList<EmailHeader>();

    public EmailPart() {
        // no op
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getContentDisposition() {
        return contentDisposition;
    }

    public void setContentDisposition(String contentDisposition) {
        this.contentDisposition = contentDisposition;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContentTransferEncoding() {
        return contentTransferEncoding;
    }

    public void setContentTransferEncoding(String contentTransferEncoding) {
        this.contentTransferEncoding = contentTransferEncoding;
    }

    public List<EmailPart> getParts() {
        return parts;
    }

    public void setParts(List<EmailPart> parts) {
        this.parts = parts;
    }

    public void addPart(EmailPart part) {
        parts.add(part);
        part.setParent(this);
    }

    public EmailPart getParent() {
        return parent;
    }

    public void setParent(EmailPart parent) {
        this.parent = parent;
    }

    public String getBoundary() {
        return boundary;
    }

    public void setBoundary(String boundary) {
        this.boundary = boundary;
    }

    public List<String> getContents() {
        return contents;
    }

    public void setContents(List<String> contents) {
        this.contents = contents;
    }

    public void addContent(String content) {
        contents.add(content);
    }

    public String getContent() {
        if (StringUtils.isEmpty(content)) {
            processContent();
        }
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
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

    public boolean isMultipart() {
        return contentType != null && contentType.startsWith("multipart");
    }

    public boolean isMultipartAlternative() {
        return contentType != null && contentType.startsWith("multipart/alternative");
    }

    public void processContentDisposition(String contentDispositionLine) {
        if (contentDispositionLine.indexOf(";") > 0) {
            contentDisposition = contentDispositionLine.substring(contentDispositionLine.indexOf(":") + 1, contentDispositionLine.indexOf(";")).trim();
            if (contentDisposition.equals("attachment") && contentDispositionLine.indexOf("filename=") >= 0) {
                filename = contentDispositionLine.substring(contentDispositionLine.indexOf("=") + 1).trim();
                filename = EmailParserHelper.decodeAllNonASCIIHeaderValue(filename.replaceAll("\"", "").replace(";", ""));
            }
            logger.debug("processContentDisposition : contentDisposition " + contentDisposition + " , filename " + filename + " of " + contentDispositionLine);
        }
        else {
            contentDisposition = contentDispositionLine.substring(contentDispositionLine.indexOf(":")).trim();
            logger.debug("processContentDisposition : contentDisposition " + contentDisposition + " of " + contentDispositionLine);
        }
    }

    public void processContent() {
        StringBuffer result = new StringBuffer("");
        for (String contentLine : contents) {
            if (contentTransferEncoding.equals("quoted-printable")) {
                if (contentLine.length() > 0 &&  contentLine.charAt(contentLine.length()-1) == '=') {
                    result.append(contentLine);
                }
                else {
                    result.append(contentLine);
                    result.append("\n");
                }
            }
            else {
                result.append(contentLine);
                result.append("\n");
            }
        }
        content = result.toString();
    }
}
