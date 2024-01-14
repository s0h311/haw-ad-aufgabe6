package application;

public class Node<T> {
  public Color color;
  public T value;
  public Node<T> parent;
  public Node<T> left;
  public Node<T> right;

  public Node(final Color color, final T value) {
    if (color == null || value == null) {
      throw new IllegalArgumentException("color and value cannot be null");
    }

    this.color = color;
    this.value = value;
    this.parent = null;
    this.left = null;
    this.right = null;
  }

  public Node(final T value) {
    this(Color.RED, value);
  }
}
