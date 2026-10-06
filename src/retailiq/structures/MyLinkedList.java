package retailiq.structures;

/**
 * Custom Generic Doubly-Linked List implemented without java.util.*
 *
 * @param <T> Element type
 */
public class MyLinkedList<T> {

    public static class Node<E> {
        public E data;
        public Node<E> prev;
        public Node<E> next;

        public Node(E data) {
            this.data = data;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void add(T item) {
        Node<T> newNode = new Node<>(item);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    public void addFirst(T item) {
        Node<T> newNode = new Node<>(item);
        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> curr;
        if (index < size / 2) {
            curr = head;
            for (int i = 0; i < index; i++) {
                curr = curr.next;
            }
        } else {
            curr = tail;
            for (int i = size - 1; i > index; i--) {
                curr = curr.prev;
            }
        }
        return curr.data;
    }

    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> curr = head;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        return unlink(curr);
    }

    public boolean remove(T item) {
        Node<T> curr = head;
        while (curr != null) {
            if ((item == null && curr.data == null) || (item != null && item.equals(curr.data))) {
                unlink(curr);
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    private T unlink(Node<T> node) {
        T data = node.data;
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
        size--;
        return data;
    }

    public boolean contains(T item) {
        Node<T> curr = head;
        while (curr != null) {
            if ((item == null && curr.data == null) || (item != null && item.equals(curr.data))) {
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    public Node<T> getHead() {
        return head;
    }

    public Node<T> getTail() {
        return tail;
    }

    @SuppressWarnings("unchecked")
    public T[] toArray(T[] a) {
        if (a.length < size) {
            a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
        }
        int i = 0;
        Node<T> curr = head;
        while (curr != null) {
            a[i++] = curr.data;
            curr = curr.next;
        }
        return a;
    }
}
