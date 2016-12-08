package menumanager.src;

import java.util.Vector;

/**
 *
 * @author Glenn Ulansey
 */
public class MenuDay{
	private Vector<MenuMeal> meals_;
	private String dayName_;
	private int numPeople_;
	
	public MenuDay(String dayName){
		this.dayName_ = dayName;
		this.meals_ = new Vector();
	}
	
	public int mealCount(){ return this.meals_.size(); }
	public MenuMeal meal(int index){ return this.meals_.get(index); }
	public int mealIndex(MenuMeal meal){ return this.meals_.indexOf(meal); }
	public void mealIs(MenuMeal meal){ this.meals_.add(meal); }
	public void removeMeal(MenuMeal meal){ this.meals_.remove(meal); }
	public Vector<MenuMeal> meals(){ return this.meals_; }
	
	public void numPeopleIs(int numPeople){ this.numPeople_ = numPeople; }
	public int numPeople(){ return this.numPeople_; }
	
	public void nameIs(String name){ this.dayName_ = name; }
	public String name(){ return this.dayName_; }
	
	@Override 
	public String toString(){ return this.name() + " (" + this.numPeople() + " people)"; }
}
