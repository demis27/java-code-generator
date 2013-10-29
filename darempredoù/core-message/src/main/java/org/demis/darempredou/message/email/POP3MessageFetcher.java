package org.demis.darempredou.message.email;

import org.apache.commons.net.pop3.POP3Client;
import org.apache.commons.net.pop3.POP3MessageInfo;
import org.demis.darempredou.message.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLServerSocketFactory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.net.SocketException;
import java.util.Calendar;

public class POP3MessageFetcher extends EmailMessageFetcher {

    private final Logger logger = LoggerFactory.getLogger(POP3MessageFetcher.class);

    private POP3Client pop3;

    @Override
    public BinaryMessage[] fetch(MessageFetcherConfiguration configuration) throws MessageFetcherException {
        try {
            connectToServer((EmailFetcherConfiguration)configuration);
            login((EmailFetcherConfiguration)configuration);

            BinaryMessage[] messages = readMessages((EmailFetcherConfiguration)configuration);

            logout((EmailFetcherConfiguration)configuration);
            disconnectToServer((EmailFetcherConfiguration)configuration);

            return messages;
        }
        catch(MessageFetcherException ex) {
            throw new MessageFetcherException(ex.getMessage(), ex.getCause(), ex.getCode());
        }
        finally {
            pop3 = null;
        }
    }

    @Override
    public void delete(MessageFetcherConfiguration configuration, int id) throws MessageFetcherException {
        try {
            connectToServer((EmailFetcherConfiguration)configuration);
            login((EmailFetcherConfiguration)configuration);

            deleteMessage((EmailFetcherConfiguration)configuration, id);

            logout((EmailFetcherConfiguration)configuration);
            disconnectToServer((EmailFetcherConfiguration)configuration);

        }
        catch(MessageFetcherException ex) {
            throw new MessageFetcherException(ex.getMessage(), ex.getCause(), ex.getCode());
        }
        finally {
            pop3 = null;
        }
    }

    private void connectToServer(EmailFetcherConfiguration configuration) throws MessageFetcherException {
        if (configuration.getMailbox() == null || configuration.getMailServer() == null || configuration.getMailServer().getHost() == null) {
            logger.error("no mail server and mailbox are configure");
            throw new MessageFetcherException(MessageFetcherExceptionCode.BAD_FETCHER_CONFIGURATION);
        }

        pop3 = new POP3Client();
        pop3.setServerSocketFactory(SSLServerSocketFactory.getDefault());
        pop3.setDefaultTimeout(60000);
        try {
            pop3.connect(configuration.getMailServer().getHost(), configuration.getMailServer().getPort());
        } catch (SocketException ex) {
            logger.error("Error when try to connect to Pop3 server " + configuration.getMailServer().toString());
            pop3 = null;
            throw new MessageFetcherException(ex, MessageFetcherExceptionCode.SERVER_CONNECTION_FAILED);
        } catch (IOException ex) {
            logger.error("Error when try to connect to Pop3 server " + configuration.getMailServer().toString());
            pop3 = null;
            throw new MessageFetcherException(ex, MessageFetcherExceptionCode.SERVER_CONNECTION_FAILED);
        }
        logger.info("Connect to POP3 server " + configuration.getMailServer().getHost());
    }

    private void deleteMessage(EmailFetcherConfiguration configuration, int id) throws MessageFetcherException {
        try {
            pop3.deleteMessage(id);
        } catch (IOException ex) {
            logger.error("Error when try to delete message #" + id);
            throw new MessageFetcherException(ex, MessageFetcherExceptionCode.SERVER_CONNECTION_FAILED);
        }
    }

    private void login(EmailFetcherConfiguration configuration) throws MessageFetcherException {
        if (pop3 == null) {
            logger.error("Error you are not connected to pop3 server " + configuration.getMailServer().toString());
            throw new MessageFetcherException(MessageFetcherExceptionCode.NOT_CONNECTED);
        }
        try {
            pop3.login(configuration.getMailbox().getLogin(), configuration.getMailbox().getPassword());
        } catch (IOException ex) {
            logger.error("Error when try to login to mailbox " + configuration.getMailbox().toString());
            throw new MessageFetcherException(ex, MessageFetcherExceptionCode.MAILBOX_LOGIN_ERROR);
        }
        logger.info("Login to POP3 server " + configuration.getMailServer().getHost() + " with login " + configuration.getMailbox().getLogin());
    }

    private synchronized BinaryMessage[] readMessages(EmailFetcherConfiguration configuration) throws MessageFetcherException {
        if (pop3 == null) {
            logger.error("Error you are not connected to pop3 server " + configuration.getMailServer().toString());
            throw new MessageFetcherException(MessageFetcherExceptionCode.NOT_CONNECTED);
        }
        BinaryMessage[] messagesResult = null;
        try {
            POP3MessageInfo[] messages = pop3.listMessages();
            if (messages != null) {
                messagesResult = new BinaryMessage[messages.length];
                logger.info("They are " + messages.length + " message(s) on configuration.getMailbox() ");

                for (int i = 0; i < Math.min(configuration.getMaxFetchedMessage(), messages.length); i++) {
                    POP3MessageInfo message = messages[i];
                    logger.debug("Reading message " + message.number + " on configuration.getMailbox() ");
                    Reader reader = pop3.retrieveMessage(message.number);
                    ByteArrayOutputStream output = new ByteArrayOutputStream();
                    char[] buffer = new char[4096];
                    int read = 0;
                    while ((read = reader.read(buffer)) >= 0) {
                        String readString = new String(buffer, 0, read);
                        output.write(readString.getBytes());
                        logger.trace("read " + read + " chars : " + readString);
                    }
                    reader.close();
                    output.close();
                    BinaryMessage messageResult = new BinaryMessageImpl();
                    messageResult.setBinaryContent(output.toByteArray());
                    Calendar calendar = Calendar.getInstance();
                    messageResult.setReceivedTime(calendar.getTimeInMillis());
                    messageResult.setId(message.number);
                    messagesResult[message.number - 1] = messageResult;
                }
                if (configuration.isDeleteAfterFetch()) {
                    for (POP3MessageInfo message : messages) {
                        logger.debug("Deleting message " + message.number + " on configuration.getMailbox() ");
                        pop3.deleteMessage(message.number);
                    }
                }
            }
            else {
                logger.info("They are no message on configuration.getMailbox() ");
            }

        } catch (IOException ex) {
            logger.error("Error when try to read mailbox messages " + configuration.getMailbox().toString());
            throw new MessageFetcherException(ex, MessageFetcherExceptionCode.MAILBOX_LOGIN_ERROR);
        }
        return messagesResult;
    }

    private void logout(EmailFetcherConfiguration configuration) throws MessageFetcherException {
        if (pop3 == null) {
            logger.error("Error you are not connected to pop3 server " + configuration.getMailServer().toString());
            throw new MessageFetcherException(MessageFetcherExceptionCode.NOT_CONNECTED);
        }
        try {
            pop3.logout();
        } catch (IOException ex) {
            logger.error("Error when try to logout mailserver " + configuration.getMailServer().toString());
            throw new MessageFetcherException(ex, MessageFetcherExceptionCode.MAILBOX_LOGOUT_ERROR);
        }
        logger.info("Logout to POP3 server " + configuration.getMailServer().getHost() + " with login " + configuration.getMailbox().getLogin());
    }

    private void disconnectToServer(EmailFetcherConfiguration configuration) throws MessageFetcherException {
        if (pop3 == null) {
            logger.error("Error you are not connected to pop3 server " + configuration.getMailServer().toString());
            throw new MessageFetcherException(MessageFetcherExceptionCode.NOT_CONNECTED);
        }
        try {
            pop3.disconnect();
            pop3 = null;
        } catch (IOException ex) {
            logger.error("Error when try to disconnect to mailserver " + configuration.getMailServer().toString());
            throw new MessageFetcherException(ex, MessageFetcherExceptionCode.SERVER_DISCONNECTION_FAILED);
        }
        logger.info("Disconnect to POP3 server " + configuration.getMailServer().getHost());

    }
}
