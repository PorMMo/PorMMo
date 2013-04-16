package project.senior.app.pormmo;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * @author John Fisher
 */
public class Saturation
{

  public Saturation()
  {
  }

  public static void AdjustSaturation(BufferedWrapper bw, int adjustQuantity)
  {
    Color newColor;
    float[] hsbvals = new float[3];
    float saturation;
    BufferedImage bi2 = new BufferedImage(bw.img.getWidth(), bw.img.getHeight(), bw.img.getType());

    for (int x = 0; x < bw.img.getWidth(); x++)
    {
      for (int y = 0; y < bw.img.getHeight(); y++)
      {
        if(bw.img.getRGB(x,y) == 0) continue;
        newColor = new Color(bw.img.getRGB(x, y));
        Color.RGBtoHSB(newColor.getRed(), newColor.getGreen(), newColor.getBlue(), hsbvals);

        saturation = adjustQuantity * .001f;
        if (saturation < -.9f)
        {
          saturation = (-.9f);
        }
        if (saturation > 1f)
        {
          saturation = 1f;
        }

        bi2.setRGB(x, y,
                Color.HSBtoRGB(
                hsbvals[0],
                (hsbvals[1] + saturation < 0)
                ? 0 : (hsbvals[1] + (saturation) > 1.0f)
                ? 1.0f : hsbvals[1] + saturation,
                hsbvals[2]));
      }
    }

    bw.img = bi2;
  }
}
