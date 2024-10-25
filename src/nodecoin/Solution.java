package nodecoin;

// @author mio

import java.util.Scanner;




//hackerrank version
//this is where to put all classes in one file to submit on hr
public class Solution {
    
    public static void main(String[] args) {
        Solution sol = new Solution();
        Solution.UserInterface ui = sol.new UserInterface();
        ui.run();
    }
    
    class Transaction{
        double amount;
        int number = 0;

        public Transaction(double amount, int number){
            this.amount = amount;
            this.number = number;
        }
        
        public int compare(Transaction other){
            return(this.amount > other.amount? 1: this.amount == other.amount? 0: -1);
        }
        
        public String getNumber(){
            return String.valueOf(number);
        }
        
        public String getAmount(){
            return String.valueOf(amount);
        }
        
        @Override
        public String toString(){
            return getAmount() + " " + getNumber();
        }
    }
    
    
    class MaxHeap {
    
        private Transaction[] heap;
    private int capacity;
    private int currentSize;

    // Constructor for the MaxHeap
    public MaxHeap(int capacity) {
        this.capacity = capacity;
        this.currentSize = 0;
        this.heap = new Transaction[capacity]; 
    }

    
    public boolean insert(Transaction t) {
        if (currentSize >= capacity) {
            System.out.println("Heap is full");
            return false; 
        }
        heap[currentSize] = t;    
        swim(currentSize);        
        currentSize++;
        return true;              
    }

    
    public Transaction removeMax() {
        if (currentSize == 0) {
            System.out.println("Heap is empty");
            return null; 
        }        
        Transaction max = heap[0];    //max transaction at root       
        swap(0, --currentSize);     //swap last transaction to the root        
        sink(0);                    
        heap[currentSize] = null;     //clear removed transaction       
        return max;
    }

    // Swim method to restore the heap property by moving a node up
    private void swim(int k) {
        while (k > 0 && heap[getParent(k)].compareTo(heap[k]) < 0) { // Parent is smaller than current
            swap(k, getParent(k));
            k = getParent(k);
        }
    }

    
    private void sink(int k) {
        while (getLeftChild(k) < currentSize) { 
            int j = getLeftChild(k);            
            if (j + 1 < currentSize && heap[j].compareTo(heap[j + 1]) < 0) {
                j++;  // Right child exists and is larger
            }
            if (heap[k].compareTo(heap[j]) >= 0) {
                break;  // Parent is larger than both children
            }
            swap(k, j);
            k = j;
        }
    }

    
    private int getParent(int k) 
    {
     return (k - 1) / 2; 
    }
    private int getLeftChild(int k) 
    { 
        return 2 * k + 1; 
    }
    private int getRightChild(int k) 
    { 
        return 2 * k + 2; 
    }

    
    private void swap(int i, int j) {
        Transaction temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    
    public boolean isEmpty() {
        return currentSize == 0;
    }
}


class Transaction implements Comparable<Transaction> {
    double tAmt;  
    int tNum;     

    public Transaction(double tAmt, int tNum) {
        this.tAmt = tAmt;
        this.tNum = tNum;
    }

    @Override // Compare based on transaction amount
    public int compareTo(Transaction other) {
        return Double.compare(this.tAmt, other.tAmt);  
    }

    @Override
    public String toString() {
        return tAmt + " " + tNum;
    }
    }
    
    
    
    class NodeCoin {
    
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
                sb.append(record.removeMax().toString()).append("\n");
            sb.deleteCharAt(sb.length()-1);
            
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
    
    
    class UserInterface{

        final int INSERT = 1;
        final int GET_MAX = 2;
        final int REMOVE_MAX = 3;
        final int GET_ALL = 4;

        NodeCoin nodeCoin;


        public UserInterface(){
            nodeCoin = new NodeCoin();
        }


        public void run(){
            Scanner input = new Scanner(System.in);

            String response;
            while(input.hasNextLine()){
                response = parse(input.nextLine());
                System.out.print(response == null? "": response + "\n");
            }
        }


        private String parse(String line){
            Scanner reader = new Scanner(line);
            if(!reader.hasNextInt())
                return "-1";
            int operation = reader.nextInt();
            if(!reader.hasNext())
                return "-1";
            String date = reader.next();


            String output = null;

            switch(operation){
                case INSERT:
                    if(!reader.hasNextDouble()){
                        output = "-1";
                        break;
                    }
                    double amount = reader.nextDouble();
                    nodeCoin.insert(date, amount);
                    break;

                case GET_MAX:
                    Transaction t = nodeCoin.getMax(date);
                    output = (t == null? "-1": t.toString());
                    break;

                case REMOVE_MAX:
                    if(!nodeCoin.removeMax(date))
                        output = "-1";
                    break;

                case GET_ALL:
                    String s = nodeCoin.getAll(date);
                    output = (s == null? "-1": s);
                    break;

                default:
                    output = "-1";
            }

            return output;
        }
    }
}
