public class Office_friend extends Receipient {
    
    public Office_friend(String name, String email, String designation, String  birthday){
        super(name, email);
        this.designation = designation;
        this.birthday =birthday;    
        }

    public String greeting(){
        return "Wish you a Happy Birthday. Tharaka";
     }

}
