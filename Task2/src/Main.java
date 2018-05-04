import java.io.File;

public class Main {

    public static void main(String[] args) {
        String[] content = new File("C:\\Users\\danil\\Desktop\\DD").list();
        for(int i = 0; i < content.length; i++){
            System.out.println(content[i]);
        }
    }
}
