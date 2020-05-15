import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


/**
 *	A sink
 *	@author Joel Karel
 *	@version %I%, %G%
 */
public class Sink implements ProductAcceptor
{
	/** All products are kept */
	private ArrayList<Product> products;
	/** All properties of products are kept */
	private ArrayList<Integer> numbers;
	private ArrayList<Double> times;
	private ArrayList<String> events;
	private ArrayList<String> stations;
	/** Counter to number products */
	private int number;
	/** Name of the sink */
	private String name;
	
	/**
	*	Constructor, creates objects
	*/
	public Sink(String n)
	{
		name = n;
		products = new ArrayList<>();
		numbers = new ArrayList<>();
		times = new ArrayList<>();
		events = new ArrayList<>();
		stations = new ArrayList<>();
		number = 0;
	}
	
        @Override
	public boolean giveProduct(Product p)
	{
		number++;
		products.add(p);
		// store stamps
		ArrayList<Double> t = p.getTimes();
		ArrayList<String> e = p.getEvents();
		ArrayList<String> s = p.getStations();
		for(int i=0;i<t.size();i++)
		{
			numbers.add(number);
			times.add(t.get(i));
			events.add(e.get(i));
			stations.add(s.get(i));
		}
		return true;
	}
	
	public int[] getNumbers()
	{
		numbers.trimToSize();
		int[] tmp = new int[numbers.size()];
		for (int i=0; i < numbers.size(); i++)
		{
			tmp[i] = (numbers.get(i)).intValue();
		}
		return tmp;
	}

	public double[] getTimes()
	{
		times.trimToSize();
		double[] tmp = new double[times.size()];
		for (int i=0; i < times.size(); i++)
		{
			tmp[i] = (times.get(i)).doubleValue();
		}
		return tmp;
	}

	public String[] getEvents()
	{
		String[] tmp = new String[events.size()];
		tmp = events.toArray(tmp);
		return tmp;
	}

	public String[] getStations()
	{
		String[] tmp = new String[stations.size()];
		tmp = stations.toArray(tmp);
		return tmp;
	}
	public void writeToFile(String filename)
	{
		FileWriter fw = null;

		try {
			fw = new FileWriter(filename);
			for (int i = 0; i < products.size(); i++) {
				fw.append(String.valueOf(numbers.get(i)));
				fw.append(",");
				fw.append(String.valueOf(events.get(i)));
				fw.append(",");
				fw.append(String.valueOf(times.get(i)));
				fw.append(",");
				fw.append(String.valueOf(stations.get(i)));
				fw.append("\n");
			}
		}
		catch (Exception e) {
			System.out.println("Error in CSV writes");
			e.printStackTrace();
		} finally {

			try {
				fw.flush();
				fw.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter");
				e.printStackTrace();
			}

		}

	}

}