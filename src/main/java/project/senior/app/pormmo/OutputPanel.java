package project.senior.app.pormmo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Fisher
 */
public class OutputPanel extends JPanel {

    private BufferedImage bi;
    public GSR gSR;
    private JSlider left, right, top, bottom;
    private BorderLayout bLayout;
    private Point masks[];

    public OutputPanel() {
        gSR = new GSR();        
        masks = new Point[4];
        bLayout = new BorderLayout();
        setLayout(bLayout);
        setControls();
    }

    private void setControls()
    {
        if(bi==null)
        {
            masks[0] = new Point(0,0);
            masks[1] = new Point(0,0);
            masks[2] = new Point(0,0);
            masks[3] = new Point(0,0);    
        }
    }
    private void setSliders()
    {
        left = new JSlider();
        right = new JSlider();
        top = new JSlider();
        bottom = new JSlider();
        
        left.setMinimum(0);
        left.setMaximum(bi.getWidth());
        left.setValue(0);
        left.setName("left");
        left.addChangeListener(new SliderListener());
        add(left, BorderLayout.NORTH);

        right.setMinimum(0);
        right.setName("right");
        right.addChangeListener(new SliderListener());
        right.setMaximum(bi.getWidth());
        right.setValue(bi.getWidth());
        add(right, BorderLayout.SOUTH);

        top.setMaximum(bi.getHeight());
        top.setMinimum(0);
        top.setValue(0);
        top.setOrientation(SwingConstants.VERTICAL);
        top.setName("top");
        top.addChangeListener(new SliderListener());
        add(top, BorderLayout.WEST);
        
        bottom.setMaximum(bi.getHeight());
        bottom.setValue(bi.getHeight());
        bottom.setMinimum(0);
        bottom.setOrientation(SwingConstants.VERTICAL);
        bottom.setName("bottom");
        bottom.addChangeListener(new SliderListener());
        add(bottom, BorderLayout.EAST);
    }
    
    public void DrawBufferedImage(BufferedImage bi) {
        if (bi != null) {
            this.bi = bi;

            setSliders();
            
            setSize(new Dimension(bi.getWidth(), bi.getHeight()));
            setMinimumSize(new Dimension(bi.getWidth(), bi.getHeight()));
            
            this.repaint();
            this.validate();
        }
    }

    public void ReDrawBufferedImage(float T, float H) {
        if (bi != null) {
            bi = gSR.RemoveGreen_2(bi, T, H);
            this.repaint();
        }
    }

    public boolean IsReady() {
        return bi != null;
    }

    @Override
    public void paintComponent(Graphics page) {
        Graphics2D g2d = (Graphics2D) page;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        page.clearRect(0, 0, this.getWidth(), this.getHeight());
        page.drawImage(bi, 0, 0, this);
        
        page.setColor(Color.black);
        if(bi!=null)
        {
            page.drawRect(0, 0, (int)masks[0].getX(), bi.getHeight());
        }
    }

    private class SliderListener implements ChangeListener {

        private JSlider sliderOfInteraction;

        @Override
        public void stateChanged(ChangeEvent e) {
            sliderOfInteraction = (JSlider) e.getSource();

            switch (sliderOfInteraction.getName()) 
            {
                case "left":
                    masks[0] = new Point(left.getValue(), bi.getHeight());
                    break;
                case "right":
                    masks[1] = new Point(right.getValue(), bi.getHeight());
                    break;
                case "top":
                    masks[2] = new Point(bi.getHeight(), top.getValue());
                    break;
                case "bottom":
                    masks[3] = new Point(bi.getHeight(), top.getValue());
                    break;
            }

        }
    }
}
