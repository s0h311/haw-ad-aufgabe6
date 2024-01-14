// package test

import application.RedBlackTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utility.Item;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RedBlackTreeTest {
  RedBlackTree<Item> tree = new RedBlackTree<>();

  List<Item> items = List.of(
      new Item(20),
      new Item(1),
      new Item(99),
      new Item(14),
      new Item(31),
      new Item(55),
      new Item(67),
      new Item(4)
  );

  @BeforeEach
  public void init() {
    this.tree = new RedBlackTree<>();
    this.tree.addAll(items);
    // this.items.forEach(tree::add);
  }

  @Test
  public void testClear() {
    RedBlackTree<Integer> integerTree = new RedBlackTree<>();

    integerTree.add(0);
    integerTree.add(1);

    integerTree.clear();

    assertFalse(integerTree.contains(0));
    assertFalse(integerTree.contains(1));
  }

  @Test
  public void testClearWithItem() {
    this.tree.clear();

    this.items.forEach((item) -> assertFalse(this.tree.contains(item)));
  }

  @Test
  public void testContains() {
    RedBlackTree<Integer> integerTree = new RedBlackTree<>();

    integerTree.add(0);
    integerTree.add(1);

    assertTrue(integerTree.contains(0));
    assertTrue(integerTree.contains(1));
  }

  @Test
  public void testContainsWithItem() {
    this.items.forEach((item) -> {
      assertTrue(this.tree.contains(new Item(item.sortKey())));
    });
  }

  @Test
  public void testAdd() {
    this.tree.add(new Item(888));
    this.tree.add(new Item(999));

    assertTrue(this.tree.contains(new Item(999)));
    assertTrue(this.tree.contains(new Item(888)));
  }

  @Test
  public void testAddDuplicate() {
    assertThrows(IllegalArgumentException.class, () -> this.tree.add(items.get(0)));
    assertThrows(IllegalArgumentException.class, () -> this.tree.add(items.get(1)));
    assertThrows(IllegalArgumentException.class, () -> this.tree.add(items.get(2)));
  }

  @Test
  public void testAddNegativeSortKey() {
    this.tree.add(new Item(-1));
    this.tree.add(new Item(-999));

    assertTrue(this.tree.contains(new Item(-1)));
    assertTrue(this.tree.contains(new Item(-999)));

  }

  @Test
  public void testAddZero() {
    this.tree.add(new Item(0));
    assertTrue(this.tree.contains(new Item(0)));
  }

  @Test
  public void testAddMinAndMax() {
    this.tree.add(new Item(Integer.MIN_VALUE));
    this.tree.add(new Item(Integer.MAX_VALUE));

    assertTrue(this.tree.contains(new Item(Integer.MIN_VALUE)));
    assertTrue(this.tree.contains(new Item(Integer.MAX_VALUE)));
  }

  @Test
  public void testAddNull() {
    assertThrows(NullPointerException.class, () -> this.tree.add(null));
  }

  @Test
  public void testAddAll() {
    this.tree.clear();
    
    List<Item> items = List.of(
        new Item(-31),
        new Item(-14),
        new Item(-99),
        new Item(-1),
        new Item(-4),
        new Item(-20),
        new Item(-67),
        new Item(-55),
        new Item(55),
        new Item(6),
        new Item(29)
    );

    this.tree.addAll(items);
    items.forEach((item) -> assertTrue(this.tree.contains(item)));
  }

  @Test
  public void testToArray() {
    Item[] expected = new Item[]{
        new Item(1),
        new Item(4),
        new Item(14),
        new Item(20),
        new Item(31),
        new Item(55),
        new Item(67),
        new Item(99),
    };

    Item[] actual = this.tree.toArray(new Item[expected.length]);

    for (int i = 0; i < actual.length; i++) {
      assertEquals(expected[i], actual[i]);
    }
  }

  @Test
  public void testToArrayWithNegatives() {
    RedBlackTree<Item> tree = new RedBlackTree<>();

    List<Item> negativeItems = List.of(
        new Item(-31),
        new Item(-14),
        new Item(-99),
        new Item(-1),
        new Item(-4),
        new Item(-20),
        new Item(-67),
        new Item(-55),
        new Item(55),
        new Item(6),
        new Item(29)
    );

    Item[] expected = new Item[]{
        new Item(-99),
        new Item(-67),
        new Item(-55),
        new Item(-31),
        new Item(-20),
        new Item(-14),
        new Item(-4),
        new Item(-1),
        new Item(6),
        new Item(29),
        new Item(55),
    };

    tree.addAll(negativeItems);

    Item[] actual = tree.toArray(new Item[expected.length]);

    for (int i = 0; i < actual.length; i++) {
      assertEquals(expected[i], actual[i]);
    }
  }

  @Test
  public void testToArrayOnlyRoot() {
    Item[] expected = new Item[]{
        new Item(99),
    };

    RedBlackTree<Item> tree = new RedBlackTree<>();
    tree.addAll(expected);

    Item[] actual = tree.toArray(new Item[1]);

    for (int i = 0; i < actual.length; i++) {
      assertEquals(expected[i], actual[i]);
    }
  }
}
