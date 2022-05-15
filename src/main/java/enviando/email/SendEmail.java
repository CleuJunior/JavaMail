package enviando.email;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
}
