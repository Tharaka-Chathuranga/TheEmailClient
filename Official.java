public class Official extends Receipient {
    
    public Official(String name, String email, String designation){
        super(name, email);
        this.designation = designation;
     }

     public String greeting(){return null;}
}
