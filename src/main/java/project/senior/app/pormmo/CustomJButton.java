package project.senior.app.pormmo;

import java.awt.event.MouseListener;
import javax.swing.JButton;

/**
 * @author John Fisher
 */
public class CustomJButton extends JButton
{
  private int gridXPosition, gridYPosition, hGridSpan;

  public CustomJButton(
          String buttonText
          , int gridXPosition
          , int gridYPosition
          , int hGridSpan
          , MouseListener buttonListener)
  {
    setText(buttonText);
    setName(buttonText.toLowerCase().replace(" ", ""));
    addMouseListener(buttonListener);
    this.gridXPosition = gridXPosition;
    this.gridYPosition = gridYPosition;
    this.hGridSpan = hGridSpan;
  }

  public String GetButtonText()
  {
    return getText();
  }

  public int GetGridXPosition()
  {
    return gridXPosition;
  }

  public int GetGridYPosition()
  {
    return gridYPosition;
  }
  
  public int GetGridHSpan()
  {
    return hGridSpan;
  }

  public String GetButtonName()
  {
    return getName();
  }
}
