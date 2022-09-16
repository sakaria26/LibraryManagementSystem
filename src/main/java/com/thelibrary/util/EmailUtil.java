package com.thelibrary.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

public class EmailUtil {
    private String RecipientEmail;
    private String RecipientName;
    private String RecipientUserID;
    private String RecipientPassword;
    private final String SenderEmail = "dasbiblioteck@gmail.com";
    private final String SenderPassword = "Cv,.01fd";
    private String MediaName;
    private final Properties Properties = System.getProperties();

    public String getRecipientEmail() {
        return this.RecipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.RecipientEmail = recipientEmail;
    }

    public String getRecipientName() {
        return this.RecipientName;
    }

    public void setRecipientName(String recipientName) {
        RecipientName = recipientName;
    }

    public String getRecipientUserID() {
        return RecipientUserID;
    }

    public void setRecipientUserID(String recipientUserID) {
        RecipientUserID = recipientUserID;
    }

    public String getRecipientPassword() {
        return RecipientPassword;
    }

    public void setRecipientPassword(String recipientPassword) {
        RecipientPassword = recipientPassword;
    }

    public String getMediaName() {
        return MediaName;
    }

    public void setMediaName(String mediaName) {
        MediaName = mediaName;
    }

    private String buildEmailBody(){
        return MessageFormat.format(" <h1>Dear {0}</h1>\n" +
                "    <p>Thank you for being part of The Library</p>\n" +
                "    <p>Attached is a copy of {1}</p>\n" +
                "    <p>Happy Reading</p>", this.RecipientName, this.MediaName);
    }

    private String buildAccountCreationEmailBody(){
        return MessageFormat.format(" <h1>Dear {0}</h1>\n" +
                "    <p>Thank you for being part of The Library</p>\n" +
                "    <p>Please log in with the following credentials : </p>\n" +
                "    <p>Username: {1}</p>\n"+
                "     <p>Password: {2}</p>\n"+
                "     <p>Please Change your password once logged in</p>\n"+
                "     <p>Kind Regards</p>\n"+
                "     <p>The Library</p>\n", this.RecipientName, this.RecipientUserID, this.RecipientPassword);
    }

    private String buildAccountRejectionEmailBody(){
        return MessageFormat.format(" <h1>Dear {0}</h1>\n" +
                "    <p>Thank you for being interested in The Library</p>\n" +
                "    <p>We have choosen to reject your membership </p>\n" +
                "    <p>Please note that this is due to our policies</p>\n"+
                "     <p>To open your account please contact The Library</p>\n"+
                "     <p>Kind Regards</p>\n"+
                "     <p>The Library</p>\n", this.RecipientName);
    }

    private void setMailServerProperties(){
        String mailHost = "smtp.google.com";
        this.Properties.put("mail.smtp.host", mailHost);
        this.Properties.put("mail.smtp.port", "465");
        this.Properties.put("mail.smtp.ssl.enable", "true");
        this.Properties.put("mail.smtp.auth", "true");
    }




    public void sendMediaEmail(File media){

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("dasbiblioteck@gmail.com", "Cv,.01fd");
            }
        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(this.SenderEmail));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(this.RecipientEmail));

            // Set Subject: header field
            message.setSubject("Requested Media");

            Multipart multipart = new MimeMultipart();
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            MimeBodyPart textPart = new MimeBodyPart();
            try {
                mimeBodyPart.attachFile(media);
                textPart.setContent(buildEmailBody(), "text/html");
                multipart.addBodyPart(textPart);
                multipart.addBodyPart(mimeBodyPart);
            }catch (IOException exception){
                exception.printStackTrace();
            }
            message.setContent(multipart);
            // Send message
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public void sendAccountCreationEmail(){

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("dasbiblioteck@gmail.com", "Cv,.01fd");
            }
        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(this.SenderEmail));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(this.RecipientEmail));

            // Set Subject: header field
            message.setSubject("Membership Approved");

            Multipart multipart = new MimeMultipart();
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            MimeBodyPart textPart = new MimeBodyPart();
            try {
                textPart.setContent(buildAccountCreationEmailBody(), "text/html");
                multipart.addBodyPart(textPart);
            }catch (Exception exception){
                exception.printStackTrace();
            }
            message.setContent(multipart);
            // Send message
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public void sendAccountRejectionEmail(){

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("dasbiblioteck@gmail.com", "Cv,.01fd");
            }
        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(this.SenderEmail));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(this.RecipientEmail));

            // Set Subject: header field
            message.setSubject("Membership Disabled");

            Multipart multipart = new MimeMultipart();
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            MimeBodyPart textPart = new MimeBodyPart();
            try {
                textPart.setContent(buildAccountRejectionEmailBody(), "text/html");
                multipart.addBodyPart(textPart);
            }catch (Exception exception){
                exception.printStackTrace();
            }
            message.setContent(multipart);
            // Send message
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
