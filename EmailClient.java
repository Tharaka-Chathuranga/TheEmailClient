import java.util.Scanner;

public class EmailClient {
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


