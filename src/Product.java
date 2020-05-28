import java.util.ArrayList;

// Product is Call in our case. Added type of cust call

/**
 *	Product that is send trough the system
 *	@author Joel Karel
 *	@version %I%, %G%
 */
class Product
{
	/** Stamps for the products */
	private ArrayList<Double> times;
	private ArrayList<String> events;
	private ArrayList<String> stations;
	private int typeOfCustCall;
  private int callID;
	
	/** 
	*	Constructor for the product
	*	Mark the time at which it is created
	*/
	public Product()
	{
		times = new ArrayList<>();
		events = new ArrayList<>();
		stations = new ArrayList<>();
	}

	public Product( int typeOfCustCall) {
		times = new ArrayList<>();
		events = new ArrayList<>();
		stations = new ArrayList<>();
		this.typeOfCustCall = typeOfCustCall;
	}

  public Product(int typeOfCustCall, int callID) {
    times = new ArrayList<>();
    events = new ArrayList<>();
    stations = new ArrayList<>();
    this.typeOfCustCall = typeOfCustCall;
    this.callID = callID;
  }
	
	
	public void stamp(double time,String event,String station)
	{
		times.add(time);
		events.add(event);
		stations.add(station);
	}

	/** Getter functions */
	public ArrayList<Double> getTimes()
	{
		return times;
	}

	public ArrayList<String> getEvents()
	{
		return events;
	}

	public ArrayList<String> getStations()
	{
		return stations;
	}

	public int getTypeOfCustCall() { return typeOfCustCall; }
	
	public double[] getTimesAsArray()
	{
		times.trimToSize();
		double[] tmp = new double[times.size()];
		for (int i=0; i < times.size(); i++)
		{
			tmp[i] = (times.get(i)).doubleValue();
		}
		return tmp;
	}

	public String[] getEventsAsArray()
	{
		String[] tmp = new String[events.size()];
		tmp = events.toArray(tmp);
		return tmp;
	}

	public String[] getStationsAsArray()
	{
		String[] tmp = new String[stations.size()];
		tmp = stations.toArray(tmp);
		return tmp;
	}
	public int getTypeOfCall() {return typeOfCustCall;}

  	public int getCallID() {
    return callID;
  }

	public String toString(){ return "Type of Call: " + typeOfCustCall + "and no. of times: " + times; }
}