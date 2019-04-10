import java.util.Random;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

import java.util.Set;
import java.util.HashSet;

/**
 * Class which makes the maze. Generates a random breadth first maze
 * Can be visually stepped through one by one by calling the step method
 *
 * @author Alex Woods
 */
public class MazeGrid
{
  private MazeCell[][] map;
  private final int width;
  private final int height;
  private Set<Coordinate> visited;

  // Tree for the full route
  private Tree<Coordinate> fullRoute;
  // Temporary placeholder for the top of the current tree
  private Tree<Coordinate> currentRoute;

  private boolean genFinished = false;

  // Start coordinate, (0, 0)
  private Coordinate start;
  // next/current coordinate
  private Coordinate next;

  // true means there is no free space around it to move into
  private boolean boxxedIn;

  // current direction of travel
  private Directions direction;
  // whether this move is in a new direction or not
  private boolean newDirection;


  /** 
   * Enum to hold the values of different directions that can be traversed
   */
  private enum Directions{
    LEFT, RIGHT, UP, DOWN
  }

  /** 
   * Creates the instance of MazeGrid of size width * height
   *
   * @param width The desired width of the maze
   * @param height The desired height of the maze
   */
  public MazeGrid(int width, int height)
  {
    // the next 2 if statements guaruantee there is not am empty column far
    // right or empty top row
    if(height % 2 == 0)
      this.width = width + 1;
    else
      this.width = width;
    if(width % 2 == 0)
      this.height = height + 1;
    else
      this.height = height;

    // make a 2D array of MazeCells which make rendering a lot easier. Each
    // item represents 1 block in the maze
    map = new MazeCell[this.width][this.height];

    // HashSet to check which Coordinates have already been reached so they are
    // not visited twice unless backtracking.
    visited = new HashSet<Coordinate>();
    
    // initialise all cells as walls
    for(int i = 0; i < this.width; i++)
      for(int j = 0; j < this.height; j++)
        map[i][j] = new MazeCell(true);


    // initialise start coordinate 
    start = new Coordinate(0,0);
    setIsWall(start, false);
    visit(start);

    // initialise the full Tree
    fullRoute = new Tree<Coordinate>(start, null);

    // choose the first direction to take and move in that direction,
    // initialising the next coordinate
    direction = nextLocation(start);
    next = move(start);

    // initialise the current tree by adding the next coordinate. Visit the
    // coordinate and unset it as a wall
    currentRoute = fullRoute.addChild(next);
    setIsWall(next, false);
    visit(next);

    // onlt beeen one move so not a new direction yet
    newDirection = false;

    // set boxxedIn as false as that is all it can be 
    boxxedIn = false;
  }

  /** 
   * Checks if generation has finished
   *
   * @return
   */
  public boolean isDone()
  {
    return genFinished;
  }

  /**
   * Tries to set the cell at the location of coord to a wall. Catches
   * Exception if coord is out of the grid. However, this should never happen.
   * returns true for a successful set. False if exception thrown
   *
   * @param coord the location to be made a wall
   * @param isWall true if it should be set a wall, else false
   *
   * @return true if it succeeds, false if an exception is caught
   */
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

  /**
   * This sets the cell to be boxxed in if it has been in this spot and
   * backtracked as there is nowhere it can move
   *
   * @param coord Coordinate to set.
   */
  private void setBoxxedIn(Coordinate coord)
  {
    // get the x and y accordingly and call the instance method
    map[coord.getX()][coord.getY()].finalPath();
  }


  /** 
   * One call is a single step through the generation
   *
   */
  public void step()
  {
    if(!boxxedIn)   // if it is not boxxed in, i.e. there is a direction that 
      {             // it can move in
      if(newDirection)
      // if its a new direction, select a direction and move once in that
      // direction
      {
        direction = nextLocation(next);
        // nextLocation returns null if it cant move
        if(direction != null) // it is is not boxxed in, move
          next = move(next);
        // next move is then not a new direction as this one was.
        newDirection = false;
      }
      else
      {
        // if not a new direction. Continue moving in this direction, next move
        // is in a new direction
        next = move(next);
        newDirection = true;
      }
      // whether it is a newmove or not. Add it to the tree, visit it and set
      // it as a walll
      setIsWall(next, false);
      visit(next);
      currentRoute = currentRoute.addChild(next);
    }
    else
    {
      // if it cannot progress further from where it is (boxxed in)
      // new root node is the parent of the current (go back one step)
      currentRoute = currentRoute.getParent();
      // current (next) coordinate of that is the value of that tress root node
      next = currentRoute.getValue();
      // that coordinate is boxxed in as the previous was null.
      setBoxxedIn(next);

      // if the current location is at the start then the maze has been
      // generated, it has traversed all the way back to the start and we can
      // exit the generation by setting genFinished so step is not called again
      if(next.equals(start))
      {
        System.out.println("Generated");
        genFinished = true;
      }
      else
      {
        // if it is not the start then check if we can move, if we can, unset
        // boxxedIn and declare next move is in a new direction.
        if(canMove(next))
        {
          boxxedIn = false;
          newDirection = true;
        }
      }
    }
  }

  /**
   * This returns the next Coordinate in the direction specified.
   * It also visits all cells adjacent to the one it is moving from to ensure
   * that there is always a single wall around all paths
   *
   * @param current The Coordinate to move from
   * @return The resulting Coordinate
   */
  private Coordinate move(Coordinate current)
  {
    // visits all surrounding to ensure a single layer wall around the paths
    visit(current.getLeft());
    visit(current.getRight());
    visit(current.getAbove());
    visit(current.getBelow());
    
    Coordinate next = null;
    switch(direction)
    {
      case LEFT:  next = current.getLeft();
                  break;
      case RIGHT: next = current.getRight();
                  break;
      case UP:    next = current.getAbove();
                  break;
      case DOWN:  next = current.getBelow();
                  break;
    }
    return next;
  }

  /**
   * Calculates the next direction to travel.
   * Only returns a possible direction.
   * If there is none possible then it returns null
   *
   * @param current the Coordinate that the direction will taken from
   */
  private Directions nextLocation(Coordinate current)
  {
    // if there are no valid moves then return back null
    if(!canMove(current))
    {
      boxxedIn = true;
      return null;
    }
    else
      boxxedIn = false;

    // create a random variable and use the result of this to equally choose
    // which direction to take
    Random rand = new Random();
    double randVal = rand.nextDouble();
    if(randVal < 0.25) // try left
    {
      // check if the direction is valid before taking it. i.e. This move is
      // possible
      if(isValid(Directions.LEFT, current))
        // if it is then return it
        return(Directions.LEFT);
      else
        // if not then recursively call this method until a valid one is
        // chosen. This is not the most efficient but it is simple and
        // effective
        return nextLocation(current);
    }
    else if(randVal < 0.5) // try right
    {
      if(isValid(Directions.RIGHT, current))
        return(Directions.RIGHT);
      else
        return nextLocation(current);
    }
    else if(randVal < 0.75) // try above
    {
      if(isValid(Directions.UP, current))
        return(Directions.UP);
      else
        return nextLocation(current);
    }
    else // try below
    {
      if(isValid(Directions.DOWN, current))
        return(Directions.DOWN);
      else
        return nextLocation(current);
    }
  }

  /**
   * if the Coordinate is in the maze then add it to visited if it does not
   * already exist in it
   *
   * @param coord the Coordinate to be adding
   * @return
   */
  private boolean visit(Coordinate coord)
  {
    if(isPossible(coord))
      // returns true if not already in it
      return visited.add(coord);
    else
      return false;

  }

  /** 
   * Checks if a coordinate is a valid location in the maze.
   * This depends on its x and y locations
   *
   * @param coord the Coordinate to check
   * @return true if valid, else, false
   */
  private boolean isPossible(Coordinate coord)
  {
    int x = coord.getX();
    int y = coord.getY();
    // boolean expression to test if it is within the bounds
    return (x >= 0 && x < width && y >= 0 && y < height);
  }

  /** 
   * Checks if a direction from a location is a possible direction to travel
   *
   * @param direction desired direction of travel
   * @param coord the coordinate it is travelling from
   * @return true if it can move in that direction, else false
   */
  private boolean isValid(Directions direction, Coordinate coord)
  {
    boolean possible = false;
    Coordinate adjacent;
    switch(direction)
    {
      case LEFT:  possible = isPossible((adjacent = coord.getLeft().getLeft())) &&
                     !visited.contains(adjacent);
                  break;
      case RIGHT: possible = isPossible((adjacent = coord.getRight().getRight())) &&
                     !visited.contains(adjacent);
                  break;
      case UP:    possible = isPossible((adjacent = coord.getAbove().getAbove())) &&
                     !visited.contains(adjacent);
                  break;
      case DOWN:  possible = isPossible((adjacent = coord.getBelow().getBelow())) &&
                     !visited.contains(adjacent);
                  break;
    }
    return possible;
  }

  /** 
   * Checks whether it can move or not from this Coordinate.
   * Calls isValid for each direction for code reuse
   *
   * @param coord Coordinate to be checked if it can move from there
   * @return true if it can, false if it cant
   */
  private boolean canMove(Coordinate coord)
  {
    // if any direction is a valid direction then return true
    Coordinate adjacent;
    if(isValid(Directions.LEFT, coord))
      return true;
    if(isValid(Directions.RIGHT, coord))
      return true;
    if(isValid(Directions.UP, coord))
      return true;
    if(isValid(Directions.DOWN, coord))
      return true;

    // if no directions come back as valid then return false
    return false;    
  }

  /**
   * Draws the maze to the screen.
   *
   * @param g2d Instance of Graphics2D
   */
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
