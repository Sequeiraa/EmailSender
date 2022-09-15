import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.swing.*;

public class mailSender {

    public static void main(String [] args){

        String to="asfafas@gmail.com";//recipient mail
        final String user="google@gmail.com";//Sender mail
        final String password="vjkbbnqqsuygnvjy";//Password 16 digits

        //1) get the session object
        Properties p = new Properties();
        p.put("mail.smtp.host", "smtp.gmail.com");
        p.setProperty("mail.smtp.starttls.enable","true");
        p.put("mail.smtp.ssl.trust","smtp.gmail.com");
        p.setProperty("mail.smtp.port","587");
        p.setProperty("mail.smtp.user",user);
        p.setProperty("mail.smtp.auth","true");
        p.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getDefaultInstance(p,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user,password);
                    }
                });

        //2) compose message
        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject("Subjecto of the mail");

            //3) create MimeBodyPart object and set your message content
            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setText("Message");

            //4) create new MimeBodyPart object and set DataHandler object to this object
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();

            BodyPart attachment = new MimeBodyPart();
            attachment.setDataHandler(new DataHandler(new FileDataSource("direction of the file"))); //add the name of the file too
            attachment.setFileName("Name file");


            //5) create Multipart object and add MimeBodyPart objects to this object
            MimeMultipart allMessage = new MimeMultipart();
            allMessage.addBodyPart(messageBodyPart1);
            allMessage.addBodyPart(attachment);

            //6) set the multiplart object to the message object
            message.setContent(allMessage);

            //7) send message
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", user, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            JOptionPane.showMessageDialog(null,"Message send");
            //System.out.println("message sent");
        }catch (MessagingException ex) {ex.printStackTrace();}
    }
}
