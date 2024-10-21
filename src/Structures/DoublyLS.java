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
    
    
    
    public void insert(T data){
        Node node = new Node(data);
        if(size == 0){
            head = tail = node;
            return;
        }
        node.prev = tail;
        tail.next = node;
        tail = node;
    }
    
    public void insert(T data, long idx){
        
    }
    
    
}
