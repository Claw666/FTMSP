
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
	private int typeOfSource;

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
	public Source(ProductAcceptor q, CEventList l, String n, int typeOfSource) {
		list = l;
		queue = q;
		name = n;
		this.typeOfSource = typeOfSource;
		if (typeOfSource == 0) {
			meanArrTime = getMeanArrivalRateConsumers(l.getTime());
		}
		if (typeOfSource == 1) {
			meanArrTime = getMeanArrivalRateCorporate(l.getTime());
		}
		// put first event in list for initialization
		list.add(this, typeOfSource, drawRandomExponential(meanArrTime) + l.getTime()); //target,type,time
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
		Product p = new Product();
		p.stamp(tme,"Creation",name);
		queue.giveProduct(p);
		// generate duration
		if(meanArrTime>0)
		{
			double duration = drawRandomExponential(meanArrTime);
			// Create a new event in the eventlist
			list.add(this,0,tme+duration); //target,type,time
		}
		else
		{
			interArrCnt++;
			if(interarrivalTimes.length>interArrCnt)
			{
				list.add(this,0,tme+interarrivalTimes[interArrCnt]); //target,type,time
			}
			else
			{
				list.stop();
			}
		}
	}
	
	public static double drawRandomExponential(double mean)
	{
		// draw a [0,1] uniform distributed number
		double u = Math.random();
		// Convert it into a exponentially distributed random variate with mean 33
		return -mean*Math.log(u);
	}

	public static double getMeanArrivalRateConsumers(double tme){
		//Handle Hours in 24h format
		double tmeInHour = tme / 3600;
		handle24H(tmeInHour);

		// A nonstationary Poisson process that is a sinusoid
		// 60 divided by rate per minute so average arrival time will be returned in seconds
		// Inspiration for sinusoidal rate: http://www.columbia.edu/~ww2040/UsingBD.pdf
		return  60 / (1.8 * Math.sin((2*Math.PI/24)*(tmeInHour+15))+2);
	}

	public static double getMeanArrivalRateCorporate(double tme) {
		double averageTime = 0;

		//Handle Hours in 24h format
		double tmeInHour = tme / 3600;
		handle24H(tmeInHour);


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
		return "Source: " + typeOfSource + " with mean arrival time " + meanArrTime;
	}
}