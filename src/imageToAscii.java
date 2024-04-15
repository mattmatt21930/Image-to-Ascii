import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;

public class imageToAscii {
    public static void main(String[] args) throws Exception {
        try {
            // image to convert
            String inputImage = "C:\\Users\\Matt\\Documents\\code\\java\\imageAscii\\gavin.jpg";
            BufferedImage readImg = ImageIO.read(new File(inputImage));
            
            // output file writer
            FileWriter myWriter = new FileWriter("out.txt");
            
            // init variables
            int wCount = 0;
            int hCount = 0;
            int height = readImg.getHeight();
            int width = readImg.getWidth();
            double lumAvg = 0;
            ArrayList<Double> lumAvgList = new ArrayList<Double>();
            
            // loop through rows of pixels 
            for (int i = 0; i < height; i++) {
                // only use every 18th row
                hCount++;
                if (hCount == 18) {
                    hCount = 0;
                }
                if (hCount == 0) {
                    // loop through every pixel in the row
                    for (int n = 0; n < width; n++) {
                        
                        // get rgb values of the pixel and convert to luminance
                        int pixelColour = readImg.getRGB(n, i);
                        int r = (pixelColour >>> 16) & 0xff;
                        int g = (pixelColour >>> 8) & 0xff;
                        int b = pixelColour & 0xff;
                        double lum = (0.2126*r) + (0.7152*g) + (0.0722*b);
                        
                        // append luminance to the ArrayList
                        lumAvgList.add(lum);
                        
                        // counting variable
                        wCount++;
                        
                        // new line if at end of row
                        if (n == width - 1) {
                            myWriter.write(System.lineSeparator());
                            wCount = 0;
                            
                        }
                        
                        // every 9th pixel take the average of the last 9 pixels and write a character to the output file based on luminance
                        if (wCount == 9) {
                            for (int q = 0; q < 9; q++) {
                                lumAvg += lumAvgList.get(q);
                            }
                            lumAvg = lumAvg/9;
                            if (lumAvg < 50) {
                                myWriter.write("@");
                            } else if (lumAvg < 100 && lumAvg >= 50) {
                                myWriter.write("%");
                            } else if (lumAvg < 150 && lumAvg >= 100) {
                                myWriter.write("#");
                            } else if (lumAvg < 200 && lumAvg >= 150) {
                                myWriter.write("*");
                            } else if (lumAvg < 225 && lumAvg >= 200) {
                                myWriter.write(".");
                            } else if (lumAvg <= 255 && lumAvg >= 225) {
                                myWriter.write(" ");
                            }

                            // reset variables
                            wCount = 0;
                            lumAvg = 0;
                            lumAvgList.clear();
                        }
                    }
                }
            }
            // close file writer
            myWriter.close();
        } catch (IOException e) {
            return;
        }
        

    }
}
