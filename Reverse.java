package code2040;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Reverse {
	


	
	public static void main(String args[]) {

		final String token = "{\"token\":\"88fc2262fe28c7953f94fd2330f93b24\"}";

		HttpURLConnection conn;
		HttpURLConnection conn2;

		OutputStream os;
		OutputStream ostwo;

		BufferedReader br;
		BufferedReader br2;

		try {


			URL url = new URL("http://challenge.code2040.org/api/reverse");
			
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			os = conn.getOutputStream();
			os.write(token.getBytes());
			os.flush();

			br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Server response 1:");
			while((output = br.readLine()) != null) {
				System.out.println("The given string is: "+ output);   //printing the String given from server

				System.out.println("");
				

				String reverse = new StringBuffer(output).reverse().toString(); // creating String buffer and reversing the output
				System.out.println("The reversed String is: " + reverse);          //print the reversed string to make sure it worked 
				 
				String validate = "{\"token\":\"88fc2262fe28c7953f94fd2330f93b24\",\"string\":\"" + reverse + "\"}"; 
				
				System.out.println("");
			
				//sending reversed string to validation endpoint
				URL validateURL = new URL("http://challenge.code2040.org/api/reverse/validate");

				conn2 = (HttpURLConnection) validateURL.openConnection();
				conn2.setDoOutput(true);
				conn2.setRequestMethod("POST");
				conn2.setRequestProperty("Content-Type", "application/json");

				ostwo = conn2.getOutputStream();
				ostwo.write(validate.getBytes());
				ostwo.flush();

				br2 = new BufferedReader(new InputStreamReader((conn2.getInputStream())));

				String new_output;
				System.out.println("Server response 2:");
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
