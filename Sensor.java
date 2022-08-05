public class Sensor {
    double p;
    boolean isOn;

    public Sensor(double p) {
        this.p = p;
        runDutyCycle();
    }

    public void runDutyCycle() {
        double sample = Math.random();
        if (sample < p) {
            isOn = true;
        } else {
            isOn = false;
        }
    }   
}
