import edu.princeton.cs.algs4.Queue;

public class PilaDeCola<T> implements Pila<T> {

    private Queue<T> cola;

    public PilaDeCola() {
        cola = new Queue<T>();
    }

    @Override
    public void push(T item) {
        int tamañoAnterior = cola.size();
        cola.enqueue(item);
        rotar(tamañoAnterior);
    }

    private void rotar(int n) {
        if (n <= 0) {
            return;
        }
        T elementoViejo = cola.dequeue();
        cola.enqueue(elementoViejo);

        rotar(n - 1);
    }

    @Override
    public T pop() {
        return cola.dequeue();
    }

    @Override
    public T peek() {
        return cola.peek();
    }

    @Override
    public boolean isEmpty() {
        return cola.isEmpty();
    }

    @Override
    public int size() {
        return cola.size();
    }
}