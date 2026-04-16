import edu.princeton.cs.algs4.Queue;

public class PilaConFila<T> implements Pila<T> {

    private Queue<T> fila;

    public PilaConFila() {
        fila = new Queue<T>();
    }

    @Override
    public void push(T item) {
        int tamañoAnterior = fila.size();
        fila.enqueue(item);
        rotar(tamañoAnterior);
    }

    private void rotar(int n) {
        if (n <= 0) {
            return;
        }
        T elementoViejo = fila.dequeue();
        fila.enqueue(elementoViejo);

        rotar(n - 1);
    }

    @Override
    public T pop() {
        return fila.dequeue();
    }

    @Override
    public T peek() {
        return fila.peek();
    }

    @Override
    public boolean isEmpty() {
        return fila.isEmpty();
    }

    @Override
    public int size() {
        return fila.size();
    }
}