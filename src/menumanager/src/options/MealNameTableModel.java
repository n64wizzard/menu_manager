package menumanager.src.options;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Glenn Ulansey
 */
public class MealNameTableModel extends AbstractTableModel{
	public int getRowCount(){
		return MealName.mealNameCount();
	}
	public int getColumnCount(){ return 1; }
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex){ return false; }
	
	@Override
	public String getColumnName(int columnIndex) {
		if(columnIndex == 0)
			return "Meal Name";
		else
			throw new ArrayIndexOutOfBoundsException();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {	
		if(columnIndex == 0)
			return MealName.mealName(rowIndex);
		else
			throw new ArrayIndexOutOfBoundsException();
	}
}
