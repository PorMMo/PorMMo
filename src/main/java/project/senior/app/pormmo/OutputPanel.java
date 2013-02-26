package project.senior.app.pormmo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author John Fisher, Shelby Copeland
 */
public class OutputPanel extends JPanel
{

  private BufferedImage bi;
  public GSR gSR;
  private BorderLayout bLayout;
  private boolean isCropping = false;
  private Point startCropPoint, stopCropPoint;

  public OutputPanel()
  {
    gSR = new GSR();
    bLayout = new BorderLayout();
    setLayout(bLayout);
    setControls();
  }
  
  public BufferedImage GetLatestBI()
  {
    return bi;
  }

  private void setControls()
  {
    //if (bi == null)
    {
      addMouseListener(new MouseAdapter()
      {
        @Override
        public void mousePressed(MouseEvent e)
        {
          isCropping = true;
          startCropPoint = e.getPoint();
        }

        @Override
        public void mouseReleased(MouseEvent e)
        {
          isCropping = false;
          stopCropPoint = e.getPoint();

          CheckPoints();
          Crop();
        }

        @Override
        public void mouseExited(MouseEvent e)
        {
          isCropping = false;
        }
      });

      addMouseMotionListener(new MouseMotionListener()
      {
        @Override
        public void mouseDragged(MouseEvent e)
        {
          if (!isCropping)
          {
            return;
          }

          stopCropPoint = e.getPoint();
          repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e)
        {
        }
      });
    }
  }

  private void CheckPoints()
  {
    if (startCropPoint.x > stopCropPoint.x)
    {
      int temp = stopCropPoint.x;
      stopCropPoint.x = startCropPoint.x;
      startCropPoint.x = temp;
    }
    if (startCropPoint.y > stopCropPoint.y)
    {
      int temp = stopCropPoint.y;
      stopCropPoint.y = startCropPoint.y;
      startCropPoint.y = temp;
    }
  }

  public void Crop()
  {
    BufferedImage temp = new BufferedImage(stopCropPoint.x - startCropPoint.x, stopCropPoint.y - startCropPoint.y, bi.getType());

    int w = stopCropPoint.x - startCropPoint.x;
    int h = stopCropPoint.y - startCropPoint.y;

    for (int i = 0; i < w; i++)
    {
      for (int j = 0; j < h; j++)
      {
        temp.setRGB(i, j, bi.getRGB(startCropPoint.x + i, startCropPoint.y + j));
      }
    }

    int response = JOptionPane.showConfirmDialog(
            this, "Is this the what you want?", "Confirm", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, new ImageIcon(temp));

    if (response == JOptionPane.OK_OPTION)
    {
      bi = temp;
      repaint();
      setSize(w, h);
    }

  }

  public void DrawBufferedImage(BufferedImage bi)
  {
    if (bi != null)
    {
      this.bi = bi;

//            setSliders();

      setSize(new Dimension(bi.getWidth(), bi.getHeight()));
      setMinimumSize(new Dimension(bi.getWidth(), bi.getHeight()));

      this.repaint();
      this.validate();
    }
  }

  public void ReDrawBufferedImage(float T, float H)
  {
    if (bi != null)
    {
      bi = gSR.RemoveGreen_3(bi, T);//, H);
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
    Graphics2D g2d = (Graphics2D) page;

    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    page.clearRect(0, 0, this.getWidth(), this.getHeight());
    page.drawImage(bi, 0, 0, this);

    if (bi != null)
    {
      page.setColor(Color.red);
      if (isCropping)
      {
        page.drawRect(((startCropPoint.x > stopCropPoint.x) ? stopCropPoint.x : startCropPoint.x), 
                       ((startCropPoint.y > stopCropPoint.y) ? stopCropPoint.y : startCropPoint.y), 
                         Math.abs(stopCropPoint.x - startCropPoint.x), 
                           Math.abs(stopCropPoint.y - startCropPoint.y));
      }
    }
  }
}
