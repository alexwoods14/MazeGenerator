import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import java.util.Date;

/**
 * This initialises the GUI and is where all the GUI drawing is handled
 *
 * @author Alex Woods
 */
class MazeDraw extends JPanel
{
  private MazeGrid maze;
  private MazeSolver solver;

  /** 
   * Creates a new maze
   *
   * @param width width of the maze
   * @param height height of the maze
   */
  public MazeDraw(int width, int height)
  {
    Date before = new Date();
    maze = new MazeGrid(width, height); // make a maze of size width*height
    Date after = new Date();
    System.out.println("Time taken = " + (after.getTime() - before.getTime())
                                          /1000.0 + " seconds" );

    solver = new MazeSolver(maze.getFullRoute(), width, height,
                            maze.getCells());
  }

  /** 
   * Override from Graphics. Handles all the things that should happen between
   * repaints
   *
   * @param g Instance of Graphics to use
   */
  @Override
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
  	g2d.setColor(Color.white);
    g2d.setColor(Color.BLACK);
    
    if(!solver.isSolved())
    {
      solver.step();
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
