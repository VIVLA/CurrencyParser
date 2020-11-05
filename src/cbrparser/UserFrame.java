
package cbrparser;

import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class UserFrame extends JFrame implements Runnable {
    
    @Override
    public void run() {
        try {
            initTable();
        } catch (Exception ex) {
            Logger.getLogger(UserFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        initFrame();        
    }

    private void initTable() throws Exception {
        String columnNames [] = {"Currency", "Value"};
        JTable table = new JTable(Parser.getRates(), columnNames);
        
        table.getTableHeader().setFont(new Font("Arial", Font.ITALIC, 20));
        table.setFont(new Font("Arial", Font.BOLD, 18));
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        
        table.setRowHeight(getHeight() + 25);
        
        JScrollPane scroll = new JScrollPane(table);
        add(scroll);
    }
    
    private void initFrame() {
        pack();
        setTitle("Currency Parser");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
   
}
