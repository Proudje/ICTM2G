import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.*;

public class ReturnedOrdersFrame extends JPanel {
    JTable jt;
    Action delete = new AbstractAction() {
        /**
         * @param e
         * Gets the first collumn of the order where is clicked on.
         * This gives back the ID of the order and sends it to the ViewOrderFrame
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                new ViewOrderFrame(Integer.parseInt(jt.getValueAt(Integer.parseInt(e.getActionCommand()), 0).toString()),Integer.parseInt(jt.getValueAt(Integer.parseInt(e.getActionCommand()), 1).toString()));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    };
    public ReturnedOrdersFrame() throws ClassNotFoundException, SQLException {
        setPreferredSize(new Dimension(900, 600));

        String column[] = {"OrderID", "CustomerID", "Customer name", "Edit"};
        Database data = new Database();

        jt = new JTable(data.getOrderFromToday(), column);

        ButtonColumn buttonColumn = new ButtonColumn(jt, delete, 3);
        buttonColumn.setMnemonic(KeyEvent.VK_D);

        jt.setBounds(30, 40, 200, 300);
        jt.setAutoCreateRowSorter(true);
        JScrollPane sp = new JScrollPane(jt);
        add(sp);
        setVisible(true);
    }


}
