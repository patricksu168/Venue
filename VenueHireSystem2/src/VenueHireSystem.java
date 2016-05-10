import java.util.*;
import java.io.*;
/**
 * 
 * @author Patrick Su
 * Student Number: z5060527
 * This program is written to simulate a venue hire
 * system which does reservation, changing reservation
 * and cancelling reservation with venue rooms
 *
 */
public class VenueHireSystem {
	
	public VenueHireSystem (){
		
	}
	/**
	 * 
	 * The main method provides implementation of operating the system
	 * that does all the work on the requests
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {		 
		List<Venue>a = new ArrayList<Venue>();
		List<Request>b = new ArrayList<Request>();
		String months[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
				 		   "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
		BufferedReader br = null;
		Scanner sc = null;
		String line, type;
		VenueHireSystem vhs = new VenueHireSystem();
		try{
			br = new BufferedReader(new FileReader(args[0]));
			while ((line = br.readLine()) != null) {
				sc = new Scanner(line);
				type = sc.next();
		        switch(type){
		        	case "Venue" :
		        		vhs.createVenue(sc, a);
		        		break;
		        	case "Request" :
		        		vhs.modifyRequest(months, sc, a, b, 1, vhs);
		        		break;
		        	case "Change" :
		        		vhs.modifyRequest(months, sc, a, b, 0, vhs);
		        		break;
		        	case "Cancel" :
		        		vhs.cancelRequest(a, b, sc);
		        		break;
		        	case "Print" :
		        		String venueType = sc.next();
		        		int i;
		        		for (i=0; i<a.size(); i++){
		        			if (venueType.equals(a.get(i).getType())){
		        				a.get(i).printOccupancy(months);
		        			}
		        		}
		        		break;
		        	default :
		        		System.out.println("Invalid line");
		        }
		    }
		}
		
		catch (FileNotFoundException e){
			System.out.println("Could not find file");
		}
		
		finally{
			if(sc != null) sc.close();
			if(br != null) br.close();
			}
		}
	/**
	 * 
	 * This method reads information on venues and store
	 * as elements in a list kept by the system
	 * @param sc
	 * @param a
	 * @precondition a valid venue element
	 */
	public void createVenue(Scanner sc, List<Venue>a){
		String venueType, name, roomSize;
		venueType = sc.next();
		name = sc.next();
		roomSize = sc.next();
		a.add(new Venue(venueType, name, roomSize));
	}
	
	/**
	 * This method creates and change reservation
	 * @param months
	 * @param sc
	 * @param a
	 * @param b
	 * @param d
	 * @param vhs
	 * @precondition No invalid dates, two dates are in the same year
	 */
	public void modifyRequest (String months[], Scanner sc, List<Venue>a, List<Request>b, int d,
							   VenueHireSystem vhs){
		int id, day1, day2, i, num;
		int large = 0, small = 0, medium = 0, roomNum1 = 0, roomNum2 = 0, roomNum3 = 0;
		String month1, month2, roomSize;
		List<Integer>c = new ArrayList<Integer>();
		id = sc.nextInt();
		month1 = sc.next();
		day1 = sc.nextInt();
		month2 = sc.next();
		day2 = sc.nextInt();
		while (sc.hasNext()){
			num = sc.nextInt();
			roomSize = sc.next();
			switch(roomSize){
				case "large": roomNum1 = num;
							  break;
				case "medium": roomNum2 = num;
							   break;
				case "small": roomNum3 = num;
							  break;
			}
		}
		
		int count = 0, counter = 0;
		while (!month1.equals(months[count])) count++;
		while (!month2.equals(months[counter])) counter++;
		GregorianCalendar date1 = new GregorianCalendar(2016, ++count, day1);
		GregorianCalendar date2 = new GregorianCalendar(2016, ++counter, day2);
		GregorianCalendar date3 = null, date4 = null;
		List<Integer> e = null;
		boolean change = false;
		if (d == 0){
			i = 0;
			for(i=0; i<b.size() && id != b.get(i).getID(); i++);
			if (i< b.size()){
				date3 = b.get(i).getDate1();
				date4 = b.get(i).getDate2();
				e = b.get(i).getIntegerList();
				for (i = 0; i < e.size(); i++){
					a.get(e.get(i)).changeState(date3, date4);
				}
				change = true;
			}
		}
		for (i = 0; i < a.size(); i++){
			if (a.get(i).checkVacancy(date1, date2, "large") && large != roomNum1){
				large++;
				c.add(i);
			}
			if (a.get(i).checkVacancy(date1, date2, "small") && small != roomNum3){
				small++;
				c.add(i);
			}
			if (a.get(i).checkVacancy(date1, date2, "medium") && medium != roomNum2){
				medium++;
				c.add(i);
			}
		}
		boolean h = false;
		
		if (large == roomNum1 && small == roomNum3 && medium == roomNum2){
			String type = a.get(c.get(0)).getType();
			h = true;
			for (i=1; i < c.size(); i++){
				if (!type.equals(a.get(c.get(i)).getType())){
					h = false;
				}
			}
		}
		if (h == true){
			if (d == 1){
				b.add(new Request(id, date1, date2, c));
				for (i=0; i < c.size(); i++){
					a.get(c.get(i)).changeState(date1, date2);
				}
				vhs.printOutput("Reservation", id, date1, date2, c, a);
			}else if (d ==0 && change == true){
				for (i=0; i < c.size(); i++){
					a.get(c.get(i)).changeState(date1, date2);
				}
				for(i=0; i<b.size() && id != b.get(i).getID(); i++);
				b.get(i).changeRequest(date1, date2, c);
				vhs.printOutput("Change", id, date1, date2, c, a);
			}
		}else{
			if (d == 0 && change == true){
				for (i = 0; i < e.size(); i++){
					a.get(e.get(i)).changeState(date3, date4);
				}
			}
			if (d == 1){
				System.out.println("Request rejected");
			}else{
				System.out.println("Change rejected");
			}
		}
		
	}
	
	/**
	 * This method cancel reservations
	 * @param a
	 * @param b
	 * @param sc
	 * @precondition cancel exiting reservation id
	 */
	public void cancelRequest(List<Venue>a, List<Request>b, Scanner sc){
		int id = sc.nextInt();
		int i;
		for(i=0; i < b.size() && id != b.get(i).getID(); i++);
		if (i < b.size()){
			GregorianCalendar date1 = b.get(i).getDate1();
			GregorianCalendar date2 = b.get(i).getDate2();
			List<Integer>c = b.get(i).getIntegerList();
			for (i=0; i<c.size();i++){
				a.get(c.get(i)).changeState(date1, date2);
			}
			for(i=0; i<b.size() && id != b.get(i).getID(); i++);
			b.remove(i);
			System.out.println("Cancel " + id);
		}else{
			System.out.println("Cancel rejected");
		}
	}
	
	/**
	 * This method prints out reservation in order of room declaration
	 * @param type
	 * @param id
	 * @param date1
	 * @param date2
	 * @param c
	 * @param d
	 */
	public void printOutput(String type, int id, GregorianCalendar date1, GregorianCalendar date2,
							List<Integer>c, List<Venue>d){
		String venueType = d.get(c.get(0)).getType();
		System.out.print(type + " " + id + " " + venueType);
		int i;
		for (i=0; i<c.size(); i++){
			System.out.print(" "  + d.get(c.get(i)).getName());
		}
		System.out.println("");
	}
	
}

