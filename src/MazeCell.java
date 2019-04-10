/** 
 * Cell Object representing one square in the grid.
 * One instance variable that says if it is a wall or not
 *
 * @author Alex Woods
 */
public class MazeCell
{
  private boolean wall;
  private boolean finalPath;

  /** 
   * Creates a new cell.
   *
   * @param wall boolean value, true if a wall. False if not
   */
  public MazeCell(boolean wall)
  {   
    this.wall = wall;
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
   * Sets the cell to be a final path when it has been traversed back past
   */
  public void finalPath()
  {
    finalPath = true;
  }

  /**
   * Gets whether it is a final path wall or not
   *
   * @return true if it is final, false if not
   */
  public boolean isFinalPath()
  {
    return finalPath;
  }
} // MazeCell
