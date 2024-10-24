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
        double number = 0;

        @Override
        public String toString(){
            return null;
        }
    }
    
    
    class MaxHeap {
    
        public Transaction getMax(){
            return null;
        }

        public Transaction removeMax(){
            return null;
        }

        public boolean isEmpty(){
            return false; //
        }

        public boolean insert(double amount){
            return false;
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
            int operation = reader.nextInt();
            String date = reader.next();
            //optional
            double amount = reader.hasNextDouble()? reader.nextDouble(): -1;

            String output = null;

            switch(operation){
                case INSERT:
                    nodeCoin.insert(date, amount);
                    break;
                case GET_MAX:
                    Transaction t = nodeCoin.getMax(date);
                    output = (t == null? "-1": t.toString());
                    break;
                case REMOVE_MAX:
                    nodeCoin.removeMax(date);
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
