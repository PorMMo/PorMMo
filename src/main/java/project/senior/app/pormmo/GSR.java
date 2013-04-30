//==============================================================================
//==  Due to PorMMo being open source, I will be leaving some other methods   ==
//==         That we do not use as often, for learning experience.            ==
//==                                                                          ==
//==   You may use this in your own projects if and only if you give credit   ==
//==         To the PorMMo team or the Authors of this specified file.        ==
//==============================================================================
package project.senior.app.pormmo;

//:TODO -> Possibly add a control for allowing a non-removal of a certain G lvl.
//----Help to not remove gray/black.
import java.awt.Color;
import java.awt.Graphics;
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
  private final Color TRANSPARENT = new Color(0, 0, 0, 0);
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
  public void RemoveGreen_3(BufferedWrapper before, float Ratio)
  {
    BufferedWrapper bw = new BufferedWrapper();
    bw.img = BufferedWrapper.CloneImg(before.img);

    Filters.GaussianBlurStatic(bw);
    Filters.MeanBlurStatic(bw);
    Filters.GaussianBlurStatic(bw);
    Filters.GaussianBlurStatic(bw);

    Color c;
    int r, g, b;
    float ratio = Ratio;
    Color a = new Color(4);

    for (int i = 0; i < bw.img.getWidth(); i++)
    {
      for (int j = 0; j < bw.img.getHeight(); j++)
      {
        c = new Color(bw.img.getRGB(i, j));
        r = c.getRed();
        b = c.getBlue();
        g = c.getGreen();

        g = (int) (g * ratio);
        g += 1;//:Lenient
        if (g >= r && g >= b)
        {
          if (g > 100)//:dont remove black
          {
            bw.img.setRGB(i, j, Color.GREEN.getRGB());
          }
        }
      }
    }

    BufferedImage output = new BufferedImage(bw.img.getWidth(),
            bw.img.getHeight(),
            BufferedImage.TYPE_INT_ARGB);

    Graphics G = output.createGraphics();
    G.setColor(new Color(0, 0, 0, 0));
    G.fillRect(0, 0, output.getWidth(), output.getHeight());

    for (int i = 0; i < before.img.getWidth(); i++)
    {
      for (int j = 0; j < before.img.getHeight(); j++)
      {
        if (before.img.getRGB(i, j) == 0)
        {
          continue;
        }

        c = new Color(bw.img.getRGB(i, j));

        if (c.getRGB() != Color.GREEN.getRGB())
        {
          //before.img.setRGB(i, j, TRANSPARENT.getRGB()); 
          G.setColor(new Color(before.img.getRGB(i, j)));
          G.fillRect(i, j, 1, 1);
        }
      }
    }
    
    before.img = BufferedWrapper.CloneImg(output);
  }

  /**
   * This method relies on a ratio of green to red/blue as per suggestion by
   * Fish.
   *
   * @param before BufferedImage containing green background.
   * @param Ratio The ratio of Green to : Blue/Red.
   * @return Image with most/all green removed.
   */
  public void RemoveGreen_2(BufferedWrapper before, float Ratio)
  {
    BufferedWrapper bw = new BufferedWrapper();
    bw.img = BufferedWrapper.CloneImg(before.img);

    Color c;
    int r, g, b;
    float ratio = Ratio;

    for (int i = 0; i < bw.img.getWidth(); i++)
    {
      for (int j = 0; j < bw.img.getHeight(); j++)
      {
        c = new Color(bw.img.getRGB(i, j));
        r = c.getRed();
        b = c.getBlue();
        g = c.getGreen();

        g = (int) (g * ratio);
        g += 1;//:Lenient
        if (g >= r && g >= b)
        {
          if (g > 100)//:dont remove black
          {
            bw.img.setRGB(i, j, Color.GREEN.getRGB());
          }
        }
      }
    }

    BufferedImage output = new BufferedImage(bw.img.getWidth(),
            bw.img.getHeight(),
            BufferedImage.TYPE_INT_ARGB);

    Graphics G = output.createGraphics();
    G.setColor(new Color(0, 0, 0, 0));
    G.fillRect(0, 0, output.getWidth(), output.getHeight());

    for (int i = 0; i < before.img.getWidth(); i++)
    {
      for (int j = 0; j < before.img.getHeight(); j++)
      {
        if (before.img.getRGB(i, j) == 0)
        {
          continue;
        }

        c = new Color(bw.img.getRGB(i, j));

        if (c.getRGB() != Color.GREEN.getRGB())
        {
          //before.img.setRGB(i, j, TRANSPARENT.getRGB()); 
          G.setColor(new Color(before.img.getRGB(i, j)));
          G.fillRect(i, j, 1, 1);
        }
      }
    }
    
    before.img = BufferedWrapper.CloneImg(output);
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