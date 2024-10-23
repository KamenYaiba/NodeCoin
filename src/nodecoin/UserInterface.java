package nodecoin;

// @author mio
import java.util.Scanner;
import structures.*;

public class UserInterface{

    static final int INSERT = 1;
    static final int GET_MAX = 2;
    static final int REMOVE_MAX = 3;
    static final int GET_ALL = 4;
    
    static NodeCoin nodeCoin;
    
    public static void main(String args[]) {
        
        nodeCoin = new NodeCoin();
        Scanner input = new Scanner(System.in);
        
        while(input.hasNextLine())
            parse(input.nextLine());
    }
    
    
    private static void parse(String line){
        
        Scanner reader = new Scanner(line);
        int operation = reader.nextInt();
        String date = reader.next();
        //optional
        double amount = reader.hasNextDouble()? reader.nextDouble(): -1;
        
        switch(operation){
            case INSERT:
                nodeCoin.insert(date, amount);
                break;
            case GET_MAX:
                Transaction t = nodeCoin.getMax(date);
                System.out.println(t == null? -1: t);
                break;
            case REMOVE_MAX:
                nodeCoin.removeMax(date);
                break;
            case GET_ALL:
                String s = nodeCoin.getAll(date);
                System.out.println(s == null? -1: s);
            default:
                System.out.println(-1);
        }
    }  
}
