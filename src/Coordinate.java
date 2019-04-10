/** 
 * A simple model of a 2D coordinate.
 * Has an x and y location and various methods to get locations around it as
 * well as accessor methods for the x and y. They are final however
 *
 * @author Alex Woods
 */
public class Coordinate
{
  private final int x;
  private final int y;

  /**
   * Creates a Coordinate object and initialises the instance variables
   *
   * @param x the desired horizontal, x, value
   * @param y the desired vertical, y, value
   */
  public Coordinate(int x, int y)
  {
    this.x = x;
    this.y = y;
  }

  /** 
   * returns a new instance of Coordinate that is to the left of it
   *
   * @return a new Coordinate
   */
  public Coordinate getLeft()
  {
    return new Coordinate(x - 1, y);
  }
  
  /** 
   * returns a new instance of Coordinate that is to the right of it
   *
   * @return a new Coordinate
   */
  public Coordinate getRight()
  {
    return new Coordinate(x + 1, y);
  }
  
  /** 
   * returns a new instance of Coordinate that is above it
   *
   * @return a new Coordinate
   */
  public Coordinate getAbove()
  {
    return new Coordinate(x, y + 1);
  }
  
  /** 
   * returns a new instance of Coordinate that is below it
   *
   * @return a new Coordinate
   */
  public Coordinate getBelow()
  {
    return new Coordinate(x, y - 1);
  }

  /** 
   * Gets the x value of this instance
   *
   * @return x value
   */
  public int getX()
  {
    return x;
  }

  /** 
   * Gets the y value of this instance
   *
   * @return y value
   */
  public int getY()
  {
    return y;
  }

  /** 
   * Compares it to another object. Returns true if they are equal
   *
   * @param o The object to be compared with
   * @return true if equal, else false
   */
  @Override
  public boolean equals(Object o)
  {
    if(o == this)
      return true;

    if(!(o instanceof Coordinate))
      return false;

    Coordinate c = (Coordinate)o;
    return (c.getX() == x && c.getY() == y);
  }

  /**
   * Override of Object method. Makes toString understandable
   *
   * @return Understandable string describing the object
   */
  @Override
  public String toString()
  {
    return String.format("(%2d, %2d)", x, y); 
  }

  /**
   * Override of Object method. Unique Hashcode for different coordinates.
   * Objects with the same x and y will have the same hash code.
   *
   * @return hash of the coordinate
   */
  @Override
  public int hashCode()
  {
    return (int)(Math.pow(2.0,x) * Math.pow(3.0,y));
  }

} // Coordinate
