package project.senior.app.pormmo;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import uk.co.caprica.vlcj.player.MediaPlayer;

/**
 * @author Max De Benedetti
 */
public class OutputProcessing
{
  SequenceGrabber parent;
  MediaPlayer mPlayer;
  
  public OutputProcessing(SequenceGrabber parent)
  {
    this.parent = parent;
    mPlayer = parent.parent.mPlayer;
  }
  
  private ArrayList<BufferedImage> frames;

  public ArrayList<BufferedImage> getFrames()
  {
    return frames;
  }

  public void setFrames(ArrayList<BufferedImage> frames)
  {
    this.frames = frames;
  }

  public void processFrames(String path)
  {
    GSR gsr = new GSR();
    BufferedWrapper bWrap;
    BufferedImage currentImage;
    int cropStartX, cropStartY, cropWidth, cropHeight;

    JFrame statusFrame = new JFrame();
    statusFrame.setPreferredSize(new Dimension(500, 100));
    JPanel content = new JPanel();
    JLabel status = new JLabel("Currently saving ");
    statusFrame.add(content.add(status));

    statusFrame.pack();
    statusFrame.setVisible(true);

    for (int i = 0; i < frames.size(); i++)
    {
      try
      {
        currentImage = frames.get(i);
        cropWidth = parent.parent.seqSet.getWidth() > 0 ? parent.parent.seqSet.getWidth() : currentImage.getWidth();
        cropHeight = parent.parent.seqSet.getHeight() > 0 ? parent.parent.seqSet.getHeight() : currentImage.getHeight();
        cropStartX = parent.parent.seqSet.getCropStart().getX() > parent.parent.seqSet.getCropStop().getX() ? (int)parent.parent.seqSet.getCropStop().getX(): (int)parent.parent.seqSet.getCropStart().getX();
        cropStartY = parent.parent.seqSet.getCropStart().getY() > parent.parent.seqSet.getCropStop().getY() ? (int)parent.parent.seqSet.getCropStop().getY(): (int)parent.parent.seqSet.getCropStart().getY();
        
        bWrap = new BufferedWrapper(currentImage.getSubimage(cropStartX, cropStartY, cropWidth, cropHeight));
        gsr.RemoveGreen_3(bWrap, parent.parent.seqSet.getGsrTolerance());
        status.setText("Currently saving " + path + "-" + i + ".png");
        ImageIO.write(bWrap.img, "png", new File(path + "-" + i + ".png"));
      }
      catch (IOException ex)
      {
      }
    }
    statusFrame.setVisible(false);
  }
}
