package project.senior.app.pormmo;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Shelby Copeland, John "Fish" Fisher
 */
public class BufferedWrapper
{
  /**
   * Pass by Reference.
   */
  public BufferedImage img;
  
  /**
   * Constructor
   * @param img A BufferedImage
   * @param frameNum An int which represents the order this BufferedWrapper is in a sequence of frames
   */
  public BufferedWrapper(BufferedImage img)
  {
      this.img = img;
  }
  
  public BufferedWrapper()
  {
      
  }
  
  /**
   * Clone the img.
   * @return A clone of the BufferedWrappers Image
   */
  public BufferedImage Clone()
  {
    BufferedImage newImage = new BufferedImage(img.getWidth(), img.getHeight(),
                                                   BufferedImage.TYPE_INT_ARGB);
    
    Graphics g = newImage.createGraphics();
    g.drawImage(img, 0, 0, null);

    return newImage;
  }
  
  public static BufferedImage CloneImg(BufferedImage bi)
  {
    BufferedImage newImage = new BufferedImage(bi.getWidth(), bi.getHeight(),
                                                   BufferedImage.TYPE_INT_ARGB);
    
    Graphics g = newImage.createGraphics();
    g.drawImage(bi, 0, 0, null);

    return newImage;
  }
}