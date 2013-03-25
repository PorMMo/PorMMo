package project.senior.app.pormmo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * @author John Fisher
 */
public class SnapshotSave extends JFileChooser
{

  private BufferedImage fileToSave;
  private File fileHandle;

  public SnapshotSave(BufferedImage fileToSave)
  {
    this.fileToSave = fileToSave;
    this.fileHandle = null;
    saveFile();
  }

  private void saveFile()
  {
    showFileChooser();
  }

  public void showFileChooser()
  {
    showSaveDialog(this);
    fileHandle = getSelectedFile();

    if (fileHandle != null)
    {
      try
      {
        ImageIO.write(fileToSave, "png", fileHandle);
      }
      catch (IOException ex)
      {
        JOptionPane.showMessageDialog(this, "Error saving image!");
      }
    }
  }
}
