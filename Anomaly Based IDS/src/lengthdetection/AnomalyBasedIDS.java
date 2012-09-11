package lengthdetection;

public class AnomalyBasedIDS {

	public static void main(String[] args) {
		System.out.println("HTTP URI Length Anomaly Detection "); 
		fileManipulate normal = new fileManipulate();
		
		System.out.println("Processing normal uri...");
		normal.openFile(); //  /home/dimaz/Documents/IDSTextFiles/SaturdaySmallLen.txt
		normal.convertFromStringToDouble();
		
		System.out.println("Calculate data for normal values");
		System.out.println("Packet Count: "+ normal.getPacketCount() + "\tSum: "+ normal.getSumVal() 
				+ "\tMean: "+ normal.getMeanVal() + "\nVariance: "+ normal.getVarianceVal()+ 
				"\tStandard Deviation: "+ normal.getStanDevVal());

		
		System.out.println("\nComputing for Anomaly Score....");
		normal.computeAnomalyScore();
		
		System.out.println("No of Anomaly Score Greater than 1.5 is: "+ normal.getNumAnomalies());
		//Display AS table
		normal.displayAnomalies();
		
		System.out.println("\nCreating Anomaly Score File for Normal data...");
		normal.writeFile(); // /home/dimaz/Documents/IDSTextFiles/Length/SatTestMod1.txt
		System.out.println("Done!");
		
		System.out.println("Detection.....");
		detectedAttacks normalDetection = new detectedAttacks();
		
		normalDetection.fixedThreshold(); // set threshold
		 
		normalDetection.detectVal();// detect HTTP URI with AS greater than (1.5 + threshold) for Normal Data
		normalDetection.displayDetectedAttacks(); // display results
		
		
		normalDetection.writeDetectedAttacks();//Save detected Attacks in text file /home/dimaz/Documents/IDSTextFiles/Length/AfterMathMod1.txt
		
		
		System.out.println("\nDone!");
	}

}

/*
 * HttpURILengthNormal handleFn = new HttpURILengthNormal();
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
		handleFn.displayAnomalies();
		
		System.out.println("\nCreating Anomaly Score File for Normal data...");
			handleFn.writeFile();
		System.out.println("Done!");
		
		System.out.println("Detection.....");
			handleExtraCalc.fixedThreshold(); // set threshold
			handleExtraCalc.detectVal(handleFn.getGreaterThanAvg());// detect HTTP URI with AS greater than (1.5 + threshold) for Normal Data
			handleExtraCalc.displayDetectedAttacks(); // display results
			
			System.out.println("\nAttack URI for Normal:" + handleExtraCalc.getAttackURI());
			//handleExtraCalc.writeDetectedAttacks();//Save detected Attacks in text file
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
			System.out.println("\nAttack URI for not normal:" + handleExtraCalc.getAttackURI());
			//handleExtraCalc.writeDetectedAttacks();
	
		
		System.out.println("\nDone!");
 * */
