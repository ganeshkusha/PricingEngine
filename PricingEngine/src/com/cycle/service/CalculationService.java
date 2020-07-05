package com.cycle.service;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;

import com.cycle.entity.Component;
import com.cycle.entity.ComponentList;
import com.cycle.entity.Configuration;

public class CalculationService implements Callable<Map<Integer,Integer>> {
	private ComponentList list;
	private int start;
	private int end;
	Map<Integer,Integer> priceMap = new TreeMap<Integer, Integer>();
	private static final int DEFAULT_FRAME_PRICE = 50;
	private static final int DEFAULT_HDL_BREAKS_PRICE = 50;
	private static final int DEFAULT_SEATING_PRICE = 50;
	private static final int DEFAULT_WHEELS_PRICE = 50;
	private static final int DEFAULT_CHAIN_ASSEMBLY_PRICE = 50;
	

	public CalculationService(ComponentList list, int start, int end) {
		this.list = list;
		this.start = start;
		this.end = end;
	}
	
	/**
	 * Thread implementation starts here
	 */

	@Override
	public Map<Integer,Integer> call() throws Exception {
		while(start<=end) {
			try {
				CalculatePrice(list.getComponents().get(start));
			start++;
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return priceMap;
	}

	/**
	 * 
	 * @param component
	 * @return
	 */
	private void CalculatePrice(Component component) {
		int price = 0;
		price = getQuotedPrice(component);
		priceMap.put(component.getBookingId(), price);
	}

	/**
	 * This method is used to return the price quotation
	 * Data can be default of customized
	 * "default" is hardcoded to calculate the price
	 * @param component
	 * @return
	 */
	private int getQuotedPrice(Component component) {
		Configuration conf = new Configuration();
		int price = 0;
		if(component.getFrame().contentEquals("default")){
			price +=DEFAULT_FRAME_PRICE;
		}else {
			conf.setFrameConfig(component.getFrame());
		}
		if(component.getChainAssembly().contentEquals("default")){
			price +=DEFAULT_CHAIN_ASSEMBLY_PRICE;
		}else {
			conf.setChainAssemblyConfig(component.getChainAssembly());
		}
		if(component.getHandleBarWithBrakes().contentEquals("default")){
			price +=DEFAULT_HDL_BREAKS_PRICE;
		}else {
			conf.setHandleBarWithBrakesConfig(component.getHandleBarWithBrakes());
		}
		if(component.getSeating().contentEquals("default")){
			price +=DEFAULT_SEATING_PRICE;
		}else {
			conf.setSeatingConfig(component.getSeating());
		}
		if(component.getWheels().contentEquals("default")){
			price +=DEFAULT_WHEELS_PRICE;
		}else {
			conf.setWheelsConfig(component.getWheels());
		}
		
		price = CalculateCustomPrice(conf,price);
		if(component.getBookingYear()>2020) {
			price = (int) (price+(price*0.2));
		}else if(component.getBookingYear()<2020) {
			int diff = 2020-component.getBookingYear();
			price = price-(price*(diff/10));
			if(price<120) {
				price = 120;
			}
		}
		return price;
	}

	/**
	 * Calculate price for custom/user entered configuration
	 * @param conf
	 * @param price
	 * @return
	 */
	private int CalculateCustomPrice(Configuration conf, int price) {
		try {
		if(conf.getChainAssemblyConfig()!=null) {
			price = (int) (price+DEFAULT_CHAIN_ASSEMBLY_PRICE+(DEFAULT_CHAIN_ASSEMBLY_PRICE*0.1));
		}if(conf.getFrameConfig()!=null) {
			price = (int) (price+DEFAULT_FRAME_PRICE+(DEFAULT_FRAME_PRICE*0.1));
		}if(conf.getHandleBarWithBrakesConfig()!=null) {
			price = (int) (price+DEFAULT_HDL_BREAKS_PRICE+(DEFAULT_HDL_BREAKS_PRICE*0.2));
		}if(conf.getSeatingConfig()!=null) {
			price = (int) (price+DEFAULT_SEATING_PRICE+(DEFAULT_SEATING_PRICE*0.1));
		}if(conf.getWheelsConfig()!=null) {
			price = (int) (price+DEFAULT_WHEELS_PRICE+(DEFAULT_WHEELS_PRICE*0.3));
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return price;
	}
}
