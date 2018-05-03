import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeSet;


/** Решение
 * Создаём ArrayList<Integer> все длины полос
 * Создаём HashSet<Integer>, содержащий номера пикселей, являющихся пройденными границами линий
 *
 * Проходимяся по всем пикселям картинки и ищем края линий (BEGIN), не являющихся углами и ещё не записанные в HashSet
 * Найдя такую границу записываем её в HashSet и начинаем рекурсивно проходится по ней, определяя направление на каждом шаге,
 * учитывая что цвет монотонный, считая её длинну до тех пор пока она не закончится
 * После этого записываем вторую границу в HashSet и длинну линии в ArrayList
 *
 * (Рекурсивная функция по проходу, перебор пикселей от 0 до Width*Height)
 */

public class Main {
    private final static int WHITE_PIXEL_CODE = -1;
    private static Edge whichEdge(int prevPixCode, int currPixCode, int i, int j, BufferedImage image) {
        int rightPixCode;
        int upPixCode;
        int downPixCode;

        if(j != image.getWidth()-1){
            rightPixCode = image.getRGB(j+1,i);
        }else{
            rightPixCode = WHITE_PIXEL_CODE;
        }

        if(currPixCode == rightPixCode){
            return Edge.NONE;
        }

        if(i != image.getHeight()-1){
            downPixCode = image.getRGB(j,i+1);
        }else{
            downPixCode = WHITE_PIXEL_CODE;
        }

        if(i != 0){
            upPixCode = image.getRGB(j,i-1);
        }else{
            upPixCode = WHITE_PIXEL_CODE;
        }


    }

    private static void passTheLine(int i, Edge edge, int prevPixCode, Object p3) {

    }



    public static void main(String[] args) {

        try{
            System.out.println("Enter the path: ");
            String path = new Scanner(System.in).next();
            BufferedImage image = ImageIO.read(new File(path));

            ArrayList<Integer> lineLengths = new ArrayList<Integer>();
            HashSet<Integer> usedEdges = new HashSet<Integer>();

            int prevPixCode;
            int currPixCode;
            for(int i = 0; i < image.getHeight(); i++){
                prevPixCode = WHITE_PIXEL_CODE;
                for(int j = 0; j < image.getWidth(); j++){
                    currPixCode = image.getRGB(j,i);

                    Edge edge = whichEdge(prevPixCode, currPixCode, i, j, image);
                    if(edge != Edge.NONE){
                        if(edge == Edge.LEFT){
                            usedEdges.add(image.getWidth() * i + j);
                            passTheLine(1,edge,prevPixCode,);
                        }else{
                            usedEdges.add(image.getWidth() * i + j);
                            passTheLine(1,edge,currPixCode,);
                        }

                    }

                }
            }



            
        }catch (IOException ioe){
            System.out.println("Couldn't find the image");
        }
    }
}
