/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package project.senior.app.pormmo;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *  Saves our buffered images to png's
 * I think it is a good idea to have separate save classes for the different kinds of outputs.
 * For now though, this will just be called SaveUnit
 * @author Max De Benedetti
 */
public class SaveUnit {

    /**
     * Saves a BufferedWrapper to a png in a specified folder.
     * @param bw BufferedWrapper that is the image to be saved
     * @param filePath String representing the file path to where the file should be saved
     */
    public static void save(BufferedWrapper bw, String filePath){
        try {
            File outFile = new File(fileName(bw.frameNum, filePath));
            ImageIO.write(bw.img, "png", outFile);
        } catch (IOException ex) {
            Logger.getLogger(SaveUnit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This method contains our file naming algorithm
     * I am not sure what that algorithm is yet.
     * Having it as a separate method will make it easier to alter later.
     * @param filePath 
     * @param orderNum
     * @return The name of the output file
     */
    private static String fileName( long orderNum, String filePath){
        return filePath + orderNum + ".png";
    }
    
    
    
}
