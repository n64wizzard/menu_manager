/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package menumanager.src.options;

import java.util.Vector;

/**
 *
 * @author Glenn Ulansey
 */
public class GroceryStore {
	static private Vector<GroceryStore> storeNames = new Vector();
	final static String fileName = "groceryStores.xml";
	static public Vector<GroceryStore> storeNames(){ return GroceryStore.storeNames; }
	static public void addStoreName(GroceryStore store){ GroceryStore.storeNames.add(store); }
	static public void clearStoreNames(){ GroceryStore.storeNames.clear(); }
	static public void removeStore(int index){ GroceryStore.storeNames.remove(index); }
	static public int storeCount(){ return GroceryStore.storeNames.size(); }
	static public GroceryStore store(int index){ return GroceryStore.storeNames.get(index); }
	
	private String storeName_;
	
	public GroceryStore(String storeName){
		this.storeName_ = storeName;
	}
	
	@Override 
	public String toString(){ return this.storeName_; }
	
	@Override
	public boolean equals(Object aThat){
		if(aThat != null && ((GroceryStore)aThat).toString().equals(this.toString()))
			return true;
		return false;
	}
}
