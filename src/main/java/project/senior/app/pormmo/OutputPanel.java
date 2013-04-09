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

//  private BufferedImage bi;
  public GSR gSR;
  private BorderLayout bLayout;
  private boolean isCropping = false;
  private Point startCropPoint, stopCropPoint;
  protected UserInterfaceFrame parent;

  public OutputPanel()
  {
    gSR = new GSR();
    bLayout = new BorderLayout();
    setLayout(bLayout);
    setControls();
  }

  public OutputPanel(UserInterfaceFrame parent)
  {
    gSR = new GSR();
    bLayout = new BorderLayout();
    setLayout(bLayout);
    setControls();

    this.parent = parent;
  }

  public BufferedImage GetLatestBI()
  {
    return parent.ic.getCurrentlyDisplayImage();
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
          if (!IsReady())
          {
            return;
          }

          isCropping = true;
          startCropPoint = e.getPoint();
        }

        @Override
        public void mouseReleased(MouseEvent e)
        {
          if (!IsReady())
          {
            return;
          }
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
          if (!isCropping || !IsReady())
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
    BufferedImage temp = new BufferedImage(stopCropPoint.x - startCropPoint.x, stopCropPoint.y - startCropPoint.y, parent.ic.getCurrentlyDisplayImage().getType());
    BufferedImage currentDisplay = parent.ic.getCurrentlyDisplayImage();
    
    int w = stopCropPoint.x - startCropPoint.x;
    int h = stopCropPoint.y - startCropPoint.y;

    parent.seqSet.setCropStart(startCropPoint);
    parent.seqSet.setCropStop(stopCropPoint);
            
    if(w>currentDisplay.getWidth()) w=currentDisplay.getWidth();
    if(h>currentDisplay.getHeight()) h=currentDisplay.getHeight();

    parent.seqSet.setWidth(w);
    parent.seqSet.setHeight(h);
    
    for (int i = 0; i < w; i++)
    {
      for (int j = 0; j < h; j++)
      {
        temp.setRGB(i, j, currentDisplay.getRGB(startCropPoint.x + i, startCropPoint.y + j));
      }
    }

    int response = JOptionPane.showConfirmDialog(
            this, "Is this the what you want?", "Confirm", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, new ImageIcon(temp));

    if (response == JOptionPane.OK_OPTION)
    {
      parent.ic.setCurrentlyDisplayImage(temp);
      
      repaint();
      setSize(w, h);
    }

  }

  public void ReDrawOutputPanel()
  {
    this.setSize(new Dimension(parent.ic.getCurrentDisplayImageDimension()));
    repaint();
  }

  public void DrawBufferedImage(BufferedImage bi)
  {
    if (bi != null)
    {
      parent.ic.setCurrentlyDisplayImage(bi);

      setSize(parent.ic.getCurrentDisplayImageDimension());
      setMinimumSize(parent.ic.getCurrentDisplayImageDimension());

      this.repaint();
      this.validate();
    }
  }

  public boolean IsReady()
  {
    return parent.ic.getCurrentlyDisplayImage() != null;
  }

  @Override
  public void paintComponent(Graphics page)
  {
    Graphics2D g2d = (Graphics2D) page;

    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    page.clearRect(0, 0, this.getWidth(), this.getHeight());

    if (parent.ic.getCurrentlyDisplayImage() != null)
    {
      page.drawImage(parent.ic.getCurrentlyDisplayImage(), 0, 0, this);
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
