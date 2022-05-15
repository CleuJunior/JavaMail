package enviando.email;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SendEmailTest {

    @org.junit.jupiter.api.Test
    void testEmail()  throws Exception {

        /**
         * Omitting Email and Password
         */

        Path path = Paths.get("C:\\Users\\Desktop\\Documents\\email_pass.txt");

        String userEmail = Files.readAllLines(path).get(0);

        new SendEmail(
                userEmail,
                "Cleonildo Junior",
                "Email enviado atravez do JavaMail",
                "Olá, eu fui enviado atravez do JavaMail, o que achou? \n" +
                        "Olá, eu fui enviado atravez do JavaMail, o que achou? \n" +
                        "Olá, eu fui enviado atravez do JavaMail, o que achou?"

        ).emailSendTo(false);

        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append("Olá, <br/><br/>");
        stringBuffer.append("Testando Email com HTML, funcionou?<br/><br/>");

        stringBuffer.append("testando botao de link<br/><br/>");

        stringBuffer.append("<a target=\"_blank\" " +
                "href=\"https://mega.ibxk.com.br/2016/09/13/13183726350674.jpg?ims=610x\"" +
                " style=\"color: #2525A7; padding: 14px; text-align:center; " +
                "text-decoration:none; display: inline-block; border-radius: 30px; font-siz: 20px; font-family: courier; " +
                "border: 3px solid green;\">");


        stringBuffer.append("Consegue ver o gatinho?");

        new SendEmail(
                userEmail,
                "Cleonildo Junior",
                "Email enviado atravez do JavaMail",
                 stringBuffer.toString()

        ).emailSendTo(true);


        assertTrue(true);
    }
}