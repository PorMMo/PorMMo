package project.senior.app.pormmo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Shelby Copeland
 */
public class TolerancePanel extends JPanel
{
  private JSlider tolerance;
  private GSR gsr;
  private OutputPanel main;
  private float T;
  
  public TolerancePanel(OutputPanel op)
  {
    main = op;
    gsr = new GSR();
    initPanel();
    initControls();
  }

  private void initPanel()
  {
    setPreferredSize(new Dimension(250, 100));
    setLayout(new BorderLayout());
  }

  private void initControls()
  {
    GridBagLayout gbl = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();
    
    JPanel inner = new JPanel();
    inner.setMinimumSize(new Dimension(100, 100));
    inner.setLayout(gbl);
    
    gbc.gridx = 0;
    gbc.gridy = 0;
    inner.add(new JLabel("Green Screen Tolerance: "), gbc);
    
    gbc.gridx = 0;
    gbc.gridy = 1;
    tolerance = new JSlider();
    tolerance.setName("tolerance");
    tolerance.setMinimum(0);
    tolerance.setMaximum(100);
    tolerance.setSnapToTicks(true);
    tolerance.setMajorTickSpacing(10);
    tolerance.setValue(50);
    tolerance.addChangeListener(new ChangeListener()
    {
      @Override
      public void stateChanged(ChangeEvent e)
      {
        T = (1.0f * tolerance.getValue()) / 100.0f;
      }
    });
    inner.add(tolerance, gbc);
    
    gbc.gridx = 0;
    gbc.gridy = 2;
    inner.add(new JLabel(""), gbc);
    
    gbc.gridx = 0;
    gbc.gridy = 3;
    JButton apply = new JButton("Apply");
    apply.setSize(75,40);
    apply.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        if(main.IsReady())
        {
          main.ReDrawBufferedImage(T, gsr.GREEN);
        }
      }
    });
    inner.add(apply, gbc);
    
    add(inner);
  }
}