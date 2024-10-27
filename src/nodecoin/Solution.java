package nodecoin;

import java.util.Scanner;

//hackerrank version
//this is where to put all classes in one file to submit on hr
public class Solution {
    
    public static void main(String[] args) {
        Solution sol = new Solution();
        Solution.UserInterface ui = sol.new UserInterface();
        ui.run();
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
                    output = t == null? "-1": t.toString();

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
    
    class NodeCoin {
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

        private Node head = null;
        private Node tail = null;
        private long size = 0;


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

            detatch(node);

            return sb.length() == 0? null: sb.toString();        
        }


        public long getSize(){
            return size;
        }


        public boolean contains(String date){
            if(date.length() != 8)
                date = 0 + date;
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
    }
    
    class MaxHeap {
    
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
    
    class Transaction{
        double amount;
        int number = 0;

        public Transaction(double amount){
            this.amount = amount;
        }

        public int compareTo(Transaction other){
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
    
}
