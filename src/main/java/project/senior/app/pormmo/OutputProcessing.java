package project.senior.app.pormmo;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
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
    content.add(status);


    JProgressBar bar = new JProgressBar(0, frames.size());
    bar.setValue(0);
    bar.setStringPainted(true);
    statusFrame.add(bar);
    content.add(bar);

    statusFrame.add(content);
    statusFrame.pack();
    statusFrame.setVisible(true);
    int failcount = 0;

    String logOfFailedSaves = "";

    for (int i = 0; i < frames.size(); i++)
    {
      try
      {
        currentImage = frames.get(i);
        bWrap = new BufferedWrapper(currentImage);

        //if there was no cropping, don't create a cropped image
        if (parent.parent.seqSet.getCropStart() != null)
        {
          cropWidth = parent.parent.seqSet.getWidth() > 0 ? parent.parent.seqSet.getWidth() : currentImage.getWidth();
          cropHeight = parent.parent.seqSet.getHeight() > 0 ? parent.parent.seqSet.getHeight() : currentImage.getHeight();

          cropStartX = parent.parent.seqSet.getCropStart().getX() > parent.parent.seqSet.getCropStop().getX() ? (int) parent.parent.seqSet.getCropStop().getX() : (int) parent.parent.seqSet.getCropStart().getX();
          cropStartY = parent.parent.seqSet.getCropStart().getY() > parent.parent.seqSet.getCropStop().getY() ? (int) parent.parent.seqSet.getCropStop().getY() : (int) parent.parent.seqSet.getCropStart().getY();

          bWrap = new BufferedWrapper(currentImage.getSubimage(cropStartX, cropStartY, cropWidth, cropHeight));
        }

        parent.parent.seqOrder.ApplySequence(bWrap, parent.parent.seqSet.getGsrTolerance());

        ImageIO.write(bWrap.img, "png", new File(path + "-" + i + ".png"));

      }
      catch (IOException | java.lang.NullPointerException ex)
      {
        logOfFailedSaves = logOfFailedSaves + path + "-" + i + ".png" + "\n";
        failcount++;
      }
      //moved progress to outside the try catch loop
      status.setText("Currently saving " + path + "-" + i + ".png");
      bar.setValue(i);
    }
    statusFrame.setVisible(false);

    if (failcount > 0)
    {
      logOfFailedSaves = "Images that failed to save: \n" + logOfFailedSaves;
      JOptionPane.showMessageDialog(parent, logOfFailedSaves);
    }
  }
}
