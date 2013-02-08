package project.senior.app.pormmo;

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
   * Clone the img.
   * @return A clone of the BufferedWrappers Image
   */
  public BufferedImage Clone()
  {
    int iWidth = img.getWidth(), iHeight = img.getHeight();
    BufferedImage newImage = new BufferedImage(iWidth, iHeight, img.getType());

    for (int x = 0; x < iWidth; x++)
    {
      for (int y = 0; y < iHeight; y++)
      {
        newImage.setRGB(x, y, img.getRGB(x, y));
      }
    }

    return newImage;
  }
}