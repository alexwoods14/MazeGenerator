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

  public MazeCell(boolean wall)
  {   
    this.wall = wall;
  }

  public boolean isWall()
  {
    return wall;
  }
  
  public void setIsWall(boolean wall) throws Exception
  {
    this.wall = wall;
  }

  public void finalPath()
  {
    System.out.println("here");
    finalPath = true;
  }

  public boolean isFinalPath()
  {
    return finalPath;
  }
} // MazeCell
