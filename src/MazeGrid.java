import java.util.Random;

import java.awt.Graphics;
import java.awt.Graphics2D;

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
        map[i][j] = new MazeCell(false);

    Coordinate start;
    Coordinate end;

    start = this.genStart();
    end = this.genEnd(start);

    System.out.println("Start: " + start);
    System.out.println("End:   " + end);
  }

  private Coordinate genStart()
  {
    Random rand = new Random();
    double randVal = rand.nextDouble();
    if(randVal < 0.25) // starts from top
      return new Coordinate(rand.nextInt(width), height); // top edge
    else if(randVal < 0.5)  // starts from left
      return new Coordinate(0, rand.nextInt(height)); // left edge
    else if(randVal < 0.75)  // starts from bottom
      return new Coordinate(rand.nextInt(width), 0); // bottom edge
    else // starts from right
      return new Coordinate(width, rand.nextInt(height)); // right edge

  }

  private Coordinate genEnd(Coordinate start)
  {
    Random rand = new Random();
    Coordinate end;
    double randVal = rand.nextDouble();
    if(randVal < 0.25) // starts from top
      end = new Coordinate(rand.nextInt(width), height); // top edge
    else if(randVal < 0.5)  // starts from left
      end = new Coordinate(0, rand.nextInt(height)); // left edge
    else if(randVal < 0.75)  // starts from bottom
      end = new Coordinate(rand.nextInt(width), 0); // bottom edge
    else // starts from right
      end = new Coordinate(width, rand.nextInt(height)); // right edge

    if(!end.equals(start))
      return end;
    else
    {
      setIsWall(start, true);
      setIsWall(end, true);
      return genEnd(start);
    }

  }

  public void draw(Graphics2D g2d){
    System.out.println("here");
		for(int i = 0; i < width; i++)
			for(int j = 0; j < height; j++)
        if(map[i][j].isWall())
          g2d.fillRect(i*100, j*100, 100, 100);
  }

} // MazeGrid