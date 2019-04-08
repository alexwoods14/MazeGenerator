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

  @Override
  public String toString()
  {
    return String.format("(%2d, %2d)", x, y); 
  }

  @Override
  public int hashCode()
  {
    return (int)(Math.pow(2.0,x) * Math.pow(3.0,y));
  }

} // Coordinate
