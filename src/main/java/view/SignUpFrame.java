package view;

import model.User;
import model.dao.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

public class SignUpFrame extends JFrame implements ActionListener{
    private final JPanel logoPanel, signupPanel, titleLabelPanel, inputsPanel, invisiblePanel;
    private final JLabel logoLabel, titleLabel, usernameLabel, passwordLabel, invisibleLabel1, emailLabel, fullNameLabel;
    private final JTextField usernameTextField, fullNameTextField, emailTextField;
    private final JPasswordField passwordTextField;
    private final JButton signUpButton, backButton;
    private final Dimension inputBoxDimension = new Dimension(180, 20), signupBoxesDimension = new Dimension(350, 25);
    private final Color logoPanelColor = Color.magenta, signupPanelColor = Color.BLACK, presetTextFieldColor = Color.gray, inputBorderColor = Color.MAGENTA;
    private final ImageIcon logoIcon = new ImageIcon("images\\icons\\member_signup_icon.png");
    private final UserDAO userDAO;


    SignUpFrame() throws SQLException {
        userDAO = new UserDAO();
        this.setTitle("Sign up");
        this.setSize(800, 460);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(new ImageIcon("images\\icons\\user_icon.png").getImage());

        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        /**************************************** Frame ****************************************/

        logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 100));
        logoPanel.setPreferredSize(new Dimension(355, 0));
        logoPanel.setBackground(logoPanelColor);
        this.add(logoPanel, BorderLayout.EAST);

        signupPanel = new JPanel(new BorderLayout());
        signupPanel.setPreferredSize(new Dimension(395, 0));
        signupPanel.setBackground(signupPanelColor);
        this.add(signupPanel, BorderLayout.WEST);

        titleLabelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5));
        titleLabelPanel.setPreferredSize(new Dimension(0, 100));
        titleLabelPanel.setBackground(signupPanelColor);
        signupPanel.add(titleLabelPanel, BorderLayout.NORTH);

        inputsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 25));
        inputsPanel.setPreferredSize(new Dimension(0, 350));
        inputsPanel.setBackground(signupPanelColor);
        signupPanel.add(inputsPanel, BorderLayout.CENTER);

        invisiblePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        invisiblePanel.setBackground(signupPanelColor);
        this.add(invisiblePanel, BorderLayout.CENTER);
        /**************************************** Frame ****************************************/
        /*************************************** Extras ***************************************/
        titleLabel = new JLabel("SIGN UP");
        titleLabel.setForeground(logoPanelColor);
        titleLabel.setFont(new Font("Calibri", Font.BOLD, 60));
        titleLabelPanel.add(titleLabel);

        logoLabel = new JLabel();
        logoLabel.setIcon(new ImageIcon(logoIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
        logoPanel.add(logoLabel);
        /*************************************** Extras ***************************************/
        /****************************** Input ******************************/

        usernameLabel = new JLabel("Username");
        usernameTextField = new JTextField();
        setTextFieldDesign(usernameLabel, usernameTextField);

        fullNameLabel = new JLabel("Full name");
        fullNameTextField = new JTextField();
        setTextFieldDesign(fullNameLabel, fullNameTextField);

        emailLabel = new JLabel("E-mail");
        emailTextField = new JTextField();
        setTextFieldDesign(emailLabel, emailTextField);

        passwordLabel = new JLabel("Password(at least 6 character)");
        passwordTextField = new JPasswordField();

        passwordLabel.setPreferredSize(new Dimension(150, 20));
        passwordLabel.setForeground(inputBorderColor);
        passwordLabel.setFont(new Font("Calibri", Font.BOLD, 14));
        inputsPanel.add(passwordLabel);

        passwordTextField.setPreferredSize(inputBoxDimension);
        passwordTextField.setForeground(presetTextFieldColor);
        passwordTextField.setBackground(signupPanelColor);
        passwordTextField.setCaretColor(inputBorderColor);
        passwordTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, inputBorderColor));
        inputsPanel.add(passwordTextField);

        /****************************** Input ******************************/
        /****************************** sign up Button *******************************/
        invisibleLabel1 = new JLabel();
        invisibleLabel1.setPreferredSize(new Dimension(10, 10));
        inputsPanel.add(invisibleLabel1);

        signUpButton = new JButton("Sign up");
        signUpButton.setPreferredSize(signupBoxesDimension);
        signUpButton.setFocusable(false);
        signUpButton.setBorder(BorderFactory.createLineBorder(inputBorderColor));
        signUpButton.setBackground(signupPanelColor);
        signUpButton.setForeground(inputBorderColor);
        signUpButton.addMouseListener(mouseAdapter);
        signUpButton.addActionListener(this);
        inputsPanel.add(signUpButton);
        /****************************** sign up Button *******************************/
        /******************************* back Button *******************************+*/

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(50, 20));
        backButton.setFocusable(false);
        backButton.setBorder(BorderFactory.createLineBorder(Color.RED));
        backButton.setBackground(signupPanelColor);
        backButton.setForeground(Color.RED);
        backButton.addMouseListener(mouseAdapter);
        backButton.addActionListener(this);
        inputsPanel.add(backButton);

        this.setVisible(true);

    }

    private void setTextFieldDesign(JLabel label, JTextField textField) {
        label.setPreferredSize(new Dimension(150, 20));
        label.setForeground(inputBorderColor);
        label.setFont(new Font("Calibri", Font.BOLD, 14));
        inputsPanel.add(label);

        textField.setPreferredSize(inputBoxDimension);
        textField.setForeground(presetTextFieldColor);
        textField.setBackground(signupPanelColor);
        textField.setCaretColor(inputBorderColor);
        textField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, inputBorderColor));
        inputsPanel.add(textField);
    }




    MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent event) {
            if (event.getSource().equals(signUpButton)) {
                signUpButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            } else if (event.getSource().equals(backButton)) {
                backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        }

        @Override
        public void mouseExited(MouseEvent event) {
            if (event.getSource().equals(signUpButton)) {
                signUpButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            } else if (event.getSource().equals(backButton)) {
                backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        }
    };

    private boolean isBoxesEmpty() {
        return (fullNameTextField.getText().isBlank() || usernameTextField.getText().isBlank()
                || String.valueOf(passwordTextField.getPassword()).isBlank() || emailTextField.getText().isBlank());
    }

    private void dbCreateUser(User user) {
        if (isBoxesEmpty()) {
            JOptionPane.showMessageDialog(null, "You must fill all text fields!",
                    "Input error", JOptionPane.WARNING_MESSAGE);
        } else if (String.valueOf(passwordTextField.getPassword()).length() < 6) {
            JOptionPane.showMessageDialog(null, "The password must be at least 6 character!",
                    "Input error", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                if (userDAO.createUser(user)) {
                    JOptionPane.showMessageDialog(null, "This user has been created successfully!",
                            "User created", JOptionPane.INFORMATION_MESSAGE);
                    FileWriter fileWriter = new FileWriter("app_local_settings.txt");
                    fileWriter.write("currentlyLoggedUser:" + user.getUsername());
                    fileWriter.close();
                    userDAO.close();
                    new MenuFrame();
                    this.dispose();
                }
            } catch (SQLException sqlException) {
                JOptionPane.showMessageDialog(null, "This username has been created.Please try another username!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        User user = new User(
                usernameTextField.getText(),
                fullNameTextField.getText(),
                String.valueOf(passwordTextField.getPassword()),
                emailTextField.getText(), "Attendant");
        if (e.getSource().equals(signUpButton)) {
            dbCreateUser(user);
        } else if (e.getSource().equals(backButton)) {
            try {
                userDAO.close();
                new LoginFrame();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            this.dispose();
        }

    }
}
