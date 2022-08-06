public class Main {
    
    public static void main(){

        int width = 6;
        double p = 0.6;
        
        Border border = new Border(width, p);

        Clock uniClock = new Clock();

        Infiltrator infil = new Infiltrator();

        while( !border.border_crossed && !border.intruder_detected)
        {
            border.runDutyCycle();
            border.updateView();
            Integer[] Position = infil.NextMove();
            uniClock.incrimentTime(1); 
            border.moveIntruder(Position[0], Position[1]);
            uniClock.incrimentTime(9);   
        }

        if(border.border_crossed)
        {
            System.out.println("Intruder crossed successfully!");
            System.out.println("p: "+ p);
            System.out.println("width: " + width);
            System.out.println("Time taken: " + uniClock.time);
        }

        if(border.intruder_detected)
        {
            System.out.println("Intruder detected!");
            System.out.println("p: "+ p);
            System.out.println("width: " + width);
            System.out.println("Time taken: " + uniClock.time);
        }
    }
}
