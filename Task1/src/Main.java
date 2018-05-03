import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/** Решение
 * Создаём класс VertiStripe, содержащий длинну, код цвета, номер столбца,
 * номера крайних пикселей вертикальной полосы (N[px]=N[строки]*Width[картинки]+N[столбца]) и значение, говорящее о том, входит ли эта полоса в группу
 *
 * Создаём класс HorizStripe, содержащий длинну, код цвета, номер строки,
 * номера крайних пикселей горизонтальной полосы (номера пикселей высчитываются по той же формуле) и значение, говорящее о том, входит ли эта полоса в группу
 *
 * Создаём ArrayList<Integer>, каждым объект которого символизирует линию, состоящую из нескольких вертикальных и горизонталных полос
 * ArrayList<Integer> содержит длинну каждой линии
 *
 * Создаём TreeSet<VertiStripe> для горизонтальных полос и TreeSet<HorizStripe> для вертикальных полос,
 * TreeSet сортируются по номерам столбцов вертикальных линий и строк горизонталльных
 *
 * Создаём класс Edge содержащий номер границы
 * Создаём HashSet<Integer> для записи номеров границ вертикальных полос и ещё один для горизонтальных
 *
 * Ищем все вертикальные полосы, и горизонтальные, записывая их в соответствующие TreeSet, а границы в HashSet
 * Все полосы длинной 1px сразу записываются в ArrayList<Integer>
 *
 * Проходимся по TreeSet<HorizStripe>: если HorizStripe ещё не входит в состав линии, проверяем, являются ли её края краями какой нибудь из VertiStripe
 * Если хотябы один край не является краем другой полосы, то полоса становится членом новой линии и её size добовляется к значению линии
 * Смотрим на второй край, если он единаличный край, то линия зписывается в ArrayList<Integer>, переходим к следующей HorizStripe,
 * иначе смотрим на второй край соединённой с ней VertiStripe, если он единаличный край, то линия зписывается в ArrayList<Integer>, переходим к следующей HorizStripe...
 */

/**
 *
 */

public class Main {
    private final static int WHITE_PIXEL_CODE = -1;

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
                    if(image.getRGB(i,j) != WHITE_PIXEL_CODE){
                        int firstEdgeNumber = j * image.getWidth() + i;
                        int lineLength = 0;
                        int colorCode = image.getRGB(i,j);
                        while ((image.getRGB(i,j) == colorCode)&&(j<image.getWidth())){
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
                    if(image.getRGB(j,i) != WHITE_PIXEL_CODE){
                        int firstEdgeNumber = i * image.getWidth() + j;
                        int lineLength = 0;
                        int colorCode = image.getRGB(j,i);
                        while ((image.getRGB(j,i) == colorCode)&&(j<image.getWidth())){
                            lineLength++;
                            j++;
                        }
                        int secondEdgeNumber = i * image.getWidth() + j - 1;

                        if(lineLength != 1){
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
