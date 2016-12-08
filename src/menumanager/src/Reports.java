package menumanager.src;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.Vector;
import menumanager.src.dishes.Dish;
import menumanager.src.dishes.DishIngredient;
import menumanager.src.dishes.Dishes;
import menumanager.src.ingredients.Ingredient;
import menumanager.src.ingredients.Ingredients;
import menumanager.src.options.GroceryStore;
import menumanager.src.options.IngredientCategory;
import orinoco.*;

/**
 *
 * @author Glenn Ulansey
 */
public class Reports {
	static public void createShoppingList(String fileName, Vector<MenuDay> menuDays, File outputFile){
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String currentDate = dateFormat.format(date);
		
		try{
			Document doc = new Document(Paper.LETTER, new PDFWriter(outputFile));

			Font titleFont = new Font(Font.HELVETICA_BOLD, 14);
			LayoutWriter header = doc.getHeader();

			Column[] headercols = new Column[2];
			headercols[0] = new Column(13, Alignment.LEFT);
			headercols[1] = new Column(2, Alignment.RIGHT);
			
			Font dataFont = new Font(Font.HELVETICA, 11);
			Column col1 = new Column(8, Alignment.LEFT, dataFont);
			Column col2 = new Column(4, Alignment.LEFT, dataFont);
			Column col3 = new Column(5, Alignment.LEFT, dataFont);
			Column col4 = new Column(2, Alignment.CENTRE, dataFont);
			Column[] cols = new Column[] {col1, col2, col3, col4};

			Table ht = header.createTable(headercols);
			String[] titlerow = new String[]{currentDate, "Page"};
			ht.addCells(titlerow);
			TextWriter w = ht.getCellWriter(1);
			w.writeMacro(new PageNumber("Page ", "", doc));
			ht.writeRow();
			ht.close();
			header.writeLine("DAO Shopping List - " + fileName, new Font(Font.TIMES_BOLD, 14), Alignment.CENTRE);
			header.space(.2);
			header.drawLine();
			
			LayoutWriter footer = doc.getFooter();
			footer.drawLine();
			Table keyTable = footer.createTable(cols);
			String[] keyRow = new String[]{"Key:A", "B (C) D", "(E @ F)", "G - H - I"};
			keyTable.addCells(keyRow);
			keyTable.writeRow();
			keyTable.close();
			footer.writeLine("A: Item Name; B: Amount to Purchase; C: Actual # required; D: Measuring Unit; E: # of packages to purchase; F: Quantity per package");
			footer.writeLine("G: # of Dishes with Ingredient; H: # of Meals with Ingredient; I: # Extra Purchased");
			
			doc.open();
			
			HashMap<Integer, Double> ingredients = new HashMap();
			HashMap<Integer, Integer> numDishes = new HashMap();
			HashMap<Integer, TreeSet<Integer> > numMeals = new HashMap();	// <Ingredient ID, <meal ID 1, meal ID 2, ...> >
			
			for(MenuDay menuDay : menuDays){
				for(MenuMeal meal : menuDay.meals()){
					for(MealDish mealDish : meal.dishes()){
						Dish dish = Dishes.getSingletonObject().dish(mealDish.id());
						Iterator<DishIngredient> iter = dish.ingredients();
						while(iter.hasNext()){
							DishIngredient ingredient = iter.next();
							double oldValue = ingredients.containsKey(ingredient.id().value()) ? ingredients.get(ingredient.id().value()) : 0;
							double newValue = oldValue + ingredient.quantityPerServing() * menuDay.numPeople();
							TreeSet<Integer> oldMealCount = numMeals.containsKey(ingredient.id().value()) ? numMeals.get(ingredient.id().value()) : new TreeSet();
									
							int newDishCount = numDishes.containsKey(ingredient.id().value()) ? numDishes.get(ingredient.id().value()) + 1 : 1;
							oldMealCount.add(mealDish.id().value());
							
							ingredients.put(ingredient.id().value(), newValue);
							numDishes.put(ingredient.id().value(), newDishCount);
							
							numMeals.put(ingredient.id().value(), oldMealCount);
						}
					}
				}
			}
			
			boolean firstPage = true;
			for(GroceryStore store : GroceryStore.storeNames()){
				if(!firstPage) doc.newPage();
				
				boolean firstCategory = true;
				for(IngredientCategory category : IngredientCategory.categories()){
					if(!firstCategory){
						doc.space(.5);
						doc.drawLine();
					}
					firstCategory &= false;
					
					String storeName = store.toString().compareTo("") == 0 ? "Other" : store.toString();
					String categoryName = category.toString().compareTo("") == 0 ? "Other" : category.toString();
					
					doc.writeLine(storeName + " - " + categoryName, new Font(Font.TIMES_BOLD, 12));
					doc.drawLine();
					
					Table table = doc.createTable(cols);
					table.setColumnSpacing(0.25);
	  
					for(Ingredient ingredient : Ingredients.getSingletonObject().ingredients()){
						if(ingredient.category().equals(category) 
								&& ingredient.store().equals(store)
								&& ingredients.containsKey(ingredient.id().value())){
							double amount = ingredients.get(ingredient.id().value());
							int numPackages = (int)Math.ceil(amount / ingredient.unitsPerPackage());
							double packagedAmount = ingredient.unitsPerPackage() * numPackages;
							
							int mealCount = numMeals.containsKey(ingredient.id().value()) ? numMeals.get(ingredient.id().value()).size() : 0;
							int dishCount = numDishes.get(ingredient.id().value());
							
							String[] data = new String[] {ingredient.name(), 
								packagedAmount + " (" + amount + ") " + ingredient.measureUnit().toString(), 
								"(" + numPackages + " @ " + ingredient.unitsPerPackage() + " " + ingredient.measureUnit().toString() + ")", 
								 dishCount + " - " + mealCount + " - 0"};
							table.addRow(data);
						}
					}
					table.close();
				}
				firstPage &= false;
			}
			
			doc.close();
		} catch (Throwable t){ t.printStackTrace(); }
	}

	public static void createDailyDirections(String fileName, Vector<MenuDay> menuDays, File outputFile) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String currentDate = dateFormat.format(date);
		
		try{
			Document doc = new Document(Paper.LETTER, new PDFWriter(outputFile));
			doc.addTab(1);

			Font titleFont = new Font(Font.HELVETICA_BOLD, 14);
			LayoutWriter header = doc.getHeader();

			Column[] headercols = new Column[2];
			headercols[0] = new Column(13, Alignment.LEFT);
			headercols[1] = new Column(2, Alignment.RIGHT);
			
			Font dataFont = new Font(Font.HELVETICA, 11);

			Table ht = header.createTable(headercols);
			String[] titlerow = new String[]{currentDate, "Page"};
			ht.addCells(titlerow);
			TextWriter w = ht.getCellWriter(1);
			w.writeMacro(new PageNumber("Page ", "", doc));
			ht.writeRow();
			ht.close();
			header.writeLine("DAO Cooking Directions - " + fileName, new Font(Font.TIMES_BOLD, 14), Alignment.CENTRE);
			header.space(.2);
			header.drawLine();
			
			doc.open();
			
			Column col1 = new Column(4, Alignment.LEFT, dataFont);
			Column col2 = new Column(5, Alignment.CENTRE, dataFont);
			Column col3 = new Column(5, Alignment.LEFT, dataFont);
			Column[] ingredientCols = new Column[] {col1, col2, col3};
			
			boolean firstPage = true;
			for(MenuDay menuDay : menuDays){
				for(MenuMeal meal : menuDay.meals()){
					if(!firstPage) doc.newPage();
					firstPage &= false;
				
					doc.writeLine(menuDay.name() + " - " + meal.mealName() + ": Serves " + menuDay.numPeople(), titleFont, Alignment.LEFT);
			
					for(MealDish mealDish : meal.dishes()){
						doc.space(.5);
						doc.drawLine();
						doc.writeLine("Recipe: " + mealDish.name(), new Font(Font.HELVETICA_BOLD, 12), Alignment.LEFT);
							
						Dish dish = Dishes.getSingletonObject().dish(mealDish.id());
						Iterator<DishIngredient> iter = dish.ingredients();
						while(iter.hasNext()){
							DishIngredient ingredient = iter.next();
							double amount = ingredient.quantityPerServing() * menuDay.numPeople();
							
							Table table = doc.createTable(ingredientCols);
							String[] ingredientRow = new String[]{"", amount + " " + ingredient.measureUnit().toString(), ingredient.name()};
							table.addCells(ingredientRow);
							table.writeRow();
							table.close();
						}
						doc.newLine();
						doc.tab();
						doc.setIndent();
						String[] instructions = dish.instructions().split("\n");
						for(String line : instructions)
							doc.writeLine(line, dataFont, Alignment.LEFT);

						doc.releaseIndent();
						doc.newLine();
					}
				}
			}
			
			doc.close();
		} catch (Throwable t){ t.printStackTrace(); }
	}
}
