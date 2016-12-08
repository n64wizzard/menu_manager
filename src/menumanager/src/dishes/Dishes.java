package menumanager.src.dishes;

import java.util.Collections;
import java.util.Vector;
import menumanager.src.ingredients.Ingredient;

/**
 *
 * @author Glenn Ulansey
 */
public class Dishes {
	private static Dishes ref;
	public static Dishes getSingletonObject(){
		if(ref == null)
			ref = new Dishes();
		return ref;
	}
		
	private Vector<Dish> dishes_;
	
	private Dishes(){
		this.dishes_ = new Vector();
	}
	
	public Vector<Dish> dishes(){ return this.dishes_; }
	public Dish dish(Dish.ID id){
		for(Dish dish: this.dishes_)
			if(dish.id().equals(id))
				return dish;
		return null;
	}
	public Dish dish(int index){ return dishes_.get(index); }
	public void dishIs(Dish ingredient){ this.dishes_.add(ingredient); }
	
	public int dishesCount(){ return this.dishes_.size(); }
	public void clearDishes(){ this.dishes_.clear(); }
	public void removeDish(int index){ this.dishes_.remove(index); }
	
	public void deleteIngredient(Ingredient.ID id){
		for(Dish dish: this.dishes_)
			dish.ingredientRemove(id);
	}
	
	public void sort(){ Collections.sort(this.dishes_); }
}
