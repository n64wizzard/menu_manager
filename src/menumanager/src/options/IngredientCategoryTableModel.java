package menumanager.src.options;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Glenn Ulansey
 */
public class IngredientCategoryTableModel extends AbstractTableModel{
	
	public IngredientCategoryTableModel(){
		super();
	}
	
	public int getRowCount(){
		return IngredientCategory.categoryCount();
	}
	public int getColumnCount(){ return 1; }
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex){ return false; }
	
	@Override
	public String getColumnName(int columnIndex) {
		if(columnIndex == 0)
			return "Category Name";
		else
			throw new ArrayIndexOutOfBoundsException();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {	
		if(columnIndex == 0)
			return IngredientCategory.category(rowIndex);
		else
			throw new ArrayIndexOutOfBoundsException();
	}
}
