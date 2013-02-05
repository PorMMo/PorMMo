package project.senior.app.pormmo;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * @author Fisher
 */
public class OutputPanel extends JPanel
{

  private BufferedImage bi;

  public OutputPanel()
  {
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

  @Override
  public void paintComponent(Graphics page)
  {
    page.clearRect(0, 0, this.getWidth(), this.getHeight());
    page.drawImage(bi, 0, 0, this);
  }
}
