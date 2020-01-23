/* *****************************************************************************
 *  Name: Faustine Ginoux
 *  Date: 20-01-2020
 *  Description: Coursera - Algo part I - Queues assignment - Deque class
 **************************************************************************** */
// to run: java-algs4 Deque

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported!");
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("The end is reached!");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("The item cannot be null!");
        }
        Node oldfirst = first; // can be null?
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        if (size == 0) {
            last = first;
        }
        else {
            oldfirst.prev = first;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("The item cannot be null!");
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.prev = oldLast;
        if (size == 0) {
            first = last;
        }
        else {
            oldLast.next = last;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("The deque is empty!");
        }
        Item firstItem = first.item;
        if (size == 1) {
            first = null;
            last = null;
        }
        else {
            first = first.next;
            first.prev = null;
        }
        size--;
        return firstItem;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("The deque is empty!");
        }
        Item lastItem = last.item;
        if (size == 1) {
            first = null;
            last = null;
        }
        else {
            last = last.prev;
            last.next = null;
        }
        size--;
        return lastItem;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }


    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deq = new Deque<Integer>();
        deq.addFirst(3);
        deq.addLast(4);
        deq.addFirst(2);
        deq.addLast(5);
        deq.addLast(1);
        // 2 3 4 5 1 in deq
        StdOut.println(deq.removeLast()); // 1
        StdOut.println(deq.removeFirst()); // 2
        // 3 4 5 in deq
        Iterator<Integer> itr = deq.iterator();

        StdOut.println(itr.next());
        StdOut.println(itr.next());
        StdOut.println(itr.next());
        StdOut.println("finitooo");
        //StdOut.println(itr.next()); // error
    }
}
