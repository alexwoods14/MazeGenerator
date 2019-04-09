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
  private Tree<Coordinate> currentRoute;
  private boolean genFinished = false;

  private Coordinate start;
  private Coordinate next;
  private boolean boxxedIn;

  private Directions direction;
  private boolean newMove;
  private int backtracked;

  private enum Directions{
    LEFT, RIGHT, UP, DOWN
  }

  public MazeGrid(int width, int height)
  {
    if(height % 2 == 0)
      this.width = width + 1;
    else
      this.width = width;
    if(width % 2 == 0)
      this.height = height + 1;
    else
      this.height = height;

    map = new MazeCell[this.width][this.height];

    visited = new HashSet<Coordinate>();
    
    for(int i = 0; i < this.width; i++)
      for(int j = 0; j < this.height; j++)
        map[i][j] = new MazeCell(true);
    

    start = new Coordinate(0,0);
    setIsWall(start, false);
    visit(start);

    fullRoute = new Tree<Coordinate>(start, null);


    direction = nextLocation(start);
    next = move(start);

    currentRoute = fullRoute.addChild(next);
    setIsWall(next, false);
    visit(next);

    newMove = false;
    
    boxxedIn = false;
    backtracked = 0;
  }

  public boolean isDone()
  {
    return genFinished;
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

  private void setBoxxedIn(Coordinate coord)
  {
    map[coord.getX()][coord.getY()].finalPath();
  }

  public void step()
  {
    if(!boxxedIn)
    {
      if(newMove)
      {
        direction = nextLocation(next);
        if(direction != null)
          next = move(next);
        newMove = false;
      }
      else
      {
        next = move(next);
        newMove = true;
      }
      setIsWall(next, false);
      visit(next);
      currentRoute = currentRoute.addChild(next);
    }
    else
    {
      currentRoute = currentRoute.getParent();
      next = currentRoute.getValue();
      setBoxxedIn(next);
      if(next.equals(start))
      {
        System.out.println("Generated");
        genFinished = true;
      }
      else
      {
        if(canMove(next))
        {
          boxxedIn = false;
          newMove = true;
        }
      }
    }
  }

  private Coordinate move(Coordinate current)
  {
    Coordinate next = null;
    switch(direction)
    {
      case LEFT:  next = current.getLeft();
                  visit(current.getLeft());
                  visit(current.getRight());
                  visit(current.getAbove());
                  visit(current.getBelow());
                  break;
      case RIGHT: next = current.getRight();
                  visit(current.getLeft());
                  visit(current.getRight());
                  visit(current.getAbove());
                  visit(current.getBelow());
                  break;
      case UP:    next = current.getAbove();
                  visit(current.getLeft());
                  visit(current.getRight());
                  visit(current.getAbove());
                  visit(current.getBelow());
                  break;
      case DOWN:  next = current.getBelow();
                  visit(current.getLeft());
                  visit(current.getRight());
                  visit(current.getAbove());
                  visit(current.getBelow());
                  break;
    }
    return next;
  }

  private Directions nextLocation(Coordinate current)
  {
    if(!canMove(current))
    {
      boxxedIn = true;
      return null;
    }
    else
      boxxedIn = false;

    /*

      buffer = current.getLeft();
      buffer2 = buffer.getLeft();
      if(visit(buffer2)) // not been visited yet
      {
        if(setIsWall(buffer, false) && setIsWall(buffer2, false))
          return buffer2;
        else
          return nextLocation(current);
      }

        visit(current.getLeft());
        visit(current.getLeft().getLeft());
        setIsWall(current.getLeft(), false);
        setIsWall(current.getLeft().getLeft(), false);
        return current.getLeft().getLeft();
     */

    Random rand = new Random();
    double randVal = rand.nextDouble();
    if(randVal < 0.25) // try left
    {
      if(isValid(Directions.LEFT, current))
        return(Directions.LEFT);
      else
        return nextLocation(current);
    }
    else if(randVal < 0.5) // try right
    {
      if(isValid(Directions.RIGHT, current))
        return(Directions.RIGHT);
      else
        return nextLocation(current);
    }
    else if(randVal < 0.75) // above
    {
      if(isValid(Directions.UP, current))
        return(Directions.UP);
      else
        return nextLocation(current);
    }
    else // below
    {
      if(isValid(Directions.DOWN, current))
        return(Directions.DOWN);
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

  private boolean isValid(Directions d, Coordinate c)
  {
    boolean possible = false;
    Coordinate adjacent;
    switch(d)
    {
      case LEFT:  possible = isPossible((adjacent = c.getLeft().getLeft())) &&
                     !visited.contains(adjacent);
                  break;
      case RIGHT: possible = isPossible((adjacent = c.getRight().getRight())) &&
                     !visited.contains(adjacent);
                  break;
      case UP:    possible = isPossible((adjacent = c.getAbove().getAbove())) &&
                     !visited.contains(adjacent);
                  break;
      case DOWN:  possible = isPossible((adjacent = c.getBelow().getBelow())) &&
                     !visited.contains(adjacent);
                  break;
    }
    return possible;
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

        if(!map[i][j].isWall())
        {
          if(map[i][j].isFinalPath())
            g2d.setColor(Color.WHITE);
          else
            g2d.setColor(Color.GRAY);
        }

        //if(visited.contains(temp))
        //  g2d.setColor(Color.BLUE);


        if(i == 0 && j == 0)
          g2d.setColor(Color.GREEN);

        else if(i == width - 1 && j == height - 1)
          g2d.setColor(Color.RED);
        
        g2d.fillRect(i*17, (height - j - 1)*17, 17, 17);
      }

  }
} // MazeGrid
