package project.senior.app.pormmo;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * @author Fisher
 */
public class OutputPanel extends JPanel
{

  private BufferedImage bi;
  GSR gSR;

  public OutputPanel()
  {
    gSR = new GSR();
  }

  public void DrawBufferedImage(BufferedImage bi)
  {
    if (bi != null)
    {
      this.bi = bi;
      setSize(new Dimension(bi.getWidth(), bi.getHeight()));
      setMinimumSize(new Dimension(bi.getWidth(), bi.getHeight()));
      this.repaint();
    }
  }
  
  public void ReDrawBufferedImage(float T, float H)
  {
    if (bi != null)
    {
      bi =gSR.RemoveGreen_2(bi, T, H);    
      this.repaint();
    }
  }
  
  public boolean IsReady()
  {
    return bi != null;
  }

  @Override
  public void paintComponent(Graphics page)
  {
    Graphics2D g2d = (Graphics2D)page;
    
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    page.clearRect(0, 0, this.getWidth(), this.getHeight());
    page.drawImage(bi, 0, 0, this);
  }
}
