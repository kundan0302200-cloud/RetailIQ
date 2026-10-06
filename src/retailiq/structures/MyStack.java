package retailiq.structures;

/**
 * Custom Generic LIFO Stack implemented without java.util.*
 * Dynamic array-backed stack with O(1) amortized push and pop.
 *
 * @param <T> Element type
 */
public class MyStack<T> {
    private Object[] elements;
    private int top;
    private static final int INITIAL_CAPACITY = 16;

    public MyStack() {
        this.elements = new Object[INITIAL_CAPACITY];
        this.top = -1;
    }

    public void push(T item) {
        if (top == elements.length - 1) {
            resize();
        }
        elements[++top] = item;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) {
            return null;
        }
        T item = (T) elements[top];
        elements[top--] = null;
        return item;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return (T) elements[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public int size() {
        return top + 1;
    }

    private void resize() {
        Object[] newElements = new Object[elements.length * 2];
        for (int i = 0; i <= top; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;
    }

    public void clear() {
        for (int i = 0; i <= top; i++) {
            elements[i] = null;
        }
        top = -1;
    }
}
