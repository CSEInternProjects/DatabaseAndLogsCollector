package ConnDbClass;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class ZipFolder {
	public void ZippingAllFilesForMflogs(String mfLogsPath) throws Exception//
	{
		
		List<String> listOfUniqueFileNames=new ArrayList<String>();
		List<ForStringCollection> listOfRequiredFilesToZip = new ArrayList<ForStringCollection>();
		File folder = new File(mfLogsPath);
		File[] listOfFiles = folder.listFiles();
		listOfUniqueFileNames.add("start");
		for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		        //System.out.println("File " + listOfFiles[i].getName()+" last modified "+listOfFiles[i].lastModified());
		        String nameOfFile=listOfFiles[i].getName();
		        //System.out.println(nameOfFile);
		        
		        //for(String uniqueFileNameInList:listOfUniqueFileNames)
		        int flag=0;
		        String uniqueSingleIdentifiedFileName;
		        for(int k=0;k<listOfUniqueFileNames.size();k++)
		        {   flag++;
		        	
		        	if(nameOfFile.indexOf(listOfUniqueFileNames.get(k))!=-1)
		        	   flag--;
		        		
		      }
		        if(flag==listOfUniqueFileNames.size())
		        {
		        	int findDot=nameOfFile.indexOf("log");
	        		//String uniqueSingleIdentifiedFileName;
					if(findDot!=-1)
	        		    uniqueSingleIdentifiedFileName = nameOfFile.substring(0,findDot );
	        		else
	        			uniqueSingleIdentifiedFileName = nameOfFile;
	        		
		        	listOfUniqueFileNames.add(uniqueSingleIdentifiedFileName);
		        }
		        
		      }
		}
		for(String uniqueFileNameInList:listOfUniqueFileNames)
		{
			System.out.println(uniqueFileNameInList+" unique file names");
		}
		for(int i1=0;i1<listOfUniqueFileNames.size();i1++)//st[i1]->
		{
			ForStringCollection fsc= new ForStringCollection();
			fsc.map = new HashMap<String,Long>();
			//fsc.map.put(key, value)
			//System.out.println("length : "+listOfFiles.length);
			for (int i = 0; i < listOfFiles.length; i++) {
			      if (listOfFiles[i].isFile()) {
			        //System.out.println("File " + listOfFiles[i].getName()+" last modified "+listOfFiles[i].lastModified());
			        String nameOfFile=listOfFiles[i].getName();
			        Long lastModified=listOfFiles[i].lastModified(); 
			        if(nameOfFile.indexOf(listOfUniqueFileNames.get(i1))!=-1)
			            {
			    		   fsc.map.put(nameOfFile, lastModified);
			    		   
			        	//new ForStringCollection().map.put(nameOfFile, lastModified);
			        	}
			        
			        
			      }
			      
		}
			listOfRequiredFilesToZip.add(fsc);
		/*for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		        System.out.println("File " + listOfFiles[i].getName()+" last modified "+listOfFiles[i].lastModified());
		        
		      } else if (listOfFiles[i].isDirectory()) {
		        System.out.println("Directory " + listOfFiles[i].getName());
		      }
		    }*/
		}
		for(int j=0;j<listOfRequiredFilesToZip.size();j++)
        {
        	ForStringCollection fscReplaceerAsItIsSorted=this.eachFileModificationTimeBasedSort(listOfRequiredFilesToZip.get(j)); 
			
        	//listOfRequiredFilesToZip.
        	listOfRequiredFilesToZip.remove(j);
        	listOfRequiredFilesToZip.add(j, fscReplaceerAsItIsSorted);
        	
        }
		String fileName = null;
		Long time = null;
		List<String> listOfFinalPathsToGetZipped=new ArrayList<>();
		for(int j=0;j<listOfRequiredFilesToZip.size();j++)
        {
			String finalPathForEachFile=null;
			for (Entry<String, Long> entry : listOfRequiredFilesToZip.get(j).map.entrySet()) {
			    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			    fileName=entry.getKey();
			    time=entry.getValue();
			}
		    finalPathForEachFile=mfLogsPath.concat("\\"+fileName);
		    listOfFinalPathsToGetZipped.add(finalPathForEachFile);
			System.out.println("gap"+" found "+ fileName+" time "+time);
		}
		//ZipTestClass ztc= new ZipTestClass();
		//ztc.zipingAllFilesHere(listOfFinalPathsToGetZipped,listOfRequiredFileNames);
		//testCode
		MultiFileZipTest mfe = new MultiFileZipTest();
		mfe.zipFiles(listOfFinalPathsToGetZipped);
		for(int i=0;i<listOfFinalPathsToGetZipped.size();i++)
		{
			System.out.println(listOfFinalPathsToGetZipped.get(i));
		}
	}
	

	public ForStringCollection eachFileModificationTimeBasedSort(ForStringCollection forStringCollection) {
		// TODO Auto-generated method stub
		//Map<String, Long> map = new HashMap<String, Long>();
		
		Map<String, Long> map2=this.sortHashMapByValues(forStringCollection.map);
		for (Entry<String, Long> entry : map2.entrySet()) {
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
		forStringCollection.map.clear();
		forStringCollection.map.putAll(map2);
		System.out.println("sorted");
		return forStringCollection;
	}
	public LinkedHashMap<String, Long> sortHashMapByValues(
	        Map<String, Long> map) {
	    List<String> mapKeys = new ArrayList<>(map.keySet());
	    List<Long> mapValues = new ArrayList<>(map.values());
	    Collections.sort(mapValues);
	    Collections.sort(mapKeys);

	    LinkedHashMap<String, Long> sortedMap =
	        new LinkedHashMap<>();

	    Iterator<Long> valueIt = mapValues.iterator();
	    while (valueIt.hasNext()) {
	        Long val = valueIt.next();
	        Iterator<String> keyIt = mapKeys.iterator();

	        while (keyIt.hasNext()) {
	            String key = keyIt.next();
	            Long comp1 = map.get(key);
	            Long comp2 = val;

	            if (comp1.equals(comp2)) {
	                keyIt.remove();
	                sortedMap.put(key, val);
	                break;
	            }
	        }
	    }
	    return sortedMap;
	}
}
