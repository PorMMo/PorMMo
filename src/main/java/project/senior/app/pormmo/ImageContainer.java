package project.senior.app.pormmo;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

/**
 * @author Shelby Copeland, Max De Bennedetti, John Fisher
 */
public class ImageContainer {

  private BufferedImage currentDisplayImage;
  private BufferedImage lastSnapshot;
  private BufferedImage currentDisplayImageBackup;

  public BufferedImage getCurrentlyDisplayImage()
  {
    return currentDisplayImage;
  }
  
  public Dimension getCurrentDisplayImageDimension()
  {
    return new Dimension(currentDisplayImage.getWidth(), currentDisplayImage.getHeight());
  }
  
  public void setCurrentlyDisplayImage(BufferedImage currentlyDisplayImage)
  {
    this.currentDisplayImage = currentlyDisplayImage;
  }

  public BufferedImage getLastSnapshot()
  {
    return BufferedWrapper.CloneImg(lastSnapshot);
  }

  public void setLastSnapshot(BufferedImage lastSnapshot)
  {
    this.lastSnapshot = lastSnapshot;
  }

  public BufferedImage getCurrentDisplayImageBackup()
  {
    return currentDisplayImageBackup;
  }

  public void setCurrentDisplayImageBackup()
  {
    this.currentDisplayImageBackup = new BufferedImage(
            currentDisplayImage.getWidth()
            , currentDisplayImage.getHeight()
            , currentDisplayImage.getType());
  }
  
  public void backupCurrentDisplayImage()
  {
    this.currentDisplayImageBackup = new BufferedImage(
            currentDisplayImage.getWidth()
            , currentDisplayImage.getHeight()
            , currentDisplayImage.getType());
  }
  
  public void restoreCurrentDisplayImage()
  {
    this.currentDisplayImage = new BufferedImage(
            currentDisplayImageBackup.getWidth()
            , currentDisplayImageBackup.getHeight()
            , currentDisplayImageBackup.getType());
  }
  
  public ImageContainer()
  {
    currentDisplayImage = null;
    lastSnapshot=null;
    currentDisplayImageBackup=null;
  }
}
