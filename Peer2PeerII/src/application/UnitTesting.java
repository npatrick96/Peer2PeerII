package application;


import static org.junit.Assert.*;

import org.junit.Test;

public class UnitTesting {
	MessageModel model =  new MessageModel();
	final int LARGE_NUMBER = 1000;

	
	@Test
	public void testAdd(){
		for (int i = 0; i<LARGE_NUMBER; ++i){
			model.addMessage(Integer.toBinaryString(i));
			model.addMessage("");
			
		} 
	assertEquals(LARGE_NUMBER*1.0, model.size()*1.0, 0.001);
	}

}
