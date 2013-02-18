package project.senior.app.pormmo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SettingsDock extends JDialog
{

  private BufferedImage bi, biOut;
  private BorderLayout bl;
  private OutputPanel oPanel;
  private JPanel controlPanel;
  private JDialog me;
  JSlider saturationSlider, hueSlider, brightnessSlider;

  public SettingsDock(BufferedImage bi)
  {
    this.setModal(true);
    this.bi = bi;
    initDialog();
  }

  private void initDialog()
  {
    //:##############################################################
    //:##       He said try to do the whole docking thing          ##
    //:##        So I commented the output area here out           ##
    //:##############################################################
    
    me = this;
    bl = new BorderLayout();
    setLayout(bl);
    addComponentListener(new SizeChangeListener());
    //biOut = imageCopy(bi);
    
    initControls();
    //initOutputPanel();

    setPreferredSize(new Dimension(bi.getWidth() + (int) controlPanel.getMinimumSize().getWidth(), bi.getHeight()));
    System.out.println(controlPanel.getWidth());
    pack();
    setVisible(true);
  }

  private void initControls()
  {
    GridBagLayout gBL = new GridBagLayout();
    GridBagConstraints gBC = new GridBagConstraints();

    controlPanel = new JPanel();
    controlPanel.setMinimumSize(new Dimension(200, 100));
    controlPanel.setLayout(gBL);

//    gBC.gridx = 0;
//    gBC.gridy = 0;
//    controlPanel.add(new JLabel("Red"), gBC);
//
//    gBC.gridx = 0;
//    gBC.gridy = 1;
//    JSlider redSlider = new JSlider();
//    controlPanel.add(redSlider, gBC);
//
//    gBC.gridx = 0;
//    gBC.gridy = 2;
//    controlPanel.add(new JLabel("Green"), gBC);
//
//    gBC.gridx = 0;
//    gBC.gridy = 3;
//    JSlider greenSlider = new JSlider();
//    controlPanel.add(greenSlider, gBC);
//
//    gBC.gridx = 0;
//    gBC.gridy = 4;
//    controlPanel.add(new JLabel("Blue"), gBC);
//
//    gBC.gridx = 0;
//    gBC.gridy = 5;
//    JSlider blueSlider = new JSlider();
//    controlPanel.add(blueSlider, gBC);

    gBC.gridx = 0;
    gBC.gridy = 0;
    controlPanel.add(new JLabel("Hue"), gBC);

    gBC.gridx = 0;
    gBC.gridy = 1;
    hueSlider = new JSlider();
    hueSlider.setName("hue");
    controlPanel.add(hueSlider, gBC);

    gBC.gridx = 0;
    gBC.gridy = 2;
    controlPanel.add(new JLabel("Saturation"), gBC);

    gBC.gridx = 0;
    gBC.gridy = 3;
    saturationSlider = new JSlider();
    saturationSlider.setName("saturation");
    saturationSlider.setMinimum(-1000);
    saturationSlider.setMaximum(1000);
    saturationSlider.setSnapToTicks(true);
    saturationSlider.setMajorTickSpacing(100);
    saturationSlider.setValue(0);
    saturationSlider.addChangeListener(new SliderListener());
    controlPanel.add(saturationSlider, gBC);

    gBC.gridx = 0;
    gBC.gridy = 4;
    controlPanel.add(new JLabel("Brightness"), gBC);

    gBC.gridx = 0;
    gBC.gridy = 5;
    brightnessSlider = new JSlider();
    brightnessSlider.setName("brightness");
    brightnessSlider.setMinimum(-1000);
    brightnessSlider.setMaximum(1000);
    brightnessSlider.setValue(0);
    brightnessSlider.setSnapToTicks(true);
    brightnessSlider.setMajorTickSpacing(100);
    brightnessSlider.addChangeListener(new SliderListener());
    controlPanel.add(brightnessSlider, gBC);

    add(controlPanel, BorderLayout.WEST);
  }

  private BufferedImage imageCopy(BufferedImage givenImage)
  {
    int iWidth = givenImage.getWidth(), iHeight = givenImage.getHeight();
    BufferedImage newImage = new BufferedImage(iWidth, iHeight, givenImage.getType());

    for (int x = 0; x < iWidth; x++)
    {
      for (int y = 0; y < iHeight; y++)
      {
        newImage.setRGB(x, y, givenImage.getRGB(x, y));
      }
    }

    return newImage;
  }

  private void initOutputPanel()
  {
    oPanel = new OutputPanel();
    oPanel.setPreferredSize(getPreferredSize());
    oPanel.setBackground(Color.blue);
    oPanel.DrawBufferedImage(biOut);

    add(oPanel, BorderLayout.CENTER);
  }

  class SliderListener implements ChangeListener
  {
    private Color newColor;
    private float hsbvals[] = new float[3];
    private int currentSliderValue;
    private float hue, saturation, brightness;

    @Override
    public void stateChanged(ChangeEvent e)
    {
      JSlider sliderOfInteraction = (JSlider) e.getSource();

      switch (sliderOfInteraction.getName())
      {
        case "brightness":
          for (int x = 0; x < bi.getWidth(); x++)
          {
            for (int y = 0; y < bi.getHeight(); y++)
            {
              currentSliderValue = sliderOfInteraction.getValue();
              newColor = new Color(bi.getRGB(x, y));
              Color.RGBtoHSB(newColor.getRed(), newColor.getGreen(), newColor.getBlue(), hsbvals);
              
              brightness = currentSliderValue*.001f;
              if(brightness<-.9f) brightness=(-.9f);
              if(brightness>1f) brightness=1f;
              
              biOut.setRGB(x, y, Color.HSBtoRGB(
                      hsbvals[0]
                      , hsbvals[1]
                      , (hsbvals[2]+(brightness) < 0) ? 0 : 
                         (hsbvals[2] + (brightness) > 1.0f) ? 1.0f : 
                           hsbvals[2] + brightness
                      ));
            }
          }
          break;//:End brightness
          
        case "saturation":
          for (int x = 0; x < bi.getWidth(); x++)
          {
            for (int y = 0; y < bi.getHeight(); y++)
            {
              currentSliderValue = sliderOfInteraction.getValue();
              newColor = new Color(bi.getRGB(x, y));
              Color.RGBtoHSB(newColor.getRed(), newColor.getGreen(), newColor.getBlue(), hsbvals);
              
              saturation = currentSliderValue * .001f;
              if(saturation < -.9f) saturation = (-.9f);
              if(saturation > 1f)   saturation = 1f;
              
              biOut.setRGB(x, y, 
                            Color.HSBtoRGB
                            (
                              hsbvals[0], 
                             (hsbvals[1] + saturation < 0) ? 
                               0 : (hsbvals[1] + (saturation) > 1.0f) ? 
                                 1.0f :  hsbvals[1] + saturation, 
                              hsbvals[2]
                            )
                          );
            }
          }
          break;//:End saturation
      }
      oPanel.repaint();
    }
  }

  class SizeChangeListener implements ComponentListener
  {

    @Override
    public void componentResized(ComponentEvent e)
    {
    }

    @Override
    public void componentMoved(ComponentEvent e)
    {
    }

    @Override
    public void componentShown(ComponentEvent e)
    {
    }

    @Override
    public void componentHidden(ComponentEvent e)
    {
    }
  }
}
