package Structures;

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
    
    
    
    public void add(T data){
        Node node = new Node(data);
        if(size == 0){
            head = tail = node;
            return;
        }
        node.prev = tail;
        tail.next = node;
        tail = node;
        
        size++;
    }
    
    
    public boolean insert(T data, long idx){
        if(idx > size-1)
            return false;
        
        Node node = new Node(data);
        
        Node it = head;
        for(int i = 0; i < idx; i++)
            it = it.next;
        
        node.prev = it.prev;
        it.prev = node;
        node.next = it;
        
        size++;
        return true;
    }
    
    
    public boolean remove(long idx){
        if(idx > size -1)
            return false;
        
        Node it = head;
        for(int i = 0; i < idx; i++)
            it = it.next;
        
        loseNode(it);
    }
    
    private Node search(T data){
        Node it = head;
        for(int i = 0; i<size; i++)
            if(data == it.data)
                return it;
        
        return null;
    }
        
    
    
    
}
