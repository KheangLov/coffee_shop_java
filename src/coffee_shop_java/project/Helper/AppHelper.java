/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffee_shop_java.project.Helper;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;

/**
 *
 * @author KHEANG
 */
public class AppHelper {
    
    public static void setColWidth(JTable table, int col, int width){
        DefaultTableColumnModel columnModel = (DefaultTableColumnModel) table.getColumnModel();
        columnModel.getColumn(col).setWidth(width);
        columnModel.getColumn(col).setPreferredWidth(width);
        columnModel.getColumn(col).setMinWidth(width);
        columnModel.getColumn(col).setMaxWidth(width);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.LEFT);
        columnModel.getColumn(col).setCellRenderer(renderer);
    }
}
