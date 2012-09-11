package lengthdetection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;


public class HttpURILengthNotNormal {
	private static int totalNoLen;	

	private static double numAnomalies;
	
	private static DescriptiveStatistics statsNotNormal = new DescriptiveStatistics();
	
	private static ArrayList<Integer> storePacketCtr = new ArrayList<Integer>();
	
	private static ArrayList<Double> getIndexAnomaly = new ArrayList<Double>();
	private static ArrayList<Double> storeDoubleLenArray = new ArrayList<Double>();
	private static ArrayList<Double> AnomalyScoreArray = new ArrayList<Double>();
	private static ArrayList<Double> greaterThanAverage = new ArrayList<Double>();
		
	HttpURILengthNormal useThisFn = new HttpURILengthNormal();
	
	public ArrayList <String> openFile(ArrayList <String> storeLengthsInArray){
			String getBufLen;
			//DescriptiveStatistics stats = new DescriptiveStatistics();
			
			try{
				BufferedReader bufLen = new BufferedReader(new FileReader("/home/dimaz/Documents/IDSTextFiles/SundaySmallLen.txt"));
					try {
				
						//FileReader rf = new FileReader("/home/dimaz/Documents/IDSTextFiles/SundaySmallLen.txt");
						
						getBufLen = bufLen.readLine();
						
						while (getBufLen!=null){
								
								//Store strings to arraylist
								storeLengthsInArray.add(getBufLen);
								
								getBufLen = bufLen.readLine();
						}
				}
					finally{
							bufLen.close();
					}
			}
			
			catch (FileNotFoundException e){
				System.out.println(e);
			}
			catch (IOException e){
				System.out.println(e);
				}
			
			
			return storeLengthsInArray;
		}
		
		public void writeFile(){
			try {
				BufferedWriter writeFile = new BufferedWriter(new FileWriter("/home/dimaz/Documents/IDSTextFiles/Length/SundayTest1.txt"));
				
				try{
					//ArrayList <String> convDoubleToString = new ArrayList<String>();
					String convElementToString;
					
					/*converts the element of AnomalyScoreArray
					 * back into string by using convElementToString
					 * and writes the converted value into the file Write2.txt
					 */
					for (double y: AnomalyScoreArray){
						convElementToString = Double.toString(y);
						//convDoubleToString.add(convElementToString);
						writeFile.write(convElementToString);
						writeFile.newLine();
						
					}
				}
				
				finally{
					writeFile.close();
				}
			}
			
			catch (IOException e){
				System.out.println(e);
			}
			
		}
		
		
		
		public ArrayList <Double> convertFromStringToDouble(ArrayList <String> getStringLengthArray){
			//DescriptiveStatistics stats = new DescriptiveStatistics();
			//ArrayList <Double> storeDoubleLenArray = new ArrayList<Double>();
			double convStringLen = 0;
			//int totalNoLen = 0;
			
			//converts value of getStringLengthArray elements and stores them to storeDoubleLenArray
			//the for operation also adds element to stats via the argument stats.addValue(convStringLen)
			//while totalNoLen++ counts the number HTTP URI lengths processed by counting each for loop until
			//n-1 element of getStringLengthArray
			for (String indx: getStringLengthArray){
				convStringLen = Double.parseDouble(indx.trim());
				storeDoubleLenArray.add(convStringLen);
				statsNotNormal.addValue(convStringLen);
				totalNoLen++;
				
			}
			
			return storeDoubleLenArray;
		}


		
	/*
		 * Anomaly Score is computed as 1.5 ^ (length - mean)/(2.5 * standard dev)
		 * where length is each element of getDoubleArrayLen minus the mean of getDoubleArrayLen
		 * divided by 2.5 multiplied by standard deviation of getDoubleArrayLen
		 * This formula assigns an anomaly scores greater than 1.5 to HTTP URI lengths that exceed 
		 * the average length
		 * 15 is the maximum value of Anomaly Score
		 *
	*/
		
		public ArrayList <Double> computeAnomalyScore(){
			
			double AnomalyScore = 0;
			int packetCtr = 0;
			double threshold = 1.5;
			
			for (double index: storeDoubleLenArray){
				packetCtr++;
				AnomalyScore = Math.pow(1.6, (index - (useThisFn.MeanVal()))/(2.5 * useThisFn.StanDevVal()));
				
					if (AnomalyScore>= threshold){
						//stores the value of Anomaly Score greater than 1.5
						greaterThanAverage.add(AnomalyScore);
						//stores the length of the index which is has AS length greater than 1.5
						getIndexAnomaly.add(index);
						//stores the packet number with Anomaly Score greater than 1.5
						storePacketCtr.add(packetCtr);
						//counts the number of HTTP lengths with AS greater than 1.5
						numAnomalies++;
					}
					AnomalyScoreArray.add(AnomalyScore);
					
			}
			
			return AnomalyScoreArray;
		}
		
		//method for getting AS elements greater than 1.5
		public ArrayList <Double> getGreaterThanAvg(){
			return HttpURILengthNotNormal.greaterThanAverage;
		}
		
		//method for getting the value of AS lengths greater than 1.5
		public double getNumAnomalies(){
			return HttpURILengthNotNormal.numAnomalies;
		}
		
		
		public void displayAnomalies(){
			System.out.println("HTTP Packet Number "+ "\tLength "+"\tAnomaly Score");
			
				System.out.println(storePacketCtr+"\n"+ getIndexAnomaly+ "\n"+greaterThanAverage);
			
			
		}
		
		public int getTotalLen(){
			return HttpURILengthNotNormal.totalNoLen;
		}
	
		public double setSumOfLengths(){
			double getLength = statsNotNormal.getSum();
			return getLength;
		}
		
		public double setMeanNotNormal(){
			double getMean = statsNotNormal.getMean();
			return getMean;
		}
		
		public double setVarianceNotNormal(){
			double getVariance = statsNotNormal.getVariance();
			return getVariance;
		}
		
		public double setStdDevNotNormal(){
			double getStdDev = statsNotNormal.getStandardDeviation();
			return getStdDev;
		}
		
		
}
