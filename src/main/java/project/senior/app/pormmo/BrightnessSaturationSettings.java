package project.senior.app.pormmo;

/**
 *
 * @author Shelby Copeland, John Fisher
 */
public class BrightnessSaturationSettings
{
    private float brightness, saturation;
    
    public BrightnessSaturationSettings(float b, float s)
    {
        brightness = b;
        saturation = s;
    }
    
    public float[] getSettings()
    {
        return new float[] { brightness, saturation };
    }
}
