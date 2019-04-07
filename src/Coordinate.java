public class Coordinate
{
  private final int x;
  private final int y;

  public Coordinate(int x, int y)
  {
    this.x = x;
    this.y = y;
  }

  public Coordinate getLeft()
  {
    return new Coordinate(x - 1, y);
  }
  
  public Coordinate getRight()
  {
    return new Coordinate(x + 1, y);
  }
  
  public Coordinate getAbove()
  {
    return new Coordinate(x, y + 1);
  }
  
  public Coordinate getBelow()
  {
    return new Coordinate(x, y - 1);
  }

  public int getX()
  {
    return x;
  }

  public int getY()
  {
    return y;
  }

  public boolean equals(Coordinate other)
  {
    return (other.getX() == x && other.getY() == y);
  }

  public String toString()
  {
    return String.format("(%2d, %2d)", x, y); 
  }

} // Coordinate
