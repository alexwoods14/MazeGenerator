import javax.swing.JFrame;

/**
 * The JFrame which the GUI maze is within
 *
 * @author Alex Woods
 */
public class MazeWindow extends JFrame{
  
  /**
   * Sets up the JFrame settings and adds a new Maze to the GUI
   *
   * @param width Desired width
   * @param height Desired height
   */
  public MazeWindow(int width, int height) {

    setTitle("Maze Game");
    setSize(500, 500);
    setLocationRelativeTo(null);        
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    add(new MazeDraw(width, height));

  }
}
