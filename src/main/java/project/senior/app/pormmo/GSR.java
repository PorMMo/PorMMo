//==============================================================================
//==  Due to PorMMo being open source, I will be leaving some other methods   ==
//==         That we do not use as often, for learning experience.            ==
//==                                                                          ==
//==   You may use this in your own projects if and only if you give credit   ==
//==         To the PorMMo team or the Authors of this specified file.        ==
//==============================================================================
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
  public final float GREEN = 0.33f;
  public final float BLUE = 0.66f;
  //Possible add more colours?

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
    float[] hsv =
    {
      0, 0, 0
    };

    for (int i = 0; i < picture.getWidth(); i++)
    {
      for (int j = 0; j < picture.getHeight(); j++)
      {
        c = new Color(picture.getRGB(i, j));
        c.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsv);

        System.out.println(c.getGreen());
        if (hsv[0] < 0.38f && hsv[0] > 0.22f)
        {
          picture.setRGB(i, j, Color.TRANSLUCENT);
        }
      }//:End for(i,j)   
    }
    return picture;
  }//:End RemoveGreen

  /**
   * Test method to remove green screen.
   *
   * @param before The image you want to remove green from
   * @param Tolerance The amount of tolerance from a colour you wish to give it.
   * @param Target the target color to remove GSR.GREEN for green. Or a value
   * 0-1
   * @return
   */
  public BufferedImage RemoveGreen_2(BufferedImage before, float Tolerance, float Target)
  {
    BufferedImage picture = before.getSubimage(0, 0, before.getWidth(),
            before.getHeight());

    Color c;
    float[] hsv =
    {
      0, 0, 0
    };
    float difference, trans;
    float target = Target;
    float tolerance = Tolerance;

    for (int i = 0; i < picture.getWidth(); i++)
    {
      for (int j = 0; j < picture.getHeight(); j++)
      {
        c = new Color(picture.getRGB(i, j));
        c.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsv);

        difference = Math.abs(hsv[0] - target);
        trans = (difference / tolerance);

        trans = Math.min(trans, 1.0f);

        if (trans < 0.1f)
        {
          picture.setRGB(i, j, Color.TRANSLUCENT);
        }
      }
    }

    return picture;
  }//:End RemoveGreen

  /**
   * This method relies on a ratio of green to red/blue as per suggestion by
   * Fish.
   *
   * @param before BufferedImage containing green background.
   * @param Ratio The ratio of Green to : Blue/Red.
   * @return Image with most/all green removed.
   */
  public BufferedImage RemoveGreen_3(BufferedImage before, float Ratio)
  {
    BufferedImage picture = before.getSubimage(0, 0, before.getWidth(),
            before.getHeight());
    BufferedWrapper bw = new BufferedWrapper();
    bw.img = BufferedWrapper.CloneImg(picture);
    
    Filters.GaussianBlurStatic(bw); 
    Filters.MeanBlurStatic(bw);
    Filters.GaussianBlurStatic(bw);
    
    Color c;
    int r, g, b;
    float ratio = Ratio;

    for (int i = 0; i < picture.getWidth(); i++)
    {
      for (int j = 0; j < picture.getHeight(); j++)
      {
        c = new Color(bw.img.getRGB(i, j));
        r = c.getRed();
        b = c.getBlue();
        g = c.getGreen();

        g = (int) (g * ratio);
        g += 1;//:Lenient
        if (g >= r && g >= b)
        {
          bw.img.setRGB(i, j, Color.TRANSLUCENT);
        }
      }
    }
    
    
    for (int i = 0; i < picture.getWidth(); i++)
    {
      for (int j = 0; j < picture.getHeight(); j++)
      {
          c = new Color(bw.img.getRGB(i, j));
          
          if(c.getRGB() == Color.TRANSLUCENT)
          {
             picture.setRGB(i, j, Color.TRANSLUCENT); 
          }
      }
    }
    
    return picture;
  }

  /**
   * Sets BufferedWrapper.img to the image at the path.
   *
   * Example usage: BufferedWrapper picture = new BufferedWrapper();
   * ~instantiate the wrapper myGSR.GetImageFromFile("blah/blah/blah.png",
   * picture);
   *
   * At this point you will be able to call picture.img and continue
   * manipulation.
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