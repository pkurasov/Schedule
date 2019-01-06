package word;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.net.URL;
import org.apache.poi.xwpf.usermodel.*;

import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.*;

public class DocGen {
	private Button buttonA;	
	private Stage window;
	private static String message;
	private ParseFile sData;
	
	public void makeDoc() {
		try{
			//Bring in Parse file
			   sData = new ParseFile();
			   sData.readFile();
		      //Blank Document
		      XWPFDocument document = new XWPFDocument();
		        
		      //Write the Document in file system
		      FileOutputStream out = new FileOutputStream(new File("create_table.docx"));
		       
		      //create paragraph
		      XWPFParagraph paragraph = document.createParagraph();
		      paragraph.setAlignment(ParagraphAlignment.CENTER);
		     // code for today's date
		      SimpleDateFormat formatter = new SimpleDateFormat("EEEEE MMMMM yyyy");
		      Date date = new Date();
		      //format title
		      XWPFRun title = paragraph.createRun();
		      title.setBold(true);
		      title.setFontSize(20);
		      title.setText(formatter.format(date)+'\t'+'\t'+"C = "+ sData.getDailyCount());
		      title.addBreak();
		      
		    //create table
		            
		          XWPFTable tab = document.createTable(20,3);
		         
		          tab.getRow(0).getCell(0).setText("FULL TIME STAFF");
		          tab.getRow(0).getCell(1).setText("SCHEDULED TIME");
		          tab.getRow(0).getCell(2).setText("WORKSTATION");
		          
		          document.createParagraph().createRun().addBreak();
		          
		          XWPFTable tab1 = document.createTable(1,3);
		          XWPFTableRow row1 = tab.getRow(0);
		          tab1.getRow(0).getCell(0).setText("PED = "+ sData.getNumPeds());
		          tab1.getRow(0).getCell(1).setText("ENT = " + sData.getNumEnt());
		          tab1.getRow(0).getCell(2).setText("IN = "+sData.getNumIn());
		          
		          document.createParagraph().createRun().addBreak();
		          
		          // determine table size
		          int numRows;
		          if(sData.hourBreak().size() >= sData.fourFloorMd().size())
		        	  numRows = sData.hourBreak().size();
		          else
		        	  numRows = sData.fourFloorMd().size();
		          System.out.println(numRows);
		          
		          XWPFTable tableOne = document.createTable(numRows+1,5);
		          int i = 1;
		          XWPFTableRow tableOneRow = tableOne.getRow(0);
		          tableOne.getRow(0).getCell(0).setText("4 FL MD NAME");
		    	  tableOne.getRow(0).getCell(1).setText("NUMBER");
		    	  tableOne.getRow(0).getCell(3).setText("HOUR");
		    	  tableOne.getRow(0).getCell(4).setText("NUMBER");
		    	 int j = 1;
		          for (Map.Entry<Integer,Integer> entry : sData.hourBreak().entrySet()) {
		        	  
		        	  tableOneRow = tableOne.getRow(j);
		        	  tableOne.getRow(j).getCell(3).setText(entry.getKey().toString());
		        	  tableOne.getRow(j).getCell(4).setText(entry.getValue().toString());
		        	  j++;
		          }
		    	  
		    
		    	  for (Map.Entry<String,Integer> entry : sData.fourFloorMd().entrySet()) {
		        	  tableOneRow = tableOne.getRow(i);
		        	  tableOne.getRow(i).getCell(0).setText(entry.getKey());
		        	  tableOne.getRow(i).getCell(1).setText(entry.getValue().toString());
		        	  i++;
		          }
		    
		         
		         
		          
		      
		          
		      document.write(out);
		      out.close();
		      message = "create_table.docx written successully!";
		      System.out.println("create_table.docx written successully");
			AlertBox.display("Congratulations!", message);
			}catch (Exception ex) {
				message = ex.toString();
				AlertBox.display("Dam it man!", message);
				ex.printStackTrace();
			}
	}
}
