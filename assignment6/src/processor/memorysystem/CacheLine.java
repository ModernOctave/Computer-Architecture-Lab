package processor.memorysystem;

public class CacheLine {
    int[] datas;
    int[] tags;
    int[] TSLAs;
    int associativity;

    public CacheLine(int associativity) {
        this.associativity = associativity;
        datas = new int[associativity];
        tags = new int[associativity];
        TSLAs = new int[associativity];
    }

    public int getWay(int tag) { 
        for(int i = 0; i < associativity; i++){
            if(this.tags[i] == tag){
                return i;
            }
        }

        return -1;
    }

    public int getData(int way) {
        return datas[way];
    }

    public void setData(int way, int data) {
        datas[way] = data;
    }
}
