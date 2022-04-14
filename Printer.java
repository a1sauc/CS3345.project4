/** Printer class
 * author@ Josh Priest
 * email@ jsp200002@utdallas.edu
 * Reads an input file and creates objects for each entry.
 * These objects are added to a priority queue using textbook's BinaryHeap class (unmodified).
 * Input file entry example:
 *      Name    User_Priority   Num_Pages   INSIDE/OUTSIDE_FLAG
 */

 // import statements
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Printer {

    private BinaryHeap<Printjob> h;

    public Printer(){
        h = new BinaryHeap<>();
    }

    public void insert(Printjob o) {
        h.insert(o);
    }
    
    public void insert(OutsidePrintjob o) {
        h.insert(o);
    }

    public void delete() {

        Printjob inObj;
        OutsidePrintjob outObj;

        try {
        
            if ( h.findMin() instanceof OutsidePrintjob  ) {
                outObj = (OutsidePrintjob) h.deleteMin();

                System.out.printf("%-10s\t%d\t\t%d\t\t$%3.2f\n", outObj.user_name, outObj.user_priority, outObj.num_pages, outObj.getCost());
            }
            else {
                inObj = h.deleteMin();

                System.out.printf("%-10s\t%d\t\t%d\n", inObj.user_name, inObj.user_priority, inObj.num_pages);
            }

        } catch (UnderflowException e) {
            System.out.println("UnderflowException. Tried deleting from an empty heap.");
        }
        
    }

    public boolean checkEmpty(){
        return h.isEmpty();
    }

    public static void main( String[] args ) throws IOException {
        long startTime = System.currentTimeMillis();
        
        try {
           
            Scanner scan = new Scanner(new BufferedReader( new FileReader("input.txt") ) );
            Printer testObj = new Printer();
            
            while ( scan.hasNextLine() ) {
            
                String[] arrayInput = scan.nextLine().split("\\s+");
                
                // if element 4 (inside/outside flag) == "I" create Printjob object; else create outsidePrintjob object.
                if ( arrayInput[3].equalsIgnoreCase("I") ) {
                    // Create object and set fields
                    Printjob pObj = new Printjob();
                    pObj.user_name = arrayInput[0];
                    pObj.user_priority = Integer.valueOf(arrayInput[1]);
                    pObj.num_pages = Integer.valueOf(arrayInput[2]);
                    pObj.IO_flag = true;
                    
                    // add object to heap
                    testObj.insert(pObj);
                    
                }
                else {
                    // Create object and set fields
                    OutsidePrintjob outObj = new OutsidePrintjob();
                    outObj.user_name = arrayInput[0];
                    outObj.user_priority = Integer.valueOf(arrayInput[1]);
                    outObj.num_pages = Integer.valueOf(arrayInput[2]);
                    outObj.IO_flag = false;
    
                    // add object to heap
                    testObj.insert(outObj);
                }
            }
            scan.close();

            System.out.printf("\n%s\t   %s      %s\t\t%s\n", "Name", "User Priority", "Pages", "Cost");
            System.out.println("-----------------------------------------------------");

            while ( !testObj.checkEmpty() ) {
                testObj.delete();
            }


        } catch (IOException e) {
            System.out.println("IOException. Input file error.");
        }
        long endTime = System.currentTimeMillis();
        System.out.println("\nElapsed time: " + (endTime - startTime) + "milliseconds\n");
        
    } // end of main

} // end of Printer
