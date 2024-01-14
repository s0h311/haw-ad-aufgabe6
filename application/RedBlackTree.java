package application;

import jakarta.annotation.Nonnull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Stack;

public class RedBlackTree<T extends Comparable<T>> {
  private transient Node<T> root;
  private transient int size;

  public RedBlackTree() {
    this.size = 0;
  }

  public void add(@Nonnull final T value) {
    Objects.requireNonNull(value, "value cannot be null");

    Node<T> node = root;
    Node<T> parent = null;

    // Traverse the tree to the left or right depending on the key
    while (node != null) {
      parent = node;

      if (value.compareTo(node.value) < 0) {
        node = node.left;
      }
      else if (value.compareTo(node.value) > 0) {
        node = node.right;
      }
      else {
        throw new IllegalArgumentException("duplicate values are not allowed");
      }
    }

    // Insert new node
    final Node<T> newNode = new Node<>(value);

    if (parent == null) {
      root = newNode;
    }
    else if (value.compareTo(parent.value) < 0) {
      parent.left = newNode;
    }
    else {
      parent.right = newNode;
    }
    newNode.parent = parent;

    this.size++;
    fixRedBlackPropertiesAfterAdd(newNode);
  }

  public void addAll(@Nonnull Collection<T> collection) {
    Objects.requireNonNull(collection);
    collection.forEach(this::add);
  }

  public void addAll(@Nonnull T[] array) {
    Objects.requireNonNull(array);
    Arrays.stream(array).forEach(this::add);
  }

  public boolean contains(@Nonnull final T value) {
    Objects.requireNonNull(value);

    Node<T> node = root;

    while (node != null) {
      if (value.compareTo(node.value) == 0) {
        return true;
      }
      else if (value.compareTo(node.value) < 0) {
        node = node.left;
      }
      else {
        node = node.right;
      }
    }

    return false;
  }

  @SuppressWarnings("unchecked")
  public <Z> Z[] toArray(@Nonnull Z[] array) {
    Objects.requireNonNull(array);

    if (array.length < this.size) {
      array = (Z[]) java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), size);
    }

    if (root == null) {
      return array;
    }

    Stack<Node<Z>> stack = new Stack<>();
    Node<Z> currentNode = (Node<Z>) root;

    int index = 0;

    while (currentNode != null || !stack.isEmpty()) {
      while (currentNode != null) {
        stack.push(currentNode);
        currentNode = currentNode.left;
      }

      currentNode = stack.pop();
      array[index] = currentNode.value;
      currentNode = currentNode.right;
      index++;
    }
    return array;
  }

  public void clear() {
    this.root = null;
  }

  // HELPERS //

  private void fixRedBlackPropertiesAfterAdd(@Nonnull final Node<T> node) {
    Objects.requireNonNull(node);

    Node<T> parent = node.parent;

    // Case 1: Parent is null, that means node is root. Break out of the recursion.
    if (parent == null) {
      // root must be black
      node.color = Color.BLACK;
      return;
    }

    // If parent is black, nothing to do
    if (parent.color == Color.BLACK) {
      return;
    }

    // From here on, parent is red
    final Node<T> grandparent = parent.parent;


    // Get the uncle (maybe null, in which case its black)
    final Node<T> uncle = getUncle(parent);

    // Case 3: If uncle is red recolor parent, grandparent and uncle
    if (uncle != null && uncle.color == Color.RED) {
      parent.color = Color.BLACK;
      grandparent.color = Color.RED;
      uncle.color = Color.BLACK;

      // Call recursively for grandparent, which is now red.
      fixRedBlackPropertiesAfterAdd(grandparent);
    }

    // Parent is left child of grandparent
    else if (parent == grandparent.left) {
      // Case 4a: Uncle is black and node is left->right "inner child" of its grandparent
      if (node == parent.right) {
        rotateLeft(parent);

        // Let "parent" point to the new root node of the rotated subtree.
        // It will be recolored in the next step, which we're going to fall-through to.
        parent = node;
      }

      // Case 5a: Uncle is black and node is left->left "outer child" of its grandparent
      rotateRight(grandparent);

      // Recolor original parent and grandparent
      parent.color = Color.BLACK;
      grandparent.color = Color.RED;
    }

    // Parent is right child of grandparent
    else {
      // Case 4b: Uncle is black and node is right->left "inner child" of its grandparent
      if (node == parent.left) {
        rotateRight(parent);

        // Let "parent" point to the new root node of the rotated subtree.
        // It will be recolored in the next step, which we're going to fall-through to.
        parent = node;
      }

      // Case 5b: Uncle is black and node is right->right "outer child" of its grandparent
      rotateLeft(grandparent);

      // Recolor original parent and grandparent
      parent.color = Color.BLACK;
      grandparent.color = Color.RED;
    }
  }

  private Node<T> getUncle(@Nonnull final Node<T> parent) {
    Objects.requireNonNull(parent);

    final Node<T> grandparent = parent.parent;

    if (grandparent.left == parent) {
      return grandparent.right;
    }
    else if (grandparent.right == parent) {
      return grandparent.left;
    }
    else {
      throw new IllegalStateException("Parent is not a child of its grandparent");
    }
  }

  private void rotateLeft(@Nonnull final Node<T> node) {
    Objects.requireNonNull(node);

    final Node<T> parent = node.parent;
    final Node<T> right = node.right;

    node.right = right.left;
    if (right.left != null) {
      right.left.parent = node;
    }

    right.left = node;
    node.parent = right;

    replaceParentsChild(parent, node, right);
  }

  private void rotateRight(@Nonnull final Node<T> node) {
    Objects.requireNonNull(node);

    final Node<T> parent = node.parent;
    final Node<T> left = node.left;

    node.left = left.right;
    if (left.right != null) {
      left.right.parent = node;
    }

    left.right = node;
    node.parent = left;

    replaceParentsChild(parent, node, left);
  }

  private void replaceParentsChild(final Node<T> parent, final Node<T> oldChild, final Node<T> newChild) {
    if (parent == null) {
      root = newChild;
    }
    else if (parent.left == oldChild) {
      parent.left = newChild;
    }
    else if (parent.right == oldChild) {
      parent.right = newChild;
    }
    else {
      throw new IllegalStateException("Node is not a child of its parent");
    }

    if (newChild != null) {
      newChild.parent = parent;
    }
  }
}
