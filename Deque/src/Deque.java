import java.util.Iterator;
import java.util.NoSuchElementException;

/*
API:
   public Deque()                           // construct an empty deque
   public boolean isEmpty()                 // is the deque empty?
   public int size()                        // return the number of items on the deque
   public void addFirst(Item item)          // add the item to the front
   public void addLast(Item item)           // add the item to the end
   public Item removeFirst()                // remove and return the item from the front
   public Item removeLast()                 // remove and return the item from the end
   public Iterator<Item> iterator()         // return an iterator over items in order from front to end
   public static void main(String[] args)   // unit testing (optional)
*/
public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int num;

    public Deque() {
        first = null;
        last = null;
        num = 0;
    }

    private class Node {
        Item item;
        Node next;
        Node before;
    }

    public int size() {
        return num;
    }

    public boolean isEmpty() {
        // when is empty , first or last will be null.
        return first == null;
    }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        // add the first one
        if (isEmpty()) {
            Node cur = new Node();
            cur.item = item;
            first = cur;
            last = cur;
        } else {
            Node oldFirst = first;
            first = new Node();
            first.item = item;
            first.next = oldFirst;
            oldFirst.before = first;
        }
        num++;
    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        // add the first one
        if (isEmpty()) {
            Node cur = new Node();
            cur.item = item;
            first = cur;
            last = cur;
        } else {
            Node oldLast = last;
            last = new Node();
            last.item = item;
            last.before = oldLast;
            oldLast.next = last;
        }
        num++;
    }

    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = first.item;
        first = first.next;
        // make sure that first.before always is null
        if (first != null)
            first.before = null;
        else
            last = null;
        num--;
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = last.item;
        last = last.before;
        // make sure that last.next always is null
        if (last != null)
            last.next = null;
        else
            first = null;
        num--;
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Deque<Integer> test = new Deque<Integer>();
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) test.addFirst(i);
            else test.addLast(i);
        }
        System.out.println(test.isEmpty());
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) test.removeLast();
            else test.removeFirst();
        }
        System.out.println(test.isEmpty());
        for (int i = 0; i < 1000; i++) {
            if (i % 2 == 0) test.addLast(i);
            else test.addFirst(i);
        }
        System.out.println(test.size());
        for (int i = 0; i < 1000; i++) {
            test.removeLast();
        }
        System.out.println(test.isEmpty());
    }
}
