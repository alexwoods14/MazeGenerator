/**
 * Random breadth-first maze generation program.
 * Takes 2 arguments of x and y and makes a random maze with start at bottom
 * left (0,0) and end at top right (x,y)
 *
 * @param x
 * @param y
 * @author Alex Woods
 */
public class MazeGenerator {
  public static void main(String[] args) {
   
    // Checks if there are a correct number of Arguments
    if(args == null || args.length != 2)
      throw new RuntimeException("Must be 2 arguments, X and Y for maze size");

    int x; int y;

    //attempts to pass
    try
    {
      x = Integer.parseInt(args[0]); 
      y = Integer.parseInt(args[1]);
      MazeWindow maze = new MazeWindow(x, y);
      maze.setVisible(true);
    }
    catch (NumberFormatException exception)
    {
      System.out.println("Must be 2 Integer arguments, x and y for desired "
                         + "maze size");
      System.out.println("Message: " + exception.getMessage());
    }    
  }
}
