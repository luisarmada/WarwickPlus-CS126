package structures;

public class Queue<T>{

    ArrayList<T> arrList;

    public Queue(){
        arrList = new ArrayList<>();
    }

    public void enqueue(T item){ // insert to front of list, like queue
        arrList.addX(0, item);
    }

    public T dequeue(){ // remove item at end of list and return it
        T item = arrList.get(arrList.size()-1);
        arrList.removeIndex(arrList.size()-1);
        return item;
    }

    public boolean isEmpty(){
        return arrList.size() <= 0;
    }

    public T peek(){ // returns item at end of list
        return arrList.get(arrList.size()-1);
    }

}
