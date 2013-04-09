package project.senior.app.pormmo;

import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventListener;

/**
 * @author John Fisher
 */
  class PlayerEventListener implements MediaPlayerEventListener
  {

    private MediaPlayer mPlayer;
    private PlayerControlPanel parent;
    
    public PlayerEventListener(PlayerControlPanel parent, MediaPlayer mPlayer){
      this.mPlayer = mPlayer;
      this.parent = parent;
    }
    
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
//      mPlayer.stop();
      parent.FindSliderByName("PlayPosition").setValue(0);
      parent.FindButtonByName("Play").setText("Play");
    }

    @Override
    public void timeChanged(MediaPlayer mp, long l)
    {
    }

    @Override
    public void positionChanged(MediaPlayer mp, float f)
    {
      if (!parent.parent.userSelectingLocation)
      {
        parent.FindSliderByName("PlayPosition").setValue((int) (mPlayer.getPosition() * 100));
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
