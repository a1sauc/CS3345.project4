/**
 * Contains user's name, priority, and number of pages. 
 * Implements Comparable with compareTo based on job priority
 */
public class Printjob implements Comparable<Printjob> {

    // fields
    protected String user_name;
    protected int user_priority;
    protected int num_pages;
    protected boolean IO_flag;  // true for Inside && false for Outside

    /**
     * Compares objects based on job priority. 
     * @param object it is comparing to
     * @return 0 if equal, -1 if lower value (higher) priority, 1 if higher value (lower) priority
     */
    public int compareTo(Printjob o) {        

        // current object's job priority is higher
        if ( this.getJobPriority() < o.getJobPriority() ) {
            return -1;
        }
        // current object's job priority is lower
        else if ( this.getJobPriority() > o.getJobPriority() ) {
            return 1;
        }
        // Objects job priorty equal eachother
        else {
            // if job priority equals.. check user priorty. if one is less than the other, return -1, or 1
            // else they are also the same 
            // (1x10 == 1x10 returns 0) || (1x20 == 2x10 returns -1) || (2x10 == 1x20 returns 1) 
            if ( this.user_priority < o.user_priority ) {
                return -1;
            }
            else if ( this.user_priority > o.user_priority ) {
                return 1;
            }
            return 0;
        }
        
    }

    /**
     * Getter method for job priority
     * @return object's job priority
     */
    public int getJobPriority(){
        return this.jobPriority();
    }

    /**
     * Calculates job priority: user_priority * num_pages
     * @return job priority
     */
    private int jobPriority() {
        return this.user_priority * this.num_pages;
    } 
}
