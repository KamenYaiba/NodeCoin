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
        ls.remove(1);
        
        if(ls.toString().equals("[5, 4]"))
            System.out.println("TEST 3: PASS \\(^^)/");
        else
            System.out.println("TEST 3: FAIL :(");
        
        if(ls.remove(10))
            System.out.println("TEST 4: FAIL :(");
        else
            System.out.println("TEST 4: PASS \\(^^)/");
        
        ls.remove(0);
        ls.remove(0);
        if(ls.toString().equals("[]"))
            System.out.println("TEST 5: PASS \\(^^)/");
        else
            System.out.println("TEST 5: FAIL :(");
        
        if(ls.getSize() == 0)
            System.out.println("TEST 6: PASS \\(^^)/");
        else
            System.out.println("TEST 6: FAIL :(");
        
        ls.insert(5, 0);
        ls.add(6);
        ls.add(8);
        ls.insert(0, 2);
        ls.remove(1);
        ls.insert(4, 1);
        ls.remove(3);
        ls.add(10);
        
        if(ls.toString().equals("[5, 4, 0, 10]"))
            System.out.println("TEST 7: PASS \\(^^)/");
        else
            System.out.println("TEST 7: FAIL :(");
        
        ls.add(3);
        ls.insert(7, 2);
        ls.remove();
        if(ls.toString().equals("[5, 4, 7, 0, 10]"))
            System.out.println("TEST 8: PASS \\(^^)/");
        else
            System.out.println("TEST 8: FAIL :(");
        
        
    }
    
    
    
}
