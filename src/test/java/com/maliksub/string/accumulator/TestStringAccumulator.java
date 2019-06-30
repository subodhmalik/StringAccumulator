package com.maliksub.string.accumulator;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Unit test for StringAccumulator
 */
public class TestStringAccumulator 
{
	private StringAccumulator strAccObj;
	@Rule public ExpectedException exception=ExpectedException.none();
    /**
     * Create the test case
     */
	@Before
	public void setup()
	{
		strAccObj=new StringAccumulator();
	}

    
	

    /**
     * Test Empty String 
     */
	@Test
    public void testAddForEmptyString()
    {
		int iOutput = strAccObj.add("");
		assertEquals(0, iOutput);
    }
	
    /**
     * Test Add 3 values with Single \n Delimiter
     */
	@Test
    public void testAddForSingleDelimiter()
    {
		int iOutput = strAccObj.add("1\n2\n3");
		assertEquals(6, iOutput);
    }
	
	/**
     * Test Add 3 values with Single , Delimiter
     */
	@Test
    public void testAddForCommaDelimiter()
    {
		int iOutput = strAccObj.add("1,2,3");
		assertEquals(6, iOutput);
    }
	
	/**
     * Test Add 3 values with comma and new line Delimiter
     */
	@Test
    public void testAddForCommaNewLineDelimiter()
    {
		int iOutput = strAccObj.add("1,2\n3");
		assertEquals(6, iOutput);
    }
    /**
     * Test unknown number of inputs and new lines - 2 and 3
     */
	@Test
    public void testChangeDelimiter()
    {
		int iOutput = strAccObj.add("//*\n1*2*3");
		assertEquals(6, iOutput);
    }
	
	
    /**
     * Test Multiple delimiters
     */
	@Test
    public void testAddForMultipleDelimiters()
    {
		int iOutput = strAccObj.add("//*|;\n1*2;3");
		assertEquals(6, iOutput);
    }
	
	
    /**
     * Test MultiLength delimiter
     */
	@Test
    public void testAddForMultiLengthCustomDelimiter()
    {
		int iOutput = strAccObj.add("//***\n1***2***3");
		assertEquals(6, iOutput);
    }
	
	/**
     * Test MultiLength multiple delimiters
     */
	@Test
    public void testAddForMultiLengthMultipleDelimiters()
    {
		int iOutput = strAccObj.add("//**|;;\n1**2;;3");
		assertEquals(6, iOutput);
    }
	
    /**
     * Test Custom Delimiter - Pipe
     */
	@Test
    public void testAddForPipeDelimiter()
    {
		int iOutput = strAccObj.add("//|\n6|10|4");
		assertEquals(20, iOutput);
    }
	
	
    /**
     * Test Empty String 
     */
	@Test
    public void testAddForNegativeNumber()
    {
		exception.expect(RuntimeException.class);
		exception.expectMessage("Negatives not allowed, Following were found ::-3");
		strAccObj.add("1,-3,4");
		
    }
	
	
    /**
     * Test that more than 1000 gets skipped
     */
	@Test
    public void testAddForMoreThanThousand()
    {
		int iOutput = strAccObj.add("//*\n100*1002*2*3");
		assertEquals(105, iOutput);
    }
}
