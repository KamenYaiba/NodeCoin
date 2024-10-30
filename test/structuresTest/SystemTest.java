package structuresTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import structures.*;
import nodecoin.UserInterface;
        
// @author aakts
 
public class SystemTest {
    
    final int INSERT = 1;
    final int GET_MAX = 2;
    final int REMOVE_MAX = 3;
    final int GET_ALL = 4;

    NodeCoin nodeCoin;
    
    
    
    static String testCases[] = {
    "1 01072024 10.0\n" +
    "1 01072024 15.0\n" +
    "1 25092024 35.2\n" +
    "1 25092024 48.5\n" +
    "1 25092024 20.8\n" +
    "1 25092024 10.5\n" +
    "1 25092024 30.1\n" +
    "1 01072024 5.5\n" +
    "2 01072024",

    "1 11032022 \n" +
    "1 11032022 55\n" +
    "1 11032022 55.2\n" +
    "2 12032022\n" +
    "1 12032022 100\n" +
    "1 12032022 50\n" +
    "1 11032022 55.3\n" +
    "3 11032022\n" +
    "2 12032022\n" +
    "4 11032022",

    "7 13032022\n" +
    "2 14032022\n" +
    "1 14032022 100\n" +
    "1 15032022 30\n" +
    "1 16032022 80\n" +
    "1 16032022 80.1\n" +
    "1 15032022 53.9\n" +
    "1 15032022 200.10\n" +
    "2 16032022\n" +
    "3 15032022\n" +
    "4 16032022\n" +
    "4 15032022\n" +
    "2 16032022\n" +
    "3 15032022",

    "1 11032022 90\n" +
    "1 11032022 99.99\n" +
    "2 11032022\n" +
    "3 11032022\n" +
    "2 11032022\n" +
    "3 11032022\n" +
    "2 11032022\n" +
    "4 11032022",

    "7\n" +
    "7"
};

    
    
    static String expectedOutput[] = {"15.0 2", "-1\n" +
"-1\n" +
"100.0 1\n" +
"55.2 2\n" +
"55.0 1",
        
"-1\n" +
"-1\n" +
"80.1 2\n" +
"80.1 2\n" +
"80.0 1\n" +
"53.9 2\n" +
"30.0 1\n" +
"-1\n" +
"-1", 
    
"99.99 2\n" +
"90.0 1\n" +
"-1\n" +
"-1",
    
"-1\n-1"};
    
    
    
    public static void main(String[] args) {
        SystemTest ui = new SystemTest();
 
        for(int i = 0; i < testCases.length; i++){
            if(expectedOutput[i].equals(ui.test(testCases[i])))
                System.out.println("TEST " + (i+1) + ": PASS \\(^^)/");
            else
                System.out.println("TEST " + (i+1) + ": FAIL :(");
        }
        
        if(ui.correctOrder())
            System.out.println("TEST 6: PASS \\(^^)/");
        else
             System.out.println("TEST 6: FAIL :(");
        
        System.out.println("Avg Insertion time (20): "
                +ui.getInsertionSpeed(1, 10000000));
    }
    
    
    public String test(String testCase){
        nodeCoin = new NodeCoin();
        Scanner input = new Scanner(testCase);

        StringBuilder response = new StringBuilder();
        String temp;
        while(input.hasNextLine()){
            temp = parse(input.nextLine());
            if(temp == null)
                continue;
            
            response.append(temp).append("\n");
        }
        if(response.length() != 0)
            response.deleteCharAt(response.length()-1);
        return response.toString();
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
    

    public boolean correctOrder(){
        String array[] = nodeCoin.datesToString();
        for(int i = 0; i < array.length-1; i++){
            if(lessThan(array[i+1], array[i]))
                return false;
        }
        return true;
    }
    
    
    private boolean lessThan(String date1, String date2){
        int comp = date1.substring(4).compareTo(date2.substring(4));
        if( comp < 0)
            return true;
        if(comp > 0)
            return false;

        comp = date1.substring(2, 4).compareTo(date2.substring(2, 4));
        if(comp < 0)
            return true;
        if(comp > 0)
            return false;

        comp = date1.substring(0, 2).compareTo(date2.substring(0, 2));
        if(comp < 0)
            return true;
        return false;
    }
    
    
    private long getInsertionSpeed(int LinkedListSize, int heapSize){
        nodeCoin = new NodeCoin();
        Scanner reader;
        try{
            reader = new Scanner(new File("test/structuresTest/dates.txt"));
        }
        catch(FileNotFoundException e){
            System.out.println("data file not found");
            return 0;
        }
        
        int i = 0;
        while(reader.hasNextLine() && i++ < LinkedListSize){
            String date = reader.nextLine();
            for(int j = 0; j < heapSize; j++)
                nodeCoin.insert(date, (Math.random() * 10000));
        }
        
        final int numOfInserts = 20;
        
        nodeCoin.insert("10122026", 45);
        long start = System.nanoTime();
        nodeCoin.insert("01012000", 10000 + Math.random() * 100);
        nodeCoin.insert("01012000", 10000 + Math.random() * 100);
        nodeCoin.insert("01012000", 10000 + Math.random() * 100);
        nodeCoin.insert("01012000", 10000 + Math.random() * 100);
        nodeCoin.insert("01012000", 10000 + Math.random() * 100);
        nodeCoin.insert("01012000", 10000 + Math.random() * 100);
        nodeCoin.insert("01012000", 10000 + Math.random() * 100);
        nodeCoin.insert("01012000", 10000 + Math.random() * 100);
        nodeCoin.insert("01012000", 10000 + Math.random() * 100);
        nodeCoin.insert("01012000", 10000 + Math.random() * 100);
        nodeCoin.insert("01012000", 10000 + Math.random() * 100);
        nodeCoin.insert("01012000", 10000 + Math.random() * 100);
        nodeCoin.insert("01012000", 10000 + Math.random() * 100);
        nodeCoin.insert("01012000", 10000 + Math.random() * 100);
        nodeCoin.insert("01012000", 10000 + Math.random() * 100);
        nodeCoin.insert("01012000", 10000 + Math.random() * 100);
        nodeCoin.insert("01012000", 10000 + Math.random() * 100);
        nodeCoin.insert("01012000", 10000 + Math.random() * 100);
        nodeCoin.insert("01012000", 10000 + Math.random() * 100);
        nodeCoin.insert("01012000", 10000 + Math.random() * 100);
        
        
        long end = System.nanoTime();
        //System.out.println(nodeCoin.getAll("01012000"));
        return (end - start) / numOfInserts;
    }
    
}
