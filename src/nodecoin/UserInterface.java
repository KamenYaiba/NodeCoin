package nodecoin;

// @author mio
import java.util.Scanner;
import structures.*;

public class UserInterface{

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
