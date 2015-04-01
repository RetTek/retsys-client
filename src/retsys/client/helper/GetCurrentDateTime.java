/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package retsys.client.helper;

/**
 *
 * @author Admin
 */
 
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
 
 
public class GetCurrentDateTime {
  public String getsysDate() {
 
	   DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	   //get current date time with Date()
	   Date date = new Date();
	   System.out.println(dateFormat.format(date));
 return dateFormat.format(date);
	   
 
  }
  
  public String getMakeDate() {
 
	   DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	   //get current date time with Date()
	   Date date = new Date();
	   System.out.println(dateFormat.format(date));
 return dateFormat.format(date);
	   
 
  }
}