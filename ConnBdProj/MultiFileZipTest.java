package ZipTeatPack;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class MultiFileZipTest {

	// Scanner sc;
	static List<String> listOfSuccessFilesZipped = new ArrayList<String>();
	static List<String> listOfUnsuccessFilesZipped = new ArrayList<String>();
	static int i = 1;
	static List<String> listOfFilesPathsToGetZipped = new ArrayList<String>();

	public void zipFiles(List<String> files) {
		Boolean flagToSkipFirstDummyInFiles = false;
		FileOutputStream fos = null;
		ZipOutputStream zipOut = null;
		FileInputStream fis = null;
		// listOfFilesPathsToGetZipped.clear();
		// listOfFilesPathsToGetZipped.addAll(files);
		// System.out.println("Before zipping give the path where to zip");
		// System.out.println("example C:\\Users\\KH9331\\Desktop\\tmp.zip");
		// sc= new Scanner(System.in);
		// String pathToZip=sc.next();
		// sc.close();

		try {
			fos = new FileOutputStream(ZipTestClass.zipPath);
			// fos = new FileOutputStream("C:\\Users\\KH9331\\Desktop\\tmp.zip");
			zipOut = new ZipOutputStream(new BufferedOutputStream(fos));
			i = 1;
			for (String filePath : files) {
				if (flagToSkipFirstDummyInFiles == false) {
					flagToSkipFirstDummyInFiles = true;
					continue;
				}
				File input = new File(filePath);
				fis = new FileInputStream(input);
				ZipEntry ze = new ZipEntry(input.getName());
				System.out.println("Zipping the file: " + input.getName());
				zipOut.putNextEntry(ze);
				byte[] tmp = new byte[4 * 1024];
				int size = 0;
				while ((size = fis.read(tmp)) != -1) {
					zipOut.write(tmp, 0, size);
				}
				zipOut.flush();
				fis.close();
				i++;
				if (listOfSuccessFilesZipped.contains(filePath)) {

				} else
					listOfSuccessFilesZipped.add(filePath);
			}
			zipOut.close();
			System.out.println("Done... Zipped the files...");
			for (String st : listOfSuccessFilesZipped) {
				System.out.println("success " + st);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			try {
				if (fos != null)
					fos.close();
				fis.close();
				zipOut.close();
			} catch (Exception ex) {

			}
			listOfUnsuccessFilesZipped.add(files.get(i));
			
			System.out.println("this file was not avilable  " + files.get(i));
			files.remove(i);
			// MultiFileZipTest mfz= new MultiFileZipTest();
			this.zipFiles(files);

			// e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (Exception ex) {

			}
		}
	}

	/*
	 * public static void main(String a[]){
	 * 
	 * MultiFileZipTest mfe = new MultiFileZipTest(); List<String> files = new
	 * ArrayList<String>(); files.add("Start");
	 * 
	 * files.add("C:\\KonyMobileFabric\\logs\\authService.log.7");
	 * files.add("C:\\KonyMobileFabric\\logs\\workspaceServic.log");
	 * files.add("C:\\KonyMobileFabric\\logs\\syncservice.log"); files.add("ddf");
	 * //files.add("C:\\KonyMobileFabric\\logs\\workspaceServic.log");
	 * //files.add("C:\\KonyMobileFabric\\logs\\workspaceService.log");
	 * //files.add("C:/port-chn.txt"); //listOfFilesPathsToGetZipped.addAll(files);
	 * mfe.zipFiles(files); for(String st:listOfUnsuccessFilesZipped) {
	 * System.out.println("failure "+st); } //mfe.test(files); }
	 */
	/*
	 * void test(List<String> ls) { List<String> ls2=new ArrayList<String>();
	 * for(String sts:ls) { if(ls2.contains(sts)) {
	 * 
	 * } else { ls2.add(sts); } } //ls2.remove(1); //System.out.println(ls2.get(1));
	 * for(String sts:ls2) { System.out.println(sts); } }
	 */
}