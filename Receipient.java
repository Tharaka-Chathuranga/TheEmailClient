import java.util.ArrayList;
import java.util.List;

public abstract class Receipient {
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
