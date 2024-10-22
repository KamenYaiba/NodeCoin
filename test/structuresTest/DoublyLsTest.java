package structuresTest;

//@author mio

import structures.DoublyLS;

public class DoublyLsTest {
    public static void main(String[] args) {
        DoublyLS<Integer> ls = new DoublyLS<>();
        ls.add(5);
        ls.add(4);
        ls.add(2);
        
        if(ls.toString().equals("[5, 4, 2]"))
            System.out.println("TEST 1: PASS \\(^^)/");
        else
            System.out.println("TEST 1: FAIL :(");
        
        ls.insert(7, 0);
        ls.insert(8, 2);
        
        if(ls.toString().equals("[7, 5, 8, 4, 2]"))
            System.out.println("TEST 2: PASS \\(^^)/");
        else
            System.out.println("TEST 2: FAIL :(");
        
        ls.remove(0);
        ls.remove(3);
        
        if(ls.toString().equals("[5, 8, 4]"))
            System.out.println("TEST 3: PASS \\(^^)/");
        else
            System.out.println("TEST 3: FAIL :(");
        
        if(ls.remove(10))
            System.out.println("TEST 4: FAIL :(");
        else
            System.out.println("TEST 4: pass \\(^^)/");
    }
    
    
    
}
