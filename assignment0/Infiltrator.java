import java.util.ArrayList;

public class Infiltrator {
    public Infiltrator(){
        
    }

    public Integer[] NextMove(boolean[][] neighbours_status, boolean[][] On_sensors){

        Integer[] Position = {0,0};


        ArrayList<Integer[]> available = new ArrayList<Integer[]>();
        

        if(On_sensors[1][1])
        {
            return Position;
        }


        if(!neighbours_status[2][0])
        {
            Integer[] finalPos = {0,1};

            return finalPos;
        }

        else
        {
            for(int i=0; i<3; i++)
            {
                if(!On_sensors[2][i])
                {
                    Integer[] disp = {i-1, 1};
                    available.add(disp);  
                }
                
            }

            if(available.size() != 0)
            {
                int min = 0;
                int max = available.size()-1;
                int rand_int = (int)Math.floor(Math.random()*(max-min+1) + min);  
                return available.get(rand_int);
            }


            for(int i=0; i<3; i++)
            {
                if(i==1)
                    continue;
                if(!On_sensors[1][i])
                {
                    Integer[] disp = {i-1, 0};
                    available.add(disp);
                }
                
            }

            if(available.size() != 0)
            {
                int min = 0;
                int max = available.size()-1;
                int rand_int = (int)Math.floor(Math.random()*(max-min+1) + min);  
                return available.get(rand_int);
            }

            
            
        }

        return Position;


    }

    
}
