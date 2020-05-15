import java.util.ArrayList;

/**
 *	Queue that stores products until they can be handled on a machine machine
 *	@author Joel Karel
 *	@version %I%, %G%
 */
public class Queue implements ProductAcceptor
{
	/** List in which the products are kept */
	private ArrayList<Product> row;
	/** Requests from machine that will be handling the products */
	private ArrayList<csAgent> requests;
	
	/**
	*	Initializes the queue and introduces a dummy machine
	*	the machine has to be specified later
	*/
	public Queue()
	{
		row = new ArrayList<>();
		requests = new ArrayList<>();
	}
	
	/**
	*	Asks a queue to give a product to a machine (here product is a call and the machine is the CS agent)
	*	True is returned if a product could be delivered; false if the request is queued
	*/
	public boolean askProduct(csAgent csAgent)
	{
		// This is only possible with a non-empty queue
		if(row.size()>0) {
			// If the machine accepts the product
			if(csAgent.giveProduct(row.get(0))) {
				row.remove(0);// Remove it from the queue
				return true;
			}
			else
				return false; // Machine rejected; don't queue request
		}
		else {
			requests.add(csAgent);
			return false; // queue request
		}
	}
	
	/**
	*	Offer a product to the queue
	*	It is investigated whether a machine wants the product, otherwise it is stored
	*/
	public boolean giveProduct(Product p)
	{
		// Check if the machine accepts it
		if(requests.size()<1)
			row.add(p); // Otherwise store it
		else {
			boolean delivered = false;
			ArrayList<csAgent> listOfRejected = new ArrayList<>();
			while(!delivered & (requests.size()>0)) {
				delivered=requests.get(0).giveProduct(p);
				// remove the request regardless of whether or not the product has been accepted
				csAgent removedReq = requests.remove(0);
				if(!delivered) {
					// If CSA cant take call, add to rejected list.
					listOfRejected.add(removedReq);
				}
			}
			if(!delivered)
				row.add(p); // Otherwise store it
			for (csAgent CSA: listOfRejected) {
				requests.add(CSA);
			}
		}
		return true;
	}
}