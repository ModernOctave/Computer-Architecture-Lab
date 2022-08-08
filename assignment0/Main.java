public class Main {
    
    public static void main(String[] args){
        int[] widths = {6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        double[] ps = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.65, 0.7, 0.75, 0.8};

        Simulation sim = new Simulation();
        for (int width : widths) {
            for (double p : ps) {
                double expTime = sim.getExpectedTime(width, p, 0.001, 100);
                System.out.print(expTime + ", ");
            }
            System.out.println();
        }
    }
}
