package mv;

public class Movie {
	private String director;
	private String title;
	
	public Movie(String director, String title ){
		this.director = director;
		this.title = title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setDirector(String director){
		this.director = director;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getDirector(){
		return director;
	}
}
