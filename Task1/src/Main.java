import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Линии не должны соприкасаться
 * Производится 2 прохода по матрице пикселей
 * В первых проход записываются все горизонтальные линии и линии длинной 1px
 * Во второй все вертикальные, отбрасывая все линии длинной 1px
 */

public class Main {
    public static void main(String[] args) {
        final int WHITE_PIXEL_CODE = -1;
        try{
            System.out.println("Enter the path: ");
            String path = new Scanner(System.in).next();
            BufferedImage image = ImageIO.read(new File(path));
            long sumLength = 0;
            int count = 0;
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

//                        //for debag only!!!!!!!!!!!!!!!
//                        if(lineLength != 1){
//                            lineLengths.add(lineLength);
//                            sumLength+=lineLength;
//                            count++;
//                        }

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

//            System.out.println("высота " + image.getHeight());
//            System.out.println("ширина " + image.getWidth());


        }catch (IOException ioe){
            System.out.println("Couldn't find the image");
        }
    }
}
