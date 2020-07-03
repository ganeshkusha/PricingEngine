package com.cycle.controller;

import java.io.FileReader;

import com.cycle.entity.ComponentList;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PriceCaluculator {

	public static void main(String[] args) {
		System.out.println("*** This is a Cycle price calculate application***");
		System.out.println("Please enter the configuration for cycle: ");
        
        try (FileReader reader = new FileReader("cycleData.json"))
        {
            //Read JSON file
        	
           /* Object obj = jsonParser.parse(reader);
            JSONObject j = new 
            JSONArray employeeList = (JSONArray) obj.;*/
		ObjectMapper objectMapper = new ObjectMapper();
		ComponentList list = objectMapper.readValue(reader, ComponentList.class);
            System.out.println(list.getComponents().get(0).getFrame());
            
        }catch(Exception e) {
        	e.printStackTrace();
        }
		
	}

}
