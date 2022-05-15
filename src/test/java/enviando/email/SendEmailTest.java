package enviando.email;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
                "Olá, eu fui enviado atravez do JavaMail, o que achou?"

        );
    }
}