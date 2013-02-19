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

public class HSBPanel extends JPanel 
{

    private BufferedImage bi, bi2;
    private BorderLayout bl;
    private JPanel controlPanel;
    private OutputPanel oPanel;
    private JButton applyBtn;
    private JPanel me;
    private JSlider saturationSlider, hueSlider, brightnessSlider;

    public HSBPanel(BufferedImage bi, OutputPanel oPanel) 
    {
        this.bi = bi;
        this.oPanel = oPanel;

        initPanel();
    }

    public void setImage(BufferedImage bi) 
    {
        this.bi = bi;
    }

    private void initPanel() 
    {
        me = this;
        bl = new BorderLayout();
        setLayout(bl);
        initControls();
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
        controlPanel.add(new JLabel("Hue"), gBC);

        gBC.gridx = 0;
        gBC.gridy = 1;
        hueSlider = new JSlider();
        hueSlider.setName("hue");
        hueSlider.setMinimum(-1000);
        hueSlider.setMaximum(1000);
        hueSlider.setSnapToTicks(true);
        hueSlider.setMajorTickSpacing(100);
        hueSlider.setValue(0);
        hueSlider.addChangeListener(new SliderListener());
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

        gBC.gridx = 0;
        gBC.gridy = 6;
        controlPanel.add(new JLabel(" "), gBC);
        
        gBC.gridx = 0;
        gBC.gridy = 7;
        applyBtn = new JButton("Apply");
        applyBtn.setName("apply");
        applyBtn.setPreferredSize(new Dimension(75, 40));
        applyBtn.addActionListener(new ActionListener()
            {
              @Override
              public void actionPerformed(ActionEvent e)
              {
                if(bi2.equals(null)) return;
                
                bi = BufferedWrapper.CloneImg(bi2);
                saturationSlider.setValue(0);
                saturationSlider.repaint();
                brightnessSlider.setValue(0);
                brightnessSlider.repaint();
                hueSlider.setValue(0);     
                hueSlider.repaint();
              }
            });
        controlPanel.add(applyBtn, gBC);
        
        gBC.gridx = 0;
        gBC.gridy = 8;
        JButton resetButton = new JButton("Reset");
        resetButton.addMouseListener(new cButtonListener());
        resetButton.setPreferredSize(new Dimension(75, 40));
        controlPanel.add(resetButton, gBC);

        add(controlPanel, BorderLayout.WEST);
    }

    public BufferedImage imageCopy(BufferedImage givenImage) 
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

    class cButtonListener extends MouseAdapter 
    {

        @Override
        public void mouseReleased(MouseEvent e) 
        {
            bi2 = null;
            oPanel.DrawBufferedImage(bi);
            saturationSlider.setValue(0);
            saturationSlider.repaint();
            brightnessSlider.setValue(0);
            brightnessSlider.repaint();
            hueSlider.setValue(0);     
            hueSlider.repaint();
        }
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

            me.validate();
            me.repaint();

            if (bi2 == null) 
            {
                bi2 = imageCopy(bi);
            }

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
                            if (saturation > 1f) {
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

            oPanel.DrawBufferedImage(bi2);
        }
    }
}
