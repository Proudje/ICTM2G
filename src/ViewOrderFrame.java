import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

public class ViewOrderFrame extends JFrame implements ActionListener {
    private static JLabel userLabel, addressLabel, postalcodeLabel, phonenumberLabel, countryLabel, country, stateProvinceLabel, stateProvice, cityNameLabel, cityName;
    private static JTextField username, address, postalcode, phonenumber;
    private static JButton save;
    JTable jt;
    int orderID;
    int customerID;

    public ViewOrderFrame(int orderID, int customerID) throws SQLException {
        this.orderID = orderID;
        this.customerID = customerID;
        Database data = new Database();
        Customer customer = data.getCustomer(orderID);

        setSize(800,500);
        setVisible(true);
        setTitle("Edit Order");
        JPanel panel = new JPanel();
        setLayout(new GridLayout(2,1));
        JPanel tabel = new JPanel();
        panel.setLayout(null);

        tabel.setLayout(new FlowLayout());
        add(panel);
        add(tabel);

        userLabel = new JLabel("Naam");
        userLabel.setBounds(100, 10, 70, 20);
        panel.add(userLabel);

        username = new JTextField();
        username.setBounds(100, 30, 250, 28);
        panel.add(username);

        countryLabel = new JLabel("Land: ");
        countryLabel.setBounds(400, 10, 70, 20);
        panel.add(countryLabel);

        country = new JLabel(customer.getCountryname());
        country.setBounds(500, 10, 200, 20);
        panel.add(country);

        stateProvinceLabel = new JLabel("Provincie: ");
        stateProvinceLabel.setBounds(400, 30, 70, 20);
        panel.add(stateProvinceLabel);

        stateProvice = new JLabel(customer.getStateprovincename());
        stateProvice.setBounds(500, 30, 200, 20);
        panel.add(stateProvice);

        cityNameLabel = new JLabel("Stad: ");
        cityNameLabel.setBounds(400, 50, 70, 20);
        panel.add(cityNameLabel);

        cityName = new JLabel(customer.getCityname());
        cityName.setBounds(500, 50, 200, 20);
        panel.add(cityName);

        addressLabel = new JLabel("Adres");
        addressLabel.setBounds(100, 60, 70, 20);
        panel.add(addressLabel);

        address = new JTextField();
        address.setBounds(100, 80, 250, 28);
        panel.add(address);

        postalcodeLabel = new JLabel("Postcode");
        postalcodeLabel.setBounds(100, 110, 70, 20);
        panel.add(postalcodeLabel);

        postalcode = new JTextField();
        postalcode.setBounds(100, 130, 250, 28);
        panel.add(postalcode);

        phonenumberLabel = new JLabel("Telefoonnummer");
        phonenumberLabel.setBounds(100, 160, 70, 20);
        panel.add(phonenumberLabel);

        phonenumber = new JTextField();
        phonenumber.setBounds(100, 180, 250, 28);
        panel.add(phonenumber);

        save = new JButton("Opslaan");
        save.setBounds(650, 20, 90, 25);
        save.addActionListener(this);
        panel.add(save);

        String column[] = {"Productnummer", "Productnaam", "Hoeveelheid", "Voorraad", "Prijs per stuk", "Prijs totaal", "Update"};

        username.setText(customer.getName());
        address.setText(customer.getAddress());
        postalcode.setText(customer.getPostalcode());
        phonenumber.setText(customer.getPhonenumber());

        jt = new JTable(data.getProductsFromOrder(orderID), column);

        ButtonColumn buttonColumn = new ButtonColumn(jt, delete, 6);
        buttonColumn.setMnemonic(KeyEvent.VK_D);

        jt.setBounds(100, 220, 400, 100);
        jt.setAutoCreateRowSorter(true);
        JScrollPane sp = new JScrollPane(jt);
        tabel.add(sp);
        setVisible(true);
    }

    /**
     * @param e
     * Updates the Customer
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Database data = new Database();
        try {
            data.updateCustomer(customerID, username.getText(), phonenumber.getText(), address.getText(), postalcode.getText());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    Action delete = new AbstractAction() {
        /**
         * @param e
         * Gets the first collumn of the order where is clicked on.
         * This gives back the ID of the product and sends it to the updateProductFromOrder
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            Database data = new Database();
            try {
                boolean update = data.updateProductFromOrder(Integer.parseInt(jt.getValueAt(Integer.parseInt(e.getActionCommand()), 0).toString()), orderID, jt.getValueAt(Integer.parseInt(e.getActionCommand()), 1).toString(), Integer.parseInt(jt.getValueAt(Integer.parseInt(e.getActionCommand()), 2).toString()), Integer.parseInt(jt.getValueAt(Integer.parseInt(e.getActionCommand()), 3).toString()), Integer.parseInt(jt.getValueAt(Integer.parseInt(e.getActionCommand()), 4).toString()));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    };
}
