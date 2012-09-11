package lengthdetection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class fileManipulate {
	private static int totalNoLen;	

	private static double numAnomalies;
	private static DescriptiveStatistics stats = new DescriptiveStatistics();
	
	private static ArrayList<String> storeLengthsInArray = new ArrayList<String>();
	private static ArrayList<Integer> storePacketCtr = new ArrayList<Integer>();
	private static ArrayList<Double> getIndexAnomaly = new ArrayList<Double>();
	private static ArrayList<Double> storeDoubleLenArray = new ArrayList<Double>();
	private static ArrayList<Double> AnomalyScoreArray = new ArrayList<Double>();
	private static ArrayList<Double> greaterThanAverage = new ArrayList<Double>();
	
		
		public void openFile(){
			System.out.println("Enter file name of text file to be scanned: ");
			Scanner getFile = new Scanner(System.in);
			String getFileName = getFile.nextLine();
			
			String getBufLen;
			
			try {
					//FileReader rf = new FileReader("/home/dimaz/Documents/IDSTextFiles/SaturdaySmallLen.txt");
					BufferedReader bufLen = new BufferedReader(new FileReader(getFileName));
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
			
			//return storeLengthsInArray;
		}
		
		public void writeFile(){
			System.out.println("Enter file name including directory for AnomalyScore Array text file: ");
			Scanner saveFile = new Scanner (System.in);
			String saveFileName = saveFile.nextLine();
			
			try {
				BufferedWriter writeFile = new BufferedWriter(new FileWriter(saveFileName));
				
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
		
		
		
		public void convertFromStringToDouble(){
			
			double convStringLen = 0;
			
			//converts value of getStringLengthArray elements and stores them to storeDoubleLenArray
			//the for operation also adds element to stats via the argument stats.addValue(convStringLen)
			//while totalNoLen++ counts the number HTTP URI lengths processed by counting each for loop until
			//n-1 element of getStringLengthArray
			for (String indx: storeLengthsInArray){
				convStringLen = Double.parseDouble(indx.trim());
				storeDoubleLenArray.add(convStringLen);
				stats.addValue(convStringLen);
				totalNoLen++;
				
			}
			
			//return storeDoubleLenArray;
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
		public void computeAnomalyScore(){
			
			double AnomalyScore = 0;
			int packetCtr = 0;
			double threshold = 1.5;
			
			for (double index: storeDoubleLenArray){
				packetCtr++;
				AnomalyScore = Math.pow(1.6, (index- (getMeanVal()))/(2.5 * getStanDevVal()));
				
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
		}
		
		//method for getting AS elements greater than 1.5
		public ArrayList <Double> getGreaterThanAvg(){
			return greaterThanAverage;
		}
		
		//method for getting the value of AS lengths greater than 1.5
		public double getNumAnomalies(){
			return numAnomalies;
		}
		
		
		public void displayAnomalies(){
			System.out.println("HTTP Packet Number "+ "\tLength "+"\tAnomaly Score");
				System.out.println(storePacketCtr+"\n"+ getIndexAnomaly+ "\n"+greaterThanAverage);
		}
		
		//Counts the number of packets in the text file
		public ArrayList<Integer> getPacketCounter(){
			return storePacketCtr;
		}
		
		//method for Total No of HTTP URI Length count
		public double getPacketCount(){
			return totalNoLen;
		}
		//method for calculating the total lengths
		public double getSumVal(){
			//double getSum = stats.getSum();
			return ((double) stats.getSum());
		}
		//method for calculating overall mean of Saturday HTTP URI Length
		public double getMeanVal(){
			//double getMean = stats.getMean();
			return ((double)stats.getMean());
		}
		////method for calculating overall variance of Saturday HTTP URI Length
		public double getVarianceVal(){
			//double getVariance = stats.getVariance();
			return ((double) stats.getVariance());
		}
		//method for calculating overall standard dev of Saturday HTTP URI Length
		public double getStanDevVal(){
			//double getStDev = stats.getStandardDeviation();
			return ((double)stats.getStandardDeviation());
		}

	}

