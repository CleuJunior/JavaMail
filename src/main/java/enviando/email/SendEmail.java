package enviando.email;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class SendEmail {

    /**
     * Omitting Email and Password
     */
    private final Path path = Paths.get("C:\\Users\\Desktop\\Documents\\email_pass.txt");
    private final String userEmail = Files.readAllLines(path).get(0);
    private final String userPassword = Files.readAllLines(path).get(1);
    private String listOfRecipients = "";
    private String sender = "";
    private String emailSubject = "";
    private String textEmail = "";

    public SendEmail(String listOfRecipients, String sender, String emailSubject, String textEmail) throws IOException
    {
        this.listOfRecipients = listOfRecipients;
        this.sender = sender;
        this.emailSubject = emailSubject;
        this.textEmail = textEmail;
    }

    public void emailSendTo(boolean isHTML)
    {

        try {
            Properties properties = new Properties();

            properties.put("mail.smtp.ssl.trust", "*"); /* Authentication*/
            properties.put("mail.smtp.auth", "true"); /* Authorization */
            properties.put("mail.smtp.starttls", "true"); /* Authorization */
            properties.put("mail.smtp.host", "smtp.gmail.com"); /* Server Gmail */
            properties.put("mail.smtp.port", "465"); /* Server Port */
            properties.put("mail.smtp.socketFactory.port", "465"); /* Port Socket Connection */
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); /* Class Socket Connection SMTP */

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(userEmail, userPassword);
                }
            });

            Address[] toUser = InternetAddress.parse(listOfRecipients);
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(userEmail, sender)); /* Who Send */
            message.setRecipients(Message.RecipientType.TO, toUser); /* Who Receive */
            message.setSubject(emailSubject);

            if(isHTML) message.setContent(textEmail, "text/html; charset=utf-8");
            if(!isHTML) message.setText(textEmail);

            Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void emailSendToWithAttachment(boolean isHTML)
    {

        try {
            Properties properties = new Properties();

            properties.put("mail.smtp.ssl.trust", "*"); /* Authentication*/
            properties.put("mail.smtp.auth", "true"); /* Authorization */
            properties.put("mail.smtp.starttls", "true"); /* Authorization */
            properties.put("mail.smtp.host", "smtp.gmail.com"); /* Server Gmail */
            properties.put("mail.smtp.port", "465"); /* Server Port */
            properties.put("mail.smtp.socketFactory.port", "465"); /* Port Socket Connection */
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); /* Class Socket Connection SMTP */

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(userEmail, userPassword);
                }
            });

            Address[] toUser = InternetAddress.parse(listOfRecipients);
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(userEmail, sender)); /* Who Send */
            message.setRecipients(Message.RecipientType.TO, toUser); /* Who Receive */
            message.setSubject(emailSubject);

            /* Email text and description */
            MimeBodyPart emailBody = new MimeBodyPart();

            if(isHTML) emailBody.setContent(textEmail, "text/html; charset=utf-8");
            if(!isHTML) emailBody.setText(textEmail);

            /* Email Attachment */

            MimeBodyPart attachmentEmail = new MimeBodyPart();

            /* Simulator PDF */
            attachmentEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(simulatorPDF(), "application/pdf")));
            attachmentEmail.setFileName("attachments.pdf");


            Multipart multipart = new MimeMultipart();

            multipart.addBodyPart(emailBody);
            multipart.addBodyPart(attachmentEmail);

            message.setContent(multipart);

            Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }




    /**
     * Send Email attachment
     */
    private FileInputStream simulatorPDF() throws Exception
    {
        Document document = new Document();
        File file = new File("fileAttachment.pdf");
        file.createNewFile();

        PdfWriter.getInstance(document, new FileOutputStream(file));

        document.open();
        document.add(new Paragraph("PDF text with JavaMail, text PDF"));
        document.close();

        return new FileInputStream(file);
    }

}
