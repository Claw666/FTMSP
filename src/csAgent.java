
// Renaming file from Machine to csAgent

/**
 *	Machine in a factory
 *	@author Joel Karel
 *	@version %I%, %G%
 */
public class csAgent implements CProcess,ProductAcceptor
{
	/** Product that is being handled  */
	private Product product;
	/** Eventlist that will manage events */
	private final CEventList eventlist;
	/** Queue from which the machine has to take products */
	private Queue queue;
	/** Sink to dump products */
	private ProductAcceptor sink;
	/** Status of the machine (b=busy, i=idle) */
	private char status;
	/** Machine name */
	private final String name;
	/** Mean processing time */
	private double meanProcTime;
	/** Processing times (in case pre-specified) */
	private double[] processingTimes;
	/** Processing time iterator */
	private int procCnt;
	/**  Type of CSA - business or consumer */
	private int typeOfCSA;
	/** Privilege of handling all types of calls */
	private boolean privHandBothType;
	/** Standard Deviation */
	private double standardDeviation;
	/** Truncated Value */
	private double truncatedThreshold;

	public double getMeanProcTime(){
		return this.meanProcTime;
	}

	public double getStandardDeviation(){
		return this.standardDeviation;
	}

	public double getTruncatedThreshold(){
		return this.truncatedThreshold;
	}

	/**
	*	Constructor
	*   Service times are exponentially distributed with mean 30
	*	@param q	Queue from which the machine has to take products
	*	@param s	Where to send the completed products
	*	@param e	Eventlist that will manage events
	*	@param n	The name of the machine
	*/
	public csAgent(Queue q, ProductAcceptor s, CEventList e, String n)
	{
		status='i';
		queue=q;
		sink=s;
		eventlist=e;
		name=n;
		meanProcTime=30;
		queue.askProduct(this);
	}

	public csAgent(Queue q, ProductAcceptor s, CEventList e, String n, int typeOfCSA)
	{
		status='i';
		queue=q;
		sink=s;
		eventlist=e;
		name=n;
		this.typeOfCSA = typeOfCSA;
		if(typeOfCSA == 0){
            standardDeviation = 35;
			meanProcTime=72;
			truncatedThreshold = 25;

		} else{
            standardDeviation = 72;
			meanProcTime=216;
			truncatedThreshold = 45;
		}
		privHandBothType = false;
		queue.askProduct(this);
	}

	public csAgent(Queue q, ProductAcceptor s, CEventList e, String n, int typeOfCSA, boolean privHandBothType)
	{
		status='i';
		queue=q;
		sink=s;
		eventlist=e;
		name=n;
		if(typeOfCSA == 0){
            standardDeviation = 35;
			meanProcTime=72;
			truncatedThreshold = 25;

		}
		else{
            standardDeviation = 72;
			meanProcTime=216;
			truncatedThreshold = 45;
		}
		this.typeOfCSA = typeOfCSA;
		this.privHandBothType = privHandBothType;
		queue.askProduct(this);
	}

	/**
	*	Constructor
	*        Service times are exponentially distributed with specified mean
	*	@param q	Queue from which the machine has to take products
	*	@param s	Where to send the completed products
	*	@param e	Eventlist that will manage events
	*	@param n	The name of the machine
	*        @param m	Mean processing time
	*/
	public csAgent(Queue q, ProductAcceptor s, CEventList e, String n, double m)
	{
		status='i';
		queue=q;
		sink=s;
		eventlist=e;
		name=n;
		meanProcTime=m;
		queue.askProduct(this);
	}
	
	/**
	*	Constructor
	*        Service times are pre-specified
	*	@param q	Queue from which the machine has to take products
	*	@param s	Where to send the completed products
	*	@param e	Eventlist that will manage events
	*	@param n	The name of the machine
	*        @param st	service times
	*/
	public csAgent(Queue q, ProductAcceptor s, CEventList e, String n, double[] st)
	{
		status='i';
		queue=q;
		sink=s;
		eventlist=e;
		name=n;
		meanProcTime=-1;
		processingTimes=st;
		procCnt=0;
		queue.askProduct(this);
	}

	/**
	*	Method to have this object execute an event
	*	@param type	The type of the event that has to be executed
	*	@param tme	The current time
	*/
	public void execute(int type, double tme)
	{
		// show arrival
		System.out.println("Call finished at " + tme + " second");
		// Remove product from system
		product.stamp(tme,"Call completed",name);
		sink.giveProduct(product);
		product=null;
		// set machine status to idle
		status='i';
		// Ask the queue for products
		queue.askProduct(this);
	}
	
	/**
	*	Let the machine accept a product and let it start handling it
	*	@param p	The product that is offered
	*	@return	true if the product is accepted and started, false in all other cases
	*/
        @Override
	public boolean giveProduct(Product p)
	{
		// Only accept something if the machine is idle
		if(status=='i')
		{
		    // If corp CSA in idle or consumer agent idle
			if (this.privHandBothType || this.typeOfCSA == p.getTypeOfCustCall()) {
				System.out.println(this.name + " Gets call with type " + typeOfCSA);
				// accept the product
				product=p;
				// mark starting time
				product.stamp(eventlist.getTime(),"Call started",name);
				// start production
				startProduction();
				// Flag that the product has arrived
				return true;
			} else return false;
		}
		// Flag that the product has been rejected
		else return false;
	}
	
	/**
	*	Starting routine for the production
	*	Start the handling of the current product with an exponentionally distributed processingtime with average 30
	*	This time is placed in the eventlist
	*/
	private void startProduction()
	{
		// generate duration
		if(meanProcTime>0)
		{
			double duration = drawTruncatedNormalDist();
			// Create a new event in the eventlist
			double tme = eventlist.getTime();
			eventlist.add(this,typeOfCSA,tme+duration); //target,type,time
			// set status to busy
			status='b';
		}
		else
		{
			if(processingTimes.length>procCnt)
			{
				eventlist.add(this,0,eventlist.getTime()+processingTimes[procCnt]); //target,type,time
				// set status to busy
				status='b';
				procCnt++;
			}
			else
			{
				eventlist.stop();
			}
		}
	}

	public static double drawRandomExponential(double mean)
	{
		// draw a [0,1] uniform distributed number
		double u = Math.random();
		// Convert it into a exponentially distributed random variate with mean 33
		double res = -mean*Math.log(u);
		return res;
	}

	// https://mathworld.wolfram.com/Box-MullerTransformation.html - Box Muller Transform
	// https://stackoverflow.com/questions/18039341/using-the-box-muller-transform-to-generate-pseudorandom-numbers-with-any-sigma-a
	public double boxMullerTransformOne(double U1,double U2){
		double Z1;

		Z1 = getMeanProcTime() + getStandardDeviation() * (Math.sqrt(-2 * Math.log(U1)) * (Math.cos((2 * Math.PI) * U2)));

		//check if value is below threshold
		if (Z1 < getTruncatedThreshold()){
			Z1 = -1;
			return Z1;
		}
        return Z1;
	}


    public double boxMullerTransformTwo(double U1,double U2){
        double Z2;

        Z2 = getMeanProcTime() + getStandardDeviation() * (Math.sqrt(-2 * Math.log(U1)) * (Math.sin((2 * Math.PI) * U2)));

        //check if value is below threshold - set it to an impossible value so we can check it later on if it is in the range or not.
        if (Z2 < getTruncatedThreshold()){
            Z2 = -1;
            return Z2;
        }
        return Z2;
    }


	public double drawTruncatedNormalDist() {
		double U1 = Math.random();
		double U2 = Math.random();
		double randVar1 = boxMullerTransformOne(U1,U2);
		double randVar2 = boxMullerTransformTwo(U1,U2);

		//return randVar2 if the other exceeded the range
		if (randVar1 < 0 && randVar2 > 0)
			return randVar2;

        //return randVar1 if the other exceeded the range
		else if(randVar1 > 0 && randVar2 < 0)
			return randVar1;

		//in case both our outside the range, run algorithm again.
		else if (randVar1 < 0 && randVar2 < 0)
            drawTruncatedNormalDist();

		//in case both are valid, choose randomly from them.
        double tossACoin = Math.random();
		if(tossACoin <=0.5) {
			return randVar1;
		}
		return randVar2;
	}
	public String toString() { return "Status of CS Agent: " + status + ", for agent: " + name; }
}