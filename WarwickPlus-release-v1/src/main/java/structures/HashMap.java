package structures;

public class HashMap<K, V> {

    protected PairList<K,V>[] buckets;

    int numOfElements;

    public HashMap(){
        int size = 16;
        this.buckets = new PairList[size];
        for (int i = 0; i < size; i++) {
            this.buckets[i] = new PairList<>();
        }

        numOfElements = 0;
    }

    private boolean add(K key, V value){
        int index = Math.abs(key.hashCode()) % buckets.length;
        buckets[index].add(new Pair<K, V>(key, value));

        numOfElements++;

        return true;
    }

    public V get(K key){
        int index = Math.abs(key.hashCode()) % buckets.length;

        ListElement<Pair<K,V>> ptr = buckets[index].head;

        if(buckets[index].getKey(key) == null) return null;

        return (V)buckets[index].getKey(key).getValue();
    }

    public boolean remove(K key){
        if (key == null) {
            return false;
        }

        if(!containsKey(key)) return true;

        set(key, null);

        numOfElements--;

        return true;
    }

    public void put(K key, V value){
        if(containsKey(key)){
            set(key, value);
        } else {
            add(key, value);
        }
    }

    private boolean set(K key, V value){
        int index = Math.abs(key.hashCode()) % buckets.length;

        for (int i=0; i<buckets.length;i++) {
            if (buckets[index].getIndex(i).getKey().equals(key)) {
                buckets[index].getIndex(i).setValue(value);
                return true;
            }
        }
        return add(key, value);
    }

    public boolean containsKey(K key) {
        int index = Math.abs(key.hashCode()) % buckets.length;
        PairList<K, V> list = buckets[index];
        if (list != null) {
            for (int i = 0; i < list.size; i++) {
                Pair<K, V> pair = list.getIndex(i);
                if (pair.getKey().equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int size(){
        return numOfElements;
    }


}
