import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 * This initialises the GUI and is where all the GUI drawing is handled
 *
 * @author Alex Woods
 */
class MazeDraw extends JPanel
{
  private MazeGrid maze;

  /** 
   * Creates a new maze
   *
   * @param width width of the maze
   * @param height height of the maze
   */
  public MazeDraw(int width, int height)
  {
    maze = new MazeGrid(width, height); // make a maze of size width*height
  }
  
  /** 
   * Override from Graphics. Handles all the things that should happen between
   * repaints
   * This generates it block by block and repaints it every time a step happens
   *
   * @param g Instance of Graphics to use
   */
  @Override
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
    
    // while it is not made, step through each stage until it is
    if(!maze.isDone())
    {
      maze.step();
      try // Thread.sleep must be used in try block
      {
        Thread.sleep(10); // time in milliseconds before frame redraw
      }
      catch(InterruptedException ex)
      {
        Thread.currentThread().interrupt();
      }
      // after step is made, redraw it
      maze.draw(g2d);
      repaint();
    }
    else
      // redraw final model when whole maze is made
      maze.draw(g2d);
  }
}
