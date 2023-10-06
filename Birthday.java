import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Birthday {
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

