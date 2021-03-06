/*
 * IngredientsPanel.java
 *
 * Created on May 23, 2011, 2:11:10 PM
 */
package menumanager;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import menumanager.src.options.GroceryStore;
import menumanager.src.ingredients.Ingredient;
import menumanager.src.options.IngredientCategory;
import menumanager.src.ingredients.IngredientTableModel;
import menumanager.src.ingredients.Ingredients;
import menumanager.src.options.MeasureUnit;

/**
 *
 * @author Glenn Ulansey
 */
public class IngredientsPanel extends javax.swing.JPanel {

	/** Creates new form IngredientsPanel */
	public IngredientsPanel() {
		initComponents();
		
		// Create all of the ComboBoxes
		Vector<GroceryStore> storeNames = GroceryStore.storeNames();
		Vector<IngredientCategory> categories = IngredientCategory.categories();
		Vector<MeasureUnit> measureUnits = MeasureUnit.measureUnits();
		
		TableColumnModel tcm = this.ingredientsTable.getColumnModel();
		
		// If we want to make a SelectEdit field, use the following line per ComboBox:
		//  storeNamesComboBox.setEditable(true);	
		JComboBox storeNamesComboBox = new JComboBox(storeNames);
		tcm.getColumn(3).setCellEditor(new DefaultCellEditor(storeNamesComboBox));
		
		JComboBox categoriesComboBox = new JComboBox(categories);
		tcm.getColumn(4).setCellEditor(new DefaultCellEditor(categoriesComboBox));
		
		JComboBox meaureUnitsComboBox = new JComboBox(measureUnits);
		tcm.getColumn(1).setCellEditor(new DefaultCellEditor(meaureUnitsComboBox));
		
		this.ingredientsTable.setAutoCreateRowSorter(true);
	}
	
	public void addNewIngredient(){
		boolean duplicate = false;
		Ingredient.ID newID;
		do{
			newID = new Ingredient.ID((new Random()).nextInt(100000000));
			for(Ingredient ingredient: Ingredients.getSingletonObject().ingredients())
				if(ingredient.id().equals(newID))
					duplicate = true;
		}while(duplicate);
				
		Ingredient ingredient = new Ingredient(newID);
		ingredient.empty();
		Ingredients.getSingletonObject().ingredientIs(ingredient);
		this.updatePanelContents();
	}
	
	public void updatePanelContents(){
		IngredientTableModel tm = (IngredientTableModel)this.ingredientsTable.getModel();
		this.ingredientsTable.setAutoCreateColumnsFromModel(false);
		//tm.fireTableRowsInserted(0,1);
		tm.fireTableStructureChanged();
	}
	
	private void deleteIngredient(){
		int[] selectedRows = this.ingredientsTable.getSelectedRows();
		for(int i = 0; i < selectedRows.length; i++){
			int modelRow = this.ingredientsTable.convertRowIndexToModel(selectedRows[i]);
				int confirmValue = JOptionPane.showConfirmDialog(null,
				"Are you sure you want delete – " + this.ingredientsTable.getValueAt(selectedRows[i], 0).toString() + "?",
				"Confirm Deletion", JOptionPane.OK_CANCEL_OPTION);
			if(confirmValue == -1 || confirmValue == 2)
				return ;

			this.ingredientsTable.editingCanceled(null);
			Ingredients.getSingletonObject().removeIngredient(modelRow);
			this.updatePanelContents();

			break;
		}
		
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        ingredientsTable = new javax.swing.JTable();
        createIngredientButton = new javax.swing.JButton();
        deleteIngredientButton = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(1000, 300));
        setMinimumSize(new java.awt.Dimension(1000, 300));
        setName("Form"); // NOI18N
        setPreferredSize(new java.awt.Dimension(1000, 300));

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        ingredientsTable.setModel(new IngredientTableModel());
        ingredientsTable.setName("ingredientsTable"); // NOI18N
        ingredientsTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ingredientsTableKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(ingredientsTable);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(menumanager.MenuManagerApp.class).getContext().getResourceMap(IngredientsPanel.class);
        createIngredientButton.setText(resourceMap.getString("createIngredientButton.text")); // NOI18N
        createIngredientButton.setName("createIngredientButton"); // NOI18N
        createIngredientButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createIngredientButtonActionPerformed(evt);
            }
        });

        deleteIngredientButton.setText(resourceMap.getString("deleteIngredientButton.text")); // NOI18N
        deleteIngredientButton.setName("deleteIngredientButton"); // NOI18N
        deleteIngredientButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteIngredientButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 980, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(createIngredientButton)
                        .addGap(18, 18, 18)
                        .addComponent(deleteIngredientButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(deleteIngredientButton)
                    .addComponent(createIngredientButton))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

	private void ingredientsTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ingredientsTableKeyReleased
		if(evt.getKeyCode() == KeyEvent.VK_DELETE){
			this.deleteIngredient();
		}
}//GEN-LAST:event_ingredientsTableKeyReleased

	private void createIngredientButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createIngredientButtonActionPerformed
		this.addNewIngredient();
	}//GEN-LAST:event_createIngredientButtonActionPerformed

	private void deleteIngredientButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteIngredientButtonActionPerformed
		this.deleteIngredient();
	}//GEN-LAST:event_deleteIngredientButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton createIngredientButton;
    private javax.swing.JButton deleteIngredientButton;
    private javax.swing.JTable ingredientsTable;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
