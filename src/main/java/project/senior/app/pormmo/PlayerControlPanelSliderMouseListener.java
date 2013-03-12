package project.senior.app.pormmo;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author John Fisher
 */
public class PlayerControlPanelSliderMouseListener extends MouseAdapter implements  ChangeListener
{

  private PlayerControlPanel parent;
  private JSlider eventSlider;
  
  public PlayerControlPanelSliderMouseListener(PlayerControlPanel parent)
  {
    this.parent = parent;
  }

  @Override
  public void stateChanged(ChangeEvent e)
  {
    if (parent.parent.userSelectingLocation)
    {
      parent.mPlayer.setPosition((eventSlider.getValue()) * .01f);
    }
  }

  @Override
  public void mousePressed(MouseEvent e)
  {

    eventSlider = (JSlider) e.getSource();

    switch (eventSlider.getName().toLowerCase())
    {
      case "pslider":
        parent.parent.userSelectingLocation = true;
        break;
    }
  }

  @Override
  public void mouseReleased(MouseEvent e)
  {
    switch (eventSlider.getName().toLowerCase())
    {
      case "pslider":
        parent.parent.userSelectingLocation = false;
        break;
    }
  }

}
