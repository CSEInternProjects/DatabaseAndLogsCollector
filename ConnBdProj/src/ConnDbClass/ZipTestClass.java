package ConnDbClass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.*;



public class ZipTestClass {
	static String zipPath = null;
	static List<String> listOfAllZipPathsToMakeFinalZip = new ArrayList<String>();
	static List<String> ConnPathForStatusAndToZip= new ArrayList<>();
	public static void SettingStaticVar(String St)
	{
		ConnPathForStatusAndToZip.add(St);
	}
	public static void mains(String args[],Scanner sc) throws Exception {// main execution
		listOfAllZipPathsToMakeFinalZip.add("start");
		String choosenLogTypeForZippingorExit = null;
		ZipFolder zipFolder = new ZipFolder();
		//Scanner sc = null;
		// Scanner sc= new Scanner(System.in);
		sc = new Scanner(System.in);
		ZipTestClass ztc = new ZipTestClass();
		do {
			System.out.println("type \"mflogs\" for mobile fabric logs and press enter");
			System.out.println("type \"server logs\" for server logs and press enter");
			System.out.println("type \"webserver logs\" for webserver logs and press enter");
			System.out.println("type \"finalize\" to wrap up all gathered file in to one and press enter");
			System.out.println("type \"exit\" for exiting from program and press enter");

			choosenLogTypeForZippingorExit = sc.next();
			if (choosenLogTypeForZippingorExit.indexOf("mflogs") != -1) {
				System.out.println("Please type the path for MF logs and press enter");
				System.out.println("Exampe C:\\KonyMobileFabric\\logs");
				String mfLogspath = sc.next();
				System.out.println("Thank you");
				// sc.close();
				ztc.SettingZipPathValueHere(sc);
				zipFolder.ZippingAllFilesForMflogs(mfLogspath);
			} else if ((choosenLogTypeForZippingorExit.indexOf("exit") == -1)
					&& (choosenLogTypeForZippingorExit.indexOf("finalize") == -1)) {
				ztc.ZippingServerOrWebServerLogs(choosenLogTypeForZippingorExit, sc, ztc);
			} else if ((choosenLogTypeForZippingorExit.indexOf("exit") == -1)) {
				System.out.println("Before zipping type the path where to zip and press enter");
				System.out.println("example C:\\Users\\KH9331\\Desktop");
				zipPath = sc.next();
				ztc.FileCreationAboutStatusOfMentionedFiles();
                System.out.println("your final zip will be avaliable in this location\n"+
				zipPath+"KonyRequiredLogs.zip");
			}
			else if(choosenLogTypeForZippingorExit.indexOf("exit") != -1) {
				System.out.println("have you finalized?, if not do it now, else you will not have the output at all\n"+
			"It is a mandatory step so..\n"+
						"Type \"y\"to finalize or type\"n\" for not to finalize");
				String finalize = sc.next();
				if(finalize.equals("y"))
				{
					System.out.println("Before zipping type the path where to zip and press enter");
					System.out.println("example C:\\Users\\KH9331\\Desktop");
					zipPath = sc.next();
					ztc.FileCreationAboutStatusOfMentionedFiles();
	                System.out.println("your final output will be avaliable in this location\n"+
					zipPath+"KonyRequiredLogs.zip");
	                Thread.sleep(4000);
	                System.exit(0);
				}
				else
	                {
	                   System.out.println("If you finalized before well and good here will be your out put "+
	                zipPath+"KonyRequiredLogs.zip\n"+
	                		   "else you don't find output at all");
	                   Thread.sleep(4000);
		                System.exit(0);
	                   
	                }
	                	
				}
			
			// files.add("C:\KonyMobileFabric\logs\workspaceService.log");
			// files.add("C:\KonyMobileFabric\logs\syncservice.log");
		} while (true);
		// sc.close();

	}
    
	void FileCreationAboutStatusOfMentionedFiles() throws IOException {
		String pathName = zipPath;
		pathName = pathName.concat("\\status.log");
		if(ConnPathForStatusAndToZip!=null) {
			for(String tempStringToMoveContent : ConnPathForStatusAndToZip)
			{
				listOfAllZipPathsToMakeFinalZip.add(tempStringToMoveContent);
				MultiFileZipTest.listOfSuccessFilesZipped.add(tempStringToMoveContent);
			}
						}
		File file = new File(pathName);
		FileWriter fw = new FileWriter(file);
		fw.write("  List of all file failed to get zipped \n");
		for (String unSuccessFileNames : MultiFileZipTest.listOfUnsuccessFilesZipped)
			fw.write(unSuccessFileNames + "\n");
		fw.write("\n \n ");
		fw.write("   All successfully zipped file list \n");
		for (String successFileNames : MultiFileZipTest.listOfSuccessFilesZipped)
			fw.write(successFileNames + "\n");
		fw.close();
		if (listOfAllZipPathsToMakeFinalZip.contains(pathName) == false) {
			listOfAllZipPathsToMakeFinalZip.add(pathName);
		}
		MultiFileZipTest mfzt = new MultiFileZipTest();
		zipPath = zipPath.concat("\\KonyRequiredLogs.zip");
		
		mfzt.zipFiles(listOfAllZipPathsToMakeFinalZip);
	}

	void ZippingServerOrWebServerLogs(String choosenLogTypeForZippingorExit, Scanner sc, ZipTestClass ztc) {
		// if((choosenLogTypeForZippingorExit.indexOf("server logs")==-1))
		// return;
		// if((choosenLogTypeForZippingorExit.indexOf("webserver logs")==-1))
		// return;
		String doneEntringUrls = null;
		if (choosenLogTypeForZippingorExit.indexOf("server logs") != -1) {
			System.out.println("Provide all paths by typing the each path of server logs and press enter till all paths are provided"+
		"when you are done providing all paths then type \"done\" and press enter");
			System.out.println("Exampe C:\\server\\logs\\serverlog.log");
		} else {
			System.out.println("Provide all paths by typing the each path of web server logs and press enter till all paths are provided"+
					"when you are done providing all paths then type \"done\" and press enter");
			System.out.println("Exampe C:\\server\\logs\\Webserverlog.log");
		}
		List<String> lstForServerLogs = new ArrayList<String>();
		lstForServerLogs.add("start");
		do {
			// System.out.println("Please give the paths for server logs and enter \"done\"
			// at the end");
			// System.out.println("Exampe C:\\KonyMobileFabric\\logs\\serverlog.txt");
			doneEntringUrls = sc.next();
			if (doneEntringUrls.indexOf("done") == -1)
				lstForServerLogs.add(doneEntringUrls);
		} while (doneEntringUrls.indexOf("done") == -1);
		ztc.SettingZipPathValueHere(sc);
		MultiFileZipTest mfzt = new MultiFileZipTest();
		mfzt.zipFiles(lstForServerLogs);
	}

	void SettingZipPathValueHere(Scanner sc) {

		System.out.println("Before zipping type the path where to zip and press enter");
		System.out.println("Example C:\\Users\\KH9331\\Desktop\\zipLogs.zip");
		zipPath = sc.next();
		listOfAllZipPathsToMakeFinalZip.add(zipPath);

		return;
	}
}