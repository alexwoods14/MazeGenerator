/**
 * Random breadth-first maze generation program.
 * Takes 2 arguments of x and y and makes a random maze with start at bottom
 * left (0,0) and end at top right (x,y)
 *
 * @param width the desired width of the maze
 * @param height the desired height of the maze
 * @author Alex Woods
 */
public class MazeGenerator {
  public static void main(String[] args) {
   
    // Checks if there are a correct number of Arguments
    if(args == null || args.length != 2)
      throw new RuntimeException("Must be 2 arguments, width and height for maze size");

    int width;
    int height;

    //attempts to pass
    try
    {
      width = Integer.parseInt(args[0]); 
      heigh = Integer.parseInt(args[1]);
      MazeWindow maze = new MazeWindow(width, height);
      maze.setVisible(true);
    }
    catch (NumberFormatException exception)
    {
      System.out.println("Must be 2 Integer arguments, width and height for desired "
                         + "maze size");
      System.out.println("Message: " + exception.getMessage());
    }    
  }
}
