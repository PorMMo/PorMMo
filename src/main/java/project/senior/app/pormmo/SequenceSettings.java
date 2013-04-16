package project.senior.app.pormmo;

import java.awt.Point;

/**
 * @author Shelby Copeland, Max De Bennedetti, John Fisher
 */
public class SequenceSettings {

  private Point cropStart, cropStop;
  private int width, height;
  private float gsrTolerance;
  private int brightness, saturation;  

  public int getBrightness()
  {
    return brightness;
  }

  public void setBrightness(int brightness)
  {
    this.brightness = brightness;
  }

  public int getSaturation()
  {
    return saturation;
  }

  public void setSaturation(int saturation)
  {
    this.saturation = saturation;
  }

  public float getGsrTolerance()
  {
    return gsrTolerance;
  }

  public void setGsrTolerance(float gsrTolerance)
  {
    this.gsrTolerance = gsrTolerance;
  }

  public SequenceSettings()
  {
    cropStart = null;
    cropStop = null;
    width = 0;
    height = 0;
  }

  public int getWidth()
  {
    return width;
  }

  public void setWidth(int width)
  {
    this.width = width;
  }

  public int getHeight()
  {
    return height;
  }

  public void setHeight(int height)
  {
    this.height = height;
  }
  
  public Point getCropStart()
  {
    return cropStart;
  }

  public void setCropStart(Point cropStart)
  {
    this.cropStart = cropStart;
  }

  public Point getCropStop()
  {
    return cropStop;
  }

  public void setCropStop(Point cropStop)
  {
    this.cropStop = cropStop;
  }
}
