import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

public class Email implements Serializable{
    private String email;
    private String subject;
    private String content;
    private String date;
    private static HashMap<String, List<Email>> sentEmail = new HashMap<>();
    private static List<Email> email_Lst;
    private static File emailFile = new File("emailFile.ser");

    final String myEmail  = "kumara.200321m@gmail.com";
    final String password = "mfpxuclaalfzhvpy";
    final String host = "smtp.gmail.com";
    final String port = "587";


    public Email(String email, String subject, String content){
        this.email=email;
        this.subject=subject;
        this.content=content;
        this.date = (LocalDate.now()).toString();
    }
    
    public void sendEmail(){
 
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.ssl.trust", host);
        
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, password);
        }});
        
        MimeMessage message = new MimeMessage(session);
        
        try { 
            message.setFrom(new InternetAddress(myEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject(subject);
            message.setText(content); 
            
            Transport.send(message);
            System.out.println("Email sent successfully....");
            this.addEmailobj();
        } catch (MessagingException mex) {
            //mex.printStackTrace();
            System.out.println("-Messaging Error-");
        }
    }
    public void addEmailobj(){
        String key = this.date; 
        boolean found = sentEmail.containsKey(key);
    
        if (found){
            email_Lst = sentEmail.get(key);
            email_Lst.add(this);
            sentEmail.put(key,email_Lst);    
        }
    
        else{
            email_Lst = new ArrayList<>();
            email_Lst.add(this);
            sentEmail.put(key,email_Lst );
    }
    }

    public static void getemail(String date){
        email_Lst = sentEmail.get(date);
        if (email_Lst==null){
            System.out.println("haven't sent email on "+date);
        }
        else{
            for(Email i: email_Lst){
            System.out.println("Email : "+i.email);
            System.out.println("subject : "+i.subject);
            System.out.println();}
        }
    }

    public static  void serialization(){
        FileOutputStream outputstream = null;
        ObjectOutputStream outputobj = null;
        try {
            outputstream = new FileOutputStream(emailFile);
            outputobj =new ObjectOutputStream(outputstream);
            outputobj.writeObject(sentEmail);
        } catch (IOException e) {
            
            e.printStackTrace();
        }
        finally{
            try {
                outputstream.close();
                outputobj.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }

    }
    public static void deserialization(){
        ObjectInputStream inputobj=null;
        try {
            emailFile.createNewFile();
            inputobj =new ObjectInputStream(new FileInputStream(emailFile));
            Object x = inputobj.readObject();
            sentEmail = (HashMap<String, List<Email>>)x;
            } catch (IOException e) {
            //e.printStackTrace();
            }  catch (ClassNotFoundException x) {
                
            }   
            finally{
                try {
                    if (inputobj != null){
                        inputobj.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }
}
