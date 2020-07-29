import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/*
API:
  public RandomizedQueue()                 // construct an empty randomized queue
  public boolean isEmpty()                 // is the randomized queue empty?
  public int size()                        // return the number of items on the randomized queue
  public void enqueue(Item item)           // add the item
  public Item dequeue()                    // remove and return a random item
  public Item sample()                     // return a random item (but do not remove it)
  public Iterator<Item> iterator()         // return an independent iterator over items in random order
  public static void main(String[] args)   // unit testing (optional)
*/
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int last;

    public RandomizedQueue() {
        items = (Item[]) new Object[1];
        last = 0;
    }

    private void resize(int newSize) {
        Item[] newItems = (Item[]) new Object[newSize];
        for (int i = 0; i < size(); i++) {
            newItems[i] = items[i];
        }
        items = newItems;
    }

    public int size() {
        return last;
    }

    public boolean isEmpty() {
        return last == 0;
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        // double the size of items if necessary
        if (size() == items.length) resize(items.length * 2);
        items[last++] = item;
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        return items[StdRandom.uniform(last)];
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int ind = StdRandom.uniform(last);
        Item item = items[ind];
        items[ind] = items[last - 1];
        items[last - 1] = null; // to avoid loitering
        last--;
        // shrink the size of items if necessary
        if (size() == items.length / 4 && size() > 0) resize(items.length / 2);
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private final Item[] objects;
        private int cur;

        public ListIterator() {
            cur = last;
            objects = (Item[]) new Object[cur];
            //StdRandom.shuffle(obeject s);
        }

        @Override
        public boolean hasNext() {
            return cur != 0;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Item next() {
            if (cur == 0) throw new NoSuchElementException();
            int ind = StdRandom.uniform(cur);
            Item obj = objects[ind];
            objects[ind] = objects[--cur];
            //objects[cur] = obj;
            return obj;
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> test = new RandomizedQueue<Integer>();
        for (int i = 0; i < 100; i++) {
            test.enqueue(i);
        }
        System.out.println(test.size());
        Integer[] num = new Integer[100];
        for (int i = 0; i < 100; i++) {
            Integer n = test.dequeue();
            for (int j = 0; j < i; j++) {
                if (Objects.equals(num[j], n)) System.out.println("wrong");
            }
            num[i] = n;
        }
        System.out.println(test.size());
        for (int i = 0; i < 1000; i++) {
            test.enqueue(StdRandom.uniform(1000));
        }
        for (int i = 0; i < 1000; i++) {
            System.out.println(test.dequeue());
        }
        System.out.println(test.size());
    }
}
