package be.sylvainvansteelandt.projects.jwdownloader;

public class JWUtility {
	
	public static final String[] ALL_MONTHS = new String[] { "januari", "februari", "maart", "april", "mei", "juni", "juli",
															"augustus", "september", "oktober", "november", "december" };
	
	public static String getMonthFromAllMediaUrl(String url, String year) {
		String findString = "issue=" + year;
		int indexIssue = url.indexOf(findString);
		if(indexIssue < 0) {
			return null;
		} else {
			try {
				String monthCode = url.substring(indexIssue + findString.length(), indexIssue + findString.length() + 2);
				return JWUtility.monthCodeToMonth(monthCode);
			}
			catch (Exception e) {
				return null;
			}
		}
	}
	
	public static String monthCodeToMonth(String monthCode) {		
		String month = null;
		switch(monthCode){
			case "01": month = "januari"; break;
			case "02": month = "februari"; break;
			case "03": month = "maart"; break;
			case "04": month = "april"; break;
			case "05": month = "mei"; break;
			case "06": month = "juni"; break;
			case "07": month = "juli"; break;
			case "08": month = "augustus"; break;
			case "09": month = "september"; break;
			case "10": month = "oktober"; break;
			case "11": month = "november"; break;
			case "12": month = "december"; break;
			default: month = null;
		}
		return month;
	}
	
	public static String monthToMonthCode(String month) {
		String monthcode = null;
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
			default: monthcode = null;
		}
		return monthcode;
	}
	
	public static String publicationToPublicationCode(String publication) {
		String pubcode = null;
		switch(publication){
			case "Ontwaakt!": pubcode = "g"; break;
			case "Wachttoren": pubcode = "wp"; break;
			case "Wachttoren Studie": pubcode ="w"; break;
			default: pubcode = null;
		}
		return pubcode;
	}
	
	public static String languageToLanguageCode(String language) {
		String langcode = null;
		switch(language){
			case "Frans": langcode = "F"; break;
			case "Nederlands": langcode = "O"; break;
			case "Engels": langcode = "E"; break;
			default: langcode = null;
		}
		return langcode;
	}
	
	public static String removeStartingTrailingQuotes(String text) {
		if (text.startsWith("\"")) {
			text = text.substring(1, text.length());
	    }
		if (text.endsWith("\"")) {
			text = text.substring(0, text.length() - 1);
	    }
		return text;
	}
}