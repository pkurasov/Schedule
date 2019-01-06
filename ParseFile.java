package word;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class ParseFile {
	
	public static List<String> pertinentData = new ArrayList<String>();
	public static List<String> floor = new ArrayList<String>();
	public static Schedule dailyShedule = new Schedule();
	public static int dailyCount;
	public static int numIn;
	public static String mdPath;
	
	//static public void main(String[] args) {
		
	//	readFile();
	//	fourFloorMd();
	//}
	public static int getDailyCount() {
		return dailyCount;
	}
	public static int getNumIn() {
		return dailyShedule.getInOrOut().get("IN");
	}
	public static int getNumPeds() {
		return dailyShedule.getNumPeds();
	}
	public static int getNumEnt() {
		return dailyShedule.getNumEnt();
	}
	
	public static Map<Integer,Integer> hourBreak(){
		return dailyShedule.getSTime();
	}
	public static void setMdPath(String p) {
		mdPath = p;
	}
	
	public static void readFile(){
	//	List<String> pertinentData = new ArrayList<String>();
		//List<String> floor = new ArrayList<String>();
		//Shedule dailyShedule = new Shedule();
		Scanner lineBreaker=null;
		try {
			BufferedReader infile = new BufferedReader(new FileReader(mdPath));
			//"/home/pavel/Desktop/Past Programming Classes/Java/mdReport.txt"
			String buffer = null;
			int counter=0;
			while((buffer = infile.readLine()) != null){
				lineBreaker = new Scanner(buffer);
				//String floor;
				String mFloor;
				// counter
				
				if (lineBreaker.findInLine("TOTAL PATIENTS FOR")==null&&
						lineBreaker.findInLine("OPERATING ROOM")!=null)
				{
					mFloor = (buffer.substring(buffer.indexOf("ROOM")+4,(buffer.indexOf("ROOM")+4)+15)).trim();
					floor.add(mFloor);
					counter++;
					System.out.println(floor);
				}else{
					if(lineBreaker.hasNextInt()&&!buffer.contains("HOLD PROCEDURE")){
						pertinentData.add(buffer+ floor.get(counter-1));
						System.out.println(floor);
					}
				}
				
				lineBreaker.close();
				
			}
			
			infile.close();
			
}		catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		  catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
			
		try {
			// Set patient location and remove a line from pertinentData
			// if line contains "IN" (inpatient)
				//get an Iterator object for ArrayList using iterator() method.
				Iterator<String> itr = pertinentData.iterator();
				while(itr.hasNext()){
					String ofIntrest = itr.next();
					//System.out.println(ofIntrest);
					lineBreaker = new Scanner(ofIntrest);
				// set Inpatient Vs Ambulatory
					String ptLocation;
					
					ptLocation=lineBreaker.findInLine("SDC | IN | REF");
					
				dailyShedule.setInOut(ptLocation.trim());
				// Set patient's age
				// 33 is an offset for capturing age
				String rawAge = ofIntrest.substring(33, ofIntrest.indexOf("/")).trim();
				String age = rawAge.length()>3 ? "1" : rawAge;
				dailyShedule.setNumPeds(Integer.parseInt(age));
				if(ptLocation.trim().equals("IN")|| Integer.parseInt(age)<18){
					itr.remove();
				}
			}
				
			
			// Set remaining Schedule fields
			for(String ofIntrest:pertinentData){
				lineBreaker = new Scanner(ofIntrest);
				// set surgery time
				int surgeryTime = lineBreaker.nextInt()/100;
				dailyShedule.setStime(surgeryTime);

				// set MD name 123 if an offset for patient name
				String md = ofIntrest.substring(123, ofIntrest.indexOf(",", 123));
				dailyShedule.setDoc(md);
				
			}
			
			// set MD type
			dailyShedule.setType();
			// set number of ent by passing mdType map 
			dailyShedule.setNumEnt(dailyShedule.getMdType());
			// return number of ENT
			System.out.println("Number of ENT: "+dailyShedule.getNumEnt());
			// Return number of pediatrics
			System.out.println("Number of Pediatrics: " + dailyShedule.getNumPeds());
			// Surgical time breakdown
			System.out.println("Breakdown by surgical time: \n"+dailyShedule.getSTime());
			// geting Total count
			int allSurg = dailyShedule.getInOrOut().get("SDC");
			int allAmbulatory = 0;
			int allFifthFloor = 0;
			if(dailyShedule.getInOrOut().containsKey("REF")){
				 allAmbulatory = allSurg+dailyShedule.getInOrOut().get("REF");
				 allFifthFloor = allAmbulatory - (dailyShedule.getNumEnt()+dailyShedule.getNumPeds());
				 dailyCount = allFifthFloor;
				 System.out.println("Total Daily Count: \t" + allFifthFloor);
				
			}else{
				allFifthFloor = allSurg - (dailyShedule.getNumEnt()+dailyShedule.getNumPeds());
				dailyCount = allFifthFloor;
				System.out.println("Total Daily Count: \t" + allFifthFloor);
				
			}
			
		}catch (Exception e) {
			
			e.printStackTrace();
		}
		 
		
	}
	
		
	
	
	
	
	public static Map<String, Integer> fourFloorMd(){
		
		Scanner lineBreaker;
		
		try { 
		
			// Set remaining Schedule fields
			dailyShedule.doctor.clear();
			for(String ofIntrest:pertinentData){
				lineBreaker = new Scanner(ofIntrest);
				if(ofIntrest.endsWith("A") || ofIntrest.endsWith("B") || ofIntrest.endsWith("C") ||
						ofIntrest.endsWith("D") || ofIntrest.endsWith("E") || 
						ofIntrest.endsWith("F") || ofIntrest.endsWith("G")){
					String md = ofIntrest.substring(123, ofIntrest.indexOf(",", 123));
					
					dailyShedule.setDoc(md);
					
				}
			}
			/**
			System.out.println("Inpatient: " + dailyShedule.getInOrOut().get("IN"));
			System.out.println("4th floor MD's");
			System.out.println(dailyShedule.getDoc());
			**/
			//return dailyShedule.getDoc();
		}catch (Exception e) {
			
			e.printStackTrace();
		}
		  
		
		return dailyShedule.getDoc();
	}
}
	
	
