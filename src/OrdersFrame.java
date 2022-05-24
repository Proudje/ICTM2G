import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.*;

public class OrdersFrame extends JPanel {
    JTable jt;
    Action delete = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                new ViewOrderFrame(Integer.parseInt(jt.getValueAt(Integer.parseInt(e.getActionCommand()), 0).toString()),Integer.parseInt(jt.getValueAt(Integer.parseInt(e.getActionCommand()), 1).toString()));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    };

    public OrdersFrame() throws ClassNotFoundException, SQLException {
        setPreferredSize(new Dimension(900, 600));

        String column[] = {"OrderID", "CustomerID", "Customer name", "Order date", "Deliverd", "Edit"};
        Database data = new Database();

        jt = new JTable(data.getOders(), column);

        ButtonColumn buttonColumn = new ButtonColumn(jt, delete, 5);
        buttonColumn.setMnemonic(KeyEvent.VK_D);

        jt.setBounds(30, 40, 200, 300);
        jt.setAutoCreateRowSorter(true);
        JScrollPane sp = new JScrollPane(jt);
        add(sp);
        setVisible(true);
    }
    public Object GetData(JTable table, int row_index, int col_index){
        return table.getModel().getValueAt(row_index, col_index);
    }
}
