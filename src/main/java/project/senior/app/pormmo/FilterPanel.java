/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.senior.app.pormmo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Shelby Copeland
 */
public class FilterPanel extends JPanel
{
  private BufferedWrapper bw;
  private OutputPanel main;
  private HSBPanel hp;
  
  public FilterPanel(OutputPanel op, HSBPanel h)
  {
    hp = h;
    main = op;
    bw = new BufferedWrapper();
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
    inner.add(new JLabel("Filters : "), gbc);
    
    gbc.gridx = 0;
    gbc.gridy = 1;
    inner.add(new JLabel(""), gbc);
    //--------------------------------------------------------------------------
    gbc.gridx = 0;
    gbc.gridy = 2;
    JButton grays = new JButton("GrayScale");
    grays.setSize(75,40);
    grays.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        bw.img = main.GetLatestBI();
        Filters.GrayScaleStatic(bw);
        main.DrawBufferedImage(bw.img);
      }
    });
    inner.add(grays, gbc);
    //--------------------------------------------------------------------------
    gbc.gridx = 0;
    gbc.gridy = 3;
    JButton mean = new JButton("Mean [3x3]");
    mean.setSize(75,40);
    mean.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        bw.img = main.GetLatestBI();
        Filters.MeanBlurStatic(bw);
        main.DrawBufferedImage(bw.img);
      }
    });
    inner.add(mean, gbc);
    //--------------------------------------------------------------------------
    gbc.gridx = 0;
    gbc.gridy = 4;
    JButton median = new JButton("Median [3x3]");
    median.setSize(75,40);
    median.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        bw.img = main.GetLatestBI();
        Filters.MedianBlurStatic(bw);
        main.DrawBufferedImage(bw.img);
      }
    });
    inner.add(median, gbc);
    //--------------------------------------------------------------------------
    gbc.gridx = 0;
    gbc.gridy = 5;
    JButton gaussian = new JButton("Gaussian [3x3]");
    gaussian.setSize(75,40);
    gaussian.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        bw.img = main.GetLatestBI();
        Filters.GaussianBlurStatic(bw);
        main.DrawBufferedImage(bw.img);
      }
    });
    inner.add(gaussian, gbc);
    //--------------------------------------------------------------------------
    gbc.gridx = 0;
    gbc.gridy = 6;
    JButton apply = new JButton("Apply Changes");
    apply.setSize(75,40);
    apply.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        hp.Reset(bw);
      }
    });
    inner.add(apply, gbc);
    
    add(inner);
  }
}