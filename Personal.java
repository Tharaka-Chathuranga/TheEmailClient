public class Personal extends Receipient{
    public Personal(String name, String email, String birthday){
        super(name,email);
        this.birthday = birthday;
    }

    public String greeting(){
        return "Hugs and love on your birthday. Tharaka";
    }
}