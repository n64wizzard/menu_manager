package menumanager.src;

import menumanager.src.options.GroceryStore;
import menumanager.src.ingredients.Ingredient;
import menumanager.src.options.MeasureUnit;
import menumanager.src.options.IngredientCategory;
import menumanager.src.ingredients.Ingredients;
import menumanager.src.dishes.Dish;
import menumanager.src.dishes.DishIngredient;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import menumanager.src.dishes.Dishes;
import menumanager.src.options.MealName;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 *
 * @author Glenn Ulansey
 */
public class SaveData {
	static private Document dom_;
	static private Document dom(){ return SaveData.dom_; }
	
	static public void SaveData(File file){
		dom_ = SaveData.createDocument();
		SaveData.createDOMTree();
		SaveData.printToFile(file);
	}
	
	static private Document createDocument(){
		//get an instance of factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			return db.newDocument();
		}catch(ParserConfigurationException pce) {
			System.out.println("Error while trying to instantiate DocumentBuilder " + pce);
			System.exit(1);
			return null;
		}
	}
	static private void createDOMTree(){
		Element rootEle = SaveData.dom().createElement("Base");
		SaveData.dom().appendChild(rootEle);

		Element storesEle = SaveData.dom().createElement("Stores");
		for(GroceryStore store: GroceryStore.storeNames())
			storesEle.appendChild(SaveData.createStoreElement(store));
		rootEle.appendChild(storesEle);
		
		Element categoriesEle = SaveData.dom().createElement("Categories");
		for(IngredientCategory category: IngredientCategory.categories())
			categoriesEle.appendChild(SaveData.createCategoryElement(category));
		rootEle.appendChild(categoriesEle);
		
		Element mealNamesEle = SaveData.dom().createElement("MealNames");
		for(MealName mealName: MealName.mealNames())
			mealNamesEle.appendChild(SaveData.createMealNameElement(mealName));
		rootEle.appendChild(mealNamesEle);
		
		Element measureUnitsEle = SaveData.dom().createElement("MeasureUnits");
		for(MeasureUnit measureUnit: MeasureUnit.measureUnits())
			measureUnitsEle.appendChild(SaveData.createMeasureUnitElement(measureUnit));
		rootEle.appendChild(measureUnitsEle);
		
		Element ingredientsEle = SaveData.dom().createElement("Ingredients");
		for(Ingredient ingredient: Ingredients.getSingletonObject().ingredients())
			ingredientsEle.appendChild(SaveData.createIngredientElement(ingredient));
		rootEle.appendChild(ingredientsEle);
		
		Element dishesEle = SaveData.dom().createElement("Dishes");
		for(Dish dish: Dishes.getSingletonObject().dishes())
			dishesEle.appendChild(SaveData.createDishElement(dish));
		rootEle.appendChild(dishesEle);
		
		Element menuEle = SaveData.dom().createElement("Menu");
		for(MenuDay menuDay: Menu.getSingletonObject().days())
			menuEle.appendChild(SaveData.createMenuDayElement(menuDay));
		rootEle.appendChild(menuEle);
	}
	
	static private Element createMenuDayElement(MenuDay menuDay){
		Element menuDayEle = SaveData.dom().createElement("MenuDay");
		menuDayEle.setAttribute("dayName", String.valueOf(menuDay.name()));
		menuDayEle.setAttribute("numPeople", String.valueOf(menuDay.numPeople()));
		
		for(MenuMeal menuMeal: menuDay.meals())
			menuDayEle.appendChild(SaveData.createMenuMealElement(menuMeal));
		
		return menuDayEle;
	}
	
	static private Element createMenuMealElement(MenuMeal menuMeal){
		Element menuMealEle = SaveData.dom().createElement("MenuMeal");
		menuMealEle.setAttribute("name", String.valueOf(menuMeal.mealName()));
		
		for(MealDish mealDish: menuMeal.dishes()){
			Element mealDishEle = SaveData.dom().createElement("MealDish");
			mealDishEle.setAttribute("id", String.valueOf(mealDish.id().value()));
			menuMealEle.appendChild(mealDishEle);
		}
		
		return menuMealEle;
	}
	
	static private Element createStoreElement(GroceryStore store){
		Element storeEle = SaveData.dom().createElement("Store");
		Text storeText = SaveData.dom().createTextNode(store.toString());
		storeEle.appendChild(storeText);
		
		return storeEle;
	}
	static private Element createCategoryElement(IngredientCategory category){
		Element categoryEle = SaveData.dom().createElement("Category");
		Text categoryText = SaveData.dom().createTextNode(category.toString());
		categoryEle.appendChild(categoryText);
		
		return categoryEle;
	}
	static private Element createMealNameElement(MealName mealName){
		Element mealNameEle = SaveData.dom().createElement("MealName");
		Text mealNameText = SaveData.dom().createTextNode(mealName.toString());
		mealNameEle.appendChild(mealNameText);
		
		return mealNameEle;
	}
	static private Element createMeasureUnitElement(MeasureUnit measureUnit){
		Element measureUnitEle = SaveData.dom().createElement("MeasureUnit");
		Text measureUnitText = SaveData.dom().createTextNode(measureUnit.toString());
		measureUnitEle.appendChild(measureUnitText);
		
		return measureUnitEle;
	}
	static private Element createIngredientElement(Ingredient ingredient){
		Element ingredientEle = SaveData.dom().createElement("Ingredient");
		ingredientEle.setAttribute("id", String.valueOf(ingredient.id().value()));
		
		Element nameEle = SaveData.dom().createElement("name");
		Text nameText = SaveData.dom().createTextNode(ingredient.name());
		nameEle.appendChild(nameText);
		ingredientEle.appendChild(nameEle);
		
		Element measureUnitEle = SaveData.dom().createElement("measureUnit");
		Text measureUnitText = SaveData.dom().createTextNode(ingredient.measureUnit().toString());
		measureUnitEle.appendChild(measureUnitText);
		ingredientEle.appendChild(measureUnitEle);
		
		Element unitsEle = SaveData.dom().createElement("unitsPerPackage");
		Text unitsText = SaveData.dom().createTextNode(String.valueOf(ingredient.unitsPerPackage()));
		unitsEle.appendChild(unitsText);
		ingredientEle.appendChild(unitsEle);
		
		Element storeEle = SaveData.dom().createElement("store");
		Text storeText = SaveData.dom().createTextNode(ingredient.store().toString());
		storeEle.appendChild(storeText);
		ingredientEle.appendChild(storeEle);
		
		Element categoryEle = SaveData.dom().createElement("category");
		Text categoryText = SaveData.dom().createTextNode(ingredient.category().toString());
		categoryEle.appendChild(categoryText);
		ingredientEle.appendChild(categoryEle);
		
		return ingredientEle;
	}
	
	static private Element createDishElement(Dish dish){
		Element dishEle = SaveData.dom().createElement("Dish");
		dishEle.setAttribute("id", String.valueOf(dish.id().value()));
		dishEle.setAttribute("name", dish.name());
		
		Element ingredientsEle = SaveData.dom().createElement("DishIngredients");
		Iterator<DishIngredient> iter = dish.ingredients();
		while(iter.hasNext()){
			Element ingredientEle = SaveData.dom().createElement("DishIngredient");
			
			DishIngredient ingredient = iter.next();
			ingredientEle.setAttribute("id", String.valueOf(ingredient.id().value()));
			ingredientEle.setAttribute("QuantityPerServing", String.valueOf(ingredient.quantityPerServing()));
			ingredientsEle.appendChild(ingredientEle);
		}
		dishEle.appendChild(ingredientsEle);
		
		Element descriptionEle = SaveData.dom().createElement("Description");
		CDATASection descriptionText = SaveData.dom().createCDATASection(dish.description());
		descriptionEle.appendChild(descriptionText);
		dishEle.appendChild(descriptionEle);
		
		Element prepEle = SaveData.dom().createElement("PrepDirections");
		CDATASection prepText = SaveData.dom().createCDATASection(dish.instructions());
		prepEle.appendChild(prepText);
		dishEle.appendChild(prepEle);
		
		return dishEle;
	}
	
	static private void printToFile(File file){
		try{
			OutputFormat format = new OutputFormat(SaveData.dom());
			format.setIndenting(true);
			XMLSerializer serializer = new XMLSerializer(new FileOutputStream(file), format);
			serializer.serialize(SaveData.dom());

		} catch(IOException ie){}
	}
}
