/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package menumanager.src.dishes;

import menumanager.src.ingredients.Ingredient;
import menumanager.src.ingredients.Ingredients;
import menumanager.src.options.MeasureUnit;

/**
 *
 * @author Glenn Ulansey
 */
public class DishIngredient{
	private double quantityPerServing_;
	private Ingredient.ID id_;
	
	public DishIngredient(Ingredient.ID id){
		this.id_ = id;
	}
	
	public void quantityPerServingIs(double quantityPerServing){ this.quantityPerServing_ = quantityPerServing; }
	public double quantityPerServing(){ return this.quantityPerServing_; }
	
	public MeasureUnit measureUnit(){ return Ingredients.getSingletonObject().ingredient(this.id()).measureUnit(); }
	public String name(){ return Ingredients.getSingletonObject().ingredient(this.id()).name();  }
	public Ingredient.ID id(){ return this.id_; }
}
