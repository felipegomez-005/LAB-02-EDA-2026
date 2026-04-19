import edu.princeton.cs.algs4.Stack;

public class ColaDePilas<T> implements Cola<T> {

    private Stack<T> pilaEntrada;
    private Stack<T> pilaSalida;

    public ColaDePilas() {
        pilaEntrada = new Stack<T>();
        pilaSalida = new Stack<T>();
    }

    @Override
    public void enqueue(T item) {
        pilaEntrada.push(item);
    }

    private void vaciarEntradaEnSalida() {
        if (pilaSalida.isEmpty()) {
            while (!pilaEntrada.isEmpty()) {
                T elemento = pilaEntrada.pop();
                pilaSalida.push(elemento);
            }
        }
    }

    @Override
    public T dequeue() {
        vaciarEntradaEnSalida();
        return pilaSalida.pop();
    }

    @Override
    public T peek() {
        vaciarEntradaEnSalida();
        return pilaSalida.peek();
    }

    @Override
    public boolean isEmpty() {
        return pilaEntrada.isEmpty() && pilaSalida.isEmpty();
    }

    @Override
    public int size() {
        return pilaEntrada.size() + pilaSalida.size();
    }
}