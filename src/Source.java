
/**
 *	A source of products
 *	This class implements CProcess so that it can execute events.
 *	By continuously creating new events, the source keeps busy.
 *	@author Joel Karel
 *	@version %I%, %G%
 */
public class Source implements CProcess
{
	/** Eventlist that will be requested to construct events */
	private CEventList list;
	/** Queue that buffers products for the machine */
	private ProductAcceptor queue;
	/** Name of the source */
	private String name;
	/** Mean interarrival time */
	private double meanArrTime;
	/** Interarrival times (in case pre-specified) */
	private double[] interarrivalTimes;
	/** Interarrival time iterator */
	private int interArrCnt;
	/** Type of Source */
	private int typeOfCall;

	private int callerID;

	/**
	*	Constructor, creates objects
	*        Interarrival times are exponentially distributed with mean 33
	*	@param q	The receiver of the products
	*	@param l	The eventlist that is requested to construct events
	*	@param n	Name of object
	*/
	public Source(ProductAcceptor q,CEventList l,String n)
	{
		list = l;
		queue = q;
		name = n;
		meanArrTime=33;
		// put first event in list for initialization
		list.add(this,0,drawRandomExponential(meanArrTime)); //target,type,time
	}
	// TODO write comment
	public Source(ProductAcceptor q, CEventList l, String n, int typeOfCall) {
		this.list = l;
		this.queue = q;
		this.name = n;
		this.typeOfCall = typeOfCall;
		double firstCall = 0;
		if (typeOfCall == 0) {
			firstCall = drawRandomExponential(l.getTime()) + l.getTime();
		}
		if (typeOfCall == 1) {
			firstCall = drawRandomExponential(getMeanArrivalRateCorporate(l.getTime())) + l.getTime();
		}
		// put first event in list for initialization
		list.add(this, typeOfCall, firstCall);
	}

	/**
	*	Constructor, creates objects
	*        Interarrival times are exponentially distributed with specified mean
	*	@param q	The receiver of the products
	*	@param l	The eventlist that is requested to construct events
	*	@param n	Name of object
	*	@param m	Mean arrival time
	*/
	public Source(ProductAcceptor q,CEventList l,String n,double m)
	{
		list = l;
		queue = q;
		name = n;
		meanArrTime=m;
		// put first event in list for initialization
		list.add(this,0,drawRandomExponential(meanArrTime)); //target,type,time
	}

	/**
	*	Constructor, creates objects
	*        Interarrival times are prespecified
	*	@param q	The receiver of the products
	*	@param l	The eventlist that is requested to construct events
	*	@param n	Name of object
	*	@param ia	interarrival times
	*/
	public Source(ProductAcceptor q,CEventList l,String n,double[] ia)
	{
		list = l;
		queue = q;
		name = n;
		meanArrTime=-1;
		interarrivalTimes=ia;
		interArrCnt=0;
		// put first event in list for initialization
		list.add(this,0,interarrivalTimes[0]); //target,type,time
	}
	
	@Override
	public void execute(int type, double tme)
	{
		// show arrival
		System.out.println("Arrival at time = " + tme);
		// give arrived product to queue
		Product p = new Product(typeOfCall, callerID);
		callerID = callerID+1;
		p.stamp(tme,"Creation",name);
		queue.giveProduct(p);

		if(typeOfCall == 0) {
			double arrivalTime = drawRandomNonStationaryExp(tme);
			list.add(this,typeOfCall, arrivalTime);
		}

		if(typeOfCall == 1) {
			double duration = drawRandomExponential(getMeanArrivalRateCorporate(tme));
			list.add(this,typeOfCall, (duration+tme));
		}
	}
	
	public static double drawRandomExponential(double mean)
	{
		// draw a [0,1] uniform distributed number
		double u = Math.random();
		// Convert it into a exponentially distributed random variate with mean
		return -mean*Math.log(u);
	}

	public static double drawRandomNonStationaryExp(double tme){

		//Equations by general thinning algorithm - recursive function
		double maxLambda = 3.8;

		double U1 = Math.random();
		double U2 = Math.random();

		double arrivalTime = (tme / 60) - (1/maxLambda)*Math.log(U1);

		//Handle Hours in 24h format
		double tmeInHour = handle24H(arrivalTime / 3600);

		double lambdaT = (1.8 * Math.sin((2 * Math.PI / 24) * (tmeInHour + 15)) + 2 );

		if (U2 <= (lambdaT/maxLambda)) {
			return arrivalTime * 60 ;
		}

		return drawRandomNonStationaryExp(arrivalTime * 60);
	}

	public static double getMeanArrivalRateCorporate(double tme) {
		double averageTime = 0;

		//Handle Hours in 24h format
		double tmeInHour = handle24H(tme / 3600);


		// Poisson process and between 8 am and 6 pm - rate 1 per minute
		if (8 < tmeInHour && tmeInHour < 18) {
			averageTime = 60 / 1;
		}
		// Poisson process and between 6 pm and 8 am - rate 0.2 per minute
		if (18 < tmeInHour || tmeInHour < 8) {
			averageTime = 60 / 0.2;
		}

		return averageTime;
	}


	// Function to handle 24h format: 30h = 6am in the morning
	public static double handle24H(double timeHour) {
		if (timeHour > 24) {
			timeHour = timeHour - 24;
		}
		return timeHour;
	}

	public String toString(){
		return "Source: " + typeOfCall + " with mean arrival time " + meanArrTime;
	}
}