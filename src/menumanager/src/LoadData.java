package menumanager.src;

import menumanager.src.options.GroceryStore;
import menumanager.src.ingredients.Ingredient;
import menumanager.src.options.MeasureUnit;
import menumanager.src.ingredients.Ingredients;
import menumanager.src.options.IngredientCategory;
import menumanager.src.dishes.Dish;
import menumanager.src.dishes.Dishes;
import menumanager.src.dishes.DishIngredient;
import java.util.Stack;
import org.xml.sax.*;
import javax.xml.parsers.SAXParserFactory; 
import javax.xml.parsers.SAXParser;
import menumanager.src.options.MealName;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Loads GroceryStores, IngredientCategories, MeasuringUnits, Ingredients,
 *	Dishes, and Menus from the data XML file.
 * @author Glenn Ulansey
 */
final public class LoadData extends DefaultHandler{
	private String fileName_;
	private Stack<Object> objectStack_;
	private String currValue_;
	
	static public void clearData(){
		Ingredients.getSingletonObject().clearIngredients();
		Dishes.getSingletonObject().clearDishes();
		GroceryStore.clearStoreNames();
		MeasureUnit.clearMeasureUnits();
		IngredientCategory.clearCategories();
		Menu.getSingletonObject().clearMenu();
	}
	
	public LoadData(String fileName, boolean clearCurrentData){
		this.objectStack_ = new Stack();
		this.fileName_ = fileName;
		
		if(clearCurrentData)
			LoadData.clearData();
			
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
			SAXParser sp = spf.newSAXParser();
			sp.parse(fileName, this);
		}catch(Exception se) {
		}
		
		Ingredients.getSingletonObject().sort();
		Dishes.getSingletonObject().sort();
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		this.currValue_ = "";
		
		if(this.objectStack_.size() == 0){
			if(qName.equalsIgnoreCase("Ingredient")) {
				this.objectStack_.push(new Ingredient(new Ingredient.ID(Integer.parseInt(attributes.getValue("id")))));
			}else if(qName.equalsIgnoreCase("Store")){
				this.objectStack_.push(GroceryStore.class);
			}else if(qName.equalsIgnoreCase("Category")){
				this.objectStack_.push(IngredientCategory.class);
			}else if(qName.equalsIgnoreCase("MeasureUnit")){
				this.objectStack_.push(MeasureUnit.class);
			}else if(qName.equalsIgnoreCase("MealName")){
				this.objectStack_.push(MealName.class);
			}else if(qName.equalsIgnoreCase("Dish")){
				Dish dish = new Dish(new Dish.ID(Integer.parseInt(attributes.getValue("id"))));
				dish.nameIs(attributes.getValue("name"));
				this.objectStack_.push(dish);
			}else if(qName.equalsIgnoreCase("MenuDay")){
				MenuDay menuDay = new MenuDay(attributes.getValue("dayName"));
				menuDay.numPeopleIs(Integer.parseInt(attributes.getValue("numPeople")));
				this.objectStack_.push(menuDay);
			}
		}else{
			Object currObject = this.objectStack_.peek();
			
			if(currObject.getClass() == Dish.class && qName.equalsIgnoreCase("DishIngredient")){
				Dish dish = (Dish)currObject;
				DishIngredient ingredient = new DishIngredient(new Ingredient.ID(Integer.parseInt(attributes.getValue("id"))));
				ingredient.quantityPerServingIs(Double.parseDouble(attributes.getValue("QuantityPerServing")));
				dish.ingredientIs(ingredient);
			}else if(currObject.getClass() == MenuDay.class && qName.equals("MenuMeal")){
				MenuDay menuDay = (MenuDay)currObject;
				MenuMeal menuMeal = new MenuMeal(new MealName(attributes.getValue("name")));
				this.objectStack_.push(menuMeal);
			}else if(currObject.getClass() == MenuMeal.class && qName.equals("MealDish")){
				MenuMeal menuMeal = (MenuMeal)currObject;
				MealDish mealDish = new MealDish(new Dish.ID(Integer.parseInt(attributes.getValue("id"))));
				menuMeal.dishIs(mealDish);
			}
		}
	}
	
	public void characters(char[] ch, int start, int length) throws SAXException {
		this.currValue_ = new String(ch,start,length);
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(this.objectStack_.size() == 0)
			return ;
		
		Object currObject = this.objectStack_.peek();
				
		if(currObject.getClass() == Ingredient.class){
			Ingredient ingredient = (Ingredient)currObject;
			if(qName.equalsIgnoreCase("Ingredient")){
				Ingredients.getSingletonObject().ingredientIs(ingredient);
				this.objectStack_.pop();
			}else if(qName.equalsIgnoreCase("name")){
				ingredient.nameIs(this.currValue_);
			}else if(qName.equalsIgnoreCase("measureUnit")){
				ingredient.measureUnitIs(new MeasureUnit(this.currValue_));
			}else if(qName.equalsIgnoreCase("unitsPerPackage")){
				ingredient.unitsPerPackageIs(Double.parseDouble(this.currValue_));
			}else if(qName.equalsIgnoreCase("store")){
				ingredient.storeIs(new GroceryStore(this.currValue_));
			}else if(qName.equalsIgnoreCase("category")){
				ingredient.categoryIs(new IngredientCategory(this.currValue_));
			}		
		}else if(currObject == GroceryStore.class){
			GroceryStore.addStoreName(new GroceryStore(this.currValue_));
			this.objectStack_.pop();
		}else if(currObject== IngredientCategory.class){
			IngredientCategory.addIngredientCategory(new IngredientCategory(this.currValue_));
			this.objectStack_.pop();
		}else if(currObject == MeasureUnit.class){
			MeasureUnit.addMeasureUnit(new MeasureUnit(this.currValue_));
			this.objectStack_.pop();
		}else if(currObject == MealName.class){
			MealName.addMealName(new MealName(this.currValue_));
			this.objectStack_.pop();
		}else if(currObject.getClass() == Dish.class){
			Dish dish = (Dish)currObject;
			if(qName.equalsIgnoreCase("Dish")){
				Dishes.getSingletonObject().dishIs(dish);
				this.objectStack_.pop();
			}else if(qName.equalsIgnoreCase("Description")){
				dish.descriptionIs(this.currValue_);
			}else if(qName.equalsIgnoreCase("PrepDirections")){
				dish.instructionsIs(this.currValue_);
			}
		}else if(currObject.getClass() == MenuDay.class && qName.equalsIgnoreCase("MenuDay")){
			MenuDay menuDay = (MenuDay)currObject;
			Menu.getSingletonObject().dayIs(menuDay);
			this.objectStack_.pop();
		}else if(currObject.getClass() == MenuMeal.class){
			MenuMeal menuMeal = (MenuMeal)currObject;
			
			if(qName.equalsIgnoreCase("MenuMeal")){
				this.objectStack_.pop();
				MenuDay menuDay = (MenuDay)this.objectStack_.peek();
				menuDay.mealIs(menuMeal);
			}else if(qName.equalsIgnoreCase("MealDish")){
				;
			}
		}
	}	
}