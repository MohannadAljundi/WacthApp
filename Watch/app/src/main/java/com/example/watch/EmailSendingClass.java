package com.example.watch;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSendingClass {
    public  void sendMail(String receipt , String Name) throws MessagingException {
        System.out.println("Preparing to send Email");



        Properties properties = new Properties();

        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");

        final String MyEmil = "watchproject66@gmail.com";
        final String Password = "watch2020";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MyEmil,Password);
            }
        });

        String StrName = Name;

        Message message = MyMessage(session,MyEmil,receipt , StrName);


        Transport.send(message);


        System.out.println("Email is Sent successful");



    }

    private static Message MyMessage(Session session, String myEmil, String recepint , String Name)  {
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmil));
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(recepint));
            message.setSubject("Email Verification .");
            message.setText("Hi "+Name+". \n , We are Studding You Request  , \n As Soon As Possible we Will Connect with you ." +
                    "\n Thank You For Using Watch App. \n " +
                    "Watch Family." );

            return message;

        }catch (Exception ex){
            Logger.getLogger(EmailSendingClass.class.getName()).log(Level.SEVERE,null,ex);
        }
        return null;
    }


}

