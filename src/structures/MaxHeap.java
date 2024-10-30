package structures;

public class MaxHeap {
    
    private int currentSize = 0;
    private int capacity;
    private int lastTNum = 0;
    private Transaction transactions[];

    
    // Constructor for the MaxHeap
    public MaxHeap(int capacity){
        this.capacity = capacity;
        this.transactions = new Transaction[capacity];
    }


    public boolean insert(Transaction t){
        if(currentSize == capacity)
            return false;

        t.tNum = ++lastTNum;
        transactions[currentSize++] = t;

        swim(currentSize - 1);
        return true;
    }


    public Transaction removeMax(){
        if(currentSize == 0)
            return null;

        Transaction max = transactions[0]; //max transaction at root  
        swap(0, --currentSize); //swap last transaction to the root  
        sink(0);

        return max;  
    }


    public Transaction getMax(){
        return currentSize == 0? null : transactions[0];
    }

    public boolean isEmpty(){
        return currentSize == 0;
    }

    private void swap(int i, int j){
        Transaction temp = transactions[i];
        transactions[i] = transactions[j];
        transactions[j] = temp;
    }

    // Swim method to restore the heap property by moving a node up
    private void swim(int i){
        while(i > 0){
            int parent = getParent(i);
            if(transactions[i].compareTo(transactions[parent]) == 1) // Parent is smaller than current
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
            if(!(child < currentSize))
                break;

            if(child + 1 < currentSize && transactions[child].compareTo(transactions[child + 1]) == -1)
                child++; // left child exists and is larger

            if(transactions[i].compareTo(transactions[child]) == 1)
                break; // Parent is larger than both children

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