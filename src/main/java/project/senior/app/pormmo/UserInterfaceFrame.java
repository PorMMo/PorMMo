package project.senior.app.pormmo;

import com.sun.jna.Native;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.binding.internal.libvlc_state_t;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventListener;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

/**
 * @author John Fisher
 */
public class UserInterfaceFrame extends JFrame
{
  
  public float TOLERANCE;
  private ScrollPane left;
  private JPanel controlPanel;
  private HSBPanel hsbPanel;
  private OutputPanel outputPanel;
  private File selectedInputFile = null;
  private GridBagLayout controlLayout;
  private GridBagConstraints controlGBC;
  private ScrollPane sPane;
  private JFrame me, playerFrame;
  private BufferedImage snapshot, displayShot;
  private String fileDirectory;
  private MediaPlayerFactory mPlayerFactory;
  private MediaPlayer mPlayer;
  private EmbeddedMediaPlayerComponent mediaPlayerComponent;
  private JButton playPauseButton;
  private JSlider posSlider;
  private boolean userSelectingLocation = false;
  private MediaControlsSliderListener mcl;
  private JCheckBox jBX, resetBX;
  private GSR gSR;
  private int current;
  private BufferedWrapper BW;
  private Dimension screenSize;
  
  public UserInterfaceFrame()
  {
    TOLERANCE = 0.5f;
    initFrame();
    initLayouts();
    initMenu();
    initControlPanel();
    initOutputPanel();
    initDisplay();
    initPlayer();
    showFrame();
  }
  
  private void initFrame()
  {
    screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    
    setTitle("PorMMo");
    setPreferredSize(new Dimension((int)screenSize.getWidth()/2, (int)screenSize.getHeight()));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    this.addComponentListener(new FrameListener());
    me = this;
    gSR = new GSR();
    current = 1;
  }
  
  private void initPlayer()
  {
    Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);    

    playerFrame = new JFrame("Video Player");
    playerFrame.setSize(new Dimension((int)screenSize.getWidth()/2, (int)screenSize.getHeight()));
    playerFrame.setLocation((int)screenSize.getWidth()/2, 0);
    playerFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    
    mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
    playerFrame.setContentPane(mediaPlayerComponent);
    
    mPlayerFactory = mediaPlayerComponent.getMediaPlayerFactory();
    mPlayer = mediaPlayerComponent.getMediaPlayer();
    mPlayer.addMediaPlayerEventListener(new PlayerEventListener());
  }
  
  private void showFrame()
  {
    pack();
    setVisible(true);
  }
  
  private void initLayouts()
  {
    controlLayout = new GridBagLayout();
    controlGBC = new GridBagConstraints();
    controlGBC.fill = GridBagConstraints.HORIZONTAL;
  }
  
  private void initDisplay()
  {
    JPanel[] tools = new JPanel[3];
    hsbPanel = new HSBPanel(null, outputPanel);
    hsbPanel.setPreferredSize(new Dimension(100, 200));
    tools[0] = hsbPanel;
    
    TolerancePanel tp = new TolerancePanel(outputPanel);
    tp.setMinimumSize(new Dimension(100, 100));
    tools[1] = tp;
    
    FilterPanel fp = new FilterPanel(outputPanel, hsbPanel);
    fp.setMinimumSize(new Dimension(100,200));
    tools[2] = fp;
    
    left = new ToolsPanel(tools);
    
    sPane = new ScrollPane();
    sPane.add(outputPanel);
    sPane.setPreferredSize(this.getPreferredSize());
    
    add(controlPanel, BorderLayout.NORTH);
    add(sPane, BorderLayout.CENTER);
    add(left, BorderLayout.WEST);
    fileDirectory = "";
  }
  
  private void initMenu()
  {
    JMenuBar frameMenuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenuItem openMenuItem = new JMenuItem("Open");
    openMenuItem.addMouseListener(new UserInterfaceFrameMenuListener());
    fileMenu.add(openMenuItem);
    JMenuItem exitMenuItem = new JMenuItem("Exit");
    exitMenuItem.addMouseListener(new UserInterfaceFrameMenuListener());
    fileMenu.add(exitMenuItem);
    
    frameMenuBar.add(fileMenu);
    
    setJMenuBar(frameMenuBar);
  }
  
  private void initOutputPanel()
  {
    outputPanel = new OutputPanel();
    outputPanel.setPreferredSize(new Dimension(500, 500));
  }
  
  private void initControlPanel()
  {
//Needs rewrite into a smarter generation approach
    controlPanel = new JPanel();
    controlPanel.setLayout(controlLayout);
    
    playPauseButton = new JButton("Play/Pause");
    playPauseButton.addMouseListener(new MediaControlsButtonListener());
    playPauseButton.setName("playpause");
    controlGBC.gridx = 0;
    controlGBC.gridy = 0;
    controlPanel.add(playPauseButton, controlGBC);
    
    JButton stopButton = new JButton("Stop");
    stopButton.addMouseListener(new MediaControlsButtonListener());
    stopButton.setName("stop");
    controlGBC.gridx = 1;
    controlGBC.gridy = 0;
    controlPanel.add(stopButton, controlGBC);
    
    JButton fwdButton = new JButton("Forward");
    fwdButton.addMouseListener(new MediaControlsButtonListener());
    fwdButton.setName("forward");
    controlGBC.gridx = 2;
    controlGBC.gridy = 0;
    controlPanel.add(fwdButton, controlGBC);
    
    JButton rwdButton = new JButton("Rewind");
    rwdButton.addMouseListener(new MediaControlsButtonListener());
    rwdButton.setName("rewind");
    controlGBC.gridx = 3;
    controlGBC.gridy = 0;
    controlPanel.add(rwdButton, controlGBC);
    
    JButton snapshotButton = new JButton("Snapshot");
    snapshotButton.addMouseListener(new MediaControlsButtonListener());
    snapshotButton.setName("snapshot");
    controlGBC.gridx = 4;
    controlGBC.gridy = 0;
    controlPanel.add(snapshotButton, controlGBC);
    
    JButton saveButton = new JButton("Save");
    saveButton.addMouseListener(new MediaControlsButtonListener());
    saveButton.setName("save");
    controlGBC.gridx = 5;
    controlGBC.gridy = 0;
    controlPanel.add(saveButton, controlGBC);
    
    //--------------------------------------------------------
    //:Row 2
    //--------------------------------------------------------
    JButton reset = new JButton("Reset Image");
    reset.addMouseListener(new MediaControlsCheckBoxListener());
    controlGBC.gridx = 0;
    controlGBC.gridy = 1;
    controlPanel.add(reset, controlGBC);

    JPanel gsrControlPanel = new JPanel();
    jBX = new JCheckBox();
    jBX.setSelected(true);
    jBX.addMouseListener(new MediaControlsCheckBoxListener());
    jBX.setName("greenscreencheckbox");
    gsrControlPanel.add(jBX);
    controlGBC.gridx = 1;
    controlGBC.gridy = 1;
    controlPanel.add(gsrControlPanel, controlGBC);
    
    JLabel greenScreenLabel = new JLabel("Green Screen");
    gsrControlPanel.add(greenScreenLabel);
    controlPanel.add(gsrControlPanel, controlGBC);
    
    JLabel spaceHolder = new JLabel(" ");
    controlGBC.gridx = 3;
    controlGBC.gridy = 1;
    controlPanel.add(spaceHolder, controlGBC);
    
    posSlider = new JSlider();
    posSlider.setName("pslider");
    MediaControlsSliderListener mCSL = new MediaControlsSliderListener();
    posSlider.addChangeListener(mCSL);
    posSlider.addMouseListener(mCSL);
    controlGBC.gridx = 2;
    controlGBC.gridy = 1;
    controlGBC.gridwidth = 4;
    controlPanel.add(posSlider, controlGBC);
    
  }

  /**
   * Check and do settings. 0 = Check for the Settings Directory.
   *
   * 1 = Check for FileSelect directories and last location. ##~If there is no
   * last one, it will create the file.
   *
   * 2 = Save latest location for FileSelect
   *
   * @param setting The setting you wish to check/do.
   */
  private void doSettings(int setting)
  {
    switch (setting)
    {
      case 0:
        File SettingsDirectory = new File("Settings/");
        if (!SettingsDirectory.exists())
        {
          SettingsDirectory.mkdir();
        }
        break;
      //------------------------------------------------------------------------
      case 1:
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
            System.out.println("Insufficient Access to file! setting = 1");
          }
        }//::End if(SavedDirectory)
        break;
      //------------------------------------------------------------------------
      case 2:
        try
        {
          BufferedWriter bw =
                  new BufferedWriter(new FileWriter("Settings/SD.pmo"));
          bw.write(fileDirectory);
          bw.close();
        }
        catch (Exception e)
        {
          //#DEBUG
          System.out.println("Can't write to file! setting = 2");
        }
        break;
    }
  }
  
  private void showFileSelect()
  {
    doSettings(0);
    doSettings(1);
    
    JFileChooser jFC = new JFileChooser();
    jFC.setSelectedFile(new File(fileDirectory));
    jFC.setAcceptAllFileFilterUsed(false);
    jFC.setFileFilter(new ExtFileFilter(new String[]
            {
              ".png", ".jpg"
            }));
    jFC.setFileFilter(new ExtFileFilter(new String[]
            {
              ".mp4", ".mpg", ".avi"
            }));
    
    jFC.showDialog(this, "Open");
    selectedInputFile = jFC.getSelectedFile();
    fileDirectory = jFC.getSelectedFile().getPath();
    doSettings(2);
    OpenSource();
  }

  //Precondition: displayShot != null
  private void showFileSave(BufferedImage snap2Save)
  {
    SnapshotSave sS = new SnapshotSave(snap2Save);
  }
  
  private void OpenSource()
  {
    if (selectedInputFile.canRead())
    {
      try
      {
        playerFrame.setVisible(true);
        mediaPlayerComponent.getMediaPlayer().playMedia(selectedInputFile.getAbsolutePath());
      }
      catch (java.lang.NoClassDefFoundError e)
      {
        System.out.println("Error: " + e.getMessage());
      }
    }
  }
  
  private void showMessage(int msg2Show)
  {
    if (msg2Show == 0)
    {
      JOptionPane.showMessageDialog(null, "Please choose a file via the File menu");
    }
  }
  
  public void processSnapshot()
  {
    if (snapshot != null)
    {
      displayShot = snapshot;
      
      if (jBX.isSelected())
      {
        displayShot = gSR.RemoveGreen_3(BufferedWrapper.CloneImg(snapshot), 0.6f);
      }
      
      outputPanel.DrawBufferedImage(displayShot);
      outputPanel.setPreferredSize(new Dimension(me.getWidth(), me.getHeight() - controlPanel.getHeight()));
      hsbPanel.setImage(displayShot);
    }
  }
  
  private class MediaControlsSliderListener extends MouseAdapter implements ChangeListener
  {
    
    JSlider sliderOfInteraction;
    
    @Override
    public void stateChanged(ChangeEvent e)
    {
      if (userSelectingLocation)
      {
        mPlayer.setPosition((posSlider.getValue()) * .01f);
      }
    }
    
    @Override
    public void mousePressed(MouseEvent e)
    {
      
      sliderOfInteraction = (JSlider) e.getSource();
      
      switch (sliderOfInteraction.getName().toLowerCase())
      {
        case "pslider":
          userSelectingLocation = true;
          break;
      }
    }
    
    @Override
    public void mouseReleased(MouseEvent e)
    {
      switch (sliderOfInteraction.getName().toLowerCase())
      {
        case "pslider":
          userSelectingLocation = false;
          break;
      }
    }
  }
  
  private class MediaControlsCheckBoxListener extends MouseAdapter
  {
    
    @Override
    public void mouseReleased(MouseEvent e)
    {
      processSnapshot();
    }
  }
  
  private class MediaControlsButtonListener extends MouseAdapter
  {
    
    JButton buttonOfInteraction;
    
    @Override
    public void mouseReleased(MouseEvent e)
    {
      buttonOfInteraction = (JButton) e.getSource();
      
      switch (buttonOfInteraction.getName().toLowerCase())
      {
        case "playpause":
          if (selectedInputFile == null)
          {
            showMessage(0);
          }
          else
          {
            if (mPlayer.getMediaPlayerState() == libvlc_state_t.libvlc_Stopped)
            {
              mPlayer.playMedia(selectedInputFile.getAbsolutePath());
            }
            
            if ((mPlayer.canPause() && mPlayer.getMediaPlayerState() == libvlc_state_t.libvlc_Playing)
                    || mPlayer.getMediaPlayerState() == libvlc_state_t.libvlc_Paused)
            {
              mPlayer.pause();
            }
          }
          break;
        case "forward":
          if (selectedInputFile == null)
          {
            showMessage(0);
          }
          else
          {
            mPlayer.skip(1000);
          }
          break;
        
        case "rewind":
          if (selectedInputFile == null)
          {
            showMessage(0);
          }
          else
          {
            mPlayer.skip(-1000);
          }
          break;
        
        case "stop":
          if (selectedInputFile == null)
          {
            showMessage(0);
          }
          else
          {
            mPlayer.stop();
          }
          break;
        
        case "snapshot":
          if (selectedInputFile == null)
          {
            showMessage(0);
          }
          else
          {
            snapshot = mPlayer.getSnapshot();
            processSnapshot();
          }
          break;
        
        case "save":
          if (displayShot != null)
          {
            showFileSave(displayShot);
          }
          else
          {
            JOptionPane.showMessageDialog(null, "Please take a snapshot first.");
          }
          break;
      }
    }
  }
  
  private class UserInterfaceFrameMenuListener extends MouseAdapter
  {
    
    @Override
    public void mouseReleased(MouseEvent e)
    {
      
      switch (((JMenuItem) e.getSource()).getText().toLowerCase())
      {
        case "open":
          showFileSelect();
          break;
        case "exit":
          System.exit(0);
          break;
      }
    }
  }
  
  private class FrameListener implements ComponentListener
  {
    
    @Override
    public void componentResized(ComponentEvent e)
    {
      controlPanel.setPreferredSize(new Dimension(me.getWidth(), controlPanel.getHeight()));
      outputPanel.setPreferredSize(new Dimension(me.getWidth(), outputPanel.getHeight() - controlPanel.getHeight()));
      me.validate();
      me.repaint();
    }
    
    @Override
    public void componentMoved(ComponentEvent e)
    {
    }
    
    @Override
    public void componentShown(ComponentEvent e)
    {
    }
    
    @Override
    public void componentHidden(ComponentEvent e)
    {
    }
  }
  
  class PlayerEventListener implements MediaPlayerEventListener
  {
    
    @Override
    public void mediaChanged(MediaPlayer mp, libvlc_media_t l, String string)
    {
    }
    
    @Override
    public void opening(MediaPlayer mp)
    {
    }
    
    @Override
    public void buffering(MediaPlayer mp, float f)
    {
    }
    
    @Override
    public void playing(MediaPlayer mp)
    {
      playPauseButton.setText("Pause");
    }
    
    @Override
    public void paused(MediaPlayer mp)
    {
      playPauseButton.setText("Play");
    }
    
    @Override
    public void stopped(MediaPlayer mp)
    {
      playPauseButton.setText("Play");
    }
    
    @Override
    public void forward(MediaPlayer mp)
    {
      posSlider.setValue((int) (mPlayer.getPosition() * 100));
    }
    
    @Override
    public void backward(MediaPlayer mp)
    {
      posSlider.setValue((int) (mPlayer.getPosition() * 100));
    }
    
    @Override
    public void finished(MediaPlayer mp)
    {
      mPlayer.stop();
    }
    
    @Override
    public void timeChanged(MediaPlayer mp, long l)
    {
    }
    
    @Override
    public void positionChanged(MediaPlayer mp, float f)
    {
      if (!userSelectingLocation)
      {
        posSlider.setValue((int) (mPlayer.getPosition() * 100));
      }
    }
    
    @Override
    public void seekableChanged(MediaPlayer mp, int i)
    {
    }
    
    @Override
    public void pausableChanged(MediaPlayer mp, int i)
    {
    }
    
    @Override
    public void titleChanged(MediaPlayer mp, int i)
    {
    }
    
    @Override
    public void snapshotTaken(MediaPlayer mp, String string)
    {
    }
    
    @Override
    public void lengthChanged(MediaPlayer mp, long l)
    {
    }
    
    @Override
    public void videoOutput(MediaPlayer mp, int i)
    {
    }
    
    @Override
    public void error(MediaPlayer mp)
    {
    }
    
    @Override
    public void mediaMetaChanged(MediaPlayer mp, int i)
    {
    }
    
    @Override
    public void mediaSubItemAdded(MediaPlayer mp, libvlc_media_t l)
    {
    }
    
    @Override
    public void mediaDurationChanged(MediaPlayer mp, long l)
    {
    }
    
    @Override
    public void mediaParsedChanged(MediaPlayer mp, int i)
    {
    }
    
    @Override
    public void mediaFreed(MediaPlayer mp)
    {
    }
    
    @Override
    public void mediaStateChanged(MediaPlayer mp, int i)
    {
    }
    
    @Override
    public void newMedia(MediaPlayer mp)
    {
    }
    
    @Override
    public void subItemPlayed(MediaPlayer mp, int i)
    {
    }
    
    @Override
    public void subItemFinished(MediaPlayer mp, int i)
    {
    }
    
    @Override
    public void endOfSubItems(MediaPlayer mp)
    {
    }
  }
}
