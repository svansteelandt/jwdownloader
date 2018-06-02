package be.sylvainvansteelandt.projects.jwdownloader;

public class JWUrlBuilder {
	
	public static String[] getPossiblePublicationURLs(String language, String publication, String year, String month){
		String[] urls = new String[256];
		
		String fixedpart = "http://download-a.akamaihd.net/files/media_magazines/";
		String pubcode = "";
		String langcode = "";
		String monthcode = "";
		String extracode = "";
		
		switch(publication){
			case "Ontwaakt!": pubcode = "g"; break;
			case "Wachttoren": pubcode = "wp"; break;
			case "Wachttoren Studie": pubcode ="w"; break;
		}
		
		switch(language){
			case "Frans": langcode = "F"; break;
			case "Nederlands": langcode = "O"; break;
			case "Engels": langcode = "E"; break;
		}
		
		switch(month){
			case "januari": monthcode = "01"; break;
			case "februari": monthcode = "02"; break;
			case "maart": monthcode = "03"; break;
			case "april": monthcode = "04"; break;
			case "mei": monthcode = "05"; break;
			case "juni": monthcode = "06"; break;
			case "juli": monthcode = "07"; break;
			case "augustus": monthcode = "08"; break;
			case "september": monthcode = "09"; break;
			case "oktober": monthcode = "10"; break;
			case "november": monthcode = "11"; break;
			case "december": monthcode = "12"; break;
		}
		
		if(Integer.parseInt(year) < 2016){
			if(publication.equalsIgnoreCase("Wachttoren")){
				extracode = "01";
			} else if(publication.equalsIgnoreCase("Wachttoren Studie")){
				extracode = "15";
			}
		}
		
		int l = 0;
		for(String hex : loopHex()){
			String url = fixedpart + hex + "/" + pubcode + "_" + langcode + "_" + year + monthcode + extracode + ".mp3.zip";
			urls[l] = url;
			l++;
		}
		
		return urls;
		
		
	}
	
	private static String[] loopHex(){
		String[] hex = new String[256];
		for(int i = 0; i < 16; i++){
			for(int j = 0; j < 16; j++){
				hex[((i * 16) + j)] = hexTranslation(i) + hexTranslation(j);
			}
		}
		return hex;
	}
	
	private static String hexTranslation(int i){
		String trans = "";
		switch(i){
			case 10: trans = "a"; break;
			case 11: trans = "b"; break;
			case 12: trans = "c"; break;
			case 13: trans = "d"; break;
			case 14: trans = "e"; break;
			case 15: trans = "f"; break;
			default: trans = i + ""; break;
		}
		return trans;
	}

}
