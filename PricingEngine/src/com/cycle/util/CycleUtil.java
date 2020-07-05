package com.cycle.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.cycle.entity.ComponentList;
import com.cycle.service.CalculationService;

@SuppressWarnings("deprecation")
public class CycleUtil {
	private static int THREAD_SIZE = 10;
	static int MAX_RECORDS_PER_THREAD = 0;

	public int calculateThreadsCount(int size) {
		int count = 1;
		if (size > 10) {

		}
		return count;
	}

	/**
	 * This method creates the thread based on count of data with max ten 10 threads
	 * @param list
	 * @throws IOException
	 */
	public static void createProcessedThread(ComponentList list) throws IOException {
		try {
		int size = list.getComponents().size();
		List<CalculationService> workersList = new ArrayList<CalculationService>();
		if (size > 0) {
				MAX_RECORDS_PER_THREAD = (size / THREAD_SIZE) + (size % THREAD_SIZE == 0 ? 0 : 1); //Calculating maximum records per thread
				 THREAD_SIZE = (size/MAX_RECORDS_PER_THREAD)+(size%MAX_RECORDS_PER_THREAD==0?0:1); // Allocating data among 10 threads
			 ExecutorService executor = Executors.newFixedThreadPool(THREAD_SIZE); 
				for(int i = 1; i <= THREAD_SIZE;i++){
					int start = (i==1?0:(MAX_RECORDS_PER_THREAD*(i-1)));
					int end = (i == 1 ? (size > MAX_RECORDS_PER_THREAD ? MAX_RECORDS_PER_THREAD - 1 : (size-1))
							: (size >= MAX_RECORDS_PER_THREAD * i ? (MAX_RECORDS_PER_THREAD * i)-1 : size));
					CalculationService migrationWorker = new CalculationService(list, start, end);
					workersList.add(migrationWorker);
				}
			 List<Future<Map<Integer,Integer>>> futures = executor.invokeAll(workersList); 
			 executor.shutdownNow();
			 WritePriceData(futures);
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writes the data to output text file with booking ID 
	 * @param futures
	 */
	@SuppressWarnings({ "unchecked" })
	private static void WritePriceData(List<Future<Map<Integer, Integer>>> futures) {
		try {
			FileWriter file = new FileWriter("priceBook.txt");
			JSONArray jarray = new JSONArray();
			for(Future<Map<Integer, Integer>> data:futures) {
				JSONObject jsonObject = new JSONObject();
				  for (Map.Entry<Integer, Integer> entry : data.get().entrySet()) {
				  jsonObject.put("BookingID", entry.getKey());
				  jsonObject.put("FinalPrice", entry.getValue());
				  jarray.add(jsonObject); 
				  file.write(jarray.toJSONString());
				  file.write("\n");
				  jarray.clear();
				   }
			}
			if(file!=null)
				file.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
