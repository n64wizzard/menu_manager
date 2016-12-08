/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package menumanager.src.options;

import java.util.Vector;

/**
 *
 * @author Glenn Ulansey
 */
public class IngredientCategory {
	static private Vector<IngredientCategory> categoryNames = new Vector();
	static public void addIngredientCategory(IngredientCategory category){ IngredientCategory.categoryNames.add(category); }
	static public Vector<IngredientCategory> categories(){ return IngredientCategory.categoryNames; }
	static public void clearCategories(){ IngredientCategory.categoryNames.clear(); }
	static public void removeCategory(int index){ IngredientCategory.categoryNames.remove(index); }
	static public int categoryCount(){ return IngredientCategory.categoryNames.size(); }
	static public IngredientCategory category(int index){ return IngredientCategory.categoryNames.get(index); }
	
	private String categoryName_;
	public IngredientCategory(String categoryName){
		this.categoryName_ = categoryName;
	}
	
	@Override 
	public String toString(){ return this.categoryName_; }
	@Override
	public boolean equals(Object aThat){
		if(aThat != null && ((IngredientCategory)aThat).toString().equals(this.toString()))
			return true;
		return false;
	}
}
