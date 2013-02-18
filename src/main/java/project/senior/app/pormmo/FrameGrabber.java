/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package project.senior.app.pormmo;

import uk.co.caprica.vlcj.player.MediaPlayer;

/**
 *
 * @author Max De Benedetti
 */
public class FrameGrabber {

    private MediaPlayer mPlayer;
    
    /**
     * Creates a FrameGrabber that will extract the frames from the desired media player
     * @param mediaPlayer 
     */
    public FrameGrabber(MediaPlayer mediaPlayer){
        mPlayer = mediaPlayer;
    }
    
    /**
     * Does the frame grabbing
     * @param start where to start grabbing frames
     * @param stop where to start grabbing frames
     * @param fps how many frames to grab per second of video
     * @return A BufferedWrapper array ready to be processed and saved
     */
    public BufferedWrapper[] grabFrames(float start, float stop, float fps){
        
        
        
        return null;
        
    }
}
