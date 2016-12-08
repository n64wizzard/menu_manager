package menumanager.src.options;

import java.util.Vector;

/**
 *
 * @author Glenn Ulansey
 */
public class MealName {
	static Vector<MealName> mealNames = new Vector();;
	static public void addMealName(MealName mealName){ MealName.mealNames.add(mealName); }
	static public Vector<MealName> mealNames(){ return MealName.mealNames; }
	static public void clearMealNames(){ MealName.mealNames.clear(); }
	static public void removeMealName(int index){ MealName.mealNames.remove(index); }
	static public int mealNameCount(){ return MealName.mealNames.size(); }
	static public MealName mealName(int index){ return MealName.mealNames.get(index); }
	
	private String mealName_;
	
	public MealName(String mealName){
		this.mealName_ = mealName;
	}
	
	@Override 
	public String toString(){ return this.mealName_; }
	@Override
	public boolean equals(Object aThat){
		if(aThat != null && ((MealName)aThat).toString().equals(this.toString()))
			return true;
		return false;
	}
}
