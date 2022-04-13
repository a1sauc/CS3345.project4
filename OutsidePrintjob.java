public class OutsidePrintjob extends Printjob {
    static double cost_per_page = .10;
    private double cost;

    public double getCost(){
        return this.calcCost();
    }

    private double calcCost(){
        cost = this.num_pages * cost_per_page; 
        return cost;
    }
    
}
