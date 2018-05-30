/**
 * Created by danil on 29.05.2018.
 */
import com.linuxense.javadbf.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.nio.file.Files.newBufferedWriter;

public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String inPath = null, outPath = null, column = null, value = null;
        try{
            if(args.length == 4){
                inPath = args[1];
                outPath = args[3];
            }else if(args.length == 7){
                inPath = args[1];
                outPath = args[3];
                column = args[5];
                value = args[6];
            }else{
                System.out.println("Wrong args count");
                System.exit(0);
            }

            InputStream inputStream  = new FileInputStream(inPath);
            DBFReader reader = new DBFReader(inputStream);

            //PrintWriter writer = new PrintWriter(outPath, "UTF-8");
            Writer writer = newBufferedWriter(Paths.get(outPath));

            if(args.length == 7){
                int fieldIndex = Utils.getFieldIndex(reader, column);
                if(fieldIndex == -1){
                    System.out.println("Wrong field name");
                    System.exit(0);
                }

                Utils.writeTheRow(reader,writer,fieldIndex,value);
            }else{
                Utils.writeTheRow(reader,writer);
            }
            writer.close();

        }catch (FileNotFoundException ex){
            System.out.println("Wrong path");
        }catch (DBFException dbfex){
            System.out.println("DBFException");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
