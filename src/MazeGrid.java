import java.util.Random;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

public class MazeGrid
{
  private MazeCell[][] map;
  private final int width;
  private final int height;

  public MazeGrid(int x, int y)
  {
    map = new MazeCell[x][y];
    width = x;
    height = y;
    createMaze();
  }

  private boolean setIsWall(Coordinate coord, boolean isWall)
  {
    try
    {
      map[coord.getX()][coord.getY()].setIsWall(isWall);
      return true;
    }
    catch (Exception exception)
    {
      System.out.println("Exception Caught: " + exception.getMessage());
      return false;
    }
  }

  public void createMaze()
  {
    for(int i = 0; i < width; i++)
      for(int j = 0; j < height; j++)
        map[i][j] = new MazeCell(true);

    Coordinate start;

    start = this.genStart();
    setIsWall(start, false);

    System.out.println("Start: " + start);
  }

  private Coordinate genStart()
  {
    Random rand = new Random();
    double randVal = rand.nextDouble();
    if(randVal < 0.25) // starts from top
      return new Coordinate(rand.nextInt(width), height - 1); // top edge
    else if(randVal < 0.5)  // starts from left
      return new Coordinate(0, rand.nextInt(height)); // left edge
    else if(randVal < 0.75)  // starts from bottom
      return new Coordinate(rand.nextInt(width), 0); // bottom edge
    else // starts from right
      return new Coordinate(width - 1, rand.nextInt(height)); // right edge

  }

  public void draw(Graphics2D g2d){
		for(int i = 0; i < width; i++)
			for(int j = 0; j < height; j++)
      {
        if(map[i][j].isWall())
          g2d.setColor(Color.BLACK);
        else
          g2d.setColor(Color.WHITE);
        g2d.fillRect(i*100, j*100, 100, 100);
      }

  }
} // MazeGrid
