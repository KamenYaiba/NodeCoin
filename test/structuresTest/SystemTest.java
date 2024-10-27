package structuresTest;

import structures.*;
import nodecoin.UserInterface;
        
// @author aakts
 
public class SystemTest {
    
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
        UserInterface ui = new UserInterface();
 
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
    }
    
}
