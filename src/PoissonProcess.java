
public class PoissonProcess {
    

    public static float getNextArrival (int mean){
        return -Math.log(1.0 - Math.random()) / mean;
    }
}
