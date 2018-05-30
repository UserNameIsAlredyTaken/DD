import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

/**
 * Создаём в рабочей директории файл the_past
 * Так как создание происходит лениво, то в действительности он создастся только когда в него будет что то записано
 * Проверяем, существует ли файл
 * Если да, то с помощью рекурсивной функции readDirectory получаем текущее состояние директории, читаем из the_past серелизованное в json старое состояние директории, сравниваем и выводим разницу
 * Если нет, то читаем текущее состояние директории, серелизуем его в json и сохраняем в the_past
 */
public class Main {

    private static HashMap<String,Node> readDirectory(File path) throws IOException{
        HashMap<String,Node> allNodes = new HashMap<String,Node>();
        String files[] = path.list();
        for(String file : files){
            File node = new File(path.getPath() + "\\" + file);
            if(node.isDirectory()){
                allNodes.putAll(readDirectory(node));
            }
            BasicFileAttributes view = Files.getFileAttributeView(Paths.get(node.getAbsolutePath()), BasicFileAttributeView.class).readAttributes();
            long whenCreated = view.creationTime().toMillis();
            long whenModified = view.lastModifiedTime().toMillis();
            allNodes.put(node.getPath(),new Node(node.getPath(),whenCreated,whenModified));
        }
        return allNodes;
    }

    private static void compare(HashMap<String,Node> newState, HashMap<String,Node> oldState, long whenStateUpdated){
        List<Node> removed = new ArrayList<>();
        for (Map.Entry<String, Node> entry : oldState.entrySet()) {
            if (newState.get(entry.getKey()) == null){
                removed.add(entry.getValue());
            } else {
                if (newState.get(entry.getKey()).getWhenModified() != entry.getValue().getWhenModified()) {
                    System.out.println("Modified " + entry.getKey());
                }
                newState.remove(entry.getKey());
            }
        }

        List<Node> needToRemove = new ArrayList<>();
        for (Map.Entry<String, Node> entry : newState.entrySet()) {
            if (entry.getValue().getWhenCreated() < whenStateUpdated){
                System.out.println("Moved " + entry.getKey());
                for (Node node : removed)
                    if(entry.getValue().getPath().contains(node.getPath().
                            substring(node.getPath().lastIndexOf("\\"), node.getPath().length())))
                        needToRemove.add(node);
            } else {
                System.out.println("Created " + entry.getKey());
            }
        }
        removed.removeAll(needToRemove);
        for (Node node : removed)
            System.out.println("Deleted " + node.getPath());

    }

    public static void main(String[] args) throws IOException{
        System.out.println("Enter the path: ");
        String stringPath = new Scanner(System.in).next();
        File path = new File(stringPath);

        File thePast = new File(new File("").getAbsolutePath() + "\\the_past.txt");
        if(thePast.exists()){
            HashMap<String,Node> newState = readDirectory(path);
            HashMap<String,Node> oldState = new ObjectMapper().readValue(thePast,new TypeReference<HashMap<String,Node>>() {});
            compare(newState,oldState,thePast.lastModified());
            thePast.delete();
        }else{
            HashMap<String,Node> oldState = readDirectory(path);
            new ObjectMapper().writeValue(thePast,oldState);
            System.out.println("The state of the directory is remembered");
        }
    }
}
