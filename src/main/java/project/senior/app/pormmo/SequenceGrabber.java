package project.senior.app.pormmo;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import uk.co.caprica.vlcj.player.MediaPlayer;

/**
 * @author Shelby Copeland, Max De Bennedetti, John Fisher
 */
public class SequenceGrabber extends JPanel
{

  private Timer snapshotTimer;
  private OutputProcessing op;
  protected UserInterfaceFrame parent;
  private MediaPlayer mPlayer;
  private long start = 0, end = 0;
  private String savePath;
  private JLabel pos;
  private JTextField fps;

  public SequenceGrabber(UserInterfaceFrame parent)
  {
    this.parent = parent;
    mPlayer = parent.mPlayer;

    op = new OutputProcessing(this);
    initForm();
  }

  private void initForm()
  {
    GridBagLayout gbl = new GridBagLayout();
    GridBagConstraints gbs = new GridBagConstraints();

    setLayout(gbl);
    ButtonListener bl = new ButtonListener();

    JButton markStart = new JButton("Sequence Start: ");
    markStart.setName("Start");
    markStart.addMouseListener(bl);
    JButton markEnd = new JButton("Sequence Stop: ");
    markEnd.setName("End");
    markEnd.addMouseListener(bl);
    fps = new JTextField("10");
    fps.setPreferredSize(new Dimension(50, 20));
    JButton saveSequence = new JButton("Save Sequence");
    saveSequence.setName("SaveSequence");
    saveSequence.addMouseListener(bl);

    gbs.gridx = 0;
    gbs.gridy = 0;
    add(markStart, gbs);

    gbs.gridx = 0;
    gbs.gridy = 1;
    add(markEnd, gbs);

    JPanel groupPan = new JPanel();
    groupPan.add(new JLabel("FPS"));
    groupPan.add(fps);

    gbs.gridx = 0;
    gbs.gridy = 2;
    add(groupPan, gbs);

    gbs.gridx = 0;
    gbs.gridy = 3;
    add(saveSequence, gbs);

    pos = new JLabel("0");
    gbs.gridx = 0;
    gbs.gridy = 4;
    add(pos, gbs);

  }

  private class ButtonListener extends MouseAdapter
  {

    @Override
    public void mouseReleased(MouseEvent e)
    {
      JButton sourceButton = (JButton) e.getSource();

      switch (sourceButton.getName())
      {
        case "Start":
          start = mPlayer.getTime();
          sourceButton.setText("Sequence Start: " + start);
          parent.repaint();
          break;
        case "End":
          end = mPlayer.getTime();
          sourceButton.setText("Sequence End: " + end);
          if(mPlayer.isPlaying()) mPlayer.pause();

          break;
        case "SaveSequence":
          if (start > 0 && end > 0)
          {
            if (start < end)
            {
              JFileChooser jfc = new JFileChooser("Please choose destination folder for the sequence image(s) and enter a prefix for the images.");
              jfc.showSaveDialog(null);
              if (jfc.getSelectedFile() != null)
              {
                snapshotTimer = new Timer();
                long rate = 1000 / Long.parseLong(fps.getText());
                mPlayer.setTime(start);
                mPlayer.start();
                snapshotTimer.purge();
                snapshotTimer.scheduleAtFixedRate(new Shutter(jfc.getSelectedFile().toString()), 0, rate);
              }
            }
            else
            {
              JOptionPane.showMessageDialog(null, "Unable to Save Sequence! Start position must be less than the end position.");
            }
          }
          else
          {
            JOptionPane.showMessageDialog(null, "Unable to Save Sequence! Must have a start and end position.");
          }

          break;
      }
    }
  }

  private class Shutter extends TimerTask
  {
    private ArrayList<BufferedImage> frames;
    private String path;

    public Shutter(String path)
    {
      this.path = path;
      
      frames = new ArrayList<>();
    }

    @Override
    public void run()
    {
      if ((mPlayer.getTime() < end) && mPlayer.isPlaying())//fixes a bug that occurs if the video is paused or stopped while frame grabbing
      {        
        frames.add(mPlayer.getSnapshot());
      }
      else
      {
        snapshotTimer.cancel();
        mPlayer.setPause(true);
        op.setFrames(frames);
        op.processFrames(path);
      }
    }
  }
}
