import javax.swing.JFrame;

public class MazeWindow extends JFrame{
  
  public MazeWindow() {

    setTitle("Maze Game");
    setSize(500, 500);
    setLocationRelativeTo(null);        
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    add(new MazeDraw());

  }
}
