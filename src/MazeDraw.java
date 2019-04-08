import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

class MazeDraw extends JPanel
{
  private MazeGrid maze;
  public MazeDraw()
  {
    maze = new MazeGrid(50,50); // make an X*Y maze
  }
  
  @Override
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
  	g2d.setColor(Color.white);
    g2d.setColor(Color.BLACK);
    maze.draw(g2d);
    
    //repaint();
  }
}
