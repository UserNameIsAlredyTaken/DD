import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * Created by danil on 30.05.2018.
 */
public class Utils {
    public static int getFieldIndex(DBFReader reader, String fieldName) throws DBFException{
        DBFField field = null;
        for(int i=0; i<reader.getFieldCount(); i++) {
            field = reader.getField(i);
            if (field.getName().equals(fieldName)) {
                return i;
            }
        }
        return -1;
    }

    public static void writeTheRow(DBFReader reader, Writer writer) throws DBFException, IOException {
        Object[] rowObjects;
        while((rowObjects = reader.nextRecord()) != null) {
            for(int i=0; i<rowObjects.length-1; i++) {
                writer.append(rowObjects[i] + ",");
            }
            writer.append(rowObjects[rowObjects.length-1]+"");
            writer.append(System.getProperty("line.separator"));
        }
    }

    public static void writeTheRow(DBFReader reader, Writer writer, int fieldIndex, String value) throws DBFException, IOException {
        Object []rowObjects;
        while((rowObjects = reader.nextRecord()) != null) {
            if(rowObjects[fieldIndex].toString().contains(value)){
                for(int i=0; i<rowObjects.length-1; i++) {
                    writer.append(rowObjects[i] + ",");
                }
                writer.append(rowObjects[rowObjects.length-1]+"");
                writer.append(System.getProperty("line.separator"));
            }
        }
    }
}
