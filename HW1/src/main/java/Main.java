/**
 * Created by danil on 29.05.2018.
 */
import com.linuxense.javadbf.*;

import java.io.*;

public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String inPath = null, outPath = null, column = null, value = null;
        try{//TODO use args.length
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


            PrintWriter writer = new PrintWriter(outPath, "UTF-8");
            Object []rowObjects;
            if(args.length > 4){
                DBFField field = null;
                int fieldIndx = -1;
                for(int i=0; i<reader.getFieldCount(); i++) {
                    field = reader.getField(i);
                    if(field.getName().contains(column)){
                        fieldIndx = i;
                        break;
                    }
                }

                if(field == null){
                    System.out.println("Wrong field name");
                    System.exit(0);
                }


                while( (rowObjects = reader.nextRecord()) != null) {
                    if(rowObjects[fieldIndx].toString().contains(value)){
                        for(int i=0; i<rowObjects.length-1; i++) {
                            writer.print(rowObjects[i] + ",");
                        }
                        writer.print(rowObjects[rowObjects.length-1]);
                        writer.println();
                    }
                }
            }else{
                while((rowObjects = reader.nextRecord()) != null) {
                    for(int i=0; i<rowObjects.length-1; i++) {
                        writer.print(rowObjects[i] + ",");
                    }
                    writer.print(rowObjects[rowObjects.length-1]);
                    writer.println();
                }
            }
            writer.close();

        }catch (FileNotFoundException ex){
            System.out.println("Wrong path");
        }
    }
}
