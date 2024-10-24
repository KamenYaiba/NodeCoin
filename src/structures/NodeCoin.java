package structures;

// @author mio
public class NodeCoin {
    
    private class Node{
        MaxHeap record;
        String date;
        Node nextHash = null;
        Node prevHash = null;
        
        Node(String date){
            this.date = date;
            this.record = new MaxHeap();
        }
        
        @Override
        public String toString(){
            return "date: " + date + "\n\n" + record.toString();
        }
    }
    
    private Node head = null;
    private Node tail = null;
    private long size = 0;

    
    public boolean insert(String date, double amount){
        Node node;
        if((node = grab(date)) == null){
            node = addNewNode(date);
        }
        node.record.insert(amount);
        return true;
    }
    
    
    public Transaction getMax(String date){
        Node node;
        if((node = grab(date)) == null)
            return null;
        
        return node.record.getMax();
    }
    
    
    public boolean removeMax(String date){
        Node node;
        if((node = grab(date)) == null)
            return false;
        
        node.record.removeMax();
       
        return true;
    }
    
    
    public String getAll(String date){
        Node node;
        if((node = grab(date)) == null)
            return null;
        
        MaxHeap record = node.record;
        
        StringBuilder sb = new StringBuilder();
        while(!record.isEmpty())
            sb.append(record.removeMax().amount).append(" ");
        
        detatch(node);
        
        return sb.toString();        
    }
    
    
    public long getSize(){
        return size;
    }
    
    
    public boolean contains(String date){
        return grab(date) != null;
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
    
    
    private Node grab(String date){
        Node itr = head;
        for(int i = 0; i < size; i++){
            if(itr.date.equals(date))
                return itr;
            itr = itr.nextHash;
        }
        return null;
    }
    
    
    private Node addNewNode(String date){
        Node node = new Node(date);
        if(size == 0){
            head = tail = node;
            size++;
            return node;
        }
        node.prevHash = tail;
        tail.nextHash = node;
        tail = node;
        
        size++;
        return node;
    }
}
