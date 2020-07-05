package com.cycle.controller;

import java.io.FileReader;

import com.cycle.entity.ComponentList;
import com.cycle.util.CycleUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PriceCaluculator {

	public static void main(String[] args) {
		System.out.println("*** This is a Cycle price calculate application***");
		System.out.println("***** Both data file and calculated price file are placed in class path *****");
		System.out.println("The data is read from file: \"cycleData.json\"");
		System.out.println("The price is calculated and written into \"priceBook.txt\" file");
		
		//System.out.println("Please enter the configuration for cycle: ");
        
        try (FileReader reader = new FileReader("cycleData.json"))
        {
            //Read JSON file and cast to component object
		ObjectMapper objectMapper = new ObjectMapper();
		ComponentList list = objectMapper.readValue(reader, ComponentList.class);
		CycleUtil.createProcessedThread(list);
            
        }catch(Exception e) {
        	e.printStackTrace();
        }
		
	}

}
