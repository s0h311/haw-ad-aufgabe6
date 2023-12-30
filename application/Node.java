package application;

public class Node<T> {
  public Color color;
  public T value;
  public Node<T> parent;
  public Node<T> left;
  public Node<T> right;

  public Node(Color color, T value) {
    this.color = color;
    this.value = value;
  }
}
