package structuresTest;

import structures.*;
import nodecoin.UserInterface;
        
// @author aakts
 
public class SystemTest {
    
    static String testCases[] = {"1 01072024 10.0\n" +
"1 01072024 15.0\n" +
"1 25092024 35.2\n" +
"1 25092024 48.5\n" +
"1 25092024 20.8\n" +
"1 25092024 10.5\n" +
"1 25092024 30.1\n" +
"1 01072024 5.5\n" +
"2 01072024",
    
    "1 333 \n" +
"1 333 55\n" +
"1 333 55.2\n" +
"2 444\n" +
"1 444 100\n" +
"1 444 50\n" +
"1 333 55.3\n" +
"3 333\n" +
"2 444\n" +
"4 333",
    
    "7 5555\n" +
"2 5555\n" +
"1 4444 100\n" +
"1 6666 30\n" +
"1 4444 80\n" +
"1 4444 80.1\n" +
"1 6666 53.9\n" +
"1 6666 200.10\n" +
"2 4444\n" +
"3 6666\n" +
"4 4444\n" +
"4 6666\n" +
"2 4444\n" +
"3 6666",
    
    "1 333 90\n" +
"1 333 99.99\n" +
"2 333\n" +
"3 333\n" +
"2 333\n" +
"3 333\n" +
"2 333\n" +
"4 333",
    
    "7"
    + "\n7"};
    
    
    static String expectedOutput[] = {"15.0 2", "-1\n" +
"-1\n" +
"100.0 1\n" +
"55.2 2\n" +
"55.0 1",
        
"-1\n" +
"-1\n" +
"100.0 1\n" +
"100.0 1\n" +
"80.1 3\n" +
"80.0 2\n" +
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
        UserInterface ui = new UserInterface();
        for(int i = 0; i < testCases.length; i++){
            if(expectedOutput[i].equals(ui.test(testCases[i])))
                System.out.println("TEST " + (i+1) + ": PASS \\(^^)/");
            else
                System.out.println("TEST " + (i+1) + ": FAIL :(");
        }

//          ui.run();
    }
}
