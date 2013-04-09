package project.senior.app.pormmo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Shelby Copeland, John Fisher
 */
public class HSBPanel extends JPanel
{

  private BufferedImage bi;
  private BufferedImage bi2;
  private BorderLayout bl;
  private JPanel controlPanel;
  private OutputPanel oPanel;
  private JButton applyBtn;
  private JPanel me;
  private JSlider saturationSlider, brightnessSlider;
  private UserInterfaceFrame parent;
  private BufferedImage localBackup;
  private boolean intraStateBackup = false;

  public HSBPanel(UserInterfaceFrame parent)
  {
    this.parent = parent;

    initPanel();
  }

  private void initPanel()
  {
    me = this;
    bl = new BorderLayout();
    setLayout(bl);
    initControls();
  }

  public void Reset(BufferedWrapper bw)
  {
    if(intraStateBackup){
      bi2 = null;
      parent.ic.restoreCurrentDisplayImage();
      parent.outputPanel.ReDrawOutputPanel();
    }
  }

  private void initControls()
  {
    GridBagLayout gBL = new GridBagLayout();
    GridBagConstraints gBC = new GridBagConstraints();

    controlPanel = new JPanel();
    controlPanel.setMinimumSize(new Dimension(150, 100));
    controlPanel.setLayout(gBL);

    gBC.gridx = 0;
    gBC.gridy = 0;
    controlPanel.add(new JLabel("Saturation"), gBC);

    gBC.gridx = 0;
    gBC.gridy = 1;
    saturationSlider = new JSlider();
    saturationSlider.setName("saturation");
    saturationSlider.setMinimum(-1000);
    saturationSlider.setMaximum(1000);
    saturationSlider.setSnapToTicks(true);
    saturationSlider.setMajorTickSpacing(100);
    saturationSlider.setValue(0);
    SliderListener ssl = new SliderListener();
    saturationSlider.addChangeListener(ssl);
    saturationSlider.addMouseListener(ssl);
    controlPanel.add(saturationSlider, gBC);

    gBC.gridx = 0;
    gBC.gridy = 3;
    controlPanel.add(new JLabel("Brightness"), gBC);

    gBC.gridx = 0;
    gBC.gridy = 4;
    brightnessSlider = new JSlider();
    brightnessSlider.setName("brightness");
    brightnessSlider.setMinimum(-1000);
    brightnessSlider.setMaximum(1000);
    brightnessSlider.setValue(0);
    brightnessSlider.setSnapToTicks(true);
    brightnessSlider.setMajorTickSpacing(100);
    SliderListener bsl = new SliderListener();
    brightnessSlider.addChangeListener(bsl);
    brightnessSlider.addMouseListener(bsl);
    controlPanel.add(brightnessSlider, gBC);

    gBC.gridx = 0;
    gBC.gridy = 5;
    controlPanel.add(new JLabel(" "), gBC);

    gBC.gridx = 0;
    gBC.gridy = 6;
    applyBtn = new JButton("Apply");
    applyBtn.setName("apply");
    applyBtn.setPreferredSize(new Dimension(75, 40));
    applyBtn.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        if (parent.ic.getCurrentlyDisplayImage() == null)
        {
          return;
        }

        parent.ic.backupCurrentDisplayImage();
        localBackup = parent.ic.getCurrentlyDisplayImage();
        intraStateBackup = false;

        saturationSlider.setValue(0);
        saturationSlider.repaint();
        brightnessSlider.setValue(0);
        brightnessSlider.repaint();
      }
    });
    controlPanel.add(applyBtn, gBC);

    gBC.gridx = 0;
    gBC.gridy = 8;
    JButton resetButton = new JButton("Reset");
    resetButton.addMouseListener(new cButtonListener());
    resetButton.setPreferredSize(new Dimension(75, 40));
    controlPanel.add(resetButton, gBC);

    add(controlPanel);
  }

  class cButtonListener extends MouseAdapter
  {

    @Override
    public void mouseReleased(MouseEvent e)
    {
      bi2 = null;
      parent.ic.restoreCurrentDisplayImage();
      parent.outputPanel.ReDrawOutputPanel();
      saturationSlider.setValue(0);
      saturationSlider.repaint();
      brightnessSlider.setValue(0);
      brightnessSlider.repaint();
    }
  }

  class SliderListener extends MouseAdapter implements ChangeListener
  {

    private Color newColor;
    private float hsbvals[] = new float[3];
    private int currentSliderValue;
    private float saturation, brightness;

    @Override
    public void mousePressed(MouseEvent e)
    {
      if (!intraStateBackup)
      {
        localBackup = new BufferedImage(
                parent.ic.getCurrentlyDisplayImage().getWidth(), parent.ic.getCurrentlyDisplayImage().getHeight(), parent.ic.getCurrentlyDisplayImage().getType());
        localBackup = parent.ic.getCurrentlyDisplayImage().getSubimage(0, 0, parent.ic.getCurrentlyDisplayImage().getWidth(), parent.ic.getCurrentlyDisplayImage().getHeight());
        intraStateBackup = true;
      }
    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
      bi = localBackup;
      bi2 = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());

      JSlider sliderOfInteraction = (JSlider) e.getSource();

      me.validate();
      me.repaint();

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

              brightness = currentSliderValue * .001f;
              if (brightness < -.9f)
              {
                brightness = (-.9f);
              }

              if (brightness > 1f)
              {
                brightness = 1f;
              }

              bi2.setRGB(x, y, Color.HSBtoRGB(
                      hsbvals[0], hsbvals[1], (hsbvals[2] + (brightness) < 0) ? 0
                      : (hsbvals[2] + (brightness) > 1.0f) ? 1.0f
                      : hsbvals[2] + brightness));
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
              if (saturation < -.9f)
              {
                saturation = (-.9f);
              }
              if (saturation > 1f)
              {
                saturation = 1f;
              }

              bi2.setRGB(x, y,
                      Color.HSBtoRGB(
                      hsbvals[0],
                      (hsbvals[1] + saturation < 0)
                      ? 0 : (hsbvals[1] + (saturation) > 1.0f)
                      ? 1.0f : hsbvals[1] + saturation,
                      hsbvals[2]));
            }
          }
          break;//:End saturation
      }

      parent.ic.setCurrentlyDisplayImage(bi2);
      parent.outputPanel.ReDrawOutputPanel();
    }
  }
}
