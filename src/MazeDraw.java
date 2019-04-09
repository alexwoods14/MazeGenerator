import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

class MazeDraw extends JPanel
{
  private MazeGrid maze;

  public MazeDraw()
  {
    maze = new MazeGrid(30,30); // make an X*Y maze
  }
  
  @Override
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
    
    if(!maze.isDone())
    {
      maze.step();
      try
      {
        Thread.sleep(50);
      }
      catch(InterruptedException ex)
      {
        Thread.currentThread().interrupt();
      }
      maze.draw(g2d);
      repaint();
    }
    maze.draw(g2d);
  }
}
