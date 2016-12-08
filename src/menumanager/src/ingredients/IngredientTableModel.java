package menumanager.src.ingredients;

import menumanager.src.options.GroceryStore;
import menumanager.src.options.IngredientCategory;
import menumanager.src.options.MeasureUnit;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Glenn Ulansey
 */
public class IngredientTableModel extends AbstractTableModel{		
	public IngredientTableModel(){
		super();
	}
	
	public int getRowCount(){ return Ingredients.getSingletonObject().ingredientsCount(); }
	public int getColumnCount(){ return 5; }
	public boolean isCellEditable(int rowIndex, int columnIndex){ return true; }
	
	public String getColumnName(int columnIndex) {
		if(columnIndex == 0)
			return "Name";
		else if(columnIndex == 1)
			return "Measure Unit";
		else if(columnIndex == 2)
			return "Units Per Package";
		else if(columnIndex == 3)
			return "Store";
		else if(columnIndex == 4)
			return "Category";
		else
			throw new ArrayIndexOutOfBoundsException();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		if(columnIndex == 0)
			return Ingredients.getSingletonObject().ingredient(rowIndex).name();
		else if(columnIndex == 1)
			return Ingredients.getSingletonObject().ingredient(rowIndex).measureUnit();
		else if(columnIndex == 2)
			return Ingredients.getSingletonObject().ingredient(rowIndex).unitsPerPackage();
		else if(columnIndex == 3)
			return Ingredients.getSingletonObject().ingredient(rowIndex).store();
		else if(columnIndex == 4)
			return Ingredients.getSingletonObject().ingredient(rowIndex).category();
		else
			throw new ArrayIndexOutOfBoundsException();
	}
	
	public void setValueAt(Object aValue, int rowIndex, int columnIndex){
		if(columnIndex == 0)
			Ingredients.getSingletonObject().ingredient(rowIndex).nameIs(aValue.toString());
		else if(columnIndex == 1)
			Ingredients.getSingletonObject().ingredient(rowIndex).measureUnitIs((MeasureUnit)aValue);
		else if(columnIndex == 2)
			Ingredients.getSingletonObject().ingredient(rowIndex).unitsPerPackageIs(Double.valueOf(aValue.toString()));
		else if(columnIndex == 3)
			Ingredients.getSingletonObject().ingredient(rowIndex).storeIs((GroceryStore)aValue);
		else if(columnIndex == 4)
			Ingredients.getSingletonObject().ingredient(rowIndex).categoryIs((IngredientCategory)aValue);
		else
			throw new ArrayIndexOutOfBoundsException();
	}
}
