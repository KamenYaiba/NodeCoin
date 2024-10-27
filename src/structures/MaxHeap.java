package structures;

public class MaxHeap {
    
    private int size = 0;
    private int capacity;
    private int lastTNum = 0;
    private Transaction transactions[];

    public MaxHeap(int capacity){
        this.capacity = capacity;
        this.transactions = new Transaction[capacity];
    }


    public boolean insert(Transaction t){
        if(size == capacity)
            return false;

        t.number = ++lastTNum;
        transactions[size++] = t;

        swim(size - 1);
        return true;
    }


    public Transaction removeMax(){
        if(size == 0)
            return null;

        Transaction max = transactions[0];
        swap(0, --size);
        sink(0);

        return max;  
    }


    public Transaction getMax(){
        return size == 0? null : transactions[0];
    }

    public boolean isEmpty(){
        return size == 0;
    }

    private void swap(int i, int j){
        Transaction temp = transactions[i];
        transactions[i] = transactions[j];
        transactions[j] = temp;
    }

    private void swim(int i){
        while(i > 0){
            int parent = getParent(i);
            if(transactions[i].compareTo(transactions[parent]) == 1)
                swap(i, parent);
            else
                break;

            i = parent;    
        }
    }


    private void sink(int i){
        int child;
        while(true){
            child = getRChild(i);
            if(!(child < size))
                break;

            if(child + 1 < size && transactions[child].compareTo(transactions[child + 1]) == -1)
                child++;

            if(transactions[i].compareTo(transactions[child]) == 1)
                break;

            swap(i, child);
            i = child;        
        }
    }

    private int getParent(int child){
        return (child - 1) / 2;
    }

    private int getRChild(int parent){
        return parent * 2 + 1;
    }
}