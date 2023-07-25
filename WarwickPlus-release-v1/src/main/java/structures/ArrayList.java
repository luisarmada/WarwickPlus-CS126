package structures;

public class ArrayList<E>{

    private Object[] array;
    private int size;
    
    public ArrayList() {
        this.array = new Object[100];
        this.size = 0;
    }

    // Used to insert
    public void addX(int n, E x){

        if(n == size){
            add(x);
            return;
        }

        if (size == this.array.length) {
            resize(size*2);
        }
   
       // create a new array of size n+1
       Object newarr[] = new Object[size + 1];
   
       // insert elements from old array into new array, insert all elements up to n
       // then insert x at n+1
       for (int i = 0; i < n; i++)
           newarr[i] = this.array[i];
   
       newarr[n] = x;

       // fill in rest after n+1 (where x is)
       for (int i = n; i < size; i++)
           newarr[i+1] = this.array[i];
   
       this.array = newarr;
       size++;
   }
   

    public boolean add(E element) {
        if (size == this.array.length) {
            resize(size*2);
        }
        this.array[size++] = element;
        return true;
    }

    public boolean contains(E element) { // if cant find index of element, then not in arraylist
        return this.indexOf(element) >= 0;
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public int size() {
        return size;
    }
    
    @SuppressWarnings("unchecked")
    public E get(int index) {
        return (E) array[index];
    }
    
    public int indexOf(E element) {
        for (int i=0;i<this.size();i++) { // linear search to find index
            if (element.equals(this.array[i])) {
                return i;
            }
        }
        return -1;
    }

    public boolean removeIndex(int index) {
        if (index < 0) return false;
            
        for (int i = index+1;i < size; i++) { // shift all elements down
            set(i-1, get(i));
        }

        array[size-1] = null;
        size--;

        return true;
    }

    public void set(int index, E e) {
        if (index >= this.size()) return;

        this.array[index] = e;
    }
    
    private void resize(int newsize) {
        Object[] newArr = new Object[newsize];

        for(int i = 0; i < array.length; i++){
            newArr[i] = array[i];
        }

        this.array = newArr;
    }
    
}
