package project.senior.app.pormmo;

import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 *
 * @author Shelby Copeland
 */
public class Filters
{
  /**
   * This method returns a BufferedWrapper containing the Blurred image.
   * [3x3 mask]
   * 
   * @param bw The BufferedWrapper that contains the source image.
   * @return A BufferedWrapper that contains the blurred image.
   */
  public BufferedWrapper blur3(BufferedWrapper bw)
  {  
    BufferedWrapper temp = new BufferedWrapper();
    temp.img = bw.Clone();
    
    int[] mask = new int[9];
    
    for(int i = 1; i < temp.img.getWidth() - 1; i++)
      for(int j = 1; j < temp.img.getHeight() - 1; j++)
      {
        mask[0] = bw.img.getRGB(i - 1, j - 1);
        mask[1] = bw.img.getRGB(i    , j - 1);
        mask[2] = bw.img.getRGB(i + 1, j - 1);
        mask[3] = bw.img.getRGB(i - 1, j);
        mask[4] = bw.img.getRGB(i    , j);
        mask[5] = bw.img.getRGB(i + 1, j);
        mask[6] = bw.img.getRGB(i - 1, j + 1);
        mask[7] = bw.img.getRGB(i    , j + 1);
        mask[8] = bw.img.getRGB(i + 1, j + 1);
        //:^Yes this "could" be in a for loop
        //:However, that is just extra computation for a small amount.
        
        temp.img.setRGB(i, j, median(mask));
      }
    
    return temp;
  }
  
  /**
   * Does the same thing as blur(BW), but no extra variables required.
   * When the method is complete, bw.img will have the blurred image.
   * [3x3 mask]
   * 
   * @param bw The BufferedWrapper containing the image you want blurred. 
   */
  public void _blur3(BufferedWrapper bw)
  {  
    BufferedImage img = bw.Clone();
    
    int[] mask = new int[9];
    
    for(int i = 1; i < img.getWidth() - 1; i++)
      for(int j = 1; j < img.getHeight() - 1; j++)
      {
        mask[0] = bw.img.getRGB(i - 1, j - 1);
        mask[1] = bw.img.getRGB(i    , j - 1);
        mask[2] = bw.img.getRGB(i + 1, j - 1);
        mask[3] = bw.img.getRGB(i - 1, j);
        mask[4] = bw.img.getRGB(i    , j);
        mask[5] = bw.img.getRGB(i + 1, j);
        mask[6] = bw.img.getRGB(i - 1, j + 1);
        mask[7] = bw.img.getRGB(i    , j + 1);
        mask[8] = bw.img.getRGB(i + 1, j + 1);
        //:^Yes this "could" be in a for loop
        //:However, that is just extra computation for a small amount.
        
        img.setRGB(i, j, median(mask));
      }
    
    bw.img = img;
  }
  
  /**
   * Does the same thing as blur(BW).
   * [3x3 mask]
   * 
   * @param bw The BufferedWrapper containing the image you want blurred.
   * @param t  The BufferedWrapper that will contain the blurred image. Please
   *           that t must be initialized (t = new bufferedimage();)
   */
  public void _blur3(BufferedWrapper bw, BufferedWrapper t)
  {  
    
    t.img = bw.Clone();
    
    int[] mask = new int[9];
    
    for(int i = 1; i < t.img.getWidth() - 1; i++)
      for(int j = 1; j < t.img.getHeight() - 1; j++)
      {
        mask[0] = bw.img.getRGB(i - 1, j - 1);
        mask[1] = bw.img.getRGB(i    , j - 1);
        mask[2] = bw.img.getRGB(i + 1, j - 1);
        mask[3] = bw.img.getRGB(i - 1, j);
        mask[4] = bw.img.getRGB(i    , j);
        mask[5] = bw.img.getRGB(i + 1, j);
        mask[6] = bw.img.getRGB(i - 1, j + 1);
        mask[7] = bw.img.getRGB(i    , j + 1);
        mask[8] = bw.img.getRGB(i + 1, j + 1);
        //:^Yes this "could" be in a for loop
        //:However, that is just extra computation for a small amount.
        
        t.img.setRGB(i, j, median(mask));
      }
  }
  
  /**
   * Does the same thing as blur(BW) and _blur(BW).
   * This can be referenced from Filters.sBlur(BW).
   * No instance of Filters needed.
   * 
   * Note: This will take in a BufferedWrapper and OVERWRITE bw.img
   * :::Use with that in mind.
   * [3x3 mask]
   * 
   * @param bw The BufferedWrapper containing the image you want blurred. 
   */
  public static void sBlur3(BufferedWrapper bw)
  {  
    BufferedImage img = bw.Clone();
    
    int[] mask = new int[9];
    
    for(int i = 1; i < img.getWidth() - 1; i++)
      for(int j = 1; j < img.getHeight() - 1; j++)
      {
        mask[0] = bw.img.getRGB(i - 1, j - 1);
        mask[1] = bw.img.getRGB(i    , j - 1);
        mask[2] = bw.img.getRGB(i + 1, j - 1);
        mask[3] = bw.img.getRGB(i - 1, j);
        mask[4] = bw.img.getRGB(i    , j);
        mask[5] = bw.img.getRGB(i + 1, j);
        mask[6] = bw.img.getRGB(i - 1, j + 1);
        mask[7] = bw.img.getRGB(i    , j + 1);
        mask[8] = bw.img.getRGB(i + 1, j + 1);
        //:^Yes this "could" be in a for loop
        //:However, that is just extra computation for a small amount.
        
        img.setRGB(i, j, median(mask));
      }
    
    bw.img = img;
  }
  
  //:simple median finder.
  private static int median(int[] mask)
  {
    int[] temp = mask.clone();
    Arrays.sort(temp);
    
    return temp[4];
  }
}