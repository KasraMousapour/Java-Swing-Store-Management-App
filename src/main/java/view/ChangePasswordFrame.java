package view;

import model.User;
import model.dao.UserDAO;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class ChangePasswordFrame extends JFrame implements ActionListener {
    private JLabel currentPwLabel, newPwLabel, confirmPwLabel;
    private JPasswordField currentPasswordField, newPasswordField, confirmPasswordField;
    private JButton okButton, cancelButton;
    private Color mainColor = Color.BLACK, textColor = Color.magenta;
    private Font mainFont = new Font("Calibri", Font.BOLD, 14);
    private User currentlyLoggedUser;
    private final UserDAO userDAO;

    private Dimension labelDimension = new Dimension(150, 25), inputDimension = new Dimension(150, 25), buttonDimension = new Dimension(80, 30);

    ChangePasswordFrame(String userName) throws SQLException {
        userDAO = new UserDAO();
        currentlyLoggedUser = userDAO.readUser(userName);
        /********************* Frame *********************/
        this.setTitle("Change Password");
        this.setSize(400, 300);
        this.setResizable(false);
        this.setIconImage(new ImageIcon("images\\icons\\change_password_icon.png").getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.BLACK);
        /********************* Frame *********************/
        /********************* inputs ********************/

        currentPwLabel = new JLabel("Current Password: ");
        setLabelDesign(currentPwLabel);
        newPwLabel = new JLabel("New Password: ");
        setLabelDesign(newPwLabel);
        confirmPwLabel = new JLabel("Confirm Password: ");
        setLabelDesign(confirmPwLabel);

        currentPasswordField = new JPasswordField("");
        setTextFieldDesign(currentPasswordField);
        newPasswordField = new JPasswordField("");
        setTextFieldDesign(newPasswordField);
        confirmPasswordField = new JPasswordField("");
        setTextFieldDesign(confirmPasswordField);

        this.add(currentPwLabel);
        this.add(currentPasswordField);
        this.add(newPwLabel);
        this.add(newPasswordField);
        this.add(confirmPwLabel);
        this.add(confirmPasswordField);
        /********************* inputs ********************/
        /********************** Buttons ******************/

        okButton = new JButton("OK");
        okButton.setPreferredSize(buttonDimension);
        setButtonDesign(okButton);
        cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(buttonDimension);
        setButtonDesign(cancelButton);

        this.add(okButton);
        this.add(cancelButton);
        /********************** Buttons ******************/
        this.setVisible(true);
    }

    private void setButtonDesign(JButton button) {
        button.setPreferredSize(buttonDimension);
        button.setFocusable(false);
        button.setBorder(BorderFactory.createLineBorder(textColor));
        button.setBackground(mainColor);
        button.setForeground(textColor);
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

    public void setTextFieldDesign(JTextField textField) {
        textField.setBackground(mainColor);
        textField.setForeground(textColor);
        textField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, textColor));
        textField.setFont(mainFont);
        textField.setPreferredSize(inputDimension);

    }

    public void setLabelDesign(JLabel label) {
        label.setForeground(Color.WHITE);
        label.setFont(mainFont);
        label.setPreferredSize(labelDimension);
    }


    public void actionPerformed(ActionEvent event) {
        try {
            if (event.getSource().equals(cancelButton)) {
                new ProfileFrame(currentlyLoggedUser.getUsername());
                this.dispose();

            } else if (event.getSource().equals(okButton)) {
                if (!(String.valueOf(currentPasswordField.getPassword()).equals(currentlyLoggedUser.getPassword()))) {
                    JOptionPane.showMessageDialog(null, "The current password is wrong!!",
                            "Input error", JOptionPane.WARNING_MESSAGE);
                } else if (!(String.valueOf(newPasswordField.getPassword()).equals(String.valueOf(confirmPasswordField.getPassword())))) {
                    JOptionPane.showMessageDialog(null, "The new password and confirm password doesn't match!!",
                            "Input error", JOptionPane.WARNING_MESSAGE);
                } else if (String.valueOf(newPasswordField.getPassword()).length() < 6) {
                    JOptionPane.showMessageDialog(null, "The password must be at least 6 character!",
                            "Input error", JOptionPane.WARNING_MESSAGE);
                } else {
                    currentlyLoggedUser.setPassword(String.valueOf(newPasswordField.getPassword()));
                    if (userDAO.updateUserPassword(currentlyLoggedUser)) {
                        JOptionPane.showMessageDialog(null, "The password has been changed successfully!",
                                "Password changed", JOptionPane.INFORMATION_MESSAGE);
                        userDAO.close();
                        this.dispose();
                        new ProfileFrame(currentlyLoggedUser.getUsername());
                    }
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }


    }
}

