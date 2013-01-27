package project.senior.app.pormmo;

import com.sun.jna.Native;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.JPanel;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventListener;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

/**
 * @author John Fisher
 */
public class VlcInterface extends JPanel
{

    private MediaPlayerFactory mPlayerFactory;
    private MediaPlayer mPlayer;
    private File sourceFile;
    private BufferedImage bi;
    
    public VlcInterface(File sourceFile)
    {
        this.sourceFile = sourceFile;
        
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
        mPlayerFactory = new MediaPlayerFactory();        
        mPlayer = mPlayerFactory.newEmbeddedMediaPlayer();
            mPlayer.addMediaPlayerEventListener(new PlayerEventListener());
    }

    public void Play()
    {
        mPlayer.startMedia(sourceFile.getAbsolutePath());
    }

    public void Stop()
    {
        mPlayer.stop();
    }
    
    public void Pause()
    {
        mPlayer.pause();
    }
    
    public void Snapshot()
    {
        bi = mPlayer.getSnapshot();
    }
    
    public void Forward()
    {
      mPlayer.skip(1000);
    }
    
    public void Rewind()
    {
      mPlayer.skip(-1000);
    }

    public long getTime(){
      return mPlayer.getTime();
    }
    
    public BufferedImage LastSnapShot(){        
        return bi;
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
        }

        @Override
        public void paused(MediaPlayer mp)
        {
        }

        @Override
        public void stopped(MediaPlayer mp)
        {
        }

        @Override
        public void forward(MediaPlayer mp)
        {
        }

        @Override
        public void backward(MediaPlayer mp)
        {
        }

        @Override
        public void finished(MediaPlayer mp)
        {
        }

        @Override
        public void timeChanged(MediaPlayer mp, long l)
        {
        }

        @Override
        public void positionChanged(MediaPlayer mp, float f)
        {
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
