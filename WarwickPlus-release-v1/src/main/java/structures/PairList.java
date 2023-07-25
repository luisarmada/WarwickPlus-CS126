package structures;

public class PairList<K,V> { // Pair List is just a linked list using Pairs - required for storing key and value

    private LinkedList<Pair<K,V>> list;

    protected ListElement<Pair<K,V>> head;

    protected int size;

    public PairList() {
        list = new LinkedList<>();
        head = null;
        size = 0;
    }

    // Add new element to head - linked list
    public boolean add(Pair<K,V> e) {
        ListElement<Pair<K,V>> element = new ListElement<>(e);

        element.setNext(head);
        head = element;

        size++;

        return true;
    }

    public LinkedList<Pair<K, V>> getEntries() {
        return list;
    }

    public void removeEntry(Pair<K, V> key) {
        ListElement<Pair<K, V>> current = head;
        ListElement<Pair<K, V>> previous = null;

        while (current != null && !current.getValue().getKey().equals(key)) {
            previous = current;
            current = current.getNext();
        }

        if (current != null) {
            if (previous == null) {
                head = current.getNext();
            } else {
                previous.setNext(current.getNext());
            }
            size--;
        }
    }

    public Pair<K, V> getIndex(int index) {  // Linear search
        ListElement<Pair<K, V>> ptr = head;
        for (int i = 0; i < index; i++) {
            ptr = ptr.getNext();
        }
        return ptr.getValue();
    }

    public Pair<K,V> getKey(K key){
        ListElement<Pair<K,V>> temp = head;

        while (temp != null){
            if (temp.getValue().getKey().equals(key)){
                return temp.getValue();
            }
            temp = temp.getNext();
        }

        return null;
    }
}
