import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginFrame extends JFrame implements ActionListener {

    private static JLabel passwordLabel, userLabel;
    private static JTextField username;
    private static JButton button;
    private static JPasswordField password;

    public LoginFrame() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        setVisible(true);
        setTitle("Login Page");
        setLocation(new Point(500, 300));
        add(panel);
        setSize(new Dimension(400, 200));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Username label constructor
        userLabel = new JLabel("Username");
        userLabel.setBounds(100, 8, 70, 20);
        panel.add(userLabel);
        // Username Text field
        username = new JTextField();
        username.setBounds(100, 27, 193, 28);
        panel.add(username);
        // Password Label constructor
        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(100, 55, 70, 20);
        panel.add(passwordLabel);
        // Password TextField
        password = new JPasswordField();
        password.setBounds(100, 75, 193, 28);
        panel.add(password);
        // Button constructor
        button = new JButton("Login");
        button.setBounds(100, 110, 90, 25);
        button.addActionListener(this);
        panel.add(button);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Connection connection;
        PreparedStatement ps;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");
            ps = connection.prepareStatement("SELECT `username`, `password` FROM `user` WHERE `username` = ? AND `password` = ?");
            ps.setString(1, username.getText());
            ps.setString(2, String.valueOf(password.getPassword()));
            ResultSet result = ps.executeQuery();
            if(result.next()){
                MainFrame m = new MainFrame();
                dispose();
            }
            else{
                JOptionPane.showMessageDialog(null, "Login Failed.");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
