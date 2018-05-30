import com.

/**
 * Created by danil on 29.05.2018.
 */
public class Main {
    public static void main(String[] args) {
        String inPath = null, outPath = null, additionalArg = null;
        try{//TODO use args.length
            inPath = args[1];
            outPath = args[3];
            additionalArg = args[5];

            System.out.println(inPath);
            System.out.println(outPath);
            System.out.println(additionalArg);
        }catch(ArrayIndexOutOfBoundsException ex){
            if(inPath == null || outPath == null){
                System.out.println("Wrong args count");
            }else if(additionalArg == null){
                System.out.println(inPath);
                System.out.println(outPath);
            }
        }
    }
}
