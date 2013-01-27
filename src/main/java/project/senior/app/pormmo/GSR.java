package project.senior.app.pormmo;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

/**
 *
 * @author Shelby Copeland
 */
public class GSR
{
  /**
   * This method is used to remove the green screen.
   * 
   * @param before The image you want to remove the green from.
   * @return An image that has the green removed.
   */
  public BufferedImage RemoveGreen(BufferedImage before)
  {
    BufferedImage picture = before.getSubimage(0, 0, before.getWidth(), 
                                                     before.getHeight());
    
    Color c;
    float[] hsv = {0, 0, 0};
    
    for(int i = 0; i < picture.getWidth(); i++)
      for(int j = 0; j < picture.getHeight(); j++)
      {
        c = new Color(picture.getRGB(i,j));
        c.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsv);
        
        
        if(hsv[0] < 0.3982f && hsv[0] > 0.1614f)
          picture.setRGB(i, j, Color.TRANSLUCENT);    
      }//:End for(i,j)
    
    //:at this point I would go over the image again and check the OUTSKIRTS
    //:~for pixels that are vastly different from the original
    
    return picture;
  }//:End RemoveGreen

  /**
   * Sets BufferedWrapper.img to the image at the path.
   * 
   * Example usage:
   * BufferedWrapper picture = new BufferedWrapper(); ~instantiate the wrapper
   * myGSR.GetImageFromFile("blah/blah/blah.png", picture);
   * 
   * At this point you will be able to call picture.img
   * and continue manipulation.
   * 
   * @param path Path to the screencap.
   * @param bw A buffered wrapper.
   * @return True if the image was read successfully, false otherwise. 
   */
  public boolean GetImageFromFile(String path, BufferedWrapper bw)
  {
    try      
    {
      bw.img = ImageIO.read(new File(path));
    }
    catch (IOException e)
    {
      return false;
    }
    
    return true;
  }
}