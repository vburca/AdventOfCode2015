import java.util.ArrayList;

public class Stack<E> {
  private ArrayList<E> storage = new ArrayList<E>();

  public void push(E element) {
    this.storage.add(element);
  }

  public E pop() {
    return this.storage.remove( this.storage.size() - 1 );
  }

  public int size() {
    return this.storage.size();
  }

  public boolean isEmpty() {
    return this.storage.size() == 0;
  }

  public E peek() {
    return this.storage.get( this.storage.size() - 1 );
  }
}
