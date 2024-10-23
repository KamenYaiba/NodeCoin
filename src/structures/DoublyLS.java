package structures;

// @author mio
public class DoublyLS<T> {
    private class Node{
        T data;
        Node next = null;
        Node prev = null;
        
        Node(T data){
            this.data = data;
        }
    }
    
    private Node head = null;
    private Node tail = null;
    private long size = 0;
    
    
    public T get(long idx){
        Node node;
        if((node = grap(idx)) != null)
            return node.data;
        return null;
    }
            
    
    public boolean add(T data){
        Node node = new Node(data);
        if(size == 0){
            head = tail = node;
            size++;
            return true;
        }
        node.prev = tail;
        tail.next = node;
        tail = node;
        
        size++;
        return true;
    }
    
    
    public boolean insert(T data, long idx){
        if(idx == size)
            return add(data);
        
        Node node = new Node(data);
        
        Node itr;
        if((itr = grap(idx)) == null)
            return false;
        
        if(itr != head)
            itr.prev.next = node;
        else
            head = node;
        
        node.prev = itr.prev;
        itr.prev = node;
        node.next = itr;
        
        size++;
        return true;
    }
    
    
    public boolean remove(long idx){
        Node node;
        if((node = grap(idx)) == null)
            return false;
        
        if(detatch(node)){
            size--;
            return true;
        }
        return false;
    }
    
    public boolean remove(){
        return remove(size -1);
    }
    
    
    private Node search(T data){
        Node it = head;
        for(int i = 0; i<size; i++)
            if(data == it.data)
                return it;
        
        return null;
    }
    
    
    private boolean detatch(Node node){
        try{
            if(node != head)
                node.prev.next = node.next;
            else
                head = head.next;
            if(node != tail)
                node.next.prev = node.prev;
            else
                tail = tail.prev;
        }
        catch(NullPointerException e){
            return false;
        }
        
        return true; 
    }
    
    
    private Node grap(long idx){
        if(idx == size -1)
            return tail;
        if(idx > size -1 || idx < 0)
            return null;
        
        Node itr = head;
        for(int i = 0; i < idx; i++)
            itr = itr.next;
        
        return itr;
    }
    
    
    public long getSize(){
        return size;
    }
    
    
    @Override
    public String toString(){
        if(size == 0)
            return "[]";
        
        StringBuilder sb = new StringBuilder();
        
        sb.append("[");
        Node itr = head;
        while(itr.next != null){
            sb.append(itr.data);
            sb.append(", ");
            itr = itr.next;
        }
        
        sb.append(itr.data).append("]");
        
        return sb.toString();
    }   
}
