package mv;

import java.io.*;
import java.util.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;



public class MovieFinderImpl implements MovieFinder {

	String filename;

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Override
	public ArrayList<Movie> findAll(){
		// TODO Auto-generated method stub
		try {
			BufferedReader fileIn = new BufferedReader(new FileReader("src/main/resources/movie1.txt"));
			String line;

			ArrayList<Movie> list = new ArrayList<Movie>();
			while ((line = fileIn.readLine()) != null) {
				String[] abs =  line.trim().split(":");
				for(int x = 0 ; x<abs.length; x += 2 ){
					Movie mov = new Movie(abs[x], abs[x+1]);
					list.add(mov);
				}
			}
			return list; 
		}
		catch(FileNotFoundException e) {}
		catch(IOException e){}
		return null; 
	}


}