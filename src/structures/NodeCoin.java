package structures;

// @author mio
public class NodeCoin {
    
    private class Node{
        MaxHeap heap;
        String date;
        Node nextHash = null;
        Node prevHash = null;
        
        Node(String date){
            this.date = date;
            this.heap = new MaxHeap();
        }
        
        @Override
        public String toString(){
            return "date: " + date + "\n\n" + heap.toString();
        }
    }
    
    private Node head = null;
    private Node tail = null;
    private long size = 0;

    
    public boolean insert(String date, double amount){
        Node node;
        if((node = grap(date)) == null){
            addNewNode(date);
            insert(date, amount);
        }
        
        node.heap.insert(amount);
        return true;
    }
    
    
    public Transaction getMax(String date){
        Node node;
        if((node = grap(date)) == null)
            return null;
        
        return node.heap.getMax();
    }
    
    
    public boolean removeMax(String date){
        Node node;
        if((node = grap(date)) == null)
            return false;
        
        node.heap.removeMax();
       
        return true;
    }
    
    
    public String getAll(String date){
        Node node;
        if((node = grap(date)) == null)
            return null;
        
        MaxHeap mh = node.heap;
        
        StringBuilder sb = new StringBuilder();
        while(!mh.isEmpty())
            sb.append(mh.removeMax().amount).append(" ");
        
        detatch(node);
        
        return sb.toString();        
    }
    
    
    public long getSize(){
        return size;
    }
    
    
    public boolean contains(String date){
        return grap(date) != null;
    }
    
    
    private boolean detatch(Node node){
        try{
            if(node != head)
                node.prevHash.nextHash = node.nextHash;
            else
                head = head.nextHash;
            if(node != tail)
                node.nextHash.prevHash = node.prevHash;
            else
                tail = tail.prevHash;
        }
        catch(NullPointerException e){
            return false;
        }
        size--;
        return true; 
    }
    
    
    private Node grap(String date){
        Node itr = head;
        for(int i = 0; i < size; i++){
            if(itr.date.equals(date))
                return itr;
            itr = itr.nextHash;
        }
        return null;
    }
    
    
    private boolean addNewNode(String date){
        Node node = new Node(date);
        if(size == 0){
            head = tail = node;
            size++;
            return true;
        }
        node.prevHash = tail;
        tail.nextHash = node;
        tail = node;
        
        size++;
        return true;
    }
}
