package menumanager.src;

import java.util.Vector;

/**
 *
 * @author Glenn Ulansey
 */
public class Menu {
	private static Menu ref;
	public static Menu getSingletonObject(){
		if(ref == null)
			ref = new Menu();
		return ref;
	}	
	
	private Vector<MenuDay> days_;
	private Menu(){
		this.days_ = new Vector();
	}
	
	public void dayIs(MenuDay menuDay){ this.days_.add(menuDay); }
	public Vector<MenuDay> days(){ return this.days_; }
	public void clearMenu(){ this.days_.removeAllElements(); }
	public void removeDay(MenuDay menuDay){ this.days_.remove(menuDay); }
}
