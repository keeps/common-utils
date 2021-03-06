package pt.gov.dgarq.roda.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This thread serves as a gargage colector for directories. If you want a temp
 * directory to be deleted at the end of execution add that directory to this
 * thread throught the method {@link #add(File)}.
 * 
 * @author Rui Castro
 * 
 */
public class DirDeleter extends Thread {

	private ArrayList<File> dirList = new ArrayList<File>();

	/**
	 * @param dir
	 */
	public synchronized void add(File dir) {
		dirList.add(dir);
	}

	public void run() {
		synchronized (this) {
			Iterator iterator = dirList.iterator();
			while (iterator.hasNext()) {
				File dir = (File) iterator.next();
				deleteDirectory(dir);
				iterator.remove();
			}
		}
	}

	private void deleteDirectory(File dir) {
		File[] fileArray = dir.listFiles();

		if (fileArray != null) {
			for (int i = 0; i < fileArray.length; i++) {
				if (fileArray[i].isDirectory())
					deleteDirectory(fileArray[i]);
				else
					fileArray[i].delete();
			}
		}
		dir.delete();
	}
}
