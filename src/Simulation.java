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
    public static void main(String[] args) {

        /** Add shifts as matrix
         *  A Shift is setup as follows:
         *  no of consumer CSA, no of corporate CSA
         * */

        int[] firstShift = {0,5};
        int[] secondShift = {2,5};
        int[] thirdShift = {0,5};
        // Convert shifts to matrix, easier to manipulate (than having 3 while or 3 for-loops)
        int[][] shifts = {firstShift,secondShift,thirdShift};

        csAgent.numbOfIdledCorpAgent = 0;
        String strategy = "First Strategy";
        if(csAgent.minNumOfIdledCorpAgent > 0) {
            strategy = "Second Strategy";
        }
        int i = 0;
        int numberOfRuns = 5;
        int numberOfDays = 15;
        int startOfShift = 6 * 3600;
        int simDuration = numberOfDays * 24 * 3600;
        int shiftLength = 8 * 3600;
        int numberOfShifts = (simDuration / shiftLength);
        int endOfShift;

        //Do for all shifts - here for 3 shifts
        while (i < numberOfRuns) {

            // Create an eventlist
            CEventList l = new CEventList(startOfShift);

            // A queue for the agents so we can store calls until it can be handled by an agent
            Queue q = new Queue();
            Queue q2 = new Queue();

            // A source for Consumer Customer Call
            Source callConsumerCust = new Source(q,l,"Source 1", 0);
            // A source for Corporate Customer Call
            Source callCorporateCust = new Source(q2,l,"Source 2", 1);

            // A sink
            Sink si = new Sink("Sink 1");

            endOfShift = startOfShift;

            for(int j = 0; j < (numberOfShifts + 1); j++){
                endOfShift += shiftLength;
                int shiftType = j % 3;
                csAgent.numbOfIdledCorpAgent = 0;
                for (int k = 0; k < shifts[shiftType][0]; k++) {
                    // A consumer CSA
                    csAgent consumerAgent= new csAgent(q, si, l, "Consumer Agent with ID " + k, endOfShift,0);
                }

                for (int k = 0; k < shifts[shiftType][1]; k++) {
                    // A corporate csa
                    csAgent corporateAgent = new csAgent(q,q2, si, l, "Corporate Agent with ID " + k, endOfShift,1);
                }
            }


            // start the eventlist
            l.start(endOfShift);
            si.writeToFile("callInformation" + i + ".csv");
            si.writeToFileWaitTimeConsumer("consumerWaitingTimes" + i + ".csv");
            si.writeToFileWaitTimeCorporate("corporateWaitingTimes" + i + ".csv");
            i++;
        }
        int costOfOperation = (shifts[0][0] + shifts[1][0] + shifts[2][0]) * 8 * 35 + (shifts[0][1] + shifts[1][1] + shifts[2][1])*8 * 60;
        System.out.println(" Cost of current setup is... : " + costOfOperation + "Euro per day.");
    }
}
