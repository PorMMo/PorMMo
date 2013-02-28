package project.senior.app.pormmo;

import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * @author John Fisher
 */
public class AppMenuBar
{
 
  private JMenuBar menuBar;
  private JFrame parentFrame;
  
  public AppMenuBar(JFrame parentFrame)
  {
    this.parentFrame = parentFrame;
    menuBar = new JMenuBar();
    this.parentFrame.setJMenuBar(menuBar);
  }
  
  public JMenu AddMenuItem(String label, MouseListener clickListener)
  {
    JMenu menu = new JMenu(label);
    menuBar.add(menu);
    return menu;
  }
  
  public JMenuItem AddSubMenuItem(String label, JMenu parentMenu, MouseListener clickListener) 
  {
    JMenuItem menuItem = new JMenuItem(label);
    menuItem.addMouseListener(clickListener);
    parentMenu.add(menuItem);
    return menuItem;
  }
  
  public JMenuBar GetMenuBar(){
    return menuBar;
  }
}
