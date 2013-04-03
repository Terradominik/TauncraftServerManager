package bergler;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * @author
 * Dominik
 */
public class Bergler {

    public static void main(String[] args) {
        BufferedImage berglerbild = null;
        try {
            berglerbild = ImageIO.read(Bergler.class.getResource("blue.png"));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(convertBerglerBild(berglerbild));
    }
   
    private static String convertBerglerBild(BufferedImage image) {
      int width = image.getWidth();
      int height = image.getHeight();
      StringBuilder sb = new StringBuilder("Ausgabe:\n");

      for (int i1 = 0; i1 < height; i1++) {
         for (int i2 = 0; i2 < width; i2++) {
            sb.append(Integer.toHexString(image.getRGB(i2, i1)).substring(4));
            sb.append(":");
         }
         sb.append("\n");
      }

      return sb.toString();
   }
}