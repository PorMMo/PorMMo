package project.senior.app.pormmo;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JSlider;
import uk.co.caprica.vlcj.binding.internal.libvlc_state_t;

/**
 * @author John Fisher
 */
public class PlayerControlPanelSliderMouseListener extends MouseAdapter
{

  private PlayerControlPanel parent;

  public PlayerControlPanelSliderMouseListener(PlayerControlPanel parent)
  {
    this.parent = parent;
  }

  @Override
  public void mouseReleased(MouseEvent e)
  {
    JSlider eventSource;

    //Process the button mouse activities
    eventSource = (JSlider) e.getSource();

    switch (eventSource.getName().toLowerCase())
    {
      case "PlayPosition":
        if (parent.mPlayer.getMediaPlayerState() == libvlc_state_t.libvlc_Stopped)
        {
          parent.mPlayer.playMedia(parent.inputFile.getAbsolutePath());
        }

        if ((parent.mPlayer.canPause() && parent.mPlayer.getMediaPlayerState() == libvlc_state_t.libvlc_Playing)
                || parent.mPlayer.getMediaPlayerState() == libvlc_state_t.libvlc_Paused)
        {
          parent.mPlayer.pause();
        }

        break;

    }
  }
}
