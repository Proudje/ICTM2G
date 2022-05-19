import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ViewOrderFrame extends JFrame implements ActionListener {
    private static JLabel userLabel, addressLabel, postalcodeLabel, phonenumberLabel, countryLabel, country, stateProvinceLabel, stateProvice, cityNameLabel, cityName;
    private static JTextField username, address, postalcode, phonenumber;
    private static JButton save;



    public ViewOrderFrame(int customerID) throws SQLException {
        System.out.println(customerID);
        Database data = new Database();
        Customer customer = data.getCustomer(customerID);

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

        String column[] = {"OrderID", "Customer name", "Order date", "Deliverd", "Retour", "Edit"};

        username.setText(customer.getName());
        address.setText(customer.getAddress());
        postalcode.setText(customer.getPostalcode());
        phonenumber.setText(customer.getPhonenumber());

        JTable jt = new JTable(data.getOders(), column);

        jt.setBounds(100, 220, 400, 100);
        jt.setAutoCreateRowSorter(true);
        JScrollPane sp = new JScrollPane(jt);
        tabel.add(sp);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Database data = new Database();
        try {
            boolean test = data.updateCustomer(2, username.getText(), phonenumber.getText(), address.getText(), postalcode.getText());
            System.out.println(test);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
