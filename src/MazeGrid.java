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
  private Tree<Coordinate> fullRoute;

  public MazeGrid(int width, int height)
  {
    this.width = width;
    this.height = height;
    if(width % 2 != 0)
      width += 1;
    if(height % 2 != 0)
      height += 1;

    map = new MazeCell[this.width][this.height];

    visited = new HashSet<Coordinate>();
    
    for(int i = 0; i < width; i++)
      for(int j = 0; j < height; j++)
        map[i][j] = new MazeCell(true);
    
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

    Coordinate start;

    //start = this.genStart();
    start = new Coordinate(0,0);
    setIsWall(start, false);

    visited.add(start);
    fullRoute = new Tree<Coordinate>(start, null);

    Coordinate next;

    next = nextLocation(start);
    Tree<Coordinate> currentRoute = fullRoute.addChild(next);

    while(true)
    {
      next = nextLocation(next);
      if(next == null)
      {
        currentRoute = backtrack(currentRoute);
        if(currentRoute.getValue().equals(start))
        {
          System.out.println("Generated");
          break;
        }
        else
          next = nextLocation(currentRoute.getValue());
      }
      else
        currentRoute = currentRoute.addChild(next);
    }
  }

  private Coordinate nextLocation(Coordinate current)
  {
    if(!canMove(current))
      return null;

    Coordinate buffer;
    Coordinate buffer2;
    Random rand = new Random();
    double randVal = rand.nextDouble();
    if(randVal < 0.25) // try left
    {
      buffer = current.getLeft();
      buffer2 = buffer.getLeft();
      if(visit(buffer2)) // not been visited yet
      {
        if(setIsWall(buffer, false) && setIsWall(buffer2, false))
          return buffer2;
        else
          return nextLocation(current);
      }
      else
        return nextLocation(current);
    }
    else if(randVal < 0.5) // try right
    {
      buffer = current.getRight();
      buffer2 = buffer.getRight();
      if(visit(buffer2)) // not been visited yet
      {
        if(setIsWall(buffer, false) && setIsWall(buffer2, false))
          return buffer2;
        else
          return nextLocation(current);
      }
      else
        return nextLocation(current);
    }
    else if(randVal < 0.75) // above
    {
      buffer = current.getAbove();
      buffer2 = buffer.getAbove();
      if(visit(buffer2)) // not been visited yet
      {
        if(setIsWall(buffer, false) && setIsWall(buffer2, false))
          return buffer2;
        else
          return nextLocation(current);
      }
      else
        return nextLocation(current);
    }
    else // below
    {
      buffer = current.getBelow();
      buffer2 = buffer.getBelow();
      if(visit(buffer2)) // not been visited yet
      {
        if(setIsWall(buffer, false) && setIsWall(buffer2, false))
          return buffer2;
        else
          return nextLocation(current);
      }
      else
        return nextLocation(current);
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
    Coordinate adjacent;
    if(isPossible((adjacent = current.getLeft().getLeft())) &&
                     !visited.contains(adjacent))
      return true;
    if(isPossible((adjacent = current.getRight().getRight())) &&
                      !visited.contains(adjacent))
      return true;
    if(isPossible((adjacent = current.getAbove().getAbove())) &&
                      !visited.contains(adjacent))
      return true;
    if(isPossible((adjacent = current.getBelow().getBelow())) &&
                      !visited.contains(adjacent))
      return true;

    return false;    
  }

  private Tree<Coordinate> backtrack(Tree<Coordinate> start)
  {
    while(start.getParent() != null && !canMove(start.getValue()))
      return backtrack(start.getParent());
    
    return start;
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
        
        g2d.fillRect(i*10, (height - j - 1)*10, 10, 10);
      }

  }
} // MazeGrid
