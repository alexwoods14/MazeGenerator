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
    
    createMaze();
  }

  public Tree<Coordinate> getFullMap()
  {
    return fullRoute;
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
    boolean boxxedIn = false;
    boolean newMove = false;
  
    Coordinate start = new Coordinate(0,0);
    visited.add(start);
    setIsWall(start, false);
    fullRoute = new Tree<Coordinate>(start, null);
    
    
    Directions direction;

    direction = nextLocation(start);
    Coordinate next = move(start, direction);

    Tree<Coordinate> currentRoute = fullRoute.addChild(next);
    setIsWall(next, false);
    visit(next);


    while(true)
    {
      ////
      if(!boxxedIn)
      {
        if(newMove)
        {
          direction = nextLocation(next);
          if(direction == null)
            boxxedIn = true;
          else
            next = move(next, direction);
          newMove = false;
        }
        else
        {
          next = move(next, direction);
          newMove = true;
        }
        setIsWall(next, false);
        visit(next);
        currentRoute = currentRoute.addChild(next);
      }
      else
      {
        currentRoute = backtrack(currentRoute);
        if(next.equals(start))
        {
          System.out.println("Generated");
          break;
        }
        next = currentRoute.getValue();
        boxxedIn = false;
        newMove = true;
      }
    }
  }

  private Coordinate move(Coordinate current, Directions direction)
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
      return null;
    }

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
          g2d.setColor(Color.WHITE);
        if(i == 0 && j == 0)
          g2d.setColor(Color.GREEN);
        else if(i == width - 1 && j == height - 1)
          g2d.setColor(Color.RED);
        
        g2d.fillRect(i*10, (height - j - 1)*10, 10, 10);
      }

  }
} // MazeGrid
