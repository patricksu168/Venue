import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 
 * Venue class stores information of the venue
 * and also stores a list of reservation date
 * so that the availability of venues can be determined
 *
 */
public class Venue {
	private String type;
	private String name;
	private String roomSize;
	private List<GregorianCalendar> date;
	
	/**
	 * Class constructor
	 * @param type
	 * @param name
	 * @param roomSize
	 */
	public Venue (String type, String name, String roomSize){
		this.type = type;
		this.name = name;
		this.roomSize = roomSize;
		this.date = new ArrayList<GregorianCalendar>();
	}
	
	/**
	 * This method updates on the reservation dates as reservation objects
	 * are updated
	 * @param date
	 * @param date2
	 */
	public void changeState(GregorianCalendar date, GregorianCalendar date2){
		int i;
		boolean b = false;
		for (i = 0; i < this.date.size(); i+=2){
			if (this.date.get(i).equals(date) && this.date.get(i+1).equals(date2)){
				this.date.remove(i+1);
				this.date.remove(i);
				b = true;
			}
		}
		//System.out.println("Size of date is now " + this.date.size());
		if (b == false){
			if (this.date.size() > 1){
				int dateSize = this.date.size();
				for (i=0; (i < this.date.size()) && date.after(this.date.get(i)); i+=2);
			}
			this.date.add(i,date2);
			this.date.add(i,date);
		}
	}
	
	/**
	 * This method checks availability of this venue
	 * @param date
	 * @param date2
	 * @param size
	 * @return
	 */
	public boolean checkVacancy(GregorianCalendar date, GregorianCalendar date2,
						      String size){
		int i = 0;
		if (this.roomSize.equals(size)){
			if (this.date.size() == 0){
				return true;
			}
			while (i < this.date.size()){
				if (date.before(this.date.get(i)) && date2.before(this.date.get(i))){
				}else if (date.after(this.date.get(i+1)) && date2.after(this.date.get(i+1))){
				}else return false;
				i+=2;
			}
			return true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * @return venueType
	 */
	public String getType(){
		return type;
	}
	
	/**
	 * 
	 * @return venue name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 
	 * @return list of reservation date
	 */
	public List<GregorianCalendar> getListOfDate(){
		List<GregorianCalendar> a = new ArrayList<GregorianCalendar>();
		int i;
		for (i=0; i < date.size(); i++){
			int month = date.get(i).get(GregorianCalendar.MONTH);
			int day = date.get(i).get(GregorianCalendar.DAY_OF_MONTH);			
			a.add(new GregorianCalendar(2016, month, day));
		}
		return a;
	}
	
	/**
	 * This prints out reservation status of this venue
	 * @param months
	 */
	public void printOccupancy(String[] months){
		System.out.print(type + " " + name + " ");
		int i, day, day2, month;
		String month2; 
		for (i=0; i<date.size(); i+=2){
			day = date.get(i).get(GregorianCalendar.DAY_OF_MONTH);
			day2 = date.get(i+1).get(GregorianCalendar.DAY_OF_MONTH);
			month = date.get(i+1).get(GregorianCalendar.MONTH);
			month2 = months[month-1];
			System.out.print(month2 + " " + day + " " + (day2-day+1) + " ");
		}
		System.out.println("");
	}
}
