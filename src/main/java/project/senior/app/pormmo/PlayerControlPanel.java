package project.senior.app.pormmo;

import com.sun.jna.Native;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

/**
 * @author John Fisher
 */
public class PlayerControlPanel extends JPanel
{

  private GridBagLayout layout;
  private GridBagConstraints gbc;
  private ArrayList<CustomJButton> panelButtons;
  private ArrayList<CustomJSlider> panelSliders;
  private ArrayList<String> panelNames;
  protected MediaPlayer mPlayer;
  protected File inputFile;
  private PlayerControlPanelButtonMouseListener pcpbml;
  private PlayerControlPanelSliderMouseListener pcpsml;
  private JFrame playerFrame;
  private EmbeddedMediaPlayerComponent mediaPlayerComponent;
  private Dimension screenSize;
  private MediaPlayerFactory mPlayerFactory;
  protected BufferedImage lastSnapshot;
  protected UserInterfaceFrame parent;
  protected PlayerEventListener pel;

  public PlayerControlPanel(UserInterfaceFrame parent, MediaPlayer mPlayer)
  {
    layout = new GridBagLayout();
    gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    setLayout(layout);

    screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    this.mPlayer = mPlayer;

    this.parent = parent;

    panelButtons = new ArrayList<>();
    panelSliders = new ArrayList<>();
    panelNames = new ArrayList<>();

    inputFile = null;
    pcpbml = new PlayerControlPanelButtonMouseListener(this);
    pcpsml = new PlayerControlPanelSliderMouseListener(this);
    initPlayer();
  }

  private void initPlayer()
  {
    Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);

    playerFrame = new JFrame("PorMMo Video Player (Embedded VLC)");
    playerFrame.setSize(
            new Dimension((int) screenSize.getWidth() / 2, (int) screenSize.getHeight()));

    playerFrame.setLocation((int) screenSize.getWidth() / 2, 0);
    playerFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

    mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
    playerFrame.setContentPane(mediaPlayerComponent);
    mPlayer = mediaPlayerComponent.getMediaPlayer();
    pel = new PlayerEventListener(this, mPlayer);
    mPlayer.addMediaPlayerEventListener(pel);
  }

  public void SetInputFile(File inputFile)
  {
    this.inputFile = inputFile;
    FindButtonByName("Play").setText("Pause");
    playerFrame.setVisible(true);
    mediaPlayerComponent.
            getMediaPlayer().
            playMedia(inputFile.getAbsolutePath());
    mediaPlayerComponent.
            getMediaPlayer().
            addMediaPlayerEventListener(new PlayerEventListener(this, mPlayer));
  }

  public File GetInputFile()
  {
    return inputFile;
  }

  public BufferedImage GetLastSnapshot()
  {
    return lastSnapshot;
  }

  public void DisplayLastSnapshot(BufferedImage givenImage)
  {
    parent.processSnapshot(givenImage);
  }

  public void AddJButton(
          String buttonText, int gridXPosition, int gridYPosition, int HorizontalGridSpan)
  {
    CustomJButton newButton = new CustomJButton(
            buttonText, gridXPosition, gridYPosition, HorizontalGridSpan, pcpbml);
    panelNames.add(newButton.GetButtonName());
    gbc.gridx = gridXPosition;
    gbc.gridy = gridYPosition;
    gbc.gridwidth = HorizontalGridSpan;
    panelButtons.add(newButton);
    add(newButton, gbc);
  }

  public void AddJSlider(
          String sliderName, int gridXPosition, int gridYPosition, int HorizontalGridSpan)
  {
    CustomJSlider newSlider = new CustomJSlider(
            sliderName, gridXPosition, gridYPosition, HorizontalGridSpan, pcpsml);
    newSlider.setValue(0);
    panelNames.add(newSlider.GetSliderName());
    gbc.gridx = gridXPosition;
    gbc.gridy = gridYPosition;
    gbc.gridwidth = HorizontalGridSpan;
    panelSliders.add(newSlider);
    add(newSlider, gbc);
  }

  public JButton FindButtonByName(String buttonName)
  {
    JButton currentButton = null;

    for (int i = 0; i < panelButtons.size(); i++)
    {
      currentButton = panelButtons.get(i);

      if (currentButton != null
              && currentButton.getName().toLowerCase().equals(buttonName.toLowerCase()))
      {
        return currentButton;
      }
    }

    return null;
  }

  public JSlider FindSliderByName(String sliderName)
  {
    JSlider currentSlider = null;

    for (int i = 0; i < panelSliders.size(); i++)
    {
      currentSlider = panelSliders.get(i);

      if (currentSlider != null
              && currentSlider.getName().toLowerCase().equals(sliderName.toLowerCase()))
      {
        return currentSlider;
      }
    }

    return null;
  }
  
//  private class ButtonListener extends MouseAdapter
//  {
//
//    @Override
//    public void mouseReleased(MouseEvent e)
//    {
//      Object eventSource = null;
//      JButton eventButton;
//      JSlider eventSlider;
//
//      //Process the button mouse activities
//      if (e.getSource() instanceof JButton)
//      {
//        eventButton = (JButton) e.getSource();
//
//        if (inputFile == null)
//        {
//          JOptionPane.showMessageDialog(null, "Please choose a file via the File menu");
//        }
//        else
//        {
//          switch (eventButton.getName().toLowerCase())
//          {
//            case "pause":
//            case "play":
//              if (mPlayer.getMediaPlayerState() == libvlc_state_t.libvlc_Stopped)
//              {
//                mPlayer.playMedia(inputFile.getAbsolutePath());
//              }
//
//              if ((mPlayer.canPause() && mPlayer.getMediaPlayerState() == libvlc_state_t.libvlc_Playing)
//                      || mPlayer.getMediaPlayerState() == libvlc_state_t.libvlc_Paused)
//              {
//                mPlayer.pause();
//              }
//
//              if (eventButton.getText().equals("Play"))
//              {
//                eventButton.setText("Pause");
//              }
//              else
//              {
//                eventButton.setText("Play");
//              }
//
//              break;
//
//            case "stop":
//              mPlayer.stop();
//              break;
//
//            case "forward":
//              mPlayer.skip(1000);
//              break;
//
//            case "rewind":
//              mPlayer.skip(-1000);
//              break;
//
//            case "save":
//              if (lastSnapshot != null)
//              {
//                SnapshotSave sS = new SnapshotSave(lastSnapshot);
//              }
//              break;
//
//            case "reset":
//              break;
//
//            case "snapshot":
//              lastSnapshot = mPlayer.getSnapshot();
//              break;
//
//          }
//
//        }
//      }
//
//      //Process the slider mouse activities
//      //Code here
//    }
//  }

  private class SliderListener extends MouseAdapter
  {

    @Override
    public void mouseReleased(MouseEvent e)
    {
    }
  }
}