import java.io.IOException;

/**
 *	Example program for using eventlists
 *	@author Joel Karel
 *	@version %I%, %G%
 */



public class Simulation {

    public CEventList list;
    public Queue queue;
    public Source source;
    public Sink sink;
    public csAgent csa;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        /** Add shifts as matrix
         *  A Shift is setup as follows:
         *  start of shift (hour), finish of shift (hour), no of CSA of type 0, no of CSA of type 1
         *  24 hour is dealt later on so 30-24=6am
         * */
        int[] firstShift = {6,14,40,40};
        int[] secondShift = {14,22,50,50};
        int[] thirdShift = {22,30,40,40};
        // Convert shifts to matrix, easier to manipulate (than having 3 while or 3 for-loops)
        int[][] shifts = {firstShift,secondShift,thirdShift};

        //Length of shift matrix
        int numberOfShifts = shifts.length;


        //initialize counter
        int j = 0;

        //Boolean for checking if CSA can handle both types of call or not
        boolean privHandAllType = true;

        //Do for all shifts - here for 3 shifts
        while (j < numberOfShifts) {
            // Convert the shift start and end times to seconds
            double startOfShift = shifts[j][0] * 3600;
            double endOfShift = shifts[j][1] * 3600;
            // get number of agents
            int noOfConsAgent = shifts[j][2]; //Consumer
            int noOfCorpAgent = shifts[j][3]; //Corporate

            // Create an eventlist
            CEventList l = new CEventList(startOfShift);

            // A queue for the agents so we can store calls until it can be handled by an agent
            Queue q = new Queue();

            // A source for Consumer Customer Call
            Source callConsumerCust = new Source(q,l,"Source 1", 0);
            // A source for Corporate Customer Call
            Source callCorporateCust = new Source(q,l,"Source 2", 1);

            // A sink
            Sink si = new Sink("Sink 1");

            // Until we have agents, create them with their ID
            for (int i = 0; i < noOfConsAgent; i++) {
                csAgent consumerAgent = new csAgent(q, si, l, "Consumer Agent with ID " + i, 0);
            }

            for (int i = 0; i < noOfCorpAgent; i++) {
                csAgent corporateAgent = new csAgent(q, si, l, "Corporate Agent with ID " + i, 1, privHandAllType);
            }

            // start the eventlist
            l.start(endOfShift);
            si.writeToFile(" " + j + ".csv");
            j++;
        }
    }
}
