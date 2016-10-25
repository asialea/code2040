package code2040;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Prefix {

	public static void main(String args[]) throws JSONException {	
		
		HttpURLConnection conn;
		HttpURLConnection conn2;

		OutputStream os;
		OutputStream ostwo;

		BufferedReader br;
		BufferedReader br2;


		try {

			URL url = new URL("http://challenge.code2040.org/api/prefix");
			
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
			
				
				JSONObject jsonObj = new JSONObject(output);     //turn string into JSON object
				JSONArray jsonArray = jsonObj.getJSONArray("array");  //array of all words
				String prefix = jsonObj.getString("prefix");   //prefix
				
				
				
				JSONArray nArray = new JSONArray();; //arraylist of word with the prefix
				
				
				for(int i=0; i<jsonArray.length(); i++ ){
					if(((String) jsonArray.get(i)).startsWith(prefix)== false)
						nArray.put(jsonArray.get(i));
					
				}
				
				


				JSONObject post = new JSONObject();  //make a new json object
				String token= "88fc2262fe28c7953f94fd2330f93b24" ;
				post.put("token", token);    //add the array of words without the prefixes
				post.put("array", nArray);   //add the token

			

	
				String validate= post.toString();   //turn the Json object into a string
				
				URL validateURL = new URL("http://challenge.code2040.org/api/prefix/validate");

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

