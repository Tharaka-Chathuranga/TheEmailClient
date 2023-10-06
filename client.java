import java.util.Scanner;
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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import javax.mail.*;
import javax.mail.internet.*;

class EmailClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        File_handling.read_recepient();
        boolean bool = true;
        Email.deserialization();
        Birthday.sendGreetings();
        
        System.out.println("Enter option type: \n"
        + "1 - Adding a new recipient\n"
        + "2 - Sending an email\n"
        + "3 - Printing out all the recipients who have birthdays\n"
        + "4 - Printing out details of all the emails sent\n"
        + "5 - Printing out the number of recipient objects in the application\n"
        + "6 - Exit");
        try{    
            while(bool){
                System.out.print("Enter Option:");
                String option = scanner.nextLine();
                switch(option){
                    case "1":
                        RecepientFactory rFac = RecepientFactory.create_RecepientFactory();
                        //System.out.println("option1");
                        //scanner.nextLine();
                        System.out.println("          Official: name, email, designation \n"
                                            + "          Office_friend: name, email, designation, birthday \n"
                                            + "          Personal: name, email, birthday");
                        String rep = scanner.nextLine();
                        boolean done = false;
                        try {
                            done = rFac.add_receipient(rep);
                        } catch (Exception e) {
                            System.out.println("Wrong Birthday");
                        
                        }
                        if (done){
                            File_handling.write_recepient(rep);
                        }

                    
                        break;
                    case "2":
                        System.out.println("Enter email, subject, content in separate lines");
                        //scanner.nextLine();
                        String email = scanner.nextLine();
                        String subject = scanner.nextLine();
                        String content = scanner.nextLine();
                        //System.out.println(email+" "+subject+" "+content); 
                        Email emailobj = new Email(email, subject, content);
                        emailobj.sendEmail();
                        
                    break;
                    case "3":
                        System.out.println("Input date to get birthdays (MM-DD)");
                        //scanner.nextLine();
                        String date = scanner.nextLine();
                        Birthday.printName(date);


                    break;
                    case "4":
                        System.out.println("Input date to get details (YYYY-MM-DD)");
                        //scanner.nextLine();
                        String dateSent = scanner.nextLine();
                        Email.getemail(dateSent);
                        break;

                    case "5":
                        System.out.println(Receipient.get_count());
                        break;

                    case "6":
                        bool = false;
                        Email.serialization();
                        break;
                    
                    case default:
                        System.out.println("Wrong Option");
                }
                //scanner.nextLine();
            }
            scanner.close();

        }catch (Exception e){
            Email.serialization();
        }
    }
}



class Email implements Serializable{
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


class Birthday {
    private final static HashMap<String, List<Receipient>> birthday_lst = new HashMap<>();
    private static List<Receipient> temp_Lst;
    
    
    public static void add_birthday(Receipient receipient){
        String key = receipient.get_birthday().substring(5);
        boolean found = birthday_lst.containsKey(key);

        if (found){
            temp_Lst = birthday_lst.get(key);
            temp_Lst.add(receipient);
            birthday_lst.put(key,temp_Lst);    
        }

        else{
            temp_Lst = new ArrayList<>();
            temp_Lst.add(receipient);
            birthday_lst.put(key,temp_Lst );
    }
}

    public static void printName(String date){
        temp_Lst = birthday_lst.get(date);
        if (temp_Lst==null){
            System.out.println("Don't have birthday on "+date);
        }
        else{
            for(Receipient i: temp_Lst){
                System.out.println("Name : "+i.name);
            }
        }
}
    public static void sendGreetings(){
        String today = ((LocalDate.now()).toString()).substring(5);
        temp_Lst = birthday_lst.get(today);
        if (temp_Lst == null){return;}
        for (Receipient r:temp_Lst){
            (new Email(r.get_email(), "Greetings", r.greeting())).sendEmail();;
        }
    }
}

abstract class Receipient {
    String name;
    protected String email;
    protected String designation;
    protected String birthday;
    private static int count=0;
    private static List<Receipient> receipient_List = new ArrayList<>();

    public Receipient(String name, String email){
        this.name = name ;
        this.email =email;
        count++;
        receipient_List.add(this);        
    }
    public String get_birthday(){
        return this.birthday;
    }
    public static int  get_count() {
        return count;
        
    }

    public String get_email() {
        return this.email;
        
    }

    public abstract String greeting();
}

class Official extends Receipient {
    
    public Official(String name, String email, String designation){
        super(name, email);
        this.designation = designation;
     }

     public String greeting(){return null;}
}

class Office_friend extends Receipient {
    
    public Office_friend(String name, String email, String designation, String  birthday){
        super(name, email);
        this.designation = designation;
        this.birthday =birthday;    
        }

    public String greeting(){
        return "Wish you a Happy Birthday. Tharaka";
     }

}

class Personal extends Receipient{
    public Personal(String name, String email, String birthday){
        super(name,email);
        this.birthday = birthday;
    }

    public String greeting(){
        return "Hugs and love on your birthday. Tharaka";
    }
}

class RecepientFactory {
    private static RecepientFactory repfactory;

    
    private RecepientFactory() {
    }
    
    public static RecepientFactory create_RecepientFactory(){
        if ( repfactory == null){
            repfactory = new RecepientFactory();        
        }
        return repfactory;
    }

    public boolean add_receipient(String input) throws Exception {

        String[] result = input.split("[:,]");

        for(int i=0; i< result.length; i++){
            result[i] = result[i].strip();
        }
            
            if(result[0].equalsIgnoreCase("Official")){
                new Official(result[1], result[2], result[3]);
                
                //System.out.println("official rep added");
                }
            else if(result[0].equalsIgnoreCase("Personal")){
                LocalDate.parse(result[3]);
                Receipient R1 = new Personal(result[1], result[2], result[3]) ;
                Birthday.add_birthday(R1);
                //System.out.println("personal added");
            }
            
            else if(result[0].equalsIgnoreCase("Office_friend")){
                LocalDate.parse(result[4]);
                Receipient R2 = new Office_friend(result[1], result[2], result[3], result[4]);
                Birthday.add_birthday(R2);
                //System.out.println("office friend added");
            }
            else{
                System.out.println("Wrong format");
                return false;
            }
            return true;
    }
}



class File_handling {
    private static File receipient_file= new File("clientList.txt");
    private static FileWriter fileWriter;
    private static BufferedWriter bufferwriter;
    private static BufferedReader bufferreader;
    private static FileReader fileReader;
    

    public static void write_recepient(String text){
        
        try {
            receipient_file.createNewFile();
            fileWriter = new FileWriter(receipient_file, true);
            bufferwriter = new BufferedWriter(fileWriter);
            bufferwriter.append(text);
            
            bufferwriter.newLine();
            bufferwriter.close();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("File writting error");
            e.printStackTrace();
        } finally{
            try {
                bufferwriter.close();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Warning");
                e.printStackTrace();
            }
            
        }    
    }

    static void read_recepient(){
        
        try{
            receipient_file.createNewFile();
            fileReader = new FileReader(receipient_file);
            
            bufferreader = new BufferedReader(fileReader);
            String curLine = bufferreader.readLine();
            RecepientFactory rFac = RecepientFactory.create_RecepientFactory();
    
            //Read line by line
            while (curLine != null){
                rFac.add_receipient(curLine);
                curLine = bufferreader.readLine();
            }

            
        } catch (Exception ignore){
            System.out.println("File reading error");
        } finally{
            try {
                bufferreader.close();
            } catch (IOException e) {
                System.out.println("Warning");
                //e.printStackTrace();
            }
        }
    }
}
