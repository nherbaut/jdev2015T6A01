package com.enseirb.telecom.dngroup.dvd2c.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.enseirb.telecom.dngroup.dvd2c.ApplicationContext;
import com.enseirb.telecom.dngroup.dvd2c.db.BoxRepositoryObject;
import com.enseirb.telecom.dngroup.dvd2c.db.CrudRepository;
import com.enseirb.telecom.dngroup.dvd2c.exception.NoSuchBoxException;
import com.enseirb.telecom.dngroup.dvd2c.exception.NoSuchUserException;
import com.enseirb.telecom.dngroup.dvd2c.exception.SuchBoxException;
import com.enseirb.telecom.dngroup.dvd2c.service.request.*;
import com.enseirb.telecom.dngroup.dvd2c.model.Box;
import com.enseirb.telecom.dngroup.dvd2c.model.User;

public class BoxServiceImpl implements BoxService{
	private static final Logger LOGGER = LoggerFactory.getLogger(BoxServiceImpl.class);
	CrudRepository<BoxRepositoryObject, String> boxDatabase;
	RequestBoxService requetBoxService = new RequestBoxServiceImpl(
			ApplicationContext.getProperties().getProperty("CentralURL") + "/api/app/box/");
	
	
	public BoxServiceImpl(CrudRepository<BoxRepositoryObject, String> boxDatabase){
		
		this.boxDatabase = boxDatabase;
	}

	public boolean boxExist(Box box){
		
		// TODO: change to the correct page and add fontion for get addr of
		// server
		boolean exist = boxDatabase.exists(box.getBoxID());
		
		try {
 			Box boxGet = requetBoxService.get(box);
			if ((boxGet == null))
				exist = false;
			else if (box.getBoxID().equals(boxGet.getBoxID()))
				exist = true;
		} catch (IOException e) {
			//NHE: no print stack trace allowed in the project. Please replace it with appropriate logger and Exception handling. 
e.printStackTrace();
			 System.err.printf("Can not connect on the server :(\n");
			 
		} catch (NoSuchBoxException e) {
			exist =false;
		}
		
		return exist;
	}
	
	public Box getBox(String boxID){
		
		Box box = boxDatabase.findOne(boxID).toBox();
		
		if(box == null){
			
			return null;
		}
		
		return box;
	}
	
	public Box createBox(Box box){
		
		//box.setBoxID(ApplicationContext.getProperties().getProperty("BoxID"));
		try {
			requetBoxService.post(box);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//NHE: no print stack trace allowed in the project. Please replace it with appropriate logger and Exception handling. 
			LOGGER.error("error during creating a box ", e);
		} catch (SuchBoxException e) {
			// TODO Auto-generated catch block
			//NHE: no print stack trace allowed in the project. Please replace it with appropriate logger and Exception handling. 
			e.printStackTrace();
		}
		Box b = boxDatabase.save(new BoxRepositoryObject(box)).toBox();
		return b;
		
	}
	
	public void saveBox(Box box){
		
		try {
			boxDatabase.save(new BoxRepositoryObject(box));
			requetBoxService.put(box);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//NHE: no print stack trace allowed in the project. Please replace it with appropriate logger and Exception handling. 
e.printStackTrace();
		} catch (NoSuchBoxException e) {
			// TODO Auto-generated catch block
			//NHE: no print stack trace allowed in the project. Please replace it with appropriate logger and Exception handling. 
e.printStackTrace();
		}
	}
	
	public void deleteBox(String boxID){
		
		try {
			requetBoxService.delete(boxID);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//NHE: no print stack trace allowed in the project. Please replace it with appropriate logger and Exception handling. 
e.printStackTrace();
		} catch (NoSuchBoxException e) {
			// TODO Auto-generated catch block
			//NHE: no print stack trace allowed in the project. Please replace it with appropriate logger and Exception handling. 
e.printStackTrace();
		}
		
		boxDatabase.delete(boxID);
	}
	
}