import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class File_handling {
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
                e.printStackTrace();
            }
        }
    
}
}
