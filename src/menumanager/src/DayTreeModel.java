package menumanager.src;

import java.util.Vector;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author Glenn Ulansey
 */
public class DayTreeModel extends AbstractTreeModel{
	private MenuDay menuDay_;

	public DayTreeModel(MenuDay menuDay){
		super();
		this.menuDay_ = menuDay;
	}
	
	public Object getRoot() {
		return this.menuDay_;
	}
	
	public void reload(){
		this.fireTreeStructureChanged(new TreePath(this.menuDay_));
	}

	public Object getChild(Object parent, int index) {
		if(parent.getClass() == MenuDay.class){
			return ((MenuDay)parent).meal(index);
		}else if(parent.getClass() == MenuMeal.class){
			return ((MenuMeal)parent).dish(index);
		}else{
			throw new UnsupportedOperationException();
		}
	}

	public int getChildCount(Object parent) {
		if(parent.getClass() == MenuDay.class){
			return ((MenuDay)parent).mealCount();
		}else if(parent.getClass() == MealDish.class){
			return 0;
		}else if(parent.getClass() == MenuMeal.class){
			return ((MenuMeal)parent).dishCount();
		}else{
			throw new UnsupportedOperationException();
		}
	}

	public boolean isLeaf(Object node) {
		if(node.getClass() == MenuDay.class){
			return false;
		}else if(node.getClass() == MealDish.class){
			return true;
		}else if(node.getClass() == MenuMeal.class){
			return false;
		}else{
			throw new UnsupportedOperationException();
		}
	}

	public void valueForPathChanged(TreePath path, Object newValue) {
		System.out.println("hey");
		Object[] objects = path.getPath();
		MenuDay menuDay = null;
		MenuMeal menuMeal = null;
		MealDish dish = null;
		
		if(path.getPathCount() > 1)
			menuDay = (MenuDay)objects[0];
		if(path.getPathCount() > 2)
			menuMeal = (MenuMeal)objects[0];
		if(path.getPathCount() > 3)
			dish = (MealDish)objects[0];
	}

	public int getIndexOfChild(Object parent, Object child) {
		if(parent.getClass() == MenuDay.class){
			return ((MenuDay)parent).mealIndex((MenuMeal)child);
		}else if(parent.getClass() == MealDish.class){
			return -1;
		}else if(parent.getClass() == MenuMeal.class){
			return ((MenuMeal)parent).dishIndex((MealDish)child);
		}else{
			throw new UnsupportedOperationException();
		}
	}
}
