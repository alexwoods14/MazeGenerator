import java.util.List;
import java.util.ArrayList;

public class Tree<T>
{
  private Tree<T> parent;
  private T value;
  private List<Tree<T>> children;

  public Tree(T value, Tree<T> parent)
  {
    this.value = value;
    this.parent = parent;
    children = new ArrayList<Tree<T>>();
  }

  public Tree<T> addChild(T childVal)
  {
    Tree<T> child = new Tree<T>(childVal, this);
    this.children.add(child);
    return child;
  }

  public Tree<T> getParent()
  {
    return this.parent;
  }

  public T getValue()
  {
    return value;
  }
} // Tree
