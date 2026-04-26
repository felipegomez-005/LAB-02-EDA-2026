import edu.princeton.cs.algs4.Queue;

public class PilaDeCola<T> implements Pila<T> {

    private Queue<T> cola;

    public PilaDeCola() {
        cola = new Queue<T>();
    }

    @Override
    public void push(T item) {
        int tamanioAnterior = cola.size();
        cola.enqueue(item);
        rotar(tamanioAnterior);
    }

    private void rotar(int n) {
        for (int i = 0; i < n; i++) {
            cola.enqueue(cola.dequeue());
        }
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