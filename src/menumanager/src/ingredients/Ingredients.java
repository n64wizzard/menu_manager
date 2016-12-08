package menumanager.src.ingredients;

import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;
import menumanager.src.dishes.Dishes;

/**
 * Singleton class that contains every ingredient available to the user.
 * @author Glenn Ulansey
 */
public class Ingredients {
	private static Ingredients ref;
	public static Ingredients getSingletonObject(){
		if(ref == null)
			ref = new Ingredients();
		return ref;
	}
		
	private Vector<Ingredient> ingredients_;
	
	private Ingredients(){
		this.ingredients_ = new Vector();
	}
	
	public Vector<Ingredient> ingredients(){ return this.ingredients_; }
	public Ingredient ingredient(Ingredient.ID id){
		for(Ingredient ingredient: this.ingredients_)
			if(ingredient.id().equals(id))
				return ingredient;
		return null;
	}
	public Ingredient ingredient(int index){ return ingredients_.get(index); }
	public void ingredientIs(Ingredient ingredient){ this.ingredients_.add(ingredient); }
	
	public int ingredientsCount(){ return this.ingredients_.size(); }
	public void clearIngredients(){ this.ingredients_.clear(); }
	public void removeIngredient(int index){ 
		Dishes.getSingletonObject().deleteIngredient(this.ingredients_.get(index).id());
		this.ingredients_.remove(index); 
	}
	
	public void sort(){ Collections.sort(this.ingredients_); }
}
