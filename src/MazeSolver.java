/**
 * A solver for the maze generation program
 *
 * @author Alex Woods
 */
public class MazeSolver
{
  private Tree<Coordinate> fullTree;
  private Tree<Coordinate> currentTree;
  private Coordinate goal;
  private boolean solved;
  private MazeCell[][] maze;

  /**
   * Initialises variables ready to solve
   *
   * @param fullTree Full tree of the maze route
   * @param finalX Horizontal end of the maze
   * @param finalY Vertical end of the maze
   * @param maze Grid of mazeCells to change their state
   */
  public MazeSolver(Tree<Coordinate> fullTree, int finalX, int finalY,
                    MazeCell[][] maze)
  {
    // setting instance variables
    this.fullTree = fullTree; 
    currentTree = this.fullTree.getNextChild();
    this.maze = maze;

    // final point must be an even number, cant end (odd,odd)
    int x = finalX;
    int y = finalY;
    if(finalX % 2 != 0)
      x --;
    if(finalY % 2 != 0)
      y --;
    // set the end point as an instance variable
    goal = new Coordinate(x, y);

    solved = false;
    // make start point on the route
    setOnRoute(currentTree.getValue(), true);
  }

  /**
   * Step through one progression.
   * Either moves forwards or backwards and updates variables accordingly
   */
  public void step()
  {
    // if it is at the end, set solved to true so this method is not called
    // again
    if(currentTree.getValue().equals(goal))
      solved = true;
    else
    {
      // if not solved, get the next tree to traverse
      Tree<Coordinate> next = currentTree.getNextChild();
      if(next != null) // not at end of branch
      {
        // update current tree. Set is as on the route
        currentTree = next;
        setOnRoute(currentTree.getValue(), true);
      }
      else
      {
        // if it is a dead end branch, Set is as not on the route and traverse
        // back up one node
        setOnRoute(currentTree.getValue(), false);
        currentTree = currentTree.getParent();
      }
    }
  }

  /**
   * Set a coordinate as being visited and onRoute according to the arguments
   *
   * @param coord the relevant Coordinate
   * @param onRoute whether it is on the route or not
   */
  private void setOnRoute(Coordinate coord, boolean onRoute)
  {
    // it has always been visited when this is called so it is always set true
    maze[coord.getX()][coord.getY()].setVisited();
    maze[coord.getX()][coord.getY()].setOnRoute(onRoute);
  }

  /**
   * Does what it says on the tin
   *
   * @return Whether it has solved the maze or not
   */
  public boolean isSolved()
  {
    return solved;
  }
   
} // MazeSolver
