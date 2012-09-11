package lengthdetection;
import java.util.ArrayList;


public class AnomalyBasedIDS {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HttpURILengthNormal handleFn = new HttpURILengthNormal();
		HttpURILengthNotNormal handleNotNormalFn = new HttpURILengthNotNormal();
		MiscCalculation handleExtraCalc = new MiscCalculation();
		
		ArrayList <String> getLengthArray = new ArrayList <String>();
		ArrayList <String> getDataNotNormal = new ArrayList <String>();
		
		System.out.println("HTTP URI Length Anomaly Detection Statistics "); 
			handleFn.openFile(getLengthArray);
			handleFn.convertFromStringToDouble(getLengthArray);
		System.out.println("Calculate data for normal values");
		System.out.println("Packet Count: "+ handleFn.PacketCount() + "\tSum: "+ handleFn.SumVal() 
				+ "\tMean: "+ handleFn.MeanVal() + "\nVariance: "+ handleFn.VarianceVal()+ 
				"\tStandard Deviation: "+ handleFn.StanDevVal());
	
		//System.out.println("Elements of AS: "+ handleFn.getGreaterThanAvg());
		
		System.out.println("\nComputing for Anomaly Score....");
			handleFn.computeAnomalyScore();
		
		System.out.println("No of Anomaly Score Greater than 1.5 is: "+ handleFn.getNumAnomalies());
		//Display AS table
		//handleFn.displayAnomalies();
		
		System.out.println("\nCreating Anomaly Score File for Normal data...");
			handleFn.writeFile();
		System.out.println("Done!");
		
		System.out.println("Detection.....");
			handleExtraCalc.fixedThreshold(); // set threshold
			handleExtraCalc.detectVal(handleFn.getGreaterThanAvg());// detect HTTP URI with AS greater than (1.5 + threshold) for Normal Data
			handleExtraCalc.displayDetectedAttacks(); // display results
			handleExtraCalc.writeDetectedAttacks();//Save detected Attacks in text file
			handleExtraCalc.clearArray();
		
//--------------------Calculate for Non-Normal Data-------------------
		System.out.println("\n\nCalculate statistics for Non-Normal Data");
		handleNotNormalFn.openFile(getDataNotNormal);
		handleNotNormalFn.convertFromStringToDouble(getDataNotNormal);
		System.out.println("Calculate data for non-normal values");
		System.out.println("Packet Count: "+ handleNotNormalFn.getTotalLen() + "\tSum: "+ handleNotNormalFn.setSumOfLengths() 
				+ "\tMean: "+ handleNotNormalFn.setMeanNotNormal() + "\nVariance: "+ handleNotNormalFn.setVarianceNotNormal()+ 
				"\tStandard Deviation: "+ handleNotNormalFn.setStdDevNotNormal());
		
		System.out.println("\nComputing for Anomaly Score....");
			handleNotNormalFn.computeAnomalyScore();
		System.out.println("Found "+ handleNotNormalFn.getNumAnomalies()+ " lengths with AS score greater than 1.5");
		
		System.out.println("\nCreating Anomaly Score File for Non-Normal data...");
			handleNotNormalFn.writeFile();
			
//----------------Do Class MiscCalculation-------------------------		
		handleExtraCalc.clearArray();
			handleExtraCalc.detectVal(handleNotNormalFn.getGreaterThanAvg());
			handleExtraCalc.displayDetectedAttacks();
			handleExtraCalc.writeDetectedAttacks();
	
		
		System.out.println("\nDone!");
	}

}
