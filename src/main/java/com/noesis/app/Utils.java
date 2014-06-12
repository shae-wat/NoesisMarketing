package com.noesis.app;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class Utils {

	public static String simpleHttpGet(String urlStr) throws Exception,
			IOException {
		Content c = Request.Get(urlStr).execute().returnContent();
		return c.toString();

	}

	public static String httpGet(String urlStr) throws IOException {
		String responseStr = "";

		CloseableHttpClient httpclient = HttpClients.createDefault();
		// HttpGet httpGet = new HttpGet("http://targethost/homepage");
		HttpGet httpGet = new HttpGet(urlStr);
		CloseableHttpResponse response1 = httpclient.execute(httpGet);
		// The underlying HTTP connection is still held by the response object
		// to allow the response content to be streamed directly from the
		// network socket.
		// In order to ensure correct deallocation of system resources
		// the user MUST call CloseableHttpResponse#close() from a finally
		// clause.
		// Please note that if response content is not fully consumed the
		// underlying
		// connection cannot be safely re-used and will be shut down and
		// discarded
		// by the connection manager.
		try {
			System.out.println(response1.getStatusLine());
			HttpEntity entity1 = response1.getEntity();
			responseStr = EntityUtils.toString(response1.getEntity());
			// do something useful with the response body
			// and ensure it is fully consumed
			EntityUtils.consume(entity1);
		} finally {
			response1.close();
		}

		return responseStr;
	}

	public static String httpsGet(String https_url) {
		// String https_url = "https://www.google.com/";
		
		URL url;
		String responseStr = "";
		
		try {

			url = new URL(https_url);
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

			// pull returned content
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));

			String input;

			while ((input = br.readLine()) != null) {
				responseStr = responseStr + input;
			}
			br.close();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return responseStr;
	}

	// public static String httpsTest (String urlStr) throws KeyStoreException,
	// IOException, Exception, CertificateException
	// {
	// String responseStr = "";
	//
	// KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
	// FileInputStream instream = new FileInputStream(new File("my.keystore"));
	// try {
	// trustStore.load(instream, "nopassword".toCharArray());
	// } finally {
	// instream.close();
	// }
	//
	// // Trust own CA and all self-signed certs
	// SSLContext sslcontext = SSLContexts.custom()
	// .loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
	// .build();
	// // Allow TLSv1 protocol only
	// SSLConnectionSocketFactory sslsf = new
	// SSLConnectionSocketFactory(sslcontext);
	// CloseableHttpClient httpclient = HttpClients.custom()
	// .setSSLSocketFactory(sslsf)
	// .build();
	// try {
	//
	// HttpGet httpget = new HttpGet("https://www.noesisenergy.com/");
	//
	// System.out.println("executing request" + httpget.getRequestLine());
	//
	// CloseableHttpResponse response = httpclient.execute(httpget);
	// try {
	// HttpEntity entity = response.getEntity();
	//
	// System.out.println("----------------------------------------");
	// System.out.println(response.getStatusLine());
	// if (entity != null) {
	// System.out.println("Response content length: " +
	// entity.getContentLength());
	// }
	// EntityUtils.consume(entity);
	// } finally {
	// response.close();
	// }
	// } finally {
	// httpclient.close();
	// }
	//
	// return responseStr;
	// }

	public static String httpPost(String urlStr)
			throws ClientProtocolException, IOException {
		String responseStr = "";

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://targethost/login");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", "vip"));
		nvps.add(new BasicNameValuePair("password", "secret"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse response2 = httpclient.execute(httpPost);

		try {
			System.out.println(response2.getStatusLine());
			HttpEntity entity2 = response2.getEntity();
			// do something useful with the response body
			// and ensure it is fully consumed
			EntityUtils.consume(entity2);
		} finally {
			response2.close();
		}

		return responseStr;
	}


	public static String readFromURL(String urlStr) throws Exception {
		// replace any spaces
		urlStr = urlStr.replace(" ", "%20");

		String result = "";
		URL url = new URL(urlStr);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				url.openStream()));

		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			// System.out.println(inputLine);
			result = result + inputLine;
		}
		in.close();

		return result;
	}
	
	public static String pullPDFTextFromURL (String urlStr) throws Exception
	{
		PDFTextStripper stripper = new PDFTextStripper();
		PDDocument doc;
		URL url = new URL(urlStr);
		doc = PDDocument.load(url);
		String content = stripper.getText(doc);
		return content;
	}
	
	public static List<String> pullEmailAddressesFromString (String input)
	{
		List<String> emails = new ArrayList<String>();
		Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(input);
	    while (m.find()) {
	        emails.add(m.group());
	    }
	    return emails;
	}

}
