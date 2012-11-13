package project.senior.app.pormmo;

import com.sun.jna.NativeLibrary;

/**
 * @author John Fisher, Shelby Copeland, Max De Benedetti
 */
public class App 
{
  private static final String vlcPath = "\\VideoLAN\\VLC";
  
  public static void main(String[] args) 
  {
    if (System.getProperty("os.name").startsWith("Windows")) 
    {     
      String osArch = (System.getenv("ProgramFiles(x86)") == null) ?
                                         System.getenv("ProgramFiles") : 
                                         System.getenv("ProgramFiles(x86)");
      //::If program files (x86) folder does not exist, use program files.
      //::else use the x86 folder. -- VLC by default installs under x86 folder
      //::for 64-bit machines.

      NativeLibrary.addSearchPath("libvlc", osArch + vlcPath);
    }
        
    UserInterfaceFrame uIF = new UserInterfaceFrame();
  }
}