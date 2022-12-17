package be.sylvainvansteelandt.projects.jwdownloader;

public class JWUrlBuilder {
	
	public static String getPublicationMediaUrl(String language, String publication, String year, String month) {
		String fixedpart = "https://b.jw-cdn.org/apis/pub-media/GETPUBMEDIALINKS?output=json&fileformat=MP3&";
		String pubcode = JWUtility.publicationToPublicationCode(publication);
		String langcode = JWUtility.languageToLanguageCode(language);
		String monthcode = JWUtility.monthToMonthCode(month);
		
		return fixedpart + "issue=" + year + monthcode + "&pub=" + pubcode + "&langwritten=" + langcode;
	}	
}