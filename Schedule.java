package word;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
/**
 * 
 * @author PavelK
 * This class gets data from "ParseFile" class and maps the data to its occurences
 * Used TreeMaps to take advantage of traversal used that returns everything in 
 * alphabetical order.
 * It also checks all MD's against EYE surgeons to separate ENT's
 */
public class Schedule {
	// This map instance variable holds MD names as String and its occurence in file
	// as an Integer.
	public Map<String,Integer> doctor=new TreeMap<String,Integer>();
	// This map instance variable holds surgical times as Integer and its 
	// occurence in file as an Integer
	private Map<Integer,Integer> surgicalTime=new TreeMap<Integer,Integer>();
	// This map instance variable maps MD name to its type EYE or ENT
	private Map<String,String>typeOfDoctor=new TreeMap<String,String>();
	// Instance variable that holds number of pediatric cases
	private int numPeds=0;
	// This map instance variable maps ambulatory(SDC), inpatient(IN) or in Office(REF)
	// to its occurences in file.
	private Map<String,Integer> inOut=new TreeMap<String,Integer>();
	// Instance variable that holds number of ENT patients
	private int numEnt;
	// This instance variable array holds the names of Eye MD's(to be expanded)
	private static String entPath;
	
	public static void setEntPath(String p) {
		entPath = p;
	}
	
	/**
	 * This method checks "doctor" instance variable map for the occurence of 
	 * MD name. If no such MD name, local variable int variable "count" is set 
	 * to 0. If it is present "count" is set to a value parameter of "doctor" map
	 * Then MD name and "count incremented by 1" gets inserted into a map.
	 * 
	 * @param mdName		Accepts String mdName.
	 */
	public void setDoc(String mdName){
		
		int count = doctor.containsKey(mdName) ? doctor.get(mdName) : 0;
		doctor.put(mdName, count + 1);
	}
	/**
	 * This method checks "surgicalTime" instance variable map for the occurence of 
	 * surgical time. If no such surgical time, local variable int variable "count" is set 
	 * to 0. If it is present "count" is set to a value parameter of "surgicalTime" map
	 * Then surgical time and "count incremented by 1" gets inserted into a map.
	 * 
	 * @param surgTime        Accepts Integer surgTime
	 */
	
	public void setStime(Integer surgTime){
		Integer count = surgicalTime.containsKey(surgTime) ? surgicalTime.get(surgTime) : 0;
		surgicalTime.put(surgTime, count + 1);
	}
	
	/**
	 * This method maps MD to its type EYE or ENT
	 * It instantiates local Set reference "doctorSet" 
	 * and assigns it new TreeSet object initialized with
	 * "doctor"'s map method keySet(). In a forEach loop each
	 * "surgeon" in a "doctorSet" is added to the map 
	 * "typeOfDoctor" as ENT. In a next forEach loop each "eyeSurgeon"
	 * appended with 2 white spaces before and after (thanks Widget)
	 * "eyeSurgWithBlanks" from instance array variable "eyeMd" gets
	 * checked against all MD's. If "doctorSet" contains "eyeSurgWithBlanks"
	 * "typeOfDoctor" map method replace replaces an old value with new.
	 * 
	 */
	public void setType(){
		typeOfDoctor = new TreeMap();
		//Set<String> doctorSet=new TreeSet<String>(doctor.keySet());
		for(String i:doctor.keySet()) {
			typeOfDoctor.put(i,"EYE");
		}
		try {
			 BufferedReader infileEnt = new BufferedReader(new FileReader("/home/pavel/Desktop/WorkProject/ENT MD.txt"));
			 //"/home/pavel/Desktop/WorkProject/ENT MD.txt"
			 String bufferEnt = null;
			 
			 while((bufferEnt = infileEnt.readLine()) != null){
				// if(doctorSet.contains(bufferEnt)){
				 if(typeOfDoctor.containsKey(bufferEnt)){
					 //System.out.println("true");
					 typeOfDoctor.put(bufferEnt, "ENT");
				 }
			 }
			 
			 System.out.println(typeOfDoctor);
			 infileEnt.close();
			 
			 
		 }catch (FileNotFoundException e) {
				
				e.printStackTrace();
			}catch (IOException e) {
				
				e.printStackTrace();
			}
		
	}
		
	
	
	/**
	 * This method counts number of ENT's. 
	 * Local int variable count is set to 0;  
	 * For each Entry "entry" returned by "myType"'s method entrySet()
	 * if value of entry is ENT
	 * increment count by passing "entry"'s getKey() method as a parameter to
	 * Map "doctor"'s get() method
	 * Set numEnt equals to count
	 * 
	 * @param myType      Accepts Map<String,String> myType
	 */
	public void setNumEnt(Map<String,String> myType){
		int count=0;
			for(Map.Entry<String,String> entry : myType.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				if(value.equals("ENT")){
					count+=doctor.get(key);
				}
			}
			numEnt=count;
		}
	
		
	
	/**
	 * This method checks if the age is under 18
	 * if so increments "numPeds"
	 * 
	 * @param age     Accepts int age
	 */
	public void setNumPeds(int age){
		
		if(age<18){
			numPeds++;
		}
		
	}
	/**
	 * This method checks "inOut" instance variable map for the occurence of 
	 * ambulatory(SDC), inpatient(IN) or in Office(REF). If no such occurence, 
	 * local variable int variable "count" is set to 0. If it is present "count" 
	 * is set to a value parameter of "inOut" map. Then above occurences and 
	 * "count incremented by 1" gets inserted into a map.
	 * 
	 * @param typePt              Accepts String typePt
	 */
	public void setInOut(String typePt){
		
		int count = inOut.containsKey(typePt) ? inOut.get(typePt) : 0;
		inOut.put(typePt, count + 1);
	}
	
	// Getter methods
	public Map<String,Integer> getDoc(){
		return doctor;
	}
	public Map<Integer,Integer> getSTime(){
		return surgicalTime;
	}
	public Map<String,String> getMdType(){
		return typeOfDoctor;
	}
	public int getNumPeds(){
		return numPeds;
	}
	public Map<String,Integer> getInOrOut(){
		return inOut;
	}
	public int getNumEnt(){
		return numEnt;
	}
	
}