package com.maliksub.string.accumulator;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.collections.CollectionUtils;
import org.apache.maven.shared.utils.StringUtils;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Subodh A Basic String accumulator class
 *
 */
public class StringAccumulator {

	private static final String DOUBLE_FWD_SLASH = "//";
	private static final String PIPE = "|";
	private static final String COMMA = ",";
	private static final String NEWLINE = "\n";

	/**
	 * A method which first of identifies the delimiters using splitting
	 * Once identified, it add delimiters with original 2 - Comma and New Line
	 * Thereafter, just split the string and get list
	 * 
	 * @param numbers - The String input (it is assumed that input is validated as per the requirement doc)
	 * @return - an int which is count of numbers 0-1000
	 * @throws RuntimeException - if numbers are negative 
	 */
	public int add(String numbers)
	{
		List<Integer> lstNumbers = new ArrayList<Integer>();
		List<Integer> lstNegativeNums = new ArrayList<Integer>();

		// Max 4 possible delimiters - COMMA, NEWLINE and/OR max 2 mentioned after // as desired by user 
		List<String> lDelimiters= new ArrayList<String>();
		lDelimiters.add(COMMA);
		lDelimiters.add(NEWLINE);
		String strNumsSeparatedByDelimiters=numbers;
		// checking first 2 params to identify delimiter
		if(StringUtils.isNotEmpty(numbers) && DOUBLE_FWD_SLASH.equals(numbers.substring(0, 2))) 
		{
			// Below is necessary to change the target String so that we have numbers separated by delimiters only
			strNumsSeparatedByDelimiters=numbers.substring(numbers.indexOf(NEWLINE), numbers.length());
			// This one gives me the delimiters
			String strProbableDelimiters=numbers.substring(2,numbers.indexOf(NEWLINE));
			// split in case of multiple
			String[] strTempDelimiters = strProbableDelimiters.split("\\|");
			// Scenario - size 0 indicates | itself is the delimiter
			// or else 2 delimiters will be there in list
			if(strTempDelimiters==null || CollectionUtils.sizeIsEmpty(strTempDelimiters))
			{
				lDelimiters.add(PIPE);
			}
			else
			{
				lDelimiters.addAll(Arrays.asList(strTempDelimiters));
			}
		}
		List<String> lstAllNumbers= Arrays.asList(StringUtils.split(strNumsSeparatedByDelimiters, lDelimiters.stream().map(i -> i.toString()).collect(Collectors.joining())));
		// not checking input validation or else a check can be to ensure exact 3 integer values are there in lstAllNumbers
		lstAllNumbers.stream().map(Integer::parseInt).forEach(item ->{
			// Only want one of below conditions to be evaluated thats why if > else if > else
			if(item<0)
			{
				lstNegativeNums.add(item);
			}
			else if(item>1000)
			{
				// do nothing
			}else
			{
				lstNumbers.add(item);	
			}
		});
		// In case a negative number is found, we abort there and then
		// Exception could have been user defined but this serves the purpose
		// too
		if (CollectionUtils.isNotEmpty(lstNegativeNums)) {
			throw new RuntimeException("Negatives not allowed, Following were found ::"+lstNegativeNums.stream().map(i->i.toString()).collect(Collectors.joining()));
		}

		return lstNumbers.stream().reduce(0, Integer::sum);		

	}

	/**
	 * an alternate method which Receives String input which could have anything and numerals
	 * Instead of finding delimiters, this identifies numerals along with negative
	 * numbers Thus makes it easier to achieve the requirement of having any
	 * number of delimiters
	 * 
	 * @param strInput
	 * @return
	 */
	public int addIgnoringDelimiters(String numbers) {

		List<Integer> lstNumbers = new ArrayList<Integer>();
		List<Integer> lstNegativeNums = new ArrayList<Integer>();
		Pattern p = Pattern.compile("-?\\d+");
		Matcher m = p.matcher(numbers);
		if (m.groupCount() > 3) {
			throw new RuntimeException("More Than 3 Numbers found");
		}
		while (m.find()) {
			Integer i = Integer.parseInt(m.group());
			// Only want one of below conditions to be evaluated thats why if >
			// else if > else
			if (i < 0) {
				lstNegativeNums.add(i);
			} else if (i > 1000) {
				continue; // skip doing anything, just ignore
			} else {
				lstNumbers.add(i);
			}

		}

		// In case a negative number is found, we abort there and then
		// Exception could have been user defined but this serves the purpose
		// too
		if (CollectionUtils.isNotEmpty(lstNegativeNums)) {
			throw new RuntimeException("Negative Numbers found ::" + lstNegativeNums);
		}

		return lstNumbers.stream().reduce(0, Integer::sum);
	}

}
