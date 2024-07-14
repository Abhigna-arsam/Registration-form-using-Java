import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;



class MyFrame extends JFrame implements ActionListener {
    private Container c;
    private JLabel title;
    private JLabel id;
    private JTextField tid;
    private JLabel name;
    private JTextField tname;
    private JLabel email;
    private JTextField emailField;
    private JLabel password;
    private JPasswordField passwordField;
    private JLabel confirm;
    private JPasswordField confirmPasswordField;
    private JLabel gender;
    private JRadioButton male;
    private JRadioButton female;
    private ButtonGroup gengp;
    private JLabel dob;
    private JComboBox<String> date;
    private JComboBox<String> month;
    private JComboBox<Integer> year;
    private JButton submit;
    private JButton clear;

    public MyFrame() {
        setTitle("Registration Form");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        c = getContentPane();
        c.setLayout(new BorderLayout());

        // Top panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        title = new JLabel("Registration Form");
        topPanel.add(title);
        c.add(topPanel, BorderLayout.NORTH);

        // Center panel with GridBagLayout for better alignment
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        id = new JLabel("ID:");
        centerPanel.add(id, gbc);

        gbc.gridx = 1;
        tid = new JTextField(20);
        centerPanel.add(tid, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        name = new JLabel("Name:");
        centerPanel.add(name, gbc);

        gbc.gridx = 1;
        tname = new JTextField(20);
        centerPanel.add(tname, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        email = new JLabel("Email:");
        centerPanel.add(email, gbc);

        gbc.gridx = 1;
        emailField = new JTextField(20);
        centerPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        password = new JLabel("Password:");
        centerPanel.add(password, gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        centerPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        confirm = new JLabel("Confirm Password:");
        centerPanel.add(confirm, gbc);

        gbc.gridx = 1;
        confirmPasswordField = new JPasswordField(20);
        centerPanel.add(confirmPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gender = new JLabel("Gender:");
        centerPanel.add(gender, gbc);

        gbc.gridx = 1;
        male = new JRadioButton("Male");
        female = new JRadioButton("Female");
        gengp = new ButtonGroup();
        gengp.add(male);
        gengp.add(female);
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.add(male);
        genderPanel.add(female);
        centerPanel.add(genderPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        dob = new JLabel("Date of Birth:");
        centerPanel.add(dob, gbc);

        gbc.gridx = 1;
        date = new JComboBox<>(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"});
        month = new JComboBox<>(new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"});
        year = new JComboBox<>(getYearsArray(1990, 2010));
        JPanel dobPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dobPanel.add(date);
        dobPanel.add(month);
        dobPanel.add(year);
        centerPanel.add(dobPanel, gbc);

        c.add(centerPanel, BorderLayout.CENTER);

        // Bottom panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        submit = new JButton("Submit");
        bottomPanel.add(submit);
        clear = new JButton("Clear");
        bottomPanel.add(clear);
        c.add(bottomPanel, BorderLayout.SOUTH);

        // Add event handling
        submit.addActionListener(this);
        clear.addActionListener(this);

        setVisible(true);
    }

    private Integer[] getYearsArray(int startYear, int endYear) {
        Integer[] years = new Integer[endYear - startYear + 1];
        for (int i = startYear; i <= endYear; i++) {
            years[i - startYear] = i;
        }
        return years;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            // Get values from fields
            String id = tid.getText();
            String name = tname.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            String gender = male.isSelected() ? "Male" : "Female";
            String dob = date.getSelectedItem() + "-" + month.getSelectedItem() + "-" + year.getSelectedItem();

            // Perform database operations using JDBC
            try (Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "admin");
                 Statement stmt = conn.createStatement()) {
                // Insert data into the database
                stmt.executeUpdate("INSERT INTO users (id, name, email, password, gender, dob) VALUES ('" + id + "', '" + name + "', '" + email + "', '" + password + "', '" + gender + "', '" + dob + "')");
                JOptionPane.showMessageDialog(this, "Registration Successful!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        } else if (e.getSource() == clear) {
            // Clear the form
            tid.setText("");
            tname.setText("");
            emailField.setText("");
            passwordField.setText("");
            confirmPasswordField.setText("");
            gengp.clearSelection();
            date.setSelectedIndex(0);
            month.setSelectedIndex(0);
            year.setSelectedIndex(0);
        }

    }
	public static void main(String[] args) {
        	new MyFrame();
    }
}
