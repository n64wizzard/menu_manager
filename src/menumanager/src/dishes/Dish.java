package menumanager.src.dishes;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import menumanager.src.ingredients.Ingredient;

public class Dish implements Comparable{
	private Vector<DishIngredient> ingredients_;
	private ID id_;
	private String name_; 
	private String instructions_;
	private String description_;
	
	public Dish(ID id){ 
		this.id_ = id; 
		this.ingredients_ = new Vector();
	}
	public ID id(){ return this.id_; }
	
	public DishIngredient ingredient(int index){ return this.ingredients_.get(index); }
	public void ingredientIs(DishIngredient ingredient){ this.ingredients_.add(ingredient); }
	public void ingredientsAre(Vector<DishIngredient> ingredients){ this.ingredients_ = ingredients; }
	public Iterator<DishIngredient> ingredients(){ return this.ingredients_.iterator(); }
	public int ingredientCount(){ return this.ingredients_.size(); }
	public void ingredientRemove(int index){ this.ingredients_.remove(index); }
	public void ingredientRemove(Ingredient.ID id){ 
		Iterator<DishIngredient> iter = this.ingredients();
		while(iter.hasNext())
			if((iter.next()).id().equals(id))
				iter.remove();
	}
	
	public String name(){ return this.name_; }
	public void nameIs(String name){ this.name_ = name; }
	
	public String instructions(){ return this.instructions_; }
	public void instructionsIs(String instructions){ this.instructions_ = instructions; }
	
	public String description(){ return this.description_; }
	public void descriptionIs(String description){ this.description_ = description; }
	
	public void empty(){
		this.nameIs("New Dish");
		this.descriptionIs("Description/Comments...");
		this.instructionsIs("Preparation Instructions...");
		this.ingredientsAre(new Vector());
	}
	
	public int compareTo(Object o) {
		return this.name().compareTo(((Dish)o).name());
	}
	
	@Override 
	public String toString(){ return this.name(); }
	
	static public class ID{
		private int id_;
		public ID(int id){ this.id_ = id; }
		public int value(){ return this.id_; }
		
		@Override
		public boolean equals(Object aThat){
			if(((ID)aThat).value() == this.value())
				return true;
			return false;
		}
	}
}
