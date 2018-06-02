package be.sylvainvansteelandt.projects.jwdownloader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class JWUrlDownloader {

	private static boolean urlExists(String url) {
		URL u;
		try {
			u = new URL(url);
		} catch (MalformedURLException e1) {
			return false;
		}
		HttpURLConnection huc;
		try {
			huc = (HttpURLConnection) u.openConnection();
		} catch (IOException e) {
			return false;
		}
		try {
			huc.setRequestMethod("HEAD");
		} catch (ProtocolException e) {
			return false;
		}
		try {
			huc.connect();
		} catch (IOException e) {
			return false;
		}
		int code;
		try {
			code = huc.getResponseCode();
			if (code == 200) {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			return false;
		}
	}

	public static String findExistingURL(String[] urls) {
		
		Optional<String> result = Stream.of(urls).parallel().filter( url -> urlExists(url) == true).findFirst();
		
		if(result.isPresent()){
			return result.get();
		} else {
			return null;
		}
	}

	public static void downloadURL(String url, String destination) {
		try {
			FileUtils.copyURLToFile(new URL(url), new File(destination));
		} catch (IOException e) {
			System.out.println("error");
		}
	}

	public static String downloadUsingNIO(String urlStr) throws IOException {
		URL url = new URL(urlStr);
		
		HttpURLConnection huc = (HttpURLConnection) url.openConnection();
		huc.setRequestMethod("GET");
		String home = System.getProperty("user.home");
		String filename = StringUtils.substringBetween(huc.getHeaderField("Content-Disposition"), "\"", "\"");
		File destinationFile = new File(home + "/Downloads/" + filename);
		
		FileUtils.copyURLToFile(url, destinationFile);
		
		return home + "/Downloads/" + filename;
	}

	public static String downloadFile(String source) throws IOException {
		// initialize local vars
		int dataread = 0;
		int CHUNK_SIZE = 8192; // TCP/IP packet size
		byte[] dataChunk = new byte[CHUNK_SIZE]; // byte array for storing
													// temporary data.

		// create file and stream to write the file (throws IOException)

		URL url = new URL(source);
		HttpURLConnection huc = (HttpURLConnection) url.openConnection();
		huc.setRequestMethod("GET");
		String home = System.getProperty("user.home");
		//String filename = StringUtils.substringBetween(huc.getHeaderField("Content-Disposition"), "\"", "\"");
		String filename = "temp.zip";
		File destinationFile = new File(home + "\\Downloads\\" + filename);
		FileOutputStream fos = new FileOutputStream(destinationFile);
		BufferedOutputStream bos = new BufferedOutputStream(fos);

		// receive the file using a BufferedReader
		BufferedInputStream bis = new BufferedInputStream(huc.getInputStream());

		while (dataread >= 0) {
			dataread = bis.read(dataChunk, 0, CHUNK_SIZE);
			// only write out if there is data to be read
			if (dataread > 0)
				bos.write(dataChunk, 0, dataread);
		}

		// don't forget to close the streams
		bis.close();
		bos.close();
		
		return home + "\\Downloads\\" + filename;
	}

}
