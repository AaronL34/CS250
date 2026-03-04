package cs250.exercises;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Exercise12 {
    public static void main(String[] args) {
        try(BufferedReader br = new BufferedReader(new FileReader(args[0]))){

            String line;
            while ((line = br.readLine()) !=null){
                String[] parts = line.split(",");
                if(parts.length>= 2){
                    String filepath = parts[0].trim();
                    String info = parts[1].trim();
                    to_csv(filepath, info);
                }
            }
            
        }catch(IOException e){

        }
        File file = new File(args[0]);
            if(file.exists()){
                file.delete();
                
            }
    }
    private static void to_csv(String filepath, String info) throws IOException{
        File file = new File(filepath);

        File parent = file.getParentFile();
        if(parent != null && !parent.exists()){
            parent.mkdirs();
        }

        try(FileWriter writer = new FileWriter(filepath)){
            writer.write(info);
        }
    }
}
