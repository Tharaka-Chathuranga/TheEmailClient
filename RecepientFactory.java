import java.time.LocalDate;

public class RecepientFactory {
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
