/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package project.senior.app.pormmo;

import java.util.ArrayList;
import uk.co.caprica.vlcj.player.MediaPlayer;

/**
 *
 * @author Max De Benedetti
 */
public class FrameGrabber {

    private MediaPlayer mplayer;
    
    /**
     * Creates a FrameGrabber that will extract the frames from the desired media player
     * @param mediaPlayer 
     */
    public FrameGrabber(MediaPlayer mediaPlayer){
        mplayer = mediaPlayer;
    }
    
    /**
     * Does the frame grabbing
     * @param start where to start grabbing frames
     * @param stop where to start grabbing frames
     * @param fps how many frames to grab per second of video
     * @return A BufferedWrapper array ready to be processed and saved
     */
    public BufferedWrapper[] grabFrames(long start, long stop, float fps){
        
        /*
         * I am going to use an ArrayList for now
         * Using an array will require an algorithm that determins the number of frames of output
         * An array is desireable, but low priority
         */
        ArrayList<BufferedWrapper> frames = new ArrayList<>();
        
        mplayer.setPause(true);
        mplayer.setPosition(0);
        
        //For now, I will assume that upscaling and downscaling the fps does not require seperate algorithms
        long skipLength;
        skipLength = (long)(1/ (fps /1000));
        long frameNum = 0;
        
        if(mplayer.getFps() == fps){
            while(mplayer.getTime() < stop){
                frames.add(new BufferedWrapper(mplayer.getSnapshot(), frameNum));
                frameNum++;
                mplayer.nextFrame();
            }
        }
        else{
            while(mplayer.getTime() < stop){
                frames.add(new BufferedWrapper(mplayer.getSnapshot(), frameNum));
                frameNum++;
                mplayer.skip(skipLength);
            }
        }
        
        BufferedWrapper[] outFrames = new BufferedWrapper[frames.size()];
        
        return frames.toArray(outFrames);
        
    }
}
