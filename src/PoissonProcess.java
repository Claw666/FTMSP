import java.util.Random;

public class PoissonProcess {
    private double lambda;
    private Random rand;

    // Constructor
    public PoissonProcess(double lambda, Random rand) {
        this.lambda = lambda;
        this.rand = rand;
    }

    public Random getRand() {
        return rand;
    }

    public double getLambda() {
        return lambda;
    }

    public double getNextArrival (){
        return - Math.log(1.0 - rand.nextDouble()) / lambda;
    }
}
