package app.Models;

public class Administrator {
	private static Administrator ourInstance;

	public static Administrator getInstance() {
		if (ourInstance == null) {
			ourInstance = new Administrator();
		}
	
			return ourInstance;
		}
	

	private Administrator() {
	}
}
