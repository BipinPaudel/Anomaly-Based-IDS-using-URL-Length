package lengthdetection;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;


public class HttpURILengthNormal {
	//variable with static can be used by any function on this class
	//totalNoLen gives the value of Total Number of Lengths calculated
	//stats is a variable from Descriptive Statistics class that grants access to its methods
	//storeDoubleLenArray is an ArrayLength that stores the converted value from the string 
	//ArrayList storeLengthsInArray
	//AnomalyScoreArray is an arraylist holds the values calculated from AnomalyScore
	private static int totalNoLen;	

	private static double numAnomalies;
	private static DescriptiveStatistics stats = new DescriptiveStatistics();
	private static ArrayList<Integer> storePacketCtr = new ArrayList<Integer>();
	private static ArrayList<Double> getIndexAnomaly = new ArrayList<Double>();
	private static ArrayList<Double> storeDoubleLenArray = new ArrayList<Double>();
	private static ArrayList<Double> AnomalyScoreArray = new ArrayList<Double>();
	private static ArrayList<Double> greaterThanAverage = new ArrayList<Double>();
	
		
		public ArrayList <String> openFile(ArrayList <String> storeLengthsInArray){
			String getBufLen;
			
			try {
					//FileReader rf = new FileReader("/home/dimaz/Documents/IDSTextFiles/SaturdaySmallLen.txt");
					BufferedReader bufLen = new BufferedReader(new FileReader("/home/dimaz/Documents/IDSTextFiles/SaturdaySmallLen.txt"));
					try{
						getBufLen = bufLen.readLine();
						
						while (getBufLen!=null){
								
								//Store strings to arraylist
								storeLengthsInArray.add(getBufLen);
								
								//stats stores a double value that is why there is a need to convert the string to double
								//Trim() ensures that the length of the double doesn't have whitespaces
								//stats.addValue(Double.parseDouble(getBufLen.trim()));
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
				BufferedWriter writeFile = new BufferedWriter(new FileWriter("/home/dimaz/Documents/IDSTextFiles/Length/SatTest1.txt"));
				
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
			
			double convStringLen = 0;
			
			//converts value of getStringLengthArray elements and stores them to storeDoubleLenArray
			//the for operation also adds element to stats via the argument stats.addValue(convStringLen)
			//while totalNoLen++ counts the number HTTP URI lengths processed by counting each for loop until
			//n-1 element of getStringLengthArray
			for (String indx: getStringLengthArray){
				convStringLen = Double.parseDouble(indx.trim());
				storeDoubleLenArray.add(convStringLen);
				stats.addValue(convStringLen);
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
				AnomalyScore = Math.pow(1.6, (index- (MeanVal()))/(2.5 * StanDevVal()));
				
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
			return HttpURILengthNormal.greaterThanAverage;
		}
		
		//method for getting the value of AS lengths greater than 1.5
		public double getNumAnomalies(){
			return HttpURILengthNormal.numAnomalies;
		}
		
		
		public void displayAnomalies(){
			System.out.println("HTTP Packet Number "+ "\tLength "+"\tAnomaly Score");
			
				System.out.println(storePacketCtr+"\n"+ getIndexAnomaly+ "\n"+greaterThanAverage);
			
			
		}
		
		
		//method for Total No of HTTP URI Length count
		public double PacketCount(){
			return totalNoLen;
		}
		//method for calculating the total lengths
		public double SumVal(){
			double getSum = stats.getSum();
			
			return getSum;
		}
		//method for calculating overall mean of Saturday HTTP URI Length
		public double MeanVal(){
			double getMean = stats.getMean();
			return getMean;
		}
		////method for calculating overall variance of Saturday HTTP URI Length
		public double VarianceVal(){
			double getVariance = stats.getVariance();
			return getVariance;
		}
		//method for calculating overall standard dev of Saturday HTTP URI Length
		public double StanDevVal(){
			double getStDev = stats.getStandardDeviation();
			return getStDev;
		}

	}


