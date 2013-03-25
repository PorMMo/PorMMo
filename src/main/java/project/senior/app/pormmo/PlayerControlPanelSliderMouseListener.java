package project.senior.app.pormmo;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author John Fisher
 */
public class PlayerControlPanelSliderMouseListener 
extends MouseAdapter 
implements  ChangeListener
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
      eventSlider = (JSlider) e.getSource();
      parent.parent.mPlayer.setPosition((eventSlider.getValue()) * .01f);
    }
  }

  @Override
  public void mousePressed(MouseEvent e)
  {
    parent.parent.userSelectingLocation = true;
  }

  @Override
  public void mouseReleased(MouseEvent e)
  {
    parent.parent.userSelectingLocation = false;
  }

}
