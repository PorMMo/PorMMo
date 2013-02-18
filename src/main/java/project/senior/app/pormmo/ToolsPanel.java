package project.senior.app.pormmo;

import java.awt.*;
import javax.swing.*;

/**
 * @author Seobi-TAZS_C
 */
public class ToolsPanel extends ScrollPane
{  
  public ToolsPanel(JPanel[] tools)
  {    
    this.setPreferredSize(new Dimension(250, 1));
    JPanel main = new JPanel();
    add(main);
    main.setLayout(new BoxLayout(main, BoxLayout.PAGE_AXIS));
    
    
    for(int i = 0; i < tools.length; i++)
    {
      Box temp = Box.createVerticalBox();
      temp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      temp.add(tools[i]); 
      main.add(temp);
      
    }
  }
}
