package project.senior.app.pormmo;

/**
 *
 * @author Shelby Copeland
 */
public class SequenceOrder
{
  private String order;
  
  public final int GS = 1,
                   MEDIAN = 2,
                   MEAN = 3,
                   GAUSSIAN = 4,
                   P16B = 5,
                   REMOVE_POST = 6,
                   REMOVE_PRE = 7;
  
  public SequenceOrder(){order = "";}
  
  /**
   * For a list of actions, call your SequenceOrder object for a list
   *   of final [const] ints.
   * 
   * @param action The action used.
   */
  public void AddAction(int action)
  {
    order += action + "";
  }
  
  
  private void TEST()
  {
    System.out.println(order);
    int[] test = GetOrder();
    
    for(int i : test)
      System.out.println(i);
  }
  
  public void Reset()
  {
    //TEST();
    order = "";
  }
  
  /**
   * @return int[] containing the actions in order, starts at 0 
   */
  public int[] GetOrder()
  {
    int[] ret = new int[order.length()];
    
    int i = 0;
    for(char c : order.toCharArray())
      ret[i++] = Integer.parseInt(c + "");
    
    return ret;
  }
}
