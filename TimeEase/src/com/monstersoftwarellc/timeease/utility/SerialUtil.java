package com.monstersoftwarellc.timeease.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerialUtil {

	  public static byte[] serializeObjectToBytes(Object object){
	       	try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				
				oos.writeObject(object);
			
				return baos.toByteArray();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
	    }
	    
	    public static Object deSerializeObjectFromBytes(byte[] bytes){
	     	Object value = null;
	    	try {
				ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
				ObjectInputStream ois = new ObjectInputStream(bais);
				
				value = ois.readObject();
				ois.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
	    	
	    	return value;
	    }
		
	
	
    public static String serializeObject(Object object){
       	try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			
			oos.writeObject(object);
			
			return baos.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
    }
    
    public static Object deSerializeObject(String string){
    	return deSerializeObjectFromBytes(string.getBytes());
    }
	
	
}
