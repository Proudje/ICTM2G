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
        setVisible(true);

//        String test_passwd = "abcdefghijklmnopqrstuvwxyz";
//        String test_hash = "$2a$06$.rCVZVOThsIa97pEDOxvGuRRgzG64bvtJ0938xuqzv18d3ZpQhstC";
//
//        System.out.println("Testing BCrypt Password hashing and verification");
//        System.out.println("Test password: " + test_passwd);
//        System.out.println("Test stored hash: " + test_hash);
//        System.out.println("Hashing test password...");
//        System.out.println();
//
//        String computed_hash = hashPassword(test_passwd);
//        System.out.println("Test computed hash: " + computed_hash);
//        System.out.println();
//        System.out.println("Verifying that hash and stored hash both match for the test password...");
//        System.out.println();
//
//        String compare_test = checkPassword(test_passwd, test_hash)
//                ? "Passwords Match" : "Passwords do not match";
//        String compare_computed = checkPassword(test_passwd, computed_hash)
//                ? "Passwords Match" : "Passwords do not match";
//
//        System.out.println("Verify against stored hash:   " + compare_test);
//        System.out.println("Verify against computed hash: " + compare_computed);


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Database data = new Database();

        boolean result;
//        String computed_hash = Password.hashPassword(String.valueOf(password.getPassword()));

        try {
            result = data.getLogin(username.getText(), String.valueOf(password.getPassword()));
            if (result) {
                MainFrame m = new MainFrame();
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Login Failed.");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
}
