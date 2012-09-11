package lengthdetection;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MiscCalculation {

HttpURILengthNormal useMe = new HttpURILengthNormal();

private static double thresholdVal;
private static ArrayList<Double> detectedAttacks = new ArrayList<Double>();

private static List<ArrayList<Double>> attackURI = new ArrayList<ArrayList<Double>>();


/*
 * sets threshold value which is 1.5 multiplied by base 5% of its value
 * The threshold is used in order to tagged Lengths as not normal and are
 * considered attacks or an anomaly.
 * */
public double fixedThreshold(){
		double x = 1.5 * .05;
		
		thresholdVal = x + 1.5;
		
		return thresholdVal;
}

/*
 * Detects HTTP URI Lengths with AS greater than 1.5 + threshold value
 * current threshold value is set to .05. These detected Lengths are
 * stored in detectedAttacks array
 * */
public ArrayList<Double> detectVal(ArrayList <Double> valuesASGreaterAvg){
	
		attackURI.add(valuesASGreaterAvg);
		
		for (ArrayList<Double> entries: attackURI){ //iterate first dimension of List<ArrayList<Double>
			for (Double getVal: entries){//iterate 2nd loop of List<ArrayList<Double>
				
				if (getVal>fixedThreshold()){ //AS greater than threshold will be stored in detected attacks
					detectedAttacks.add(getVal);
				}
			}
		}
			return detectedAttacks;	
	}

//Use Method to clear previous values in the ArrayList detectedAttacks
//Method can be expanded to clear any ArrayList or Maps in the future 
//which is today but not yesterday and not currently :D
public void clearArray(){
	detectedAttacks.clear();
	System.out.println("\nCleared Array: "+ detectedAttacks);//debugging purposes
}


public void displayDetectedAttacks(){
	System.out.println("HTTP URI with lengths greater than threshold: "+ detectedAttacks);
}

public void writeDetectedAttacks(){
	Scanner getFileName = new Scanner(System.in);
	System.out.println("\nEnter File Name copy this format /home/dimaz/Documents/IDSTextFiles/Length/Test1.txt "
	+ "do not enclose in double quotes and use double slash after each folder\n"+ "Enter here: ");
	
	String getInp = getFileName.nextLine();
	try{
		//FileWriter theFile = new FileWriter(getInp, false);
		BufferedWriter readBufAtk = new BufferedWriter (new FileWriter(getInp)); //new FileWriter (getInp)
		try{
			String convDoubleToString;
			
			
			System.out.println("Detected Attacks: "+ detectedAttacks);
			for (Double getVal: detectedAttacks){
				convDoubleToString = getVal.toString();
				
				readBufAtk.write(convDoubleToString);
				readBufAtk.newLine();
			}
			System.out.println("\nDone saving attacks detected....");
			detectedAttacks.clear();
			//System.out.println("\nDetected Attacks after saving: "+ detectedAttacks);
		}
		
		finally{
			readBufAtk.close();
		}
		
	}
	
	catch (IOException e){
		System.out.println("Ahoooyyy ... error mate!!!"+e);
	}
	
}

}