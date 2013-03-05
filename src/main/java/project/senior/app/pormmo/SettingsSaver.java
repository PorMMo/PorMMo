//==============================================================================
//==    Comments labeled #DEBUG need to be deleted before shipping product    ==
//==============================================================================
package project.senior.app.pormmo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Shelby Copeland
 */
public class SettingsSaver
{
  private static void CheckDirectory()
  {
    File SettingsDirectory = new File("Settings/");
    if (!SettingsDirectory.exists())
    {
      SettingsDirectory.mkdir();
    }
  }

  //:Unsure if this will be implemented yet.
  private static void CheckLogger()
  {
    File SettingsDirectory = new File("Logs/");
    if (!SettingsDirectory.exists())
    {
      SettingsDirectory.mkdir();
    }
  }
  
  private static void WriteLogs(Exception e)
  {
    Date d = new Date();
    String fname = "Logs/";
    fname += Calendar.getInstance().getTimeInMillis();
    
    try
    {
      BufferedWriter bw = new BufferedWriter(new FileWriter(fname));
      bw.write(e.getMessage());
      bw.write("\n");
      bw.write(e.toString());
      bw.close();
    }
    catch(Exception ee)
    {
      //:Unable to write file, need elevated privilege.
    }
  }

  /**
   * Filename = JFileChooser.getSelectedFile().getPath()
   *
   * @param filename Path to file that was opened.
   */
  public static void SaveFileSelect(String filename)
  {
    CheckDirectory();

    try
    {
      BufferedWriter bw = new BufferedWriter(new FileWriter("Settings/SD.pmo"));
      bw.write(filename);
      bw.close();
    }
    catch (Exception e)
    {
      //#DEBUG
      //:write to log file
      e.printStackTrace();
    }
  }

  /**
   *
   * @return The last used location or an empty string (if no last location);
   */
  public static String LastUsed()
  {
    String fileDirectory = "";
    
    File SavedDirectory = new File("Settings/SD.pmo");
    if (SavedDirectory.exists())
    {
      try
      {
        Scanner scan = new Scanner(SavedDirectory);
        fileDirectory = scan.nextLine();
        scan.close();
      }
      catch (Exception e)
      {
        //#DEBUG
        //:Logger
        e.printStackTrace();
      }
    }

    return fileDirectory;
  }
}