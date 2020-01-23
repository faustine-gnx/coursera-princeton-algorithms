/* *****************************************************************************
 *  Name: Faustine Ginoux
 *  Date: 22-01-2020
 *  Description: Coursera - Algo part I - Queues assignment - Permutation class
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        String str = StdIn.readString();
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        rq.enqueue(str);

        while (!StdIn.isEmpty()) {
            str = StdIn.readString();
            rq.enqueue(str);
        }

        for (int i = 0; i < k; i++) {
            System.out.println(rq.dequeue());
        }
    }
}
