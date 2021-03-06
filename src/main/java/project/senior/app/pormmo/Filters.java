//==============================================================================
//==  Due to PorMMo being open source, I will be leaving some other methods   ==
//==         That we do not use as often, for learning experience.            ==
//==                                                                          ==
//==   You may use this in your own projects if and only if you give credit   ==
//==         To the PorMMo team or the Authors of this specified file.        ==
//==============================================================================
package project.senior.app.pormmo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 *
 * @author Shelby Copeland
 */
public class Filters
{

  public static final int SixteenBit = 2;
  public static final int EightBit = 1;

  //============================================================================
  //====                         Median Blur                                ====
  //============================================================================
  /**
   * This method returns a BufferedWrapper containing the Blurred image. [3x3
   * mask]
   *
   * @param bw The BufferedWrapper that contains the source image.
   * @return A BufferedWrapper that contains the blurred image.
   */
  public BufferedWrapper MedianBlur(BufferedWrapper bw)
  {
    BufferedWrapper temp = new BufferedWrapper();
    temp.img = bw.Clone();

    int[] mask = new int[9];

    for (int i = 1; i < temp.img.getWidth() - 1; i++)
    {
      for (int j = 1; j < temp.img.getHeight() - 1; j++)
      {
        mask[0] = bw.img.getRGB(i - 1, j - 1);
        mask[1] = bw.img.getRGB(i, j - 1);
        mask[2] = bw.img.getRGB(i + 1, j - 1);
        mask[3] = bw.img.getRGB(i - 1, j);
        mask[4] = bw.img.getRGB(i, j);
        mask[5] = bw.img.getRGB(i + 1, j);
        mask[6] = bw.img.getRGB(i - 1, j + 1);
        mask[7] = bw.img.getRGB(i, j + 1);
        mask[8] = bw.img.getRGB(i + 1, j + 1);
        //:^Yes this "could" be in a for loop
        //:However, that is just extra computation for a small amount.

        temp.img.setRGB(i, j, median(mask));
      }
    }

    return temp;
  }

  /**
   * Does the same thing as blur(BW), but no extra variables required. When the
   * method is complete, bw.img will have the blurred image. [3x3 mask]
   *
   * @param bw The BufferedWrapper containing the image you want blurred.
   */
  public void MedianBlurReference(BufferedWrapper bw)
  {
    BufferedImage img = bw.Clone();

    int[] mask = new int[9];

    for (int i = 1; i < img.getWidth() - 1; i++)
    {
      for (int j = 1; j < img.getHeight() - 1; j++)
      {
        mask[0] = bw.img.getRGB(i - 1, j - 1);
        mask[1] = bw.img.getRGB(i, j - 1);
        mask[2] = bw.img.getRGB(i + 1, j - 1);
        mask[3] = bw.img.getRGB(i - 1, j);
        mask[4] = bw.img.getRGB(i, j);
        mask[5] = bw.img.getRGB(i + 1, j);
        mask[6] = bw.img.getRGB(i - 1, j + 1);
        mask[7] = bw.img.getRGB(i, j + 1);
        mask[8] = bw.img.getRGB(i + 1, j + 1);
        //:^Yes this "could" be in a for loop
        //:However, that is just extra computation for a small amount.

        img.setRGB(i, j, median(mask));
      }
    }

    bw.img = img;
  }

  /**
   * Does the same thing as blur(BW). [3x3 mask]
   *
   * @param bw The BufferedWrapper containing the image you want blurred.
   * @param t The BufferedWrapper that will contain the blurred image. Please
   * that t must be initialized (t = new bufferedimage();)
   */
  public void MedianBlurUnAlterReference(BufferedWrapper bw, BufferedWrapper t)
  {

    t.img = bw.Clone();

    int[] mask = new int[9];

    for (int i = 1; i < t.img.getWidth() - 1; i++)
    {
      for (int j = 1; j < t.img.getHeight() - 1; j++)
      {
        mask[0] = bw.img.getRGB(i - 1, j - 1);
        mask[1] = bw.img.getRGB(i, j - 1);
        mask[2] = bw.img.getRGB(i + 1, j - 1);
        mask[3] = bw.img.getRGB(i - 1, j);
        mask[4] = bw.img.getRGB(i, j);
        mask[5] = bw.img.getRGB(i + 1, j);
        mask[6] = bw.img.getRGB(i - 1, j + 1);
        mask[7] = bw.img.getRGB(i, j + 1);
        mask[8] = bw.img.getRGB(i + 1, j + 1);
        //:^Yes this "could" be in a for loop
        //:However, that is just extra computation for a small amount.

        t.img.setRGB(i, j, median(mask));
      }
    }
  }

  /**
   * Does the same thing as blur(BW) and _blur(BW). This can be referenced from
   * Filters.sBlur(BW). No instance of Filters needed.
   *
   * Note: This will take in a BufferedWrapper and OVERWRITE bw.img :::Use with
   * that in mind. [3x3 mask]
   *
   * @param bw The BufferedWrapper containing the image you want blurred.
   */
  public static void MedianBlurStatic(BufferedWrapper bw)
  {
    BufferedImage img = bw.Clone();

    int[] mask = new int[9];

    for (int i = 1; i < img.getWidth() - 1; i++)
    {
      for (int j = 1; j < img.getHeight() - 1; j++)
      {
        mask[0] = bw.img.getRGB(i - 1, j - 1);
        mask[1] = bw.img.getRGB(i, j - 1);
        mask[2] = bw.img.getRGB(i + 1, j - 1);
        mask[3] = bw.img.getRGB(i - 1, j);
        mask[4] = bw.img.getRGB(i, j);
        mask[5] = bw.img.getRGB(i + 1, j);
        mask[6] = bw.img.getRGB(i - 1, j + 1);
        mask[7] = bw.img.getRGB(i, j + 1);
        mask[8] = bw.img.getRGB(i + 1, j + 1);
        //:^Yes this "could" be in a for loop
        //:However, that is just extra computation for a small amount.

        img.setRGB(i, j, median(mask));
      }
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

  //============================================================================
  //====                          Mean Blur                                 ====
  //============================================================================
  /**
   * Simple [3x3] mean based blur.
   *
   * @param bw BufferedWrapper containing the image to be blurred.
   * @return BufferedWrapper containing the blurred image.
   */
//  public BufferedWrapper MeanBlur(BufferedWrapper bw)
//  {  
//    BufferedWrapper temp = new BufferedWrapper();
//    temp.img = bw.Clone();
//    
//    int[] mask = new int[9];
//    
//    for(int i = 1; i < temp.img.getWidth() - 1; i++)
//      for(int j = 1; j < temp.img.getHeight() - 1; j++)
//      {
//        mask[0] = bw.img.getRGB(i - 1, j - 1);
//        mask[1] = bw.img.getRGB(i    , j - 1);
//        mask[2] = bw.img.getRGB(i + 1, j - 1);
//        mask[3] = bw.img.getRGB(i - 1, j);
//        mask[4] = bw.img.getRGB(i    , j);
//        mask[5] = bw.img.getRGB(i + 1, j);
//        mask[6] = bw.img.getRGB(i - 1, j + 1);
//        mask[7] = bw.img.getRGB(i    , j + 1);
//        mask[8] = bw.img.getRGB(i + 1, j + 1);
//        //:^Yes this "could" be in a for loop
//        //:However, that is just extra computation for a small amount.
//        
//        temp.img.setRGB(i, j, mean(mask));
//      }
//    
//    return temp;
//  }
  /**
   * NOTE THIS WILL OVERWRITE THE PREVIOUS bw.img!!!!!
   *
   * @param bw The buffered wrapper containing the image to be blurred.
   */
  public static void MeanBlurStatic(BufferedWrapper bw)
  {
    BufferedImage img = bw.Clone();

    Graphics g = img.createGraphics();
    g.setColor(new Color(0, 0, 0, 0));
    g.fillRect(0, 0, img.getWidth(), img.getHeight());
    Color c;

    int[] mask = new int[9];

    for (int i = 1; i < img.getWidth() - 1; i++)
    {
      for (int j = 1; j < img.getHeight() - 1; j++)
      {
        mask[0] = bw.img.getRGB(i - 1, j - 1);
        mask[1] = bw.img.getRGB(i, j - 1);
        mask[2] = bw.img.getRGB(i + 1, j - 1);
        mask[3] = bw.img.getRGB(i - 1, j);
        mask[4] = bw.img.getRGB(i, j);
        mask[5] = bw.img.getRGB(i + 1, j);
        mask[6] = bw.img.getRGB(i - 1, j + 1);
        mask[7] = bw.img.getRGB(i, j + 1);
        mask[8] = bw.img.getRGB(i + 1, j + 1);
        //:^Yes this "could" be in a for loop
        //:However, that is just extra computation for a small amount.

        if (mask[4] != 0)
        {
          g.setColor(new Color(mean(mask)));
          g.fillRect(i, j, 1, 1);
        }
      }
    }

    bw.img = BufferedWrapper.CloneImg(img);
  }

  //:simple mean finder
  private static int mean(int[] mask)
  {
    Color c, end;
    int r = 0, g = 0, b = 0;

    for (int i = 0; i < mask.length; i++)
    {
      c = new Color(mask[i]);
      r += c.getRed();
      g += c.getGreen();
      b += c.getBlue();
    }

    end = new Color(r / mask.length, g / mask.length, b / mask.length);

    return end.getRGB();
  }

  //============================================================================
  //====                          GrayScale                                 ====
  //============================================================================
  public BufferedWrapper GrayScale(BufferedWrapper bw)
  {
    BufferedWrapper temp = new BufferedWrapper();
    temp.img = bw.Clone();
    Color c, end;
    int t;

    for (int i = 0; i < temp.img.getWidth(); i++)
    {
      for (int j = 0; j < temp.img.getHeight(); j++)
      {
        c = new Color(temp.img.getRGB(i, j));
        t = c.getRed();
        t += c.getGreen();
        t += c.getBlue();
        t = t / 3;

        end = new Color(t, t, t);

        temp.img.setRGB(i, j, end.getRGB());
      }
    }

    return temp;
  }

  public static void GrayScaleStatic(BufferedWrapper bw)
  {
    BufferedImage img = bw.Clone();

    Graphics g = img.createGraphics();


    Color c, end;
    int t;

    for (int i = 0; i < bw.img.getWidth(); i++)
    {
      for (int j = 0; j < bw.img.getHeight(); j++)
      {
        c = new Color(bw.img.getRGB(i, j));

        if (c.equals(Color.BLACK))
        {
          continue;
        }

        t = c.getRed();
        t += c.getGreen();
        t += c.getBlue();
        t = t / 3;

        end = new Color(t, t, t);

        img.setRGB(i, j, end.getRGB());
      }
    }

    bw.img = img;
  }

  //============================================================================
  //====                          Gaussian                                  ====
  //============================================================================
  /**
   * Applies a [3x3] mask based on the gaussian blur methodology.
   *
   * @param bw BufferedWrapper containing original image.
   * @return BufferedWrapper containing your GB'd image.
   */
  public BufferedWrapper GaussianBlur(BufferedWrapper bw)
  {
    BufferedWrapper temp = new BufferedWrapper();
    temp.img = bw.Clone();

    int[] mask = new int[9];

    for (int i = 1; i < temp.img.getWidth() - 1; i++)
    {
      for (int j = 1; j < temp.img.getHeight() - 1; j++)
      {
        mask[0] = bw.img.getRGB(i - 1, j - 1);
        mask[1] = bw.img.getRGB(i, j - 1);
        mask[2] = bw.img.getRGB(i + 1, j - 1);
        mask[3] = bw.img.getRGB(i - 1, j);
        mask[4] = bw.img.getRGB(i, j);
        mask[5] = bw.img.getRGB(i + 1, j);
        mask[6] = bw.img.getRGB(i - 1, j + 1);
        mask[7] = bw.img.getRGB(i, j + 1);
        mask[8] = bw.img.getRGB(i + 1, j + 1);
        //:^Yes this "could" be in a for loop
        //:However, that is just extra computation for a small amount.

        temp.img.setRGB(i, j, gaussian(mask));
      }
    }

    return temp;
  }

  /**
   * Applies a [3x3] mask based on the gaussian blue methodology. Note: Will
   * overwrite bw.img
   *
   * @param bw BufferedWrapper that contains the image to be modified.
   * @returns Nothing. [bw] will contain the final image.
   */
  public static void GaussianBlurStatic(BufferedWrapper bw)
  {
    BufferedImage img = bw.Clone();

    int[] mask = new int[9];

    for (int i = 1; i < img.getWidth() - 1; i++)
    {
      for (int j = 1; j < img.getHeight() - 1; j++)
      {
        mask[0] = bw.img.getRGB(i - 1, j - 1);
        mask[1] = bw.img.getRGB(i, j - 1);
        mask[2] = bw.img.getRGB(i + 1, j - 1);
        mask[3] = bw.img.getRGB(i - 1, j);
        mask[4] = bw.img.getRGB(i, j);
        mask[5] = bw.img.getRGB(i + 1, j);
        mask[6] = bw.img.getRGB(i - 1, j + 1);
        mask[7] = bw.img.getRGB(i, j + 1);
        mask[8] = bw.img.getRGB(i + 1, j + 1);
        //:^Yes this "could" be in a for loop
        //:However, that is just extra computation for a small amount.

        if (mask[4] == 0)
        {
          continue;
        }

        img.setRGB(i, j, gaussian(mask));
      }
    }

    bw.img = img;
  }

  //:simple gaussian finder
  private static int gaussian(int[] mask)
  {
    Color c, end;
    int r = 0, g = 0, b = 0;
    int[] multi = new int[]
    {
      1, 2, 1,
      2, 4, 2,
      1, 2, 1
    };

    for (int i = 0; i < mask.length; i++)
    {
      c = new Color(mask[i]);
      r += c.getRed() * multi[i];
      g += c.getGreen() * multi[i];
      b += c.getBlue() * multi[i];
    }

    end = new Color(r / 16, g / 16, b / 16);

    return end.getRGB();
  }

  //============================================================================
  //====                            Bit                                     ====
  //============================================================================
  /**
   * A Filter for cartoonizing output. Currently supports: 16 bit.
   *
   * @param bw BufferedWrapper containing your image.
   * @param type Filters.SixteenBit || Filters.EightBit
   */
  public static void BitFilter(BufferedWrapper bw, int type)
  {
    BufferedImage temp = bw.Clone();
    int w_Squares, h_Squares, s;
    Color c;

    switch (type)
    {
      //============================================================================
      //====                          16-Bit                                    ====
      //============================================================================
      case SixteenBit:
        int pixels = temp.getWidth() * temp.getHeight();

        if (pixels < 1000000)
        {
          s = 4;
        }
        else
        {
          s = 8;
        }

        w_Squares = temp.getWidth() / s;
        h_Squares = temp.getHeight() / s;

        for (int i = 0; i < w_Squares; i++)
        {
          for (int j = 0; j < h_Squares; j++)
          {
            c = GetColor(temp, i, j, s);
            if (c.getRGB() == Color.BLACK.getRGB())
            {
              continue;
            }

            for (int k = 0; k < s; k++)
            {
              for (int m = 0; m < s; m++)
              {
                temp.setRGB((i * s) + k, (j * s) + m, c.getRGB());
              }
            }
          }
        }

        bw.img = temp;
        break;

      //============================================================================
      //====                          8-Bit                                     ====
      //============================================================================
      case EightBit:
        //:This will not be implemented, all things become indistinguishable
        break;
    }
  }

  private static Color GetColor(BufferedImage temp, int W, int H, int s)
  {
    int r = 0, g = 0, b = 0;
    Color c;

    for (int i = W * s; i < (W * s) + s; i++)
    {
      for (int j = H * s; j < (H * s) + s; j++)
      {
        c = new Color(temp.getRGB(i, j));

        r += c.getRed();
        g += c.getGreen();
        b += c.getBlue();
      }
    }

    r = r / (s * s);
    g = g / (s * s);
    b = b / (s * s);

    c = new Color(r, g, b);

    return c;
  }
}