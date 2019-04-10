/** 
 * Cell Object representing one square in the grid.
 * One instance variable that says if it is a wall or not
 *
 * @author Alex Woods
 */
public class MazeCell
{
  private boolean wall;
  private boolean onRoute;
  private boolean visited;

  /** 
   * Creates a new cell.
   *
   * @param wall boolean value, true if a wall. False if not
   */
  public MazeCell(boolean wall)
  {   
    this.wall = wall;
    onRoute = false;
    visited = false;
  }

  /**
   * Returns whether the cell is a wall or not
   *
   * @return true if it is a wall. else false
   */
  public boolean isWall()
  {
    return wall;
  }

  /** 
   * Sets whether it is a wall or not.
   *
   * @param wall true if should be set to wall. else false
   */
  public void setIsWall(boolean wall)
  {
    this.wall = wall;
  }

  /**
   * Sets if it on the current solution route
   *
   * @param onRoute is it on route
   */
  public void setOnRoute(boolean onRoute)
  {
    this.onRoute = onRoute;
  }

  /**
   * Call if this cell has been explored as a solution
   */
  public void setVisited()
  {
    this.visited = true;
  }

  /**
   * Accesor method for onRoute
   *
   * @return is it on the current solution route
   */
  public boolean isOnRoute()
  {
    return onRoute;
  }
  
  /**
   * Accessor method for visited
   *
   * @return If this cell has been visited/passed through on solving
   */
  public boolean isVisited()
  {
    return visited;
  }
} // MazeCell
