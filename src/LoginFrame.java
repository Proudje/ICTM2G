import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Timer;

public class LoginFrame extends JFrame implements ActionListener {

    private static JLabel passwordLabel, userLabel;
    private static JTextField username;
    private static JButton button;
    private static JPasswordField password;


    public LoginFrame() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        setTitle("Login pagina");
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

        java.util.Timer time = new Timer(); // Instantiate Timer Object
        ScheduledTask st = new ScheduledTask(); // Instantiate SheduledTask class
        time.schedule(st, 0, 1000); // elke seconde

        setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Database data = new Database();


        boolean result;

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
