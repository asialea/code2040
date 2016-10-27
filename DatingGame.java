package code2040;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class DatingGame {
	
	public static void main(String args[]) throws JSONException, ParseException {	

		HttpURLConnection conn;
		HttpURLConnection conn2;

		OutputStream os;
		OutputStream ostwo;

		BufferedReader br;
		BufferedReader br2;

		try {

			URL url = new URL("http://challenge.code2040.org/api/dating");
			
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String input = "{\"token\":\"88fc2262fe28c7953f94fd2330f93b24\",\"github\":\"https://github.com/asialea/internship\"}";
			System.out.println(input);

			os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Output from server.... \n");
			while((output = br.readLine()) != null) {
				System.out.println(output);
				
				
				

				JSONObject jsonObj = new JSONObject(output);     
				int interval = jsonObj.getInt("interval");       //get interval from json
				String datestamp = jsonObj.getString("datestamp");    //get the original date
				String date= datestamp.substring(0, 10);            
				String time= datestamp.substring(11,19);
				String timestamp= date+ " " + time; 				//put date into time into SimpleDateFormat
				
				SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");   //parse and create date
				Date originaldate= sdf.parse(timestamp);
				
				
				
				 Calendar cal = Calendar.getInstance();            //create calendar instance and set the time to given date
				  cal.setTime(originaldate);
				  cal.add(Calendar.SECOND, interval);          //adds the given interval in seconds to the date
				
				
		 //extract date and time from calendar object	
				  
	         String year= String.format("%04d",cal.getWeekYear());         
		 String month = String.format("%02d",cal.get(Calendar.MONTH) +1);
		 String hours = String.format("%02d",cal.get(Calendar.HOUR_OF_DAY));
		 String minutes = String.format("%02d",cal.get(Calendar.MINUTE));
		 String seconds = String.format("%02d",cal.get(Calendar.SECOND));
		 String day= String.format("%02d",cal.get(Calendar.DAY_OF_MONTH));
		 
		//put the date and time into proper format 
		 
		String newDatestamp = year+ "-" + month + "-" + day  + "T" + hours + ":" + minutes + ":" + seconds + "Z"; 		
		System.out.println(newDatestamp);
		

		//Send result to the server
		
		String validate = "{\"token\":\"88fc2262fe28c7953f94fd2330f93b24\",\"datestamp\":\"" + newDatestamp + "\"}";		

		URL validateURL = new URL("http://challenge.code2040.org/api/dating/validate");

		conn2 = (HttpURLConnection) validateURL.openConnection();
		conn2.setDoOutput(true);
		conn2.setRequestMethod("POST");
		conn2.setRequestProperty("Content-Type", "application/json");

		ostwo = conn2.getOutputStream();
		ostwo.write(validate.getBytes());
		ostwo.flush();

		br2 = new BufferedReader(new InputStreamReader((conn2.getInputStream())));

		String new_output;
		System.out.println("Output from server.... \n");
		while((new_output = br2.readLine()) != null) {
			System.out.println(new_output);
		}

		conn2.disconnect();

		
	}
		
			

			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
