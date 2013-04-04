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

/**
 * @author Max De Benedetti
 */
public class OutputProcessing
{

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

    JFrame statusFrame = new JFrame();
    statusFrame.setPreferredSize(new Dimension(500,100));
    JPanel content = new JPanel();
    JLabel status = new JLabel("Currently saving ");
    statusFrame.add(content.add(status));
    
    statusFrame.pack();
    statusFrame.setVisible(true);
    
    for (int i = 0; i < frames.size(); i++)
    {
      try
      {
        bWrap = new BufferedWrapper(frames.get(i));
        gsr.RemoveGreen_3(bWrap, .9f);
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
