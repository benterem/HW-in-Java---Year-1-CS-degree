//Ben Terem_309981512
package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is only used to practice regular expressions.
 * @author talm
 *
 */
public class RegexpPractice {
	/**
	 * Search for the first occurrence of text between single quotes, return the text (without the quotes).
	 * Allow an empty string. If no quoted text is found, return null. 
	 * Some examples :
	 * <ul>
	 * <li>On input "this is some 'text' and some 'additional text'" the method should return "text".
	 * <li>On input "this is an empty string '' and another 'string'" it should return "".
	 * </ul>
	 * @param input
	 * @return the first occurrence of text between single quotes
	 */
	public static String findSingleQuotedTextSimple(String input) {
		//pattern and matcher intiallization
		Pattern p = Pattern.compile("\\'(.*?)\\'");
		Matcher m = p.matcher(input);
		//isolate the text without the single quoteatiions
		if(m.find()){
			return m.group(1);
		}
		return null;
	}
	
	/**
	 * Search for the first occurrence of text between double quotes, return the text (without the quotes).
	 * (should work exactly like {@link #findSingleQuotedTextSimple(String)}), except with double instead 
	 * of single quotes.
	 * @param input
	 * @return the first occurrence of text between double quotes
	 */
	public static String findDoubleQuotedTextSimple(String input) {
		//init pattern and matcher
		Pattern p = Pattern.compile("\"(.*?)\"");
		Matcher m = p.matcher(input);
		//text without quotes
		if(m.find()){
			return m.group(1);
		}
		return null;
	}
	
	/**
	 * Search for the all occurrences of text between single quotes <i>or</i> double quotes. 
	 * Return a list containing all the quoted text found (without the quotes). Note that a double-quote inside
	 * a single-quoted string counts as a regular character (e.g, on the string [quote '"this"'] ["this"] should be returned).  
	 * Allow empty strings. If no quoted text is found, return an empty list. 
	 * @param input
	 * @return
	 */
	public static List<String> findDoubleOrSingleQuoted(String input) {
		//init pattern and matcher
		Pattern p = Pattern.compile("(\"|')(.*?)\\1");
		Matcher m = p.matcher(input);
		//init list
		List<String> reg = new ArrayList<String>();
		//get text without the quotes
		while(m.find()){
			reg.add(m.group(2));
		}
		return reg;
	}

	/**
	 * Separate the input into <i>tokens</i> and return them in a list.
	 * A token is any mixture of consecutive word characters and single-quoted strings (single quoted strings
	 * may contain any character except a single quote).
	 * The returned tokens should not contain the quote characters. 
	 * A pair of single quotes is considered an empty token (the empty string).
	 *
	 * For example, the input "this-string 'has only three tokens'" should return the list
	 * {"this", "string", "has only three tokens"}. 
	 * The input "this*string'has only two@tokens'" should return the list 
	 * {"this", "stringhas only two@tokens"}
	 * 
	 * @param input
	 * @return
	 */
	public static List<String> wordTokenize(String input) {
		//init matcher, pattern and list
		Pattern p = Pattern.compile("\\w*?(\\'[^\\']*\\')+|\\w+?\\b");
		Matcher m = p.matcher(input);
		List<String> reg = new ArrayList<String>();
		//replace all single quotes with empty word
		while(m.find()){
			reg.add(m.group(0).replaceAll("\\'", ""));
		}
		return reg;
	}

	/**
	 * Parse a date string with the following general format:<br> 
	 * Wdy, DD-Mon-YYYY HH:MM:SS GMT<br>
	 * Where:
	 * 	 <i>Wdy</i> is the day of the week,
	 * 	 <i>DD</i> is the day of the month, 
	 *   <i>Mon</i> is the month,
	 *   <i>YYYY</i> is the year, <i>HH:MM:SS</i> is the time in 24-hour format,
	 *   and <i>GMT</i> is a the constant timezone string "GMT".
	 * 
	 * You should also accept variants of the format: 
	 * <ul>
	 * <li>a date without the weekday, 
	 * <li>spaces instead of dashes (i.e., "DD Mon YYYY"), 
	 * <li>case-insensitive month (e.g., allow "Jan", "JAN" and "jAn"),
	 * <li>a two-digit year (assume it's between 1970 and 2069 in that case)
	 * <li>a missing timezone
	 * <li>allow multiple spaces wherever a single space is allowed.
	 * </ul>
	 *     
	 * The method should return a java {@link Calendar} object with fields  
	 * set to the corresponding date and time. Return null if the input is not a valid date string. For validity
	 * checking, consider only <i>local</i> validity: that is, only checks that don't require connecting different
	 * parts of the date. For example, the weekday string "XXX" is invalid, but "Fri, 09-Jun-2015" is considered valid
	 * even though June 9th was a Tuesday. In the same way, "40-Feb-2015" is invalid, but "31-Feb-2015" is ok because
	 * you have to know the month is February in order to realize that 31 is not a possible day-of-the-month. 
	 * @param input
	 * @return
	 */
	public static Calendar parseDate(String input) {
		List<String> months = Arrays.asList("jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec");
		//added an additinal list to determine correct days
		List<String> days = Arrays.asList("sun", "mon", "tue", "wed", "thu", "fri", "sat");
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		//init pattern, matcher
		Pattern p = Pattern.compile("(([A-Z][a-z]{2}),\\s+)?(([0-2][0-9])|3[0-1])(-|\\s+)([A-Za-z]{3})(-|\\s+)" +
				"((19[7-9][0-9])|(20[0-6][0-9])|[0-9]{2})\\s+(([0-1][0-9])|(2[0-3])):([0-5][0-9]):([0-5][0-9])(\\s+GMT)?$");
		Matcher m = p.matcher(input);
		if(m.find()){
			//set up some variables for testing certain criteria
			int year = Integer.parseInt(m.group(8));
			String timezone = m.group(16);
			String weekday= m.group(2);
			int month = months.indexOf(m.group(6).toLowerCase());
			//if random three letter string => not valid
			if(month == -1){
				return null;
			}
			//in the case of a two year format, add appropriate prefix
			if(year < 61){
				year+=2000;
			}else if(year <99){
				year+=1900;
			}
			//set cal entry
			cal.set(year, month,  Integer.parseInt(m.group(3)),
					Integer.parseInt(m.group(11)),Integer.parseInt(m.group(14)),Integer.parseInt(m.group(15)));
			//ambigous to TMZ but still need to init if we're given one
			if(timezone != null){
				cal.setTimeZone(TimeZone.getTimeZone(timezone));
			}
			return cal;
		}

		return null;
	}

	/**
	 * Search for the all occurrences of text between single quotes, but treating "escaped" quotes ("\'") as
	 * normal characters. Return a list containing all the quoted text found (without the quotes, and with the quoted escapes
	 * replaced). 
	 * Allow empty strings. If no quoted text is found, return an empty list. 
	 * Some examples :
	 * <ul>
	 * <li>On input "'This is not wrong' and 'this is isn\'t either", the method should return a list containing 
	 * 		("This is not wrong" and "This isn't either").
	 * <li>On input "No quoted \'text\' here" the method should return an empty list.
	 * </ul>
	 * @param input
	 * @return all occurrences of text between single quotes, taking escaped quotes into account.
	 */
	public static List<String> findSingleQuotedTextWithEscapes(String input) {
		//init pattern and matcher and a list
		Pattern p = Pattern.compile("(^|[^\\\\]|\\G)'(((.*?)[^\\\\])??)'");
		Matcher m = p.matcher(input);
		List<String> reg = new ArrayList<String>();
		//get text in right format, replace \' with just '
		while(m.find()){
			reg.add(m.group(2).replaceAll("\\\\'", "'"));
		}
		return reg;
	}
	
	/**
	 * Search for the all occurrences of text between single quotes, but treating "escaped" quotes ("\'") as
	 * normal characters. Return a list containing all the quoted text found (without the quotes, and with the quoted escapes
	 * replaced). 
	 * Allow empty strings. If no quoted text is found, return an empty list. 
	 * Some examples :
	 * <ul>
	 * <li>On input "'This is not wrong' and 'this is isn\'t either", the method should return a list containing 
	 * 		("This is not wrong" and "This isn't either").
	 * <li>On input "No quoted \'text\' here" the method should return an empty list.
	 * </ul>
	 * @param input
	 * @return all occurrences of text between single quotes, taking escaped quotes into account.
	 */
	public static List<String> findDoubleQuotedTextWithEscapes(String input) {
		//init pattern, matcher, list
		Pattern p = Pattern.compile("(^|[^\\\\]|\\G)\"(((.*?)[^\\\\])??)\"");
		Matcher m = p.matcher(input);
		List<String> reg = new ArrayList<String>();
		//add correct format, replace \" with "
		while(m.find()){
			reg.add(m.group(2).replaceAll("\\\\\"", "\""));
		}
		return reg;
	}
	
	
	/**
	 * A class that holds an attribute-value pair.
	 * 
	 * Attributes are "HTTP tokens": a sequence of non-special non-whitespace
	 * characters. The special characters are control characters, space, tab and the characters from the following set: <pre>
	 * ()[]{}'"<>@,;:\/?=
	 * </pre>
	 * Values are arbitrary strings and may be missing.
	 */
	public static class AVPair {
		public String attr;
		public String value;
		
		public AVPair(String attr, String value) {
			this.attr = attr;
			this.value = value;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj == null || (!(obj instanceof AVPair)))
				return false;
			AVPair other = (AVPair) obj;
			if (attr == null) {
				if (other.attr != null)
					return false;
			} else if (!attr.equals(other.attr))
				return false;
			if (value == null) {
				if (other.value != null)
					return false;
			} else if (!value.equals(other.value))
				return false;
			return true;
		}
		
		@Override
		public String toString() {
			if (value != null)
				return attr + "=\"" + value.replaceAll("\"", "\\\"") + "\"";
			else 
				return attr;
		}
	}
	
	/**
	 * Parse the input into a list of attribute-value pairs.
	 * The input should be a valid attribute-value pair list: attr=value; attr=value; attr; attr=value...
	 * If a value exists, it must be either an HTTP token (see {@link AVPair}) or a double-quoted string.
	 * 
	 * (Note: for the second of the bonus question, implement the {@link RegexpPractice#parseAvPairs2(String)} 
	 * method.)
	 * @param input
	 * @return
	 */
	public static List<AVPair> parseAvPairs(String input) {
		// TODO: implement
		return null;
	}	
	
	/**
	 * Parse the input into a list of attribute-value pairs.
	 * The input should be a valid attribute-value pair list: attr=value; attr=value; attr; attr=value...
	 * If a value exists, it must be either an HTTP token (see {@link AVPair}) or a double-quoted string.
	 * 
	 * This  method should return null if the input is not a list of attribute-value pairs with the format specified above.
	 * (this is for the second part of the bonus question).
	 * @param input
	 * @return
	 */
	public static List<AVPair> parseAvPairs2(String input) {
		// TODO: implement
		return null;
	}
}
