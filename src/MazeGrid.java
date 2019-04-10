import java.util.Random;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

import java.util.Set;
import java.util.HashSet;

/**
 * Class which makes the maze. Generates a random breadth first maze
 *
 * @author Alex Woods
 */
public class MazeGrid
{
  private MazeCell[][] map;
  private final int width;
  private final int height;
  private Set<Coordinate> visited;
  private Tree<Coordinate> fullRoute;

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


    // generate maze
    createMaze();
  }

  /**
   * Gets the full maze tree traversal
   *
   * @return full tree
   */
  public Tree<Coordinate> getFullRoute()
  {
    return fullRoute;
  }

  /**
   * Gets the array of cells
   *
   * @return
   */
  public MazeCell[][] getCells()
  {
    return map;
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
   * Generates the random maze.
   *
   */
  public void createMaze()
  {
    // boxxedIn being true means it cannot currently move so has to traverse
    // backwards until it can
    boolean boxxedIn = false;

    // every other move is a new direction so as to keep 1 wall gap between the
    // path. When true a new direction is set then it moves, else it just moves
    // in the current direction
    boolean newMove = false;

    // this is the starting point of the maze
    Coordinate start = new Coordinate(0,0);
    // adds it as a visited location, sets it as a wall.
    visit(start);
    setIsWall(start, false);

    // Initializes the Route Tree with start as the value and null as its
    // parents as it is the root node
    fullRoute = new Tree<Coordinate>(start, null);
    
    // Instance of direction that the current move is
    Directions direction;

    // get first direction and make an arbiturary Coordinate to be overwritten
    // in the loop that is the next location from the start in that direction
    direction = nextLocation(start);
    Coordinate next = move(start, direction);

    // create a temporay Tree object for the same purpose. Overwritten
    // every time a tree is added as a child.
    Tree<Coordinate> currentRoute = fullRoute.addChild(next);

    // adds these locations as walls and visits them
    setIsWall(next, false);
    visit(next);

    // loop executes until the break statement where it exits if it is at the
    // start node. This can only happen if it has traversed all the way back to
    // the start
    while(true)
    {
      if(!boxxedIn) // if it is not boxxed in, i.e. there is a direction that 
      {             // it can move in
        if(newMove)
        // if its a new move, select a direction and move once in that
        // direction
        {
          direction = nextLocation(next);
          // nextLocation returns null if it cant move
          if(direction == null)
            boxxedIn = true;
          else // move if not boxxed in
            next = move(next, direction);
          // Same direction for next move
          newMove = false;
        }
        else
        // if it is not a new move: Move once more in the current direction
        // then specify that next move is a new direction
        {
          next = move(next, direction);
          newMove = true;
        }
        // regardless of if it was a new move or not or if it moved at all:
        // Visit the current location and set it as a wall. Add it to the tree
        // if it is not already in it
        setIsWall(next, false);
        visit(next);
        if(!currentRoute.getValue().equals(next))
          currentRoute = currentRoute.addChild(next);
      }
      else
      // if it is boxxed in and it cannot progress forwards
      {
        // go back until it can
        currentRoute = backtrack(currentRoute);

        // current Coordinate (next) is the root node of currentRoute
        next = currentRoute.getValue();

        // if this is (0,0) and it has traversed all the way out. let the user
        // know generation is done. Then exit the loop
        if(next.equals(start))
        {
          currentRoute.initIterator();
          System.out.println("Generated");
          break;
        }

        // After backtracking, they are no longer boxed in and it is a new move
        // again as there is a possible direction;
        boxxedIn = false;
        newMove = true;
      }
    }
  }

  /**
   * This returns the next Coordinate in the direction specified.
   * It also visits all cells adjacent to the one it is moving from to ensure
   * that there is always a single wall around all paths
   *
   * @param current The Coordinate to move from
   * @param direction The desired direction of travel
   * @return The resulting Coordinate
   */
  private Coordinate move(Coordinate current, Directions direction)
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
      return null;
    }

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
   * Traverses back up the tree until it is in a posistion where it can move.
   * recursive method calls itself until its parameter is in a valid place to
   * move from
   *
   * @param start the tree where it is stuck and should return to
   * @return the tree starting from where it can move
   */
  private Tree<Coordinate> backtrack(Tree<Coordinate> start)
  {
    // recursively calls itself with the parent of its parameter until the
    // parameter is in a position where it can move
    if(start.getParent() != null && !canMove(start.getValue()))
    {
      start.initIterator();
      return backtrack(start.getParent());
    }
    
    // when it has traversed up enough so that it can move or until its parent
    // is null (i.e. at root node). return the new tree
    return start;
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
          if(map[i][j].isVisited())
            if(map[i][j].isOnRoute())
              g2d.setColor(Color.ORANGE);
            else
              g2d.setColor(Color.DARK_GRAY);
          else
            g2d.setColor(Color.WHITE);


        if(i == 0 && j == 0)
          g2d.setColor(Color.GREEN);
        else if(i == width - 1 && j == height - 1)
          g2d.setColor(Color.RED);
        
        g2d.fillRect(i*10, (height - j - 1)*10, 10, 10);
      }

  }
} // MazeGrid
