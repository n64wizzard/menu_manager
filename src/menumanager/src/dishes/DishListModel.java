package menumanager.src.dishes;

import javax.swing.AbstractListModel;

/**
 *
 * @author Glenn Ulansey
 */
public class DishListModel extends AbstractListModel{

	public int getSize() {
		return Dishes.getSingletonObject().dishesCount();
	}

	public Object getElementAt(int index) {
		return Dishes.getSingletonObject().dish(index).name();
	}
	
	public void updateList(){
		this.fireContentsChanged(this, 0, this.getSize());
	}
}
