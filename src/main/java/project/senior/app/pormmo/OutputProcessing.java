/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package project.senior.app.pormmo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Max De Benedetti
 */
public class OutputProcessing {

    private ArrayList<BufferedImage> frames;

    public ArrayList<BufferedImage> getFrames() {
        return frames;
    }

    public void setFrames(ArrayList<BufferedImage> frames) {
        this.frames = frames;
    }    
    
    public void processFrames(String path){
        for(int i =0; i < frames.size(); i++){
            try {
                ImageIO.write(frames.get(i), "png", new File(path + "-" + i + ".png"));
            } catch (IOException ex) {
                Logger.getLogger(OutputProcessing.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
