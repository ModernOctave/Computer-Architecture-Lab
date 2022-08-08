public class Sensor {
    double p;
    boolean isOn;
    boolean exists;

    public Sensor(double p, boolean exists) {
        this.p = p;
        this.exists = exists;
        runDutyCycle();
    }

    public void runDutyCycle() {
        double sample = Math.random();
        if (sample < p) {
            isOn = true && exists;
        } 
        else {
            isOn = false;
        }
    }   
}
