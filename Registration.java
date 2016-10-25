package code2040;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Registration {

	
		public static void main(String args[]) {	

			HttpURLConnection conn;
			OutputStream os;
			BufferedReader br;

			try {
				
				//sends token and github to Registration endpoint

				URL url = new URL("http://challenge.code2040.org/api/register");
				
				
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");

				String input = "{\"token\":\"88fc2262fe28c7953f94fd2330f93b24\",\"github\":\"https://github.com/asialea/internship\"}";

				

				os = conn.getOutputStream();
				os.write(input.getBytes());
				os.flush();

				br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

				String output;
				System.out.println("Server response:");
				while((output = br.readLine()) != null) {
					System.out.println(output);
				}

				conn.disconnect();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

