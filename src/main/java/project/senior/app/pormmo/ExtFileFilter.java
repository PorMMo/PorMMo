package project.senior.app.pormmo;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * @author Shebly Copeland
 */
public class ExtFileFilter extends FileFilter
{
  private String[] extensions;
  
  /**
   * A simple extension of the FileFilter.
   * Example Usage: FileFilter f = new ExtFileFilter(new String[] {".mpg", ".avi"});
   * 
   * @param extensions A list of extensions you wish to use.
   */
  public ExtFileFilter(String[] extensions)
  {
    this.extensions = extensions;
    
    //::Lower case for comparisons later.
    for(int i = 0; i < this.extensions.length; i++)
      this.extensions[i] = this.extensions[i].toLowerCase();
  }
  
  @Override
  public boolean accept(File file)
  {
    //::Ignore the user pathing through directories.
    if(file.isDirectory()) 
      return true;
    
    String lowerPath = file.getAbsolutePath().toLowerCase();
    
    //::Loop through the extensions, check each.
    for(int i = 0; i < extensions.length; i++)
      if(lowerPath.endsWith(extensions[i]))
        return true;
    
    //::Not an extension supported.
    return false;
  }

  @Override
  /**
   * return :A textual representation of the extensions.
   */
  public String getDescription()
  {
    String temp = "| ";
    
    for(String ext : extensions)
      temp += ext + " |";
    
    return temp;
  }
  
}