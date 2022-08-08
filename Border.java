import java.util.ArrayList;
// import java.util.Arrays;

public class Border {
    int width;
    double p;
    int intruder_progress = 0;
    boolean intruder_detected = false;
    boolean border_crossed = false;
    ArrayList<ArrayList<Sensor>> sensor_grid = new ArrayList<ArrayList<Sensor>>();
    boolean[][] neighbours_status= {{false,false,false}, {false,false,false}, {false,false,false}};
    boolean[][] On_sensors = {{false,false,false}, {false,false,false}, {false,false,false}};

    public Border(int width, double p) {
        this.width = width;
        this.p = p;

        for (int i = 0; i < 3; i++) {
            ArrayList<Sensor> sensors_row = new ArrayList<Sensor>();
            for (int j = 0; j < 3; j++) {
                sensors_row.add(new Sensor(p));
            }
            sensor_grid.add(sensors_row);
        }
    }

    public void runDutyCycle() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sensor_grid.get(i).get(j).runDutyCycle();
            }
        }
    }

    public void moveIntruder(int x, int y) {
        if (Math.abs(x) > 1 || Math.abs(y) > 1) {
            System.out.println("[Error]: Invalid move by intruder!");
            return;
        }
        else if (x == 0 && y == 0) {
            return;
        }

        // Check if the intruder gets caught
        if (sensor_grid.get(1).get(1).isOn || sensor_grid.get(1+x).get(1+y).isOn) {
            intruder_detected = true;
            return;
        }

        // Check if the intruder crosses the border
        if (intruder_progress+y > width) {
            border_crossed = true;
            return;
        }

        // Process move in x axis
        if (y == -1) {
            ArrayList<Sensor> sensors_row = new ArrayList<Sensor>();
            for (int j = 0; j < 3; j++) {
                sensors_row.add(new Sensor(p));
            }
            sensor_grid.add(0, sensors_row);
            sensor_grid.remove(3);
        }
        else if (y == 1) {
            ArrayList<Sensor> sensors_row = new ArrayList<Sensor>();
            for (int j = 0; j < 3; j++) {
                sensors_row.add(new Sensor(p));
            }
            sensor_grid.add(sensors_row);
            sensor_grid.remove(0);
        }

        // Process move in y axis
        if (x == -1) {
            for (int i = 0; i < 3; i++) {
                sensor_grid.get(i).add(0, new Sensor(p));
                sensor_grid.get(i).remove(3);
            }
        }
        else if (x == 1) {
            for (int i = 0; i < 3; i++) {
                sensor_grid.get(i).add(new Sensor(p));
                sensor_grid.get(i).remove(0);
            }
        }

        return;
    }

    public void updateView(){

        for(int i=0; i<3; i++)
        {
            for(int j=0; j<3; j++)
            {
                neighbours_status[i][j] = (sensor_grid.get(i).get(j)).exists;
                On_sensors[i][j] = sensor_grid.get(i).get(j).isOn;
            }

        }

    }


}