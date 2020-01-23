/* *****************************************************************************
 *  Name: Faustine Ginoux
 *  Date: 22-01-2020
 *  Description: Coursera - Algo part I - Queues assignment - RandomizedQueue class
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] rq; // resizing array
    private int n;

    private class RandomIterator implements Iterator<Item> {
        private Item[] queue;
        private int itemsLeft;

        public RandomIterator() {
            queue = (Item[]) new Object[n];
            for (int i = 0; i < n; i++) {
                queue[i] = rq[i];
            }
            StdRandom.shuffle(queue);
            itemsLeft = n;
        }

        public boolean hasNext() {
            return (itemsLeft != 0);
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported!");
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("The queue is empty");
            }
            return queue[--itemsLeft];
        }
    }


    // construct an empty randomized queue
    public RandomizedQueue() {
        rq = (Item[]) new Object[1];
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return rq[0] == null;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("The item cannot be null!");
        }
        if (n == rq.length) {
            resize(rq.length * 2);
        }
        rq[n++] = item;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = rq[i];
        }
        rq = copy;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("The queue is empty!");
        }
        int r = StdRandom.uniform(n);
        Item dequeued = rq[r];
        //for (int i = r; i < n; i++) {
        //    rq[i] = rq[i + 1];
        //}
        rq[r] = rq[--n];
        rq[n] = null;
        if (n > 0 && n == rq.length / 4) {
            resize(rq.length / 2);
        }
        return dequeued;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("The queue is empty!");
        }
        return rq[StdRandom.uniform(n)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        rq.enqueue(3);
        rq.enqueue(4);
        rq.enqueue(2);
        rq.enqueue(5);
        rq.enqueue(1);
        // 2 3 4 5 1 in rq
        StdOut.println(rq.sample()); // still size 5
        StdOut.println(rq.sample()); // still size 5
        StdOut.println(rq.sample()); // still size 5
        StdOut.println(rq.sample()); // still size 5
        StdOut.println(rq.sample()); // still size 5
        StdOut.println(rq.sample()); // still size 5
        StdOut.println(rq.sample()); // still size 5
        StdOut.println(rq.sample()); // still size 5
        StdOut.println("sample finished"); // still size 5

        Iterator<Integer> itr = rq.iterator();
        StdOut.println(itr.next());
        StdOut.println(itr.next());
        StdOut.println(itr.next());
        StdOut.println(itr.next());
        StdOut.println(itr.next());
        StdOut.println("1st iterator done"); // still size 5

        Iterator<Integer> itr2 = rq.iterator();
        StdOut.println(itr2.next());
        StdOut.println(itr2.next());
        StdOut.println(itr2.next());
        StdOut.println(itr2.next());
        StdOut.println(itr2.next());
        StdOut.println("2nd iterator done"); // still size 5

        StdOut.println(rq.dequeue()); // size 4
        StdOut.println("dequeued 1 item");

        Iterator<Integer> itr3 = rq.iterator();
        StdOut.println(itr3.next());
        StdOut.println(itr3.next());
        StdOut.println(itr3.next());
        StdOut.println(itr3.next());
        StdOut.println("3rd iterator done"); // still size 5
    }
}

