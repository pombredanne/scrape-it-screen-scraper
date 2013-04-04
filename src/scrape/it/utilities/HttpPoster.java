package scrape.it.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HttpPoster {
		private String answer;

		public HttpPoster(){
		
		}
		
		public String sendPost(String newurl, String data) {
	       
	       //Build parameter string
	       try {
	           
	           // Send the request
	           URL url = new URL(newurl);
	           URLConnection conn = url.openConnection();
	           conn.setDoOutput(true);
	           OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
	           
	           //write parameters
	           writer.write(data);
	           writer.flush();
	           
	           // Get the response
	           StringBuffer answer = new StringBuffer();
	           BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	           String line;
	           while ((line = reader.readLine()) != null) {
	               answer.append(line);
	           }
	           writer.close();
	           reader.close();
	           
	           //check for login
	           //if (answer.toString().contains("Incorrect Password or Username Do Not Exist") == true){

	           
	           
	       } catch (MalformedURLException ex) {
	           ex.printStackTrace();
	       } catch (IOException ex) {
	           ex.printStackTrace();
	       }
	       return answer.toString();
		}
}
