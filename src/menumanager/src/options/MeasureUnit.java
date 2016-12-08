package menumanager.src.options;

import java.util.Vector;

/**
 *
 * @author Glenn Ulansey
 */
public class MeasureUnit {
	static Vector<MeasureUnit> measureUnits = new Vector();;
	static public void addMeasureUnit(MeasureUnit measureUnit){ MeasureUnit.measureUnits.add(measureUnit); }
	static public Vector<MeasureUnit> measureUnits(){ return MeasureUnit.measureUnits; }
	static public void clearMeasureUnits(){ MeasureUnit.measureUnits.clear(); }
	static public void removeMeasureUnit(int index){ MeasureUnit.measureUnits.remove(index); }
	static public int measureUnitCount(){ return MeasureUnit.measureUnits.size(); }
	static public MeasureUnit measureUnit(int index){ return MeasureUnit.measureUnits.get(index); }
	
	private String unitName_;
	
	public MeasureUnit(String unitName){
		this.unitName_ = unitName;
	}
	
	@Override 
	public String toString(){ return this.unitName_; }
	@Override
	public boolean equals(Object aThat){
		if(aThat != null && ((MeasureUnit)aThat).toString().equals(this.toString()))
			return true;
		return false;
	}
}
