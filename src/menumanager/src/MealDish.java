package menumanager.src;

import menumanager.src.dishes.Dish;
import menumanager.src.dishes.Dishes;

/**
 *
 * @author Glenn Ulansey
 */
public class MealDish{
	private Dish.ID id_;
	
	public MealDish(Dish.ID id){ 
		this.id_ = id;
	}
	
	public Dish.ID id(){ return this.id_; }
	public String name(){ return Dishes.getSingletonObject().dish(this.id_).name(); }
	
	@Override 
	public String toString(){ return this.name(); }
}
