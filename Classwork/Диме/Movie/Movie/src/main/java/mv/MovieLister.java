package mv;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;




import antlr.collections.List;

public class MovieLister{
	
	private MovieFinder finder;
	public void setFinder(MovieFinder finder) {
		this.finder = finder;
	}
	
    public Movie[] moviesDirectedBy(String arg) {
    	ArrayList<Movie> allMovies = finder.findAll();
        for (Iterator<Movie> it = allMovies.iterator(); it.hasNext();) {
            Movie movie = (Movie) it.next();
            if (!movie.getDirector().equals(arg)) it.remove();
        }
        return (Movie[]) allMovies.toArray(new Movie[allMovies.size()]);
    }

	
	
	public static void main(String [] args){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("config.xml");
		MovieLister lister = (MovieLister) ctx.getBean("MovieLister");
        Movie [] movies = lister.moviesDirectedBy("AAA");
        for(Movie x : movies){
        	System.out.println(x.getTitle());
        }
        
	} 
}
