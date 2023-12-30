package application;

import java.util.Objects;

public class RedBlackTree<T> {
  public Node<T> root;

  public void add(Node<T> node) {
    if (root == null) {
      root = node;
      node.color = Color.BLACK;
    }


    // TODO implement
  }

  public <T> T[] toArray(T[] array) {
    // TODO implement
    return null;
  }

  public void clear() {
    // TODO implement
  }
}
