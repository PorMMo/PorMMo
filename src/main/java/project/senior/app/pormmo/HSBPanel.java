package project.senior.app.pormmo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

//  private BufferedImage bi;
  private BorderLayout bl;
  private JPanel controlPanel;
  private OutputPanel oPanel;
  private JButton applyBtn;
  private JPanel me;
  private JSlider saturationSlider, brightnessSlider;
  private UserInterfaceFrame parent;
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
    if (intraStateBackup)
    {
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
    applyBtn.addMouseListener(new applyButtonListener());
    controlPanel.add(applyBtn, gBC);

    gBC.gridx = 0;
    gBC.gridy = 8;
    JButton resetButton = new JButton("Reset");
    resetButton.addMouseListener(new resetButtonListener());
    resetButton.setPreferredSize(new Dimension(75, 40));
    controlPanel.add(resetButton, gBC);

    add(controlPanel);
  }

  class applyButtonListener extends MouseAdapter
  {

    @Override
    public void mouseReleased(MouseEvent e)
    {
      if (parent.ic.getCurrentlyDisplayImage() == null)
      {
        return;
      }

      parent.ic.setWorkingCopy(parent.ic.getCurrentlyDisplayImage());
      stopEdit();
      parent.seqSet.setBrightness(brightnessSlider.getValue());
      parent.seqSet.setSaturation(saturationSlider.getValue());

      BrightnessSaturationSettings bss = new BrightnessSaturationSettings(brightnessSlider.getValue(), saturationSlider.getValue());
      parent.seqOrder.AddAction(parent.seqOrder.BRIGHTNESS_SAT, bss);
      
      resetSliders();
    }
  }

  class resetButtonListener extends MouseAdapter
  {

    @Override
    public void mouseReleased(MouseEvent e)
    {
      parent.ic.setCurrentlyDisplayImage(parent.ic.getWorkingCopy());
      stopEdit();
      parent.outputPanel.ReDrawOutputPanel();
      resetSliders();
    }
  }

  public void adjustBrightness(BufferedWrapper bw, int adjustQuantity)
  {
    Brightness.AdjustBrightness(bw, adjustQuantity);
  }

  public void adjustSaturation(BufferedWrapper bw, int adjustQuantity)
  {
    Saturation.AdjustSaturation(bw, adjustQuantity);
  }

  private void startEdit()
  {
    if (parent.ic.getCurrentlyDisplayImage() == null)
    {
      JOptionPane.showMessageDialog(null, "Please take a snapshot first!");
    }
    else
    {
      if(!intraStateBackup)
        parent.ic.setWorkingCopy(parent.ic.getCurrentlyDisplayImage());
      
      intraStateBackup = true;
    }
  }

  private void stopEdit()
  {
    intraStateBackup = false;
  }

  public void resetSliders()
  {
    saturationSlider.setValue(0);
    saturationSlider.repaint();
    brightnessSlider.setValue(0);
    brightnessSlider.repaint();
  }

  class SliderListener extends MouseAdapter implements ChangeListener
  {

    private int currentSliderValue;

    @Override
    public void mousePressed(MouseEvent e)
    {
      if (!intraStateBackup)
      {
        startEdit();
      }
    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
      JSlider sliderOfInteraction = (JSlider) e.getSource();
      BufferedWrapper bw = new BufferedWrapper(parent.ic.getWorkingCopy());

      me.validate();
      me.repaint();

//      switch (sliderOfInteraction.getName())
//      {
//        case "brightness":
          Brightness.AdjustBrightness(bw, brightnessSlider.getValue());
////          parent.seqOrder.AddAction(parent.seqOrder.BRIGHT);
//          break;//:End brightness
//
//        case "saturation":
          Saturation.AdjustSaturation(bw, saturationSlider.getValue());
////          parent.seqOrder.AddAction(parent.seqOrder.SAT);
//          break;//:End saturation
//      }

      parent.ic.setCurrentlyDisplayImage(bw.img);
      parent.outputPanel.ReDrawOutputPanel();
    }
  }
}
