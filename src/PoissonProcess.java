import java.util.Random;

public class PoissonProcess {
    private double lambda;
    private Random rand;

    // Constructor
    public PoissonProcess(double lambda, Random rand) {
        this.lambda = lambda;
        this.rand = rand;
    }

    // Getter - get random value
    public Random getRand() {
        return rand;
    }
    // Getter - get lambda
    public double getLambda() {
        return lambda;
    }

    //Getter - get next arrival 
    public double getNextArrival (){
        return - Math.log(1.0 - rand.nextDouble()) / lambda;
    }
}
