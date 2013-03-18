package project.senior.app.pormmo;

import javax.swing.JSlider;

/**
 * @author John Fisher
 */
public class CustomJSlider extends JSlider
{
  private int gridXPosition, gridYPosition, hGridSpan;

  public CustomJSlider(
          String sliderName
          , int gridXPosition
          , int gridYPosition
          , int hGridSpan 
          , PlayerControlPanelSliderMouseListener sliderListener)
  {
    setName(sliderName.toLowerCase().replace(" ", ""));
    addMouseListener(sliderListener);
    addChangeListener(sliderListener);
    this.gridXPosition = gridXPosition;
    this.gridYPosition = gridYPosition;
    this.hGridSpan = hGridSpan;
  }

  public int GetGridXPosition()
  {
    return gridXPosition;
  }

  public int GetGridYPosition()
  {
    return gridYPosition;
  }
  
  public int GetGridVSpan()
  {
    return hGridSpan;
  }

  public String GetSliderName()
  {
    return getName();
  }
}
