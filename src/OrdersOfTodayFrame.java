import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.*;

public class OrdersOfTodayFrame extends JPanel {
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
                new ViewOrderFrame(Integer.parseInt(jt.getValueAt(Integer.parseInt(e.getActionCommand()), 0).toString()), Integer.parseInt(jt.getValueAt(Integer.parseInt(e.getActionCommand()), 1).toString()));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    };

    public OrdersOfTodayFrame() throws ClassNotFoundException, SQLException {
        setPreferredSize(new Dimension(900, 600));
        Database data = new Database();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        String column[] = {"OrderID", "CustomerID", "Customer name", "Edit"};
        jt = new JTable(data.getOrderFromToday(), column);
        int Orders = jt.getRowCount();
        JLabel jl = new JLabel("Bestellingen vandaag: " + Orders);

        ButtonColumn buttonColumn = new ButtonColumn(jt, delete, 3);
        buttonColumn.setMnemonic(KeyEvent.VK_D);

        jt.setAutoCreateRowSorter(true);
        JScrollPane sp = new JScrollPane(jt);
        add(jl);
        add(sp);
        setVisible(true);
    }


}
