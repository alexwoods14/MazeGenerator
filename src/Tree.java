import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;

import java.util.Collections;

/** 
 * Generic Tree Class. Has a Parent, a value and a list of children
 * allows linked trees
 *
 * @author Alex Woods
 */
public class Tree<T> implements Comparable<Tree<T>>
{
  private Tree<T> parent;
  private T value;
  private List<Tree<T>> children;
  private ListIterator<Tree<T>> childrenIterator;
  private int size;

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
   * Recusively calculates the size of all subtrees
   */
  public void initIterator()
  {
    // sort it in size order then make an iterator
    this.size();
    Collections.sort(children);
    childrenIterator = children.listIterator();
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

  public Tree<T> getNextChild()
  {
    if(childrenIterator.hasNext())
      return childrenIterator.next();
    else
      return null;
  }


  /**
   * Compares it to another Tree<T>
   * comparison is based on the size of tree
   *
   * @param other Tree to compare to
   * @return 0 if equal, < 0 if smaller, > 0 if bigger
   */
  @Override
  public int compareTo(Tree<T> other)
  {
    if(size == other.getSize())
      return 0;
    else
      return (size - other.getSize());
  }

  /**
   * Recursively calculates the size of the tree and all subtrees.
   */
  public void size()
  {
    size = 1;
    for(Tree<T> child: children)
      size += child.getSize();
  }

  /**
   * accessor method for the size of this tree.
   *
   * @return number of nodes in the tree
   */
  public int getSize()
  {
    return size;
  }
} // Tree
