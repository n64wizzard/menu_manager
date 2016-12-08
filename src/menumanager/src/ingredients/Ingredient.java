package menumanager.src.ingredients;

import menumanager.src.options.GroceryStore;
import menumanager.src.options.IngredientCategory;
import menumanager.src.options.MeasureUnit;

/**
 *
 * @author Glenn Ulansey
 */
public class Ingredient implements Comparable,Cloneable {
	private String name_;
	private MeasureUnit measureUnit_;
	private double unitsPerPackage_;
	private GroceryStore store_;
	private IngredientCategory category_;
	private ID id_;
	
	public Ingredient(ID id){ this.id_ = id; }
	public ID id(){ return this.id_; }
	
	public String name(){ return this.name_; }
	public void nameIs(String name){ this.name_ = name; }
	
	public MeasureUnit measureUnit(){ return this.measureUnit_; }
	public void measureUnitIs(MeasureUnit measureUnit){ this.measureUnit_ = measureUnit; }
	
	public double unitsPerPackage(){ return this.unitsPerPackage_; }
	public void unitsPerPackageIs(double unitsPerPackage){ this.unitsPerPackage_ = unitsPerPackage; }
	
	public GroceryStore store(){ return this.store_; }
	public void storeIs(GroceryStore store){ this.store_ = store; }
	
	public IngredientCategory category(){ return this.category_; }
	public void categoryIs(IngredientCategory category){ this.category_ = category; }
	
	public void empty(){
		this.nameIs("");
		this.measureUnitIs(new MeasureUnit(""));
		this.storeIs(new GroceryStore(""));
		this.unitsPerPackageIs(0.0);
		this.categoryIs(new IngredientCategory(""));
	}
	
	@Override 
	public String toString(){ return this.name(); }

	public int compareTo(Object o) {
		return this.name().compareTo(((Ingredient)o).name());
	}
	
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
