package application;

import java.util.stream.IntStream;

public class Playground {
  public static void main(String[] args) {
    RedBlackTree<Integer> rbTree = new RedBlackTree<>();

    IntStream.range(1, 100)
        .forEach(rbTree::add);

    IntStream.range(1, 100)
        .forEach(rbTree::contains);
  }
}
