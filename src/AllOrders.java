import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.*;

public class AllOrders extends JPanel {
    Action delete = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                System.out.println(e.getActionCommand());
                new EditOrder();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    };

    public AllOrders() throws ClassNotFoundException, SQLException {
        setPreferredSize(new Dimension(900, 600));

        String column[] = {"OrderID", "Customer name", "Order date", "Deliverd", "Retour", "Edit"};
        Database data = new Database();

        JTable jt = new JTable(data.getOders(), column);

        ButtonColumn buttonColumn = new ButtonColumn(jt, delete, 5);
        buttonColumn.setMnemonic(KeyEvent.VK_D);

        jt.setBounds(30, 40, 200, 300);
        jt.setAutoCreateRowSorter(true);
        JScrollPane sp = new JScrollPane(jt);
        add(sp);
        setVisible(true);
    }
}
