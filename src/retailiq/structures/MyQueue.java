package retailiq.structures;

/**
 * Custom Generic FIFO Queue implemented without java.util.*
 * Implemented using a singly linked node structure for O(1) enqueue and dequeue.
 *
 * @param <T> Element type
 */
public class MyQueue<T> {

    private static class Node<E> {
        E data;
        Node<E> next;

        Node(E data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node<T> front;
    private Node<T> rear;
    private int size;

    public MyQueue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    public void enqueue(T item) {
        Node<T> newNode = new Node<>(item);
        if (isEmpty()) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    public T dequeue() {
        if (isEmpty()) {
            return null;
        }
        T item = front.data;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        size--;
        return item;
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return front.data;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void clear() {
        front = null;
        rear = null;
        size = 0;
    }
}
