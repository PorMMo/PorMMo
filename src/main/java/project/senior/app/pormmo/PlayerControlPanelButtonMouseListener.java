package project.senior.app.pormmo;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import uk.co.caprica.vlcj.binding.internal.libvlc_state_t;

/**
 * @author John Fisher
 */
public class PlayerControlPanelButtonMouseListener extends MouseAdapter
{

  private PlayerControlPanel parent;

  public PlayerControlPanelButtonMouseListener(PlayerControlPanel parent)
  {
    this.parent = parent;
  }

  @Override
  public void mouseReleased(MouseEvent e)
  {
    JButton eventSource = null;

    //Process the button mouse activities
    if (e.getSource() instanceof JButton)
    {
      eventSource = (JButton) e.getSource();

      if (parent.GetInputFile() == null)
      {
        JOptionPane.showMessageDialog(null, "Please choose a file via the File menu");
      }
      else
      {
        switch (eventSource.getName().toLowerCase())
        {
          case "pause":
          case "play":
            if (parent.mPlayer.getMediaPlayerState() == libvlc_state_t.libvlc_Stopped)
            {
              parent.mPlayer.playMedia(parent.inputFile.getAbsolutePath());
            }

            if ((parent.mPlayer.canPause() && parent.mPlayer.getMediaPlayerState() == libvlc_state_t.libvlc_Playing)
                    || parent.mPlayer.getMediaPlayerState() == libvlc_state_t.libvlc_Paused)
            {
              parent.mPlayer.pause();
            }

            if (eventSource.getText().equals("Play"))
            {
              eventSource.setText("Pause");
            }
            else
            {
              eventSource.setText("Play");
            }

            break;

          case "stop":
            parent.mPlayer.stop();
            parent.FindButtonByName("Play").setText("Play");
            break;

          case "forward":
            parent.mPlayer.skip(1000);
            break;

          case "rewind":
            parent.mPlayer.skip(-1000);
            break;

          case "save":
            if (parent.lastSnapshot != null)
            {
              SnapshotSave sS = new SnapshotSave(parent.lastSnapshot);
            }
            break;

          case "reset":
            break;

          case "snapshot":
            parent.DisplayLastSnapshot(parent.mPlayer.getSnapshot());
            break;

        }
      }
    }
  }
}
