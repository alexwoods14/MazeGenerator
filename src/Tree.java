import java.util.List;
import java.util.ArrayList;

/** 
 * Generic Tree Class. Has a Parent, a value and a list of children
 * allows linked trees
 *
 * @author Alex Woods
 */
public class Tree<T>
{
  private Tree<T> parent;
  private T value;
  private List<Tree<T>> children;

  /** 
   * Creates a new Tree with a specified node value and a parent
   *
   * @param value The value of this trees root node
   * @param parent A reference to the parent tree where this tree is a child of
   */
  public Tree(T value, Tree<T> parent)
  {
    this.value = value;
    this.parent = parent;
    // initialize the empty arraylist of children
    children = new ArrayList<Tree<T>>();
  }

  /**
   * Adds a child to the array list of children.
   * Creates a new child with a specified value and sets the childs parent to
   * be this object
   *
   * @param childVal The value of the childs node
   * @return The new child tree
   */
  public Tree<T> addChild(T childVal)
  {
    Tree<T> child = new Tree<T>(childVal, this);
    this.children.add(child);
    return child;
  }

  /**
   * Gets the parent tree
   *
   * @return parent tree
   */
  public Tree<T> getParent()
  {
    return this.parent;
  }

  /**
   * Gets the value of this trees 'root' node
   *
   * @return node value
   */
  public T getValue()
  {
    return value;
  }
} // Tree
