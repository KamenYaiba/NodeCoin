package structures;


// @author mio
public class NodeCoin {
    private Node head = null;
    private Node tail = null;
    private Node lastAccessedNode;
    private long size = 0;
    static final int DATE_LENGTH = 8;
    
    private class Node{
        MaxHeap record;
        String date;
        Node nextHash = null;
        Node prevHash = null;

        Node(String date){
            this.date = date;
            this.record = new MaxHeap(1000);
        }

        @Override
        public String toString(){
            return "date: " + date + "\n\n" + record.toString();
        }
        
        public boolean lessThan(Node other){
            int comp = this.date.substring(4).compareTo(other.date.substring(4));
            if( comp < 0)
                return true;
            if(comp > 0)
                return false;
            
            comp = this.date.substring(2, 4).compareTo(other.date.substring(2, 4));
            if(comp < 0)
                return true;
            if(comp > 0)
                return false;
            
            comp = this.date.substring(0, 2).compareTo(other.date.substring(0, 2));
            if(comp < 0)
                return true;
            return false;
        }
    }


    public boolean insert(String date, double amount){
        if(date.length() != 8)
            date = "0" + date;
        if(!validDate(date))
            return false;
        Node node;
        if((node = grab(date)) == null){
            node = addNewNode(date);
        }
        Transaction t = new Transaction(amount);
        node.record.insert(t);
        return true;
    }


    public Transaction getMax(String date){
        Node node;
        if(date.length() != 8)
            date = "0" + date;
        if((node = grab(date)) == null || head == null)
            return null;

        return node.record.getMax();
    }


    public boolean removeMax(String date){
        Node node;
        if(date.length() != 8)
            date = "0" + date;
        if((node = grab(date)) == null)
            return false;

        node.record.removeMax();

        return true;
    }


    public String getAll(String date){
        Node node;
        if(date.length() != 8)
            date = "0" + date;
        if((node = grab(date)) == null)
            return null;

        MaxHeap record = node.record;

        StringBuilder sb = new StringBuilder();
        while(!record.isEmpty())
            sb.append(record.removeMax().toString()).append("\n");
        
        if(sb.length() != 0)
            sb.deleteCharAt(sb.length()-1);

        detach(node);

        return sb.length() == 0? null: sb.toString();        
    }


    public long getSize(){
        return size;
    }


    public boolean contains(String date){
        return grab(date) != null;
    }


    private boolean detach(Node node){
        if(node.date.equals(lastAccessedNode.date))
            lastAccessedNode = null;
        if(head != tail){
            if(node != head)
                node.prevHash.nextHash = node.nextHash;
            else
                head = head.nextHash;
            
            if(node != tail)
                node.nextHash.prevHash = node.prevHash;
            else
                tail = tail.prevHash;
        }
        else
            head = tail = null;
        size--;
        return true; 
    }


    private Node grab(String date){
        if(lastAccessedNode != null && date.equals(lastAccessedNode.date))
            return lastAccessedNode;
        
        Node itr = head;
        for(int i = 0; i < size; i++){
            if(itr.date.equals(date)){
                lastAccessedNode = itr;
                return itr;
            }   
            itr = itr.nextHash;
        }
        return null;
    }


    private Node addNewNode(String date){
        Node node = new Node(date);
        if(size == 0){
            head = tail = lastAccessedNode = node;
            size++;
            return node;
        }
        
        if(tail.lessThan(node)){
            return addToEnd(node);
        }
        Node itr;
        if(node.lessThan(head))
            return addToStart(node);
        else{
            itr = head.nextHash;
            for(int i = 0; i < size-1; i++){
                if(node.lessThan(itr))
                    break;
                itr = itr.nextHash;
            }
        }
        
        node.prevHash = itr.prevHash;
        itr.prevHash = node;
        if(node.prevHash != null)
            node.prevHash.nextHash = node;
        node.nextHash = itr;
        
        size++;
        return node;
    }
    
    
    private Node addToEnd(Node node){
        node.prevHash = tail;
        tail.nextHash = node;
        tail = node;

        size++;
        return node;
    }
    
    private Node addToStart(Node node){
        node.nextHash = head;
        head.prevHash = node;
        head = node;
        
        size++;
        return node;
    }
    
    
    private boolean validDate(String s){
        if(s.length() != DATE_LENGTH)
            return false;
        for(int i = 0; i < DATE_LENGTH; i++)
            if(!Character.isDigit(s.charAt(i)))
                return false;

        if(s.substring(0, 2).compareTo("01") < 0 || s.substring(0, 2).compareTo("31") > 0)
            return false;
        if(s.substring(2, 4).compareTo("01") < 0 || s.substring(2, 4).compareTo("12") > 0)
            return false;
        
        return true;    
    }
    
    //for testing purposes only
    public String[] datesToString(){
        Node itr = head;
        String[] array = new String[(int)size];
        for(int i = 0; i < size; i++){
            array[i] = itr.date;
            itr = itr.nextHash;
        }
        return array;
    }
}