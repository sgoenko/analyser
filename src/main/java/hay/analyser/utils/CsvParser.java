package hay.analyser.utils;

import java.util.ArrayList;
import java.util.List;

public class CsvParser {

	private static final char DEFAULT__SEPARATOR = ',';
	private static final char DEFAULT__QUOTE = '"';

	public static List<String> parseLine(String cvsLine) {
		return parseLine(cvsLine, DEFAULT__SEPARATOR, DEFAULT__QUOTE);
	}

	public static List<String> parseLine(String cvsLine, char separators) {
		return parseLine(cvsLine, separators, DEFAULT__QUOTE);
	}

	private static List<String> parseLine(String cvsLine, char separators, char customQuote) {
		List<String> result = new ArrayList<>();

		if (cvsLine == null || cvsLine.isEmpty()) {
			return result;
		}
		if (customQuote == ' ') {
			customQuote = DEFAULT__QUOTE;
		}
		if (separators == ' ') {
			separators = DEFAULT__SEPARATOR;
		}
		
		StringBuffer curVal = new StringBuffer();
		boolean inQuotes = false;
		boolean startCollectChar = false;
		boolean doubleQuotesInColumn = false;
		
		char[] chars = cvsLine.toCharArray();
		
		for (char ch : chars) {
			if (inQuotes) {
				startCollectChar = true;
				if (ch == customQuote) {
					inQuotes = false;
					doubleQuotesInColumn = false;
				} else {
					if (ch == '\"') {
						if (!doubleQuotesInColumn) {
							curVal.append(ch);
							doubleQuotesInColumn = true;
						}
					} else {
						curVal.append(ch);
					}
				}
			} else {
				if (ch == customQuote) {
					inQuotes = true;
					if (chars[0] != '"' && customQuote == '\"') {
						curVal.append('"');
					}
					if (startCollectChar) {
						curVal.append('"');
					}
				} else if (ch == separators) {
					result.add(curVal.toString().trim());
					curVal = new StringBuffer();
					startCollectChar = false;
				} else if (ch == '\r') {
					// ignore LF characters
					continue;
				} else if (ch == '\n') {
					// the end, break!
					break;
				} else {
					curVal.append(ch);
				}
			}
		}
		result.add(curVal.toString().trim());
		return result;
	}

}
