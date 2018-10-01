
import java.util.*;

/**
 * An example of bad programming
 *
 */
public class WrongQueue {

    class Node {

        String data;
        Node next;
    }

    private Node head;
    private Node current;
    private Node last;

    public void enqueue(String str) {
        Node nnode = new Node();
        nnode.data = str;
        if (head == null) {
            current = last = head = nnode;
        } else {
            last.next = nnode;
            last = nnode;
            if (current == null) {
                current = nnode;
            }
        }
    }

    public String dequeue() {
        String result = null;
        if (current != null) {

            // current keeps linked to the previous node and it never gets garbage-collected.
            // So we explicitly remove the useless link to permit GC
            Node c = head;
            while (c.next != null && c.next.next != null) {
                c = c.next;
            }
            c.next = null; // explicitly remove link with prev node

            result = current.data;
            current = current.next;
        }

        return result;

    }

    public static void main(String[] args) {
        Random rnd = new Random();
        WrongQueue queue = new WrongQueue();
        final int SIZE = 1024 * 1024;

        char[] chars = new char[SIZE];
        Arrays.fill(chars, 'f');
        String charStr = new String(chars);

        for (int i = 0; i < 5000000; ++i) {
            if (i % 2 == 0) {
                // Move string initialization outside the cycle
                queue.enqueue("String n. " + rnd.nextInt(101) + charStr);
            } else {
                System.out.printf("Dequeue \"%s\"\n", queue.dequeue().substring(0, 15));
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }
        }
    }
}
