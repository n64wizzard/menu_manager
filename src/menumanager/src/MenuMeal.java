package menumanager.src;

import java.util.Vector;
import menumanager.src.options.MealName;

/**
 *
 * @author Glenn Ulansey
 */
public class MenuMeal {
	private Vector<MealDish> dishes_;
	private MealName mealName_;
	
	public MenuMeal(MealName mealName){
		this.mealName_ = mealName;
		this.dishes_ = new Vector();
	}
	public MealName mealName(){ return this.mealName_; }
	public void mealNameIs(MealName mealName){ this.mealName_ = mealName; }
	
	public void dishIs(MealDish dish){ this.dishes_.add(dish); }
	public MealDish dish(int index){ return this.dishes_.get(index); }
	public int dishCount(){ return this.dishes_.size(); }
	public int dishIndex(MealDish dish){ return this.dishes_.indexOf(dish); }
	public void removeDish(MealDish dish){ this.dishes_.remove(dish); }
	public Vector<MealDish> dishes(){ return this.dishes_; }
	
	@Override 
	public String toString(){ return this.mealName().toString(); }
}
