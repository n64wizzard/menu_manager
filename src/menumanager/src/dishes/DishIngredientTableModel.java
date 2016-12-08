package menumanager.src.dishes;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Glenn Ulansey
 */
public class DishIngredientTableModel extends AbstractTableModel{
	private Dish currentDish_;
	
	public DishIngredientTableModel(){
		super();
		this.currentDish_ = null;
	}
	
	public void currentDishIs(Dish currentDish){ this.currentDish_ = currentDish; }
	
	public int getRowCount(){
		if(this.currentDish_ == null)
			return 0;
		else
			return this.currentDish_.ingredientCount(); 
	}
	public int getColumnCount(){ return 3; }
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex){ 
		if(columnIndex == 1)
			return true;
		return false;
	}
	
	@Override
	public String getColumnName(int columnIndex) {
		if(columnIndex == 0)
			return "Name";
		else if(columnIndex == 1)
			return "Units Per Person";
		else if(columnIndex == 2)
			return "Measure Unit";
		else
			throw new ArrayIndexOutOfBoundsException();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		if(this.currentDish_ == null)
			throw new ArrayIndexOutOfBoundsException();
			
		DishIngredient dishIngredient = this.currentDish_.ingredient(rowIndex);
		if(columnIndex == 0)
			return dishIngredient.name();
		else if(columnIndex == 1)
			return dishIngredient.quantityPerServing();
		else if(columnIndex == 2)
			return dishIngredient.measureUnit();
		else
			throw new ArrayIndexOutOfBoundsException();
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex){
		if(this.currentDish_ == null)
			throw new ArrayIndexOutOfBoundsException();
				
		DishIngredient dishIngredient = this.currentDish_.ingredient(rowIndex);
		if(columnIndex == 1)
			dishIngredient.quantityPerServingIs(Double.parseDouble((String)aValue));
		else
			throw new ArrayIndexOutOfBoundsException();
	}
}
