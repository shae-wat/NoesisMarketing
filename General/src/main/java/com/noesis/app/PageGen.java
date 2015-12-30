package com.crosslink;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.collections.CollectionUtils;

import au.com.bytecode.opencsv.CSVReader;

public class PageGen {

	private static String dataDir = "/";
	// attribute columns start with att-<att name> , and attribute columns should be all together
	private static String itemFileLoc = dataDir + "items.txt"; // tab delimited format
	
	public enum PageType
	{
	  HOMEPAGE, LINKPAGE, LISTPAGE, DETAILPAGE
	}
	
	List<String> endStatesList = null;
	List<String> attributeNames = null;
	List<Page> pages = new ArrayList<Page>();
	
	
	public static void main (String args[])
	{
		PageGen pg = new PageGen();
		pg.genPagesList();
		
		// Page table is built, output values
		int pageCount = 0;
		
		
		// Build the cross linking
		pg.genCrossLinksAndLinkCounts();
		
		// Build the page data, title, headlines ...
		
		
		// Put data into template files ...
		
		
		
		// output for testing
//		for (Page page : pg.pages)
//		{
//			System.out.println(pageCount++);
//			System.out.println(page.attTypeComboStr);
//			System.out.println(page.attComboStr);
//			System.out.println("    Page type " + page.pageType);
//			if (page.sections != null)
//			{
//				for (LinkSection ls : page.sections)
//				{
//					System.out.println("        LS : " + ls.getSectionTitle());
//				}
//			}
//		}

	}
	
	private void genCrossLinksAndLinkCounts()
	{
		// Calculate counts for powersets of all end states
		System.out.println("Processing powersets and counts for end states");
		System.out.println("    " + this.endStatesList.size() + " end states being processed, end states : ");
		
		// frequencymap holds the count of each variation on end states
		HashMap<String,Integer> frequencymap = new HashMap<String,Integer>();
		for (String endState : this.endStatesList)
		{
			Set<String> tokens = new HashSet(getTokens(endState));
			Set<Set<String>> endStatesPowerSets = this.powerSet(tokens);
			
			for (Set<String> oneSet : endStatesPowerSets)
			{
				String variation = "";
				for (String part : oneSet)
				{
					variation = variation + part + "-";
				}
				
				if(frequencymap.containsKey(variation)) {
				    frequencymap.put(variation, frequencymap.get(variation)+1);
				  }
				else{ frequencymap.put(variation, 1); }
			}
		}
		
		// frequency map now has counts of all powerset outputs 
		// TEST ONLY - OUTPUT END STATE POWERSET COUNTS
//		System.out.println("END STATE POWERSET COUNTS ...");
//		for (String esps : frequencymap.keySet())
//		{
//			System.out.println("    " + esps + " : " + frequencymap.get(esps));
//		}
		
		// for each of the page entries calculate all links and sections
		// can use the frequencymap above to order links and determine if show 0 links at all
		for (Page page : this.pages)
		{
			System.out.println("Processing xLinks for page " + page.attComboStr + " for att names " + page.attTypeComboStr);
			List<String> thisPageAttNames = this.getTokens(page.attTypeComboStr);
			List<String> diffAttributeNames = this.attributeNames;
			int pageAttCount = thisPageAttNames.size();
			int totalAttCount = this.attributeNames.size();
			int pageLevel = totalAttCount - pageAttCount;
			System.out.print("    This page attributes -> ");
			for (String pageAtt : thisPageAttNames) { System.out.print("  " + pageAtt); }
			System.out.println();
			System.out.print("    World attributes -> ");
			for (String worldAtt : this.attributeNames) { System.out.print("  " + worldAtt); }
			System.out.println();
			
			
			
			// set the page type
			if (pageLevel == 0) { page.pageType = PageType.DETAILPAGE; }
			else if (pageLevel == 1) { page.pageType = PageType.LISTPAGE; }
			else if (pageLevel == totalAttCount) { page.pageType = PageType.HOMEPAGE; }
			else { page.pageType = PageType.LINKPAGE; }
			
			if (page.pageType == PageType.LINKPAGE || page.pageType == PageType.LISTPAGE)
			{
				// determine the link sections
				
				diffAttributeNames = (List<String>) CollectionUtils.subtract(this.attributeNames, thisPageAttNames);
				
				System.out.print("    Diff attributes (potential link sections -> ");
				for (String diffAtt : diffAttributeNames) { System.out.print("  " + diffAtt); }
				System.out.println();
				List<String> linkSections = diffAttributeNames;
				for (String linkSection : linkSections)
				{
					page.addLinkSection(linkSection);
				}
			}
		}
		
		
		
	}
	
	private List<String> getTokens (String input)
	{
		StringTokenizer st = new StringTokenizer(input, "-");
		List<String> output = new ArrayList<String>();
		while (st.hasMoreTokens())
		{
			output.add(st.nextToken());
		}
		return output;
	}
	
	private void genPagesList ()
	{
		try {
			File outputDir = getOutputDir();
			File itemFile = new File(itemFileLoc);
			
			// Pull the list of attributes from source file
			// Method also sets a class level variable that tracks end states
			List<AttributeSet> attSets = buildPageTable(itemFile);
			
			// Get all the combinations of how the attribute names can be put together
			// This is not the combination of values, but combo of attributes (powerset)
			Set<Integer> mySet = new HashSet<Integer>();
			for (int i = 0; i < attSets.size(); i++)
			{
				mySet.add(i);
			}
			Set<Set<Integer>> nameSets = this.powerSet(mySet);
			// Report name set combos
			System.out.println("Name sets combos - int representing each att obj, name position in att set List");
			for (Set<Integer> s : nameSets) {
			     System.out.println("    " + s);
			 }
			
			// for each of the attribute name sets, create all the combos of values
			for (Set<Integer> s : nameSets) {
				// for each of the name sets
				if (s.size() == 0)
				{
					// Special homepage case
				} else 
				{
					// do work of creating page entries
					// using attribute name sets to pull all combos of values
					System.out.println("------------------->>");
					List<AttributeSet> currentSets = new ArrayList<AttributeSet>();
					for (Integer j : s)
					{
						currentSets.add(attSets.get(j));
						System.out.println("  Eval -> " + attSets.get(j).attributeName);
					}
					// just the current attSets that need to be cross referenced are in the currentSets list
					int[] n = new int[currentSets.size()];
					int Nr[] = new int[currentSets.size()];
					for (int k=0; k <currentSets.size(); k++)
					{
						Nr[k] = currentSets.get(k).attributes.size() - 1;
					}
					// need to pull values instead of just print them
					
					printPermutations(n, Nr, 0, currentSets);
					
				}
			}
			
		} catch (Exception e) { e.printStackTrace(); }
	}

	private List<AttributeSet> buildPageTable(File itemFile) throws IOException {
		CSVReader reader = new CSVReader(new FileReader(itemFile), '\t');
		String[] nextLine;
		int rowcount = 0;
		int attributeCount = 0;
		
		List<AttributeSet> attSets = new ArrayList<AttributeSet>();
		// Get unique Attributes
		nextLine = reader.readNext();
		int colCount = 0, firstAttCol = 0, lastAttCol = 0;
		
		List<String> inputAttributeNames = new ArrayList<String>();
		for(String value: nextLine){
			// first line
			
			if (value.contains("Att-"))
			{
				attributeCount++;
				if (attributeCount == 1) { firstAttCol = colCount; }
				if (colCount > lastAttCol) { lastAttCol = colCount; }
				AttributeSet set = new AttributeSet(value.substring(4), colCount);
				inputAttributeNames.add(value.substring(4));
				attSets.add(set);
				System.out.println("Attribute : " + value.substring(4));
				
			}
			colCount++;
		}
		this.attributeNames = inputAttributeNames;  // set a class level string list of att names
		
		// Already processed header line - process input lines
		List<String> endStates = new ArrayList<String>();
		while ( (nextLine = reader.readNext()) != null){
			rowcount++;
			colCount = 0;
			String endState = "";
			for(String v: nextLine){
				// need set for each attribute
				// need end state calcs
				// all calculated combos of attributes in page table
				if (colCount >= firstAttCol && colCount <= lastAttCol)
				{
					attSets.get(colCount - firstAttCol).add(v);
					endState = endState + v + "-";
					
				}
				
				colCount++;
			}
			endStates.add(endState);
		}
		
		this.endStatesList = endStates;  // class level list for end states
		
// -------------- REPORTING ------------------------		
		// quick report
		// list attribute sets
		for (AttributeSet set : attSets)
		{
			System.out.println("--------------------------");
			System.out.println("ATT : " + set.attributeName);
			for (String val : set.attributes)
			{
				System.out.println("    " + val);
			}
		}
		
		System.out.println("--- End States ----");
		for (String endState : endStates)
		{
			System.out.println("    End State " + endState);
		}
		System.out.println("Attribute Count " + attributeCount + " and rows " + rowcount);
		System.out.println("First Attribute Col " + firstAttCol + " last attribute col " + lastAttCol);
		
		return attSets;
	}

	private File getOutputDir() {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-kk-mm-ss");
		File outputDir = new File(this.dataDir + sdf.format(now));
		outputDir.mkdir();
		return outputDir;
	}
	
	private List<Page> genPagesMinusXLinks (List<AttributeSet> attSets)
	{
		int numOfAtt = attSets.size();
		
		return null;
	}
	
	// combinations calculator
	public void printPermutations(int[] n, int[] Nr, int idx, List<AttributeSet> sets) {
	    if (idx == n.length) {  //stop condition for the recursion [base clause]
	        System.out.println(Arrays.toString(n));
	        String attTypeComboStr = "";  //Type-Color-Mileage
	        String attComboStr = "";  //SUV-Blue-High
	        for (int i = 0; i < n.length; i++)
	        {
	        	attTypeComboStr = attTypeComboStr + sets.get(i).attributeName + "-";
	        	attComboStr = attComboStr + sets.get(i).getAttValuesAsList().get(n[i]) + "-";
	        }
	        Page page = new Page();
	        page.attTypeComboStr = attTypeComboStr;
	        page.attComboStr = attComboStr;
	        page.setTitle();
	        this.pages.add(page);
	        return;
	    }
	    for (int i = 0; i <= Nr[idx]; i++) { 
	        n[idx] = i;
	        printPermutations(n, Nr, idx+1, sets); //recursive invokation, for next elements
	    }
	}
	
	
	public <T> Set<Set<T>> powerSet(Set<T> originalSet) {
	    Set<Set<T>> sets = new HashSet<Set<T>>();
	    if (originalSet.isEmpty()) {
	    	sets.add(new HashSet<T>());
	    	return sets;
	    }
	    List<T> list = new ArrayList<T>(originalSet);
	    T head = list.get(0);
	    Set<T> rest = new HashSet<T>(list.subList(1, list.size())); 
	    for (Set<T> set : powerSet(rest)) {
	    	Set<T> newSet = new HashSet<T>();
	    	newSet.add(head);
	    	newSet.addAll(set);
	    	sets.add(newSet);
	    	sets.add(set);
	    }		
	    return sets;
	}
	
	
	private class AttributeSet {
		String attributeName = "";
		HashSet<String> attributes = new HashSet<String>();
		
		int column = -1;  // zero based col number
		
		AttributeSet (String name, int col)
		{
			this.attributeName = name;
			this.column = col;
		}
		
		void add (String att)
		{
			attributes.add(att);
		}
		
		private List<String> attVals = null;
		
		List<String> getAttValuesAsList ()
		{
			if (attVals != null)
			{
				return attVals;
			}
			
			List<String> attValues = new ArrayList<String>();
			for (String val : attributes)
			{
				attValues.add(val);
			}
			attVals = attValues;
			return attVals;
		}
	}
	
	
	private class Page {
		
		String attTypeComboStr = "";   //Type-Color-Mileage
		String attComboStr = "";       //SUV-Blue-High
		List<String> allAttributeNamesInSystem = null;
		
		PageGen.PageType pageType = null;
		
		String title = "";
		String headline = "";
		String topParagraph = "";
		List<LinkSection> sections = null;
		int numOfLinkSections = -1;
		
		void setTitle ()
		{
			if (!this.attComboStr.equals(""))
			{
				this.title = this.attComboStr;
			}
		}
		
		void addLinkSection (String sectionName)
		{
			if (sections == null)
			{
				sections = new ArrayList<LinkSection>();
				
			}
			LinkSection ls = new LinkSection();
			ls.setSectionTitle(sectionName);
			sections.add(ls);
		}
		
		
		
		
	}
	
	private class LinkSection {
		String sectionTitle = "";
		String sectionHeadline = "";
		List<Link> links = new ArrayList<Link>();
		
		
		public String getSectionTitle() {
			return sectionTitle;
		}
		public void setSectionTitle(String sectionTitle) {
			this.sectionTitle = sectionTitle;
		}
		public String getSectionHeadline() {
			return sectionHeadline;
		}
		public void setSectionHeadline(String sectionHeadline) {
			this.sectionHeadline = sectionHeadline;
		}
		public List<Link> getLinks() {
			return links;
		}
		public void setLinks(List<Link> links) {
			this.links = links;
		}
		
	}
	
	private class Link {
		String linkText = "";
		String linkUrl = "";
		int endStateCount = 0;
		
	}
}
