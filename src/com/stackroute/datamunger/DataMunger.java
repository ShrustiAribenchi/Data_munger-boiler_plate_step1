package com.stackroute.datamunger;

import java.util.ArrayList;
import java.util.Arrays;

/*There are total 5 DataMungertest files:
 * 
 * 1)DataMungerTestTask1.java file is for testing following 3 methods
 * a)getSplitStrings()  b) getFileName()  c) getBaseQuery()
 * 
 * Once you implement the above 3 methods,run DataMungerTestTask1.java
 * 
 * 2)DataMungerTestTask2.java file is for testing following 3 methods
 * a)getFields() b) getConditionsPartQuery() c) getConditions()
 * 
 * Once you implement the above 3 methods,run DataMungerTestTask2.java
 * 
 * 3)DataMungerTestTask3.java file is for testing following 2 methods
 * a)getLogicalOperators() b) getOrderByFields()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask3.java
 * 
 * 4)DataMungerTestTask4.java file is for testing following 2 methods
 * a)getGroupByFields()  b) getAggregateFunctions()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask4.java
 * 
 * Once you implement all the methods run DataMungerTest.java.This test case consist of all
 * the test cases together.
 */

public class DataMunger {

	/*
	 * This method will split the query string based on space into an array of words
	 * and display it on console
	 */

	public String[] getSplitStrings(String queryString) {
		
		String newString=queryString.toLowerCase();
		String str[]=newString.split(" ");
		System.out.print(str);
		return str;
	}

	/*
	 * Extract the name of the file from the query. File name can be found after a
	 * space after "from" clause. Note: ----- CSV file can contain a field that
	 * contains from as a part of the column name. For eg: from_date,from_hrs etc.
	 * 
	 * Please consider this while extracting the file name in this method.
	 */

	public String getFileName(String queryString) {
		int start=queryString.indexOf("from") +5;
		int end=queryString.indexOf("csv") +3;
		String finalString=queryString.substring(start,end);
		return finalString;
	}

	/*
	 * This method is used to extract the baseQuery from the query string. BaseQuery
	 * contains from the beginning of the query till the where clause
	 * 
	 * Note: ------- 1. The query might not contain where clause but contain order
	 * by or group by clause 2. The query might not contain where, order by or group
	 * by clause 3. The query might not contain where, but can contain both group by
	 * and order by clause
	 */
	
	public String getBaseQuery(String queryString) {
		String[] matches=new String[] {"where","order by","group by"};
		String end =queryString;
		for(String s: matches) 
		{
			if(end.toLowerCase().contains(s))
			{
				 end=end.substring(0, end.toLowerCase().indexOf(s));
			}		
		}
		
		return end.trim();
	}

	/*
	 * This method will extract the fields to be selected from the query string. The
	 * query string can have multiple fields separated by comma. The extracted
	 * fields will be stored in a String array which is to be printed in console as
	 * well as to be returned by the method
	 * 
	 * Note: 1. The field name or value in the condition can contain keywords
	 * as a substring. For eg: from_city,job_order_no,group_no etc. 2. The field
	 * name can contain '*'
	 * 
	 */
	
	
	public String[] getFields(String queryString) {
		String string[] = queryString.split(" ");
		String qq[] = {"",""};
		
		for(int i=0;i<string.length;i++)
		{
						
			if(string[i].contains(","))
			{
				qq= string[i].split(",");
			}
			
			else if(string[i].contains("*"))
			{
				return new String[]{"*"};
			}
		}

		return qq;
	}
	
	/*
	 * This method is used to extract the conditions part from the query string. The
	 * conditions part contains starting from where keyword till the next keyword,
	 * which is either group by or order by clause. In case of absence of both group
	 * by and order by clause, it will contain till the end of the query string.
	 * Note:  1. The field name or value in the condition can contain keywords
	 * as a substring. For eg: from_city,job_order_no,group_no etc. 2. The query
	 * might not contain where clause at all.
	 */
	
	public String getConditionsPartQuery(String queryString) {
		queryString = queryString.toLowerCase();
		String str[] = queryString.split(" ");
		String ans="";

		if(!queryString.contains("where"))
		{
			return null;
		}

		for(int i=0;i<str.length;i++)
		{
			if(str[i].equals("where"))
			{
				for(int j=i+1;j<str.length;j++)
				{
					if(str[j].equals("order") || str[j].equals("group") )
					{
						break;
					}
					else
					{
						ans = ans + " " + str[j];
					}
				}
				break;
			}

		}
		System.out.println(ans);
		return ans.trim();
	}
	
	


	/*
	 * This method will extract condition(s) from the query string. The query can
	 * contain one or multiple conditions. In case of multiple conditions, the
	 * conditions will be separated by AND/OR keywords. for eg: Input: select
	 * city,winner,player_match from ipl.csv where season > 2014 and city
	 * ='Bangalore'
	 * 
	 * This method will return a string array ["season > 2014","city ='bangalore'"]
	 * and print the array
	 * 
	 * Note: ----- 1. The field name or value in the condition can contain keywords
	 * as a substring. For eg: from_city,job_order_no,group_no etc. 2. The query
	 * might not contain where clause at all.
	 */

	public String[] getConditions(String queryString) {
		queryString = queryString.toLowerCase();
		int start = queryString.indexOf("where");

		if(start == -1)
		{
			return null;
		}

		String string[] = queryString.split("where");
		String query[] = string[1].trim().split("group by | order by");
		String query1[] = query[0].trim().split(" and | or ");

		System.out.println(Arrays.toString(query1));
		return query1;
	}

	/*
	 * This method will extract logical operators(AND/OR) from the query string. The
	 * extracted logical operators will be stored in a String array which will be
	 * returned by the method and the same will be printed Note:  1. AND/OR
	 * keyword will exist in the query only if where conditions exists and it
	 * contains multiple conditions. 2. AND/OR can exist as a substring in the
	 * conditions as well. For eg: name='Alexander',color='Red' etc. Please consider
	 * these as well when extracting the logical operators.
	 * 
	 */

	public String[] getLogicalOperators(String queryString) {
		queryString=queryString.toLowerCase();
		String[] words = queryString.split(" ");
		String getL="";
		if(queryString.contains("where")) {
			for(int i=0;i<words.length;i++) {
				if(words[i].matches("and|or|not")) {
					getL+=words[i] +" ";
				}
			}
			return getL.toString().split(" ");
		}

		return null;
	}

	/*
	 * This method extracts the order by fields from the query string. Note: 
	 * 1. The query string can contain more than one order by fields. 2. The query
	 * string might not contain order by clause at all. 3. The field names,condition
	 * values might contain "order" as a substring. For eg:order_number,job_order
	 * Consider this while extracting the order by fields
	 */

	public String[] getOrderByFields(String queryString) {
		queryString=queryString.toLowerCase();
		String[] answer=null;
		if(queryString.contains("order by")) {
			int orderby=queryString.indexOf("order by");
			String order=queryString.substring(orderby + 9);
			answer=order.split(" ");
		}
		
		return answer;
	}

	/*
	 * This method extracts the group by fields from the query string. Note:
	 * 1. The query string can contain more than one group by fields. 2. The query
	 * string might not contain group by clause at all. 3. The field names,condition
	 * values might contain "group" as a substring. For eg: newsgroup_name
	 * 
	 * Consider this while extracting the group by fields
	 * 
	 * 	String st=queryString;
		String s[]= queryString.split(" ");
		
		while(true) {
		
		if(st.contains("group by")) {
			s=st.split("group by");
		}
		
		if(st.indexOf("group by")==-1) {
			break;
		}
		
		}
		return s;
	 */

	public String[] getGroupByFields(String queryString) {
		if(!queryString.contains("group by"))
		{
			return null;
		}
		String str1[] = queryString.split("group by");
		String str[] = str1[1].trim().split(" ");

		return str;
	}

	/*
	 * This method extracts the aggregate functions from the query string. Note:
	 *  1. aggregate functions will start with "sum"/"count"/"min"/"max"/"avg"
	 * followed by "(" 2. The field names might
	 * contain"sum"/"count"/"min"/"max"/"avg" as a substring. For eg:
	 * account_number,consumed_qty,nominee_name
	 * 
	 * Consider this while extracting the aggregate functions
	 */


	public String[] getAggregateFunctions(String queryString) {
		if (queryString.contains("count") || queryString.contains("avg") || queryString.contains("sum") || queryString.contains("max") || queryString.contains("min")) {
			ArrayList<String> array = new ArrayList<String>();
			String[] str = queryString.split(" ")[1].trim().split(",");

			for (int i = 0; i < str.length; i++) {
				if (str[i].contains("(")) {
					array.add(str[i]);
				}
			}
			return array.toArray(new String[array.size()]);
		}
		return null;
	}
}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			