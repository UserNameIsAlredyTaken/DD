import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Проходим по матрице сверху вниз, ищем все вертикальные полосы
 * Если длинна полосы 1px записываем её в лист с информацией о длинах всех полос, иначе запоминаем её границы ссылающиеся друг на друга и имеющие информацию о цвете и длинне полосы
 *
 * Проходимся по матрице справа на лево, ищем все горизонтальные полосы
 * Запоминаем границы полос, проверяем с границами каких вертикальных полос они совпадают, делаем в них соответствующие ссылки
 *
 * Складываем множества границ вертикальных и горизонтальных полос, проходимся по полученному множеству
 * Ищем границу не связанную ни с какой другой границей другой оси, проходимся до кона линии, складывая длины полос из которых она состоит, записываем линию в список
 *
 * Выводим информацию из списка
 *
 * (не обрабатывает случай, когда монотонные линии идут рядом, так как тогда не ясно как их интерпретировать: как 2 линии, идущие рядом, много линий длинной 2, идущие рядои, или одна линяя, согнувшаяся попалам
 * не обрабатывает случай, когда линия состоит не только из вертикальных и горизонтальных полос, но и диагональных)
 *
 */

public class Main {
    private final static int WHITE_PIXEL_CODE = -1;

    private static boolean isNotPartOfHorizontal(BufferedImage image, int x, int y){
        if((x == 0) || (x == image.getWidth() - 1)){return true;}
        if((image.getRGB(x-1,y) == image.getRGB(x,y)) && (image.getRGB(x+1,y) == image.getRGB(x,y))){
            return false;
        }else{
            return true;
        }
    }

    private static boolean isNotPartOfVertical(BufferedImage image, int x, int y){
        if((y == 0) || (y == image.getHeight() - 1)){return true;}
        if((image.getRGB(x,y-1) == image.getRGB(x,y)) && (image.getRGB(x,y+1) == image.getRGB(x,y))){
            return false;
        }else{
            return true;
        }
    }

    private static boolean besideTheVerLine(BufferedImage image, int x, int y){
        if(((x != 0) && (image.getRGB(x,y) == image.getRGB(x-1,y))) || ((x != image.getWidth() - 1) && (image.getRGB(x,y) == image.getRGB(x+1,y)))){
            return true;
        }
        return false;
    }


    public static void main(String[] args) {

        try{
            System.out.println("Enter the path: ");
            String path = new Scanner(System.in).next();
            BufferedImage image = ImageIO.read(new File(path));

            ArrayList<Integer> lineLengths = new ArrayList<Integer>();
            HashSet<Edge> edges = new HashSet<Edge>();
            HashSet<Edge> horEdges = new HashSet<Edge>();

            /*Поиск вертикальных полос и полос длинной 1px*/
            for(int i = 0; i < image.getWidth(); i++){
                for(int j = 0; j < image.getHeight(); j++){
                    if((image.getRGB(i,j) != WHITE_PIXEL_CODE) && (isNotPartOfHorizontal(image, i, j))){
                        int firstEdgeNumber = j * image.getWidth() + i;
                        int lineLength = 0;
                        int colorCode = image.getRGB(i,j);
                        while ((image.getRGB(i,j) == colorCode) && (j<image.getWidth()) && (isNotPartOfHorizontal(image, i, j))){
                            lineLength++;
                            j++;
                        }
                        int secondEdgeNumber = (j - 1) * image.getWidth() + i;

                        if(lineLength == 1){
                            /*Проверка, что единичная полоса не является не единичной полосой в другой оси*/
                            if(i == 0){
                                if((image.getRGB(i+1,j-1) != colorCode)){
                                    lineLengths.add(lineLength);
                                }
                            }else{
                                if(i != image.getWidth()-1){
                                    if((image.getRGB(i-1,j-1) != colorCode)&&(image.getRGB(i+1,j-1) != colorCode)){
                                        lineLengths.add(lineLength);
                                    }
                                }else{
                                    if((image.getRGB(j-1,i-1) != colorCode)){
                                        lineLengths.add(lineLength);
                                    }
                                }
                            }

                        }else{
                            Edge edge1 = new Edge(firstEdgeNumber, lineLength, colorCode);
                            Edge edge2 = new Edge(secondEdgeNumber, lineLength, colorCode);
                            edge1.setAnotherEdge(edge2);
                            edge2.setAnotherEdge(edge1);
                            edges.add(edge1); edges.add(edge2);
                        }
                    }
                }
            }


            /*Поиск горизонтальных полос*/
            for(int i = 0; i < image.getHeight(); i++){
                for(int j = 0; j < image.getWidth(); j++){
                    if((image.getRGB(j,i) != WHITE_PIXEL_CODE) && (isNotPartOfVertical(image, j, i))){
                        int firstEdgeNumber = i * image.getWidth() + j;
                        int lineLength = 0;
                        int colorCode = image.getRGB(j,i);
                        while ((image.getRGB(j,i) == colorCode)&&(j<image.getWidth()) && (isNotPartOfVertical(image, j, i))){
                            lineLength++;
                            j++;
                        }
                        int secondEdgeNumber = i * image.getWidth() + j - 1;

                        if(lineLength == 1){
                            if(besideTheVerLine(image,j-1,i)){
                                lineLengths.add(lineLength);
                            }
                        }else{
                            Edge edge1 = new Edge(firstEdgeNumber, lineLength, colorCode);
                            Edge edge2 = new Edge(secondEdgeNumber, lineLength, colorCode);
                            edge1.setAnotherEdge(edge2);
                            edge2.setAnotherEdge(edge1);

                            for (Edge edge : edges){
                                if(edge.getPixNumber() == edge1.getPixNumber()){
                                    edge.setCornerEdge(edge1); edge1.setCornerEdge(edge);
                                }else if(edge.getPixNumber() == edge2.getPixNumber()){
                                    edge.setCornerEdge(edge2); edge2.setCornerEdge(edge);
                                }
                            }

                            horEdges.add(edge1); horEdges.add(edge2);
                        }
                    }
                }
            }

            edges.addAll(horEdges);

            for(Edge firstEdge : edges){
                if((firstEdge.getCornerEdge() == null) && (firstEdge.isNotInLineYet())){
                    firstEdge.setNotInLineYet(false);
                    Edge secondEdge = firstEdge.getAnotherEdge();
                    secondEdge.setNotInLineYet(false);
                    int lineLength = firstEdge.getStripeSize();
                    while (true){
                        if(secondEdge.getCornerEdge() == null){
                            break;
                        }
                        firstEdge = secondEdge.getCornerEdge();
                        firstEdge.setNotInLineYet(false);
                        secondEdge = firstEdge.getAnotherEdge();
                        secondEdge.setNotInLineYet(false);
                        lineLength += firstEdge.getStripeSize();
                        lineLength--;
                    }
                    lineLengths.add(lineLength);
                }
            }

            System.out.println("Количество линий: " + lineLengths.size());
            int summLength = 0;
            int i = 0;
            for(int length : lineLengths){
                i++;
                summLength += length;
                System.out.println("Длинна " + i + "-ой линии: " + length);
            }
            System.out.println("Суммарная длинна линий: " + summLength);

        }catch (IOException ioe){
            System.out.println("Couldn't find the image");
        }
    }
}
