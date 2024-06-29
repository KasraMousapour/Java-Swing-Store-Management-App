package view;

import model.User;
import model.dao.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProfileFrame extends JFrame implements ActionListener, FocusListener {
    private JLabel userNameLabel, fullNameLabel, phoneNumberLabel, addressLabel, emailLabel, inventoryLabel, invisibleLabel1;
    private JTextField userNameTextField, fullNameTextField, phoneNumberTextField, addressTextField, emailTextField, inventoryTextField;
    private ArrayList<JTextField> textFields;
    private JButton saveButton, backButton, editButton, changePwButton;
    private JPanel topPanel, bottomPanel;
    private String defaultUserNameText, defaultFullNameText, defaultPhoneNumberText, defaultAddressText, defaultEmailText,defaultInventoryText;
    private ArrayList<String> defaultTexts;
    private Font semiFont = new Font("Calibri", Font.BOLD, 14), mainFont = new Font("Calibri", Font.BOLD, 20);
    private final Dimension labelDimension = new Dimension(150, 20), textFieldDimension = new Dimension(180, 20), buttonsDimension = new Dimension(100, 25);
    private Color mainColor = Color.black, inputColor = Color.magenta;
    private final UserDAO userDAO;
    private User currentlyLoggedUser;

    ProfileFrame(String userName) throws SQLException {
        userDAO = new UserDAO();
        currentlyLoggedUser = userDAO.readUser(userName);
        /****************************** Frame ******************************/
        this.setTitle("Profile");
        this.setSize(800, 460);
        this.setResizable(false);
        this.setIconImage(new ImageIcon("images\\icons\\user_icon.png").getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        this.setLayout(new BorderLayout());

        topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 50));
        topPanel.setPreferredSize(new Dimension(800,300));
        topPanel.setBackground(mainColor);
        this.add(topPanel, BorderLayout.NORTH);



        /****************************** Frame ******************************/
        /***************************** Labels ******************************/
        textFields = new ArrayList<>();
        defaultTexts = new ArrayList<>();

        userNameLabel = new JLabel("user name:");
        setLabelDesign(userNameLabel);
        topPanel.add(userNameLabel);

        userNameTextField = new JTextField();
        setTextFieldDesign(userNameTextField);
        defaultUserNameText = currentlyLoggedUser.getUsername();
        userNameTextField.setText(defaultUserNameText);
        defaultTexts.add(defaultUserNameText);
        textFields.add(userNameTextField);
        topPanel.add(userNameTextField);

        fullNameLabel = new JLabel("Full name:");
        setLabelDesign(fullNameLabel);
        topPanel.add(fullNameLabel);

        fullNameTextField = new JTextField();
        setTextFieldDesign(fullNameTextField);
        defaultFullNameText = currentlyLoggedUser.getFullName();
        fullNameTextField.setText(defaultFullNameText);
        defaultTexts.add(defaultFullNameText);
        textFields.add(fullNameTextField);
        topPanel.add(fullNameTextField);

        phoneNumberLabel = new JLabel("Phone number:");
        setLabelDesign(phoneNumberLabel);
        topPanel.add(phoneNumberLabel);

        phoneNumberTextField = new JTextField();
        setTextFieldDesign(phoneNumberTextField);
        defaultPhoneNumberText = (currentlyLoggedUser.getPhoneNumber() == null) ? "unknown" : currentlyLoggedUser.getPhoneNumber();
        phoneNumberTextField.setText(defaultPhoneNumberText);
        defaultTexts.add(defaultPhoneNumberText);
        textFields.add(phoneNumberTextField);
        topPanel.add(phoneNumberTextField);

        addressLabel = new JLabel("Address:");
        setLabelDesign(addressLabel);
        topPanel.add(addressLabel);

        addressTextField = new JTextField();
        setTextFieldDesign(addressTextField);
        defaultAddressText = (currentlyLoggedUser.getAddress() == null) ? "unknown" : currentlyLoggedUser.getAddress();
        addressTextField.setText(defaultAddressText);
        defaultTexts.add(defaultAddressText);
        textFields.add(addressTextField);
        topPanel.add(addressTextField);

        emailLabel = new JLabel("E-mail:");
        setLabelDesign(emailLabel);
        topPanel.add(emailLabel);

        emailTextField = new JTextField();
        setTextFieldDesign(emailTextField);
        defaultEmailText = currentlyLoggedUser.getEmail();
        emailTextField.setText(defaultEmailText);
        defaultTexts.add(defaultEmailText);
        textFields.add(emailTextField);
        topPanel.add(emailTextField);

        inventoryLabel = new JLabel("Inventory:");
        setLabelDesign(inventoryLabel);
        topPanel.add(inventoryLabel);

        inventoryTextField = new JTextField();
        setTextFieldDesign(inventoryTextField);
        defaultInventoryText = currentlyLoggedUser.getInventory() == null ? "0" : String.valueOf(currentlyLoggedUser.getInventory());
        inventoryTextField.setText(defaultInventoryText);
        defaultTexts.add(defaultInventoryText);
        textFields.add(inventoryTextField);
        topPanel.add(inventoryTextField);
        /***************************** Labels ******************************/
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 30, 10));
        bottomPanel.setPreferredSize(new Dimension(0,130));
        bottomPanel.setBackground(mainColor);
        this.add(bottomPanel, BorderLayout.AFTER_LAST_LINE);

        /***************************** Buttons *****************************/
        backButton = new JButton("back");
        setButtonDesign(backButton);
        bottomPanel.add(backButton);

        invisibleLabel1 = new JLabel();
        invisibleLabel1.setPreferredSize(new Dimension(175,25));
        bottomPanel.add(invisibleLabel1);

        changePwButton = new JButton("Change password");
        setButtonDesign(changePwButton);
        changePwButton.setPreferredSize(new Dimension(125,25));
        bottomPanel.add(changePwButton);

        editButton = new JButton("Edit");
        setButtonDesign(editButton);
        bottomPanel.add(editButton);

        saveButton = new JButton("Save");
        setButtonDesign(saveButton);
        bottomPanel.add(saveButton);
        /***************************** Buttons *****************************/
        this.setVisible(true);

    }

    public void setLabelDesign(JLabel label) {
        label.setForeground(Color.WHITE);
        label.setFont(mainFont);
        label.setPreferredSize(labelDimension);
    }

    public void setTextFieldDesign(JTextField textField) {
        textField.setBackground(mainColor);
        textField.setForeground(inputColor);
        textField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, inputColor));
        textField.setEditable(false);
        textField.setFont(semiFont);
        textField.setPreferredSize(textFieldDimension);

    }

    private void setButtonDesign(JButton button) {
        button.setPreferredSize(buttonsDimension);
        button.setFocusable(false);
        button.setBorder(BorderFactory.createLineBorder(inputColor));
        button.setBackground(mainColor);
        button.setForeground(inputColor);
        button.addActionListener(this);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent event) {
                button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent event) {
                button.setCursor(Cursor.getDefaultCursor());
            }
        });
    }

    public  void dbUpdateUser(User user){
        int choice = JOptionPane.showConfirmDialog(null, "Do you want to save changes?",
                "Confirmation", JOptionPane.YES_NO_OPTION);
        if (choice == 0){

            if (!phoneNumberTextField.getText().startsWith("09")) {
                JOptionPane.showMessageDialog(null, "The phone number must be start with '09' and 11 length",
                        "Input error", JOptionPane.WARNING_MESSAGE);

            } else if (!(phoneNumberTextField.getText().length() == 11)) {
                JOptionPane.showMessageDialog(null, "The phone number must be start with '09' and 11 length",
                        "Input error", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    if (userDAO.updateUser2(user)) {
                        JOptionPane.showMessageDialog(null, "This user has been updated successfully!",
                                "User updated", JOptionPane.INFORMATION_MESSAGE);
                        userDAO.close();
                        new MenuFrame();
                        this.dispose();

                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "This username has been used.Please try another username!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        User user = new User(
                userNameTextField.getText(),
                fullNameTextField.getText(),
                currentlyLoggedUser.getPassword(),
                emailTextField.getText(), "Attendant",
                addressTextField.getText(),
                Float.parseFloat(inventoryTextField.getText()),
                phoneNumberTextField.getText()
                );
        if (e.getSource().equals(backButton)) {
            try {
                userDAO.close();
                new MenuFrame();
            } catch (Exception ex) {
                ex.getMessage();
            }
            this.dispose();

        } else if (e.getSource().equals(editButton)) {
            JOptionPane.showMessageDialog(null,"click on every value to edit.");
            for (JTextField text:textFields){
                text.addFocusListener(this);
                text.setEditable(true);
            }

        } else if (e.getSource().equals(saveButton)) {
            dbUpdateUser(user);
        } else if (e.getSource().equals(changePwButton)) {
            try {
                new ChangePasswordFrame(currentlyLoggedUser.getUsername());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            this.dispose();
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        for (JTextField textField:textFields){
            if (e.getSource().equals(textField)){
                textField.setText(null);
            }
        }

    }

    @Override
    public void focusLost(FocusEvent e) {
        for (int i = 0; i < textFields.size(); i++) {
            if (e.getSource().equals(textFields.get(i))){
                if (textFields.get(i).getText().isBlank()){
                    textFields.get(i).setText(defaultTexts.get(i));
                }
            }
        }

    }
}
