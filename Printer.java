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

    /** BinaryHeap class
     * Implements a binary heap.
     * Note that all "matching" is based on the compareTo method.
     * @author Mark Allen Weiss
     */
    /////// NESTED INNER CLASS BinaryHeap \\\\\\\\
    public class BinaryHeap<AnyType extends Comparable<? super AnyType>> {

        /**
         * Construct the binary heap.
         */
        public BinaryHeap( )
        {
            this( DEFAULT_CAPACITY );
        }

        /**
         * Construct the binary heap.
         * @param capacity the capacity of the binary heap.
         */
        public BinaryHeap( int capacity )
        {
            currentSize = 0;
            array = (AnyType[]) new Comparable[ capacity + 1 ];
        }
        
        /**
         * Construct the binary heap given an array of items.
         */
        public BinaryHeap( AnyType [ ] items )
        {
                currentSize = items.length;
                array = (AnyType[]) new Comparable[ ( currentSize + 2 ) * 11 / 10 ];

                int i = 1;
                for( AnyType item : items )
                    array[ i++ ] = item;
                buildHeap( );
        }

        /**
         * Insert into the priority queue, maintaining heap order.
         * Duplicates are allowed.
         * @param x the item to insert.
         */
        public void insert( AnyType x )
        {
            if( currentSize == array.length - 1 )
                enlargeArray( array.length * 2 + 1 );

                // Percolate up
            int hole = ++currentSize;
            for( array[ 0 ] = x; x.compareTo( array[ hole / 2 ] ) < 0; hole /= 2 )
                array[ hole ] = array[ hole / 2 ];
            array[ hole ] = x;
        }


        private void enlargeArray( int newSize )
        {
                AnyType [] old = array;
                array = (AnyType []) new Comparable[ newSize ];
                for( int i = 0; i < old.length; i++ )
                    array[ i ] = old[ i ];        
        }
        
        /**
         * Find the smallest item in the priority queue.
         * @return the smallest item, or throw an UnderflowException if empty.
         * @throws Exception
         */
        public AnyType findMin( ) throws Exception
        {
            if( isEmpty( ) )
                throw new Exception( "UnderflowException" );
            return array[ 1 ];
        }

        /**
         * Remove the smallest item from the priority queue.
         * @return the smallest item, or throw an UnderflowException if empty.
         */
        public AnyType deleteMin( ) throws Exception
        {
            if( isEmpty( ) ) 
                //throw new UnderflowException( );
                throw new Exception( "UnderflowException" );

            AnyType minItem = findMin( );
            array[ 1 ] = array[ currentSize-- ];
            percolateDown( 1 );

            return minItem;
        }

        /**
         * Establish heap order property from an arbitrary
         * arrangement of items. Runs in linear time.
         */
        private void buildHeap( )
        {
            for( int i = currentSize / 2; i > 0; i-- )
                percolateDown( i );
        }

        /**
         * Test if the priority queue is logically empty.
         * @return true if empty, false otherwise.
         */
        public boolean isEmpty( )
        {
            return currentSize == 0;
        }

        /**
         * Make the priority queue logically empty.
         */
        public void makeEmpty( )
        {
            currentSize = 0;
        }

        private static final int DEFAULT_CAPACITY = 10;

        private int currentSize;      // Number of elements in heap
        private AnyType [ ] array; // The heap array

        /**
         * Internal method to percolate down in the heap.
         * @param hole the index at which the percolate begins.
         */
        private void percolateDown( int hole )
        {
            int child;
            AnyType tmp = array[ hole ];

            for( ; hole * 2 <= currentSize; hole = child )
            {
                child = hole * 2;
                if( child != currentSize &&
                        array[ child + 1 ].compareTo( array[ child ] ) < 0 )
                    child++;
                if( array[ child ].compareTo( tmp ) < 0 )
                    array[ hole ] = array[ child ];
                else
                    break;
            }
            array[ hole ] = tmp;
        }

    } // end of BinaryHeap

    // MAIN
    public static void main( String[] args ) throws IOException {
        long startTime = System.currentTimeMillis();
        
        // perhaps add line 174 to 207 in a Try-catch statement incase input.txt doesnt exist
        Scanner scan = new Scanner(new BufferedReader( new FileReader("input.txt") ) );
        
        Printer printObj = new Printer();
        BinaryHeap<Printjob> h = printObj.new BinaryHeap<>();
        
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
                h.insert(pObj);
            }
            else {
                // Create object and set fields
                OutsidePrintjob outObj = new OutsidePrintjob();
                outObj.user_name = arrayInput[0];
                outObj.user_priority = Integer.valueOf(arrayInput[1]);
                outObj.num_pages = Integer.valueOf(arrayInput[2]);
                outObj.IO_flag = false;

                // add object to heap
                h.insert(outObj);
            }
        }
        scan.close();

        Printjob inObj;
        OutsidePrintjob outObj;
        
        System.out.printf("\n%s\t   %s      %s\t\t%s\n", "Name", "User Priority", "Pages", "Cost");
        System.out.println("-----------------------------------------------------");
        while ( !h.isEmpty() ) {
                
            try {
            
                if ( h.findMin().IO_flag ) {
                    inObj = h.deleteMin();
                    
                    System.out.printf("%-10s\t%d\t\t%d\n", inObj.user_name, inObj.user_priority, inObj.num_pages);
                }
                else {
                    inObj = h.deleteMin();
                    if ( inObj instanceof OutsidePrintjob ) {
                        // cast Printjob to OutsidePrintjob
                        outObj = (OutsidePrintjob) inObj;
                        System.out.printf("%-10s\t%d\t\t%d\t\t$%3.2f\n", inObj.user_name, inObj.user_priority, inObj.num_pages, outObj.getCost());
                    }  
                }
            } catch (Exception e) {
                System.out.println("Underflow Exception");      
            }   
        }

        long endTime = System.currentTimeMillis();
        System.out.println("\nElapsed time: " + (endTime - startTime) + "milliseconds\n");
        
    } // end of main

} // end of Printer
