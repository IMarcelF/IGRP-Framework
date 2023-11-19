package nosi.core.webapp.helpers;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author: Emanuel Pereira
 * 25 Oct 2017
 */
public class StringHelper {

	private StringHelper(){}

	/*Camel Case to first letter of string
	 * 
	 * listpage => Listpage
	 */
	public static String camelCaseFirst(String string){
		return string.substring(0, 1).toUpperCase() + string.substring(1);
	}
	
	public static String camelCase(String string,String regex,String replacement) {
		return camelCase(string.replace(regex, replacement));
	}
	
	/*Removel all space of string
	 * List page => Listpage
	 */
	public static String removeSpace(String string){
		return string.replaceAll("\\s+", "");
	}
	
	/*Camel Case to string
	 * List page => List Page
	 */
	public static String camelCase(String string){
		String[] strParts = string.split("\\s+");
		StringBuilder result = new StringBuilder();
		for(String s:strParts)
			result.append(StringHelper.camelCaseFirst(s));
		return result.toString();
	}
	
	/*Validade the className for Java Class
	 * 
	 */
	public static boolean validateClassName(String className){
		return className.matches("([a-zA-Z_$][a-zA-Z\\d_$]*\\.)*[a-zA-Z_$][a-zA-Z\\d_$]*");
	}
	
	public static String removeSpecialCharaterAndSpace(String string) {
		string = string.replaceAll("[^a-zA-Z0-9]", "_");
		string = removeSpace(string);
		return string;
	}

	public static String decode(String headerText) {
		try {
			final Charset utf8 = StandardCharsets.UTF_8;
			headerText = new String(headerText.getBytes(utf8), utf8.name());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return headerText;
	}

}
