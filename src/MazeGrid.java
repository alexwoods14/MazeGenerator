import java.util.Random;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

import java.util.Set;
import java.util.HashSet;

public class MazeGrid
{
  private MazeCell[][] map;
  private final int width;
  private final int height;
  private Set<Coordinate> visited;

  public MazeGrid(int x, int y)
  {
    map = new MazeCell[x][y];
    width = x;
    height = y;
    visited = new HashSet<Coordinate>();
    
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

    //start = this.genStart();
    start = new Coordinate(0,0);
    setIsWall(start, false);
    visited.add(start);

    System.out.println("Start: " + start);

    Coordinate next;

    next = nextLocation(start);

    System.out.println(visited.size());

    while(visited.size() < 50)
    {
      next = nextLocation(next);
    }
  }

  private Coordinate nextLocation(Coordinate current)
  {
    if(!canMove(current))
      return null;

    Coordinate buffer;
    Random rand = new Random();
    double randVal = rand.nextDouble();
    if(randVal < 0.25) // try left
    {
      buffer = current.getLeft();
      if(visit(buffer)) // not been visited yet
      {
        if(setIsWall(buffer, false))
        {
          //visit(current.getLeft());
          //visit(current.getRight());
          visit(current.getAbove());
          visit(current.getBelow());
          return buffer;
        }
        else
          return nextLocation(current);
      }
      else
      {
        System.out.println("already been there");
        return nextLocation(current);
      }
    }
    else if(randVal < 0.5) // try right
    {
      buffer = current.getRight();
      if(visit(buffer)) // not been visited yet
      {
        if(setIsWall(buffer, false))
        {
          //visit(current.getLeft());
          //visit(current.getRight());
          visit(current.getAbove());
          visit(current.getBelow());
          return buffer;
        }
        else
          return nextLocation(current);
      }
      else
      {
        System.out.println("already been there");
        return nextLocation(current);
      }
    }
    else if(randVal < 0.75) // above
    {
      buffer = current.getAbove();
      if(visit(buffer)) // not been visited yet
      {
        if(setIsWall(buffer, false))
        {
          visit(current.getLeft());
          visit(current.getRight());
          //visit(current.getAbove());
          //visit(current.getBelow());
          return buffer;
        }
        else
          return nextLocation(current);
      }
      else
      {
        System.out.println("already been there");
        return nextLocation(current);
      }
    }
    else // below
    {
      buffer = current.getBelow();
      if(visit(buffer)) // not been visited yet
      {
        if(setIsWall(buffer, false))
        {
          visit(current.getLeft());
          visit(current.getRight());
          //visit(current.getAbove());
          //visit(current.getBelow());
          return buffer;
        }
        else
          return nextLocation(current);
      }
      else
      {
        System.out.println("already been there");
        return nextLocation(current);
      }
    }
  }

  private boolean visit(Coordinate next)
  {
    if(isPossible(next))
      return visited.add(next);
    else
      return false;

  }

  private boolean isPossible(Coordinate c)
  {
    int x = c.getX();
    int y = c.getY();
    return (x >= 0 && x < width && y >= 0 && y < height);
  }

  private boolean canMove(Coordinate current)
  {
    
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
    Coordinate temp;
		for(int i = 0; i < width; i++)
			for(int j = 0; j < height; j++)
      {
        temp = new Coordinate(i,j);
        
        g2d.setColor(Color.BLACK);

        if(visited.contains(temp))
          g2d.setColor(Color.GRAY);
        if(!map[i][j].isWall())
          g2d.setColor(Color.WHITE);
        
        g2d.fillRect(i*50, (height - j - 1)*50, 50, 50);
      }

  }
} // MazeGrid
