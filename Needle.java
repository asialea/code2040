package code2040;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject; //add external jar in order to use this library. ask about it. 
import org.json.JSONArray;

public class Needle {
	
	public static void main(String args[]) throws JSONException {	

		int index=0;
		
		
		HttpURLConnection conn;
		HttpURLConnection conn2;

		OutputStream os;
		OutputStream ostwo;

		BufferedReader br;
		BufferedReader br2;

		try {

			URL url = new URL("http://challenge.code2040.org/api/haystack");
			
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
			String haystack = output.substring(32);
			//System.out.print(haystack);
				
				JSONObject jsonObj = new JSONObject(output);	
				String needle = jsonObj.getString("needle");
				//System.out.println(needle);
				
				JSONArray array= new JSONArray(haystack);
				
				for(int i=0; i<array.length(); i++ ){
					if(needle.equals(array.get(i)))
						index=i;
					
				}
				       
		
				

				String validate = "{\"token\":\"88fc2262fe28c7953f94fd2330f93b24\",\"needle\":\"" + index + "\"}";
				
				

				URL validateURL = new URL("http://challenge.code2040.org/api/haystack/validate");

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
		}
		
		catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
