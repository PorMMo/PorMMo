package project.senior.app.pormmo;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * @author John Fisher
 */
public class Brightness
{
  private HSBPanel parent;
  
  public Brightness(HSBPanel parent)
  {
    this.parent = parent;
  }

  public static void AdjustBrightness(BufferedWrapper bw, int adjustQuantity)
  {
    float[] hsbvals = new float[3];
    float brightness;
    Color newColor;
    BufferedImage bi2 = new BufferedImage(bw.img.getWidth(), bw.img.getHeight(), bw.img.getType());

    for (int x = 0; x < bw.img.getWidth(); x++)
    {
      for (int y = 0; y < bw.img.getHeight(); y++)
      {
        newColor = new Color(bw.img.getRGB(x, y));
        Color.RGBtoHSB(newColor.getRed(), newColor.getGreen(), newColor.getBlue(), hsbvals);

        brightness = adjustQuantity * .001f;
        if (brightness < -.9f)
        {
          brightness = (-.9f);
        }

        if (brightness > 1f)
        {
          brightness = 1f;
        }

        bi2.setRGB(x, y, Color.HSBtoRGB(
                hsbvals[0], hsbvals[1], (hsbvals[2] + (brightness) < 0) ? 0
                : (hsbvals[2] + (brightness) > 1.0f) ? 1.0f
                : hsbvals[2] + brightness));
      }
    }
    bw.img = bi2;
  }
}