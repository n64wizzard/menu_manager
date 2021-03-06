/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * OptionsPanel.java
 *
 * Created on May 26, 2011, 6:39:06 PM
 */
package menumanager;

import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import menumanager.src.options.GroceryStore;
import menumanager.src.options.GroceryStoreTableModel;
import menumanager.src.options.IngredientCategory;
import menumanager.src.options.IngredientCategoryTableModel;
import menumanager.src.options.MealName;
import menumanager.src.options.MealNameTableModel;
import menumanager.src.options.MeasureUnit;
import menumanager.src.options.MeasureUnitTableModel;

/**
 *
 * @author Glenn Ulansey
 */
public class OptionsPanel extends javax.swing.JPanel {

	/** Creates new form OptionsPanel */
	public OptionsPanel() {
		initComponents();
	}
	
	public void updatePanelContents(){
		IngredientCategoryTableModel ictm = (IngredientCategoryTableModel)this.ingredientCategoryTable.getModel();
		ictm.fireTableStructureChanged();
		
		MeasureUnitTableModel mutm = (MeasureUnitTableModel)this.measureUnitsTable.getModel();
		mutm.fireTableStructureChanged();
		
		GroceryStoreTableModel gctm = (GroceryStoreTableModel)this.groceryStoreTable.getModel();
		gctm.fireTableStructureChanged();
		
		MealNameTableModel mntm = (MealNameTableModel)this.mealNamesTable.getModel();
		mntm.fireTableStructureChanged();
	}
	
	public void deleteCategory(){
		int[] selectedRows = this.ingredientCategoryTable.getSelectedRows();
		for(int i = 0; i < selectedRows.length; i++){
			int confirmValue = JOptionPane.showConfirmDialog(null,
				"Are you sure you want delete – " + this.ingredientCategoryTable.getModel().getValueAt(selectedRows[i], 0) + "?",
				"Confirm Deletion", JOptionPane.OK_CANCEL_OPTION);
			if(confirmValue == -1 || confirmValue == 2)
				return ;

			IngredientCategory.removeCategory(selectedRows[i]);
			this.updatePanelContents();
			break;
		}
	}
	
	public void deleteMealName(){
		int[] selectedRows = this.mealNamesTable.getSelectedRows();
		for(int i = 0; i < selectedRows.length; i++){
			int confirmValue = JOptionPane.showConfirmDialog(null,
				"Are you sure you want delete – " + this.mealNamesTable.getModel().getValueAt(selectedRows[i], 0) + "?",
				"Confirm Deletion", JOptionPane.OK_CANCEL_OPTION);
			if(confirmValue == -1 || confirmValue == 2)
				return ;

			MealName.removeMealName(selectedRows[i]);
			this.updatePanelContents();
			break;
		}
	}
		
	public void deleteStore(){
		int[] selectedRows = this.groceryStoreTable.getSelectedRows();
		for(int i = 0; i < selectedRows.length; i++){
			int confirmValue = JOptionPane.showConfirmDialog(null,
				"Are you sure you want delete – " + this.groceryStoreTable.getModel().getValueAt(selectedRows[i], 0) + "?",
				"Confirm Deletion", JOptionPane.OK_CANCEL_OPTION);
			if(confirmValue == -1 || confirmValue == 2)
				return ;

			GroceryStore.removeStore(selectedRows[i]);
			this.updatePanelContents();
			break;
		}
	}
	
	public void deleteMeasureUnit(){
		int[] selectedRows = this.measureUnitsTable.getSelectedRows();
		for(int i = 0; i < selectedRows.length; i++){
			int confirmValue = JOptionPane.showConfirmDialog(null,
				"Are you sure you want delete – " + this.measureUnitsTable.getModel().getValueAt(selectedRows[i], 0) + "?",
				"Confirm Deletion", JOptionPane.OK_CANCEL_OPTION);
			if(confirmValue == -1 || confirmValue == 2)
				return ;

			MeasureUnit.removeMeasureUnit(selectedRows[i]);
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
        groceryStoreTable = new javax.swing.JTable();
        createStoreButton = new javax.swing.JButton();
        deleteStoreButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        measureUnitsTable = new javax.swing.JTable();
        deleteUnitButton = new javax.swing.JButton();
        createUnitButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        ingredientCategoryTable = new javax.swing.JTable();
        deleteCategoryButton = new javax.swing.JButton();
        createCategoryButton = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        mealNamesTable = new javax.swing.JTable();
        createMealButton = new javax.swing.JButton();
        deleteMealButton = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(1000, 300));
        setMinimumSize(new java.awt.Dimension(1000, 300));
        setName("Form"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        groceryStoreTable.setModel(new GroceryStoreTableModel());
        groceryStoreTable.setName("groceryStoreTable"); // NOI18N
        groceryStoreTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                groceryStoreTableKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(groceryStoreTable);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(menumanager.MenuManagerApp.class).getContext().getResourceMap(OptionsPanel.class);
        createStoreButton.setText(resourceMap.getString("createStoreButton.text")); // NOI18N
        createStoreButton.setName("createStoreButton"); // NOI18N
        createStoreButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createStoreButtonActionPerformed(evt);
            }
        });

        deleteStoreButton.setText(resourceMap.getString("deleteStoreButton.text")); // NOI18N
        deleteStoreButton.setName("deleteStoreButton"); // NOI18N
        deleteStoreButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteStoreButtonActionPerformed(evt);
            }
        });

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        measureUnitsTable.setModel(new MeasureUnitTableModel());
        measureUnitsTable.setName("measureUnitsTable"); // NOI18N
        measureUnitsTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                measureUnitsTableKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(measureUnitsTable);

        deleteUnitButton.setText(resourceMap.getString("deleteUnitButton.text")); // NOI18N
        deleteUnitButton.setName("deleteUnitButton"); // NOI18N
        deleteUnitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteUnitButtonActionPerformed(evt);
            }
        });

        createUnitButton.setText(resourceMap.getString("createUnitButton.text")); // NOI18N
        createUnitButton.setName("createUnitButton"); // NOI18N
        createUnitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createUnitButtonActionPerformed(evt);
            }
        });

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        ingredientCategoryTable.setModel(new IngredientCategoryTableModel());
        ingredientCategoryTable.setName("ingredientCategoryTable"); // NOI18N
        ingredientCategoryTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ingredientCategoryTableKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(ingredientCategoryTable);

        deleteCategoryButton.setText(resourceMap.getString("deleteCategoryButton.text")); // NOI18N
        deleteCategoryButton.setName("deleteCategoryButton"); // NOI18N
        deleteCategoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteCategoryButtonActionPerformed(evt);
            }
        });

        createCategoryButton.setText(resourceMap.getString("createCategoryButton.text")); // NOI18N
        createCategoryButton.setName("createCategoryButton"); // NOI18N
        createCategoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createCategoryButtonActionPerformed(evt);
            }
        });

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        mealNamesTable.setModel(new MealNameTableModel());
        mealNamesTable.setName("mealNamesTable"); // NOI18N
        mealNamesTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                mealNamesTableKeyReleased(evt);
            }
        });
        jScrollPane4.setViewportView(mealNamesTable);

        createMealButton.setText(resourceMap.getString("createMealButton.text")); // NOI18N
        createMealButton.setName("createMealButton"); // NOI18N
        createMealButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createMealButtonActionPerformed(evt);
            }
        });

        deleteMealButton.setText(resourceMap.getString("deleteMealButton.text")); // NOI18N
        deleteMealButton.setName("deleteMealButton"); // NOI18N
        deleteMealButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteMealButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(createStoreButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteStoreButton))
                    .addComponent(jScrollPane1, 0, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(createUnitButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteUnitButton))
                    .addComponent(jScrollPane2, 0, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(createCategoryButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteCategoryButton))
                    .addComponent(jScrollPane3, 0, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(createMealButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteMealButton))
                    .addComponent(jScrollPane4, 0, 0, Short.MAX_VALUE))
                .addGap(88, 88, 88))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(createMealButton)
                            .addComponent(deleteMealButton)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(createStoreButton)
                            .addComponent(deleteStoreButton)
                            .addComponent(createUnitButton)
                            .addComponent(deleteUnitButton)
                            .addComponent(createCategoryButton)
                            .addComponent(deleteCategoryButton))))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

	private void createStoreButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createStoreButtonActionPerformed
		Object response = JOptionPane.showInputDialog(null, "New Store Name:", 
			"Create New Store", JOptionPane.QUESTION_MESSAGE);
		
		if(response != null){
			GroceryStore.addStoreName(new GroceryStore((String)response));
			this.updatePanelContents();
		}
	}//GEN-LAST:event_createStoreButtonActionPerformed

	private void deleteStoreButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteStoreButtonActionPerformed
		this.deleteStore();
	}//GEN-LAST:event_deleteStoreButtonActionPerformed

	private void createUnitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createUnitButtonActionPerformed
		Object response = JOptionPane.showInputDialog(null, "New Measure Unit Name:", 
			"Create New Measure Unit", JOptionPane.QUESTION_MESSAGE);
		
		if(response != null){
			MeasureUnit.addMeasureUnit(new MeasureUnit((String)response));
			this.updatePanelContents();
		}
	}//GEN-LAST:event_createUnitButtonActionPerformed

	private void deleteUnitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteUnitButtonActionPerformed
		this.deleteMeasureUnit();
	}//GEN-LAST:event_deleteUnitButtonActionPerformed

	private void createCategoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createCategoryButtonActionPerformed
		Object response = JOptionPane.showInputDialog(null, "New Category Name:", 
			"Create New Ingredient Category", JOptionPane.QUESTION_MESSAGE);
		
		if(response != null){
			IngredientCategory.addIngredientCategory(new IngredientCategory((String)response));
			this.updatePanelContents();
		}
	}//GEN-LAST:event_createCategoryButtonActionPerformed

	private void deleteCategoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteCategoryButtonActionPerformed
		this.deleteCategory();
	}//GEN-LAST:event_deleteCategoryButtonActionPerformed

	private void groceryStoreTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_groceryStoreTableKeyReleased
		if(evt.getKeyCode() == KeyEvent.VK_DELETE)
			this.deleteStore();
	}//GEN-LAST:event_groceryStoreTableKeyReleased

	private void measureUnitsTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_measureUnitsTableKeyReleased
		if(evt.getKeyCode() == KeyEvent.VK_DELETE)
			this.deleteMeasureUnit();
	}//GEN-LAST:event_measureUnitsTableKeyReleased

	private void ingredientCategoryTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ingredientCategoryTableKeyReleased
		if(evt.getKeyCode() == KeyEvent.VK_DELETE)
			this.deleteCategory();
	}//GEN-LAST:event_ingredientCategoryTableKeyReleased

	private void mealNamesTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mealNamesTableKeyReleased
		if(evt.getKeyCode() == KeyEvent.VK_DELETE)
			this.deleteMealName();
	}//GEN-LAST:event_mealNamesTableKeyReleased

	private void createMealButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createMealButtonActionPerformed
		Object response = JOptionPane.showInputDialog(null, "New Meal Name:", 
			"Create New Meal Name", JOptionPane.QUESTION_MESSAGE);
		
		if(response != null){
			MealName.addMealName(new MealName((String)response));
			this.updatePanelContents();
		}
	}//GEN-LAST:event_createMealButtonActionPerformed

	private void deleteMealButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteMealButtonActionPerformed
		this.deleteMealName();
	}//GEN-LAST:event_deleteMealButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton createCategoryButton;
    private javax.swing.JButton createMealButton;
    private javax.swing.JButton createStoreButton;
    private javax.swing.JButton createUnitButton;
    private javax.swing.JButton deleteCategoryButton;
    private javax.swing.JButton deleteMealButton;
    private javax.swing.JButton deleteStoreButton;
    private javax.swing.JButton deleteUnitButton;
    private javax.swing.JTable groceryStoreTable;
    private javax.swing.JTable ingredientCategoryTable;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable mealNamesTable;
    private javax.swing.JTable measureUnitsTable;
    // End of variables declaration//GEN-END:variables
}
