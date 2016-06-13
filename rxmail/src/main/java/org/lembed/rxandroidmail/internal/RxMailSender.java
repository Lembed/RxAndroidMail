package org.lembed.rxandroidmail.internal;

import java.security.Security;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class RxMailSender extends javax.mail.Authenticator {
    private Session session;
    private Multipart _multipart;
    private RxMailBuilder rxMailBuilder;

    static {
        Security.addProvider(new JSSEProvider());
    }

    public RxMailSender(RxMailBuilder builder) {
        this.rxMailBuilder = builder;
        _multipart = new MimeMultipart("related");

    }

    public RxMailSender build() {
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", rxMailBuilder.getHost());
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", rxMailBuilder.getPort());
        props.put("mail.smtp.socketFactory.port", rxMailBuilder.getPort());
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.quitwait", "false");
        props.setProperty("mail.smtp.auth", "true");

        session = Session.getDefaultInstance(props, this);
        return this;
    }

    public void setRxMailBuilder(RxMailBuilder rxMailBuilder) {
        this.rxMailBuilder = rxMailBuilder;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(rxMailBuilder.getUsername(), rxMailBuilder.getPassword());
    }

    public synchronized void sendMail() throws Exception {
        MimeMessage message = new MimeMessage(session);
        DataHandler handler = new DataHandler(new ByteArrayDataSource(rxMailBuilder.getBody().getBytes(), rxMailBuilder.getType()));
        message.setFrom(new InternetAddress(rxMailBuilder.getUsername()));
        message.setSubject(rxMailBuilder.getSubject());

        message.setText(rxMailBuilder.getBody());
        message.setDataHandler(handler);
        if (_multipart.getCount() > 0) {
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(rxMailBuilder.getBody(), rxMailBuilder.getType());
            _multipart.addBodyPart(messageBodyPart);
            message.setContent(_multipart);
        }
        if (rxMailBuilder.getMailTo().indexOf(',') > 0)
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(rxMailBuilder.getMailTo()));
        else
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(rxMailBuilder.getMailTo()));
        Transport.send(message);
    }


    public void addAttachment(String filename) throws Exception {
        BodyPart messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(filename);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(filename);
        messageBodyPart.setHeader("Content-ID","<image>");

        _multipart.addBodyPart(messageBodyPart);
    }

}