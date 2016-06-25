package request;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Curl {
	
	public String testWunderlistAuthentication(String code, String clientId, String clientSecret) throws IOException {

		/*
		 * The following code snippet was taken from this thread:
		 * http://stackoverflow.com/questions/15684846/convert-curl-call-into-
		 * java-urlconnection-call
		 */
		try {

			String url = "https://www.wunderlist.com/oauth/access_token";

			URL obj = new URL(url);
			HttpsURLConnection conn = (HttpsURLConnection) obj.openConnection();

			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);

			conn.setRequestMethod("GET");

			String data = "{\"code\":\"" + code + "\", \"client_id\": \"" + clientId + "\", \"client_secret\": \"" + clientSecret + "\"}";
			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
			out.write(data);
			out.close();

			String token =  (getStringFromInputStream(conn.getInputStream()));
			
			return token.split(":")[1].replace('}', ' ').replace('\"', ' ').trim();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;

	}

	/*
	 * The following code snippet was taken from this thread:
	 * http://www.mkyong.com/java/how-to-convert-inputstream-to-string-in-java/
	 */
	private static String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}
	
	public static void main(String args[]){
		
		Curl curl = new Curl();
		
		try {
			
			String access_token = "b240fe68007a1c01b5f6da3e22f4d664cc9d57186fc39ce6a248174356cd";
			String client_id = "e56693ca9d2c248f275d";
			String client_secret = "e2fc71d1d58679cc27b4f46ae6b4f2399519e9fdc8c99253531e186bd17b";
			

			String code="4be18766d47d4d60bfe9";
			
			System.out.println(curl.testWunderlistAuthentication(code, client_id, client_secret));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



}
