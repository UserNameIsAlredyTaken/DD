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
 * Ищем все вертикальные полосы, и горизонтальные, записывая их в соответствующие TreeSet
 * Все полосы длинной 1px сразу записываются в ArrayList<Integer>
 *
 * Проходимся по TreeSet<HorizStripe>: если HorizStripe ещё не входит в состав линии, проверяем, являются ли её края краями какой нибудь из VertiStripe
 * Если хотябы один край не является краем другой полосы, то полоса становится членом новой линии и её size добовляется к значению линии
 * Смотрим на второй край, если он единаличный край, то линия зписывается в ArrayList<Integer>, переходим к следующей HorizStripe,
 * иначе смотрим на второй край соединённой с ней VertiStripe, если он единаличный край, то линия зписывается в ArrayList<Integer>, переходим к следующей HorizStripe...
 */

public class Main {
    private final static int WHITE_PIXEL_CODE = -1;



    public static void main(String[] args) {

        try{
            System.out.println("Enter the path: ");
            String path = new Scanner(System.in).next();
            BufferedImage image = ImageIO.read(new File(path));

            ArrayList<Integer> lineLengths = new ArrayList<Integer>();








            /*Поиск вертикальных полос и полос длинной 1px*/
            for(int i = 0; i < image.getHeight(); i++){
                for(int j = 0; j < image.getWidth(); j++){
                    if(image.getRGB(j,i) != WHITE_PIXEL_CODE){
                        int lineLength = 0;
                        while ((image.getRGB(j,i) != WHITE_PIXEL_CODE)&&(j<image.getWidth())){
                            lineLength++;
                            j++;
                        }

                        if(lineLength == 1){
                            /*Проверка, что единичная полоса не является не единичной полосой в другой оси*/
                            if(i == 0){
                                if((image.getRGB(j-1,i+1) == WHITE_PIXEL_CODE)){
                                    lineLengths.add(lineLength);
                                    sumLength+=lineLength;
                                    count++;
                                }
                            }else{
                                if(i != image.getHeight()-1){
                                    if((image.getRGB(j-1,i-1) == WHITE_PIXEL_CODE)&&(image.getRGB(j-1,i+1) == WHITE_PIXEL_CODE)){
                                        lineLengths.add(lineLength);
                                        sumLength+=lineLength;
                                        count++;
                                    }
                                }else{
                                    if((image.getRGB(j-1,i-1) == WHITE_PIXEL_CODE)){
                                        lineLengths.add(lineLength);
                                        sumLength+=lineLength;
                                        count++;
                                    }
                                }
                            }
                        }else{
                            lineLengths.add(lineLength);
                            sumLength+=lineLength;
                            count++;
                        }
                    }
                }
            }


            /*Поиск горизонтальных полос*/
            for(int i = 0; i < image.getWidth(); i++){
                for(int j = 0; j < image.getHeight(); j++){
                    if(image.getRGB(i,j) != WHITE_PIXEL_CODE){
                        int lineLength = 0;
                        while ((image.getRGB(i,j) != WHITE_PIXEL_CODE)&&(j<image.getHeight())){
                            lineLength++;
                            j++;
                        }

                        if(lineLength != 1){
                            lineLengths.add(lineLength);
                            sumLength+=lineLength;
                            count++;
                        }
                    }
                }
            }

            System.out.println("Количство полос: " + count);

            for(int lineLength : lineLengths){
                System.out.println(lineLength);
            }

            System.out.println("Суммарная длинна: " + sumLength);



        }catch (IOException ioe){
            System.out.println("Couldn't find the image");
        }
    }
}
