public class Simulation {
    Boolean border_crossed, intruder_detected;
    Clock clock;
    Border border;
    Infiltrator infil;

    public Simulation() {
    }

    public void run(int width, double p, boolean debug) {
        run(width, p);

        if (debug) {
            if(border.border_crossed)
            {
                System.out.println("Intruder crossed successfully!");
                System.out.println("p: "+ p);
                System.out.println("width: " + width);
                System.out.println("Time taken: " + clock.time);
            }

            if(border.intruder_detected)
            {
                System.out.println("Intruder detected!");
                System.out.println("p: "+ p);
                System.out.println("width: " + width);
                System.out.println("Time taken: " + clock.time);
            }
        }
    }

    public void run(int width, double p) {
        border = new Border(width, p);

        clock = new Clock();

        infil = new Infiltrator();

        while( !border.border_crossed && !border.intruder_detected)
        {
            border.runDutyCycle();
            border.updateView();
            Integer[] Position = infil.NextMove(border.neighbours_status, border.On_sensors);
            clock.incrimentTime(1); 
            border.moveIntruder(Position[0], Position[1]);
            clock.incrimentTime(9);   
        }
    }

    public double getExpectedTime(int width, double p, double epsilon, int iterations) {
        int n = 0, sum = 0, times = 0;
        double expectedTime = 0, prevExpectedTime = 0;
        do {
            n++;
            run(width, p);
            sum += clock.time;
            expectedTime = (double)sum / n;
            // System.out.println(expectedTime);
            if (Math.abs(expectedTime - prevExpectedTime) < epsilon) {
                times++;
            }
            else {
                times = 0;
                prevExpectedTime = expectedTime;
            }
        } while (times < iterations);
            

        return expectedTime;
    }
}