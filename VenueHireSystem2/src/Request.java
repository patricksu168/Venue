import java.util.GregorianCalendar;
import java.util.List;

/**
 * 
 * Request class stores information of a reservation
 *
 */
public class Request {
	private int id;
	private GregorianCalendar date1;
	private GregorianCalendar date2;
	private List<Integer> b; 
	
	/**
	 * Class constructor
	 * @param id
	 * @param date1
	 * @param date2
	 * @param b
	 */
	public Request(int id, GregorianCalendar date1, GregorianCalendar date2, List<Integer> b){
		this.id = id;
		this.date1 = date1;
		this.date2 = date2;
		this.b = b;
	}
	
	/**
	 * Update the reservation dates of this request
	 * as change request succeeds
	 * @param date1
	 * @param date2
	 * @param b
	 */
	public void changeRequest(GregorianCalendar date1, GregorianCalendar date2, List<Integer> b){
		this.date1 = date1;
		this.date2 = date2;
		this.b = b;
	}
	
	/**
	 * 
	 * @return id of reservation
	 */
	public int getID(){
		return id;
	}
	
	/**
	 * 
	 * @return list of venues of the reservation	
	 */
	public List<Integer> getIntegerList(){
		return b;
	}
	
	/**
	 * 
	 * @return starting date of reservation
	 */
	public GregorianCalendar getDate1(){
		GregorianCalendar tmp = new GregorianCalendar (2016, date1.get(GregorianCalendar.MONTH),
									date1.get(GregorianCalendar.DAY_OF_MONTH));
		return tmp;
	}
	/**
	 * 
	 * @return end date of reservation
	 */
	public GregorianCalendar getDate2(){
		GregorianCalendar tmp = new GregorianCalendar (2016, date2.get(GregorianCalendar.MONTH),
									date2.get(GregorianCalendar.DAY_OF_MONTH));
		return tmp;
	}
}
