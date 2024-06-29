package view;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;

import model.Sale;
import model.User;
import model.dao.ProductDAO;
import model.dao.SaleDAO;
import model.dao.UserDAO;

public class NewSaleFrame extends JFrame implements ActionListener {
    private JPanel bottomPanel, inputsPanel, tablesPanel, topPanel;
    private JLabel totalCostLabel, instructionLabel, productsTableLable, saleTableLable, inventoryLabel, invisibleLabel1, invisibleLabel2, invisibleLabel3, invisibleLabel4, searchLabel, sortLabel;
    private JTextField totalCostTextField, inventoryTextField, searchTextField;
    private JComboBox<String> sortComboBox;
    private JButton backButton, addItemButton, removeItemButton, saveSaleButton, resetButton;
    private Font mainFont = new Font("Calibri", Font.BOLD, 14), semiFont = new Font("Calibri", Font.BOLD, 20);
    private final Dimension labelDimension = new Dimension(65, 20), inputBoxDimension = new Dimension(180, 20),
            inputPanelDimension = new Dimension((int) (labelDimension.getWidth() + inputBoxDimension.getWidth()) + 20, 0),
            tableDimension = new Dimension(0, 250), buttonsDimension = new Dimension(100, 25), lineDimension = new Dimension(200, 25);
    private Color mainColor = Color.black, inputColor = Color.magenta, semiColor = Color.green;
    private DefaultTableModel saleTableModel, productsTableModel;
    private JTable saleTable, productsTable;
    private TableRowSorter<TableModel> tableRowSorter;
    private List<RowSorter.SortKey> sortKeys;
    private JScrollPane saleScrollPane, productsScrollPane;
    private Object[][] saleData;
    private Object[][] productsData;
    private final String[] productsTableColumns = new String[]{"Id", "Name", "Category", "Price"}, saleTableColumns = new String[]{"Id", "Name", "Category", "Price", "number"};
    private List<String> itemsList = new ArrayList<>();
    private int tableRowsNumber = 0;
    private final int tableColumnsNumber = 5;
    private int columnIndexToSort = 1;
    private Float totalCost = 0.0f;
    private Integer totalNumber = 0;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private LocalDateTime now;
    private final SaleDAO saleDAO;
    private final ProductDAO productDAO;
    private final UserDAO userDAO;
    private User currentlyLoggedUser;


    NewSaleFrame(String userName) throws SQLException, IOException {

        saleDAO = new SaleDAO();
        productDAO = new ProductDAO();
        userDAO = new UserDAO();

        currentlyLoggedUser = userDAO.readUser(userName);


        /****************************** Frame ******************************/

        this.setTitle("New Sale");
        this.setSize(1000, 720);
        this.setResizable(false);
        this.setIconImage(new ImageIcon("images\\icons\\new_sale_icon.png").getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        inputsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        inputsPanel.setPreferredSize(inputPanelDimension);
        inputsPanel.setBackground(mainColor);
        this.add(inputsPanel, BorderLayout.WEST);

        tablesPanel = new JPanel(new BorderLayout());
        tablesPanel.setBackground(mainColor);
        this.add(tablesPanel, BorderLayout.CENTER);

        /****************************** Frame ******************************/
        /****************************** Input ******************************/

        productsTableLable = new JLabel("Products table ---->");
        productsTableLable.setForeground(semiColor);
        productsTableLable.setFont(semiFont);
        productsTableLable.setPreferredSize(lineDimension);
        inputsPanel.add(productsTableLable);

        invisibleLabel1 = new JLabel();
        invisibleLabel1.setPreferredSize(lineDimension);
        inputsPanel.add(invisibleLabel1);

        totalCostLabel = new JLabel("Total cost");
        totalCostLabel.setPreferredSize(buttonsDimension);
        totalCostLabel.setFont(mainFont);
        totalCostLabel.setForeground(inputColor);
        inputsPanel.add(totalCostLabel);

        totalCostTextField = new JTextField(String.format("$ %.2f", totalCost));
        totalCostTextField.setPreferredSize(buttonsDimension);
        totalCostTextField.setForeground(inputColor);
        totalCostTextField.setBackground(mainColor);
        totalCostTextField.setEditable(false);
        totalCostTextField.setBorder(BorderFactory.createLineBorder(inputColor));
        inputsPanel.add(totalCostTextField);

        inventoryLabel = new JLabel("Inventory");
        inventoryLabel.setPreferredSize(buttonsDimension);
        inventoryLabel.setFont(mainFont);
        inventoryLabel.setForeground(inputColor);
        inputsPanel.add(inventoryLabel);

        inventoryTextField = new JTextField(String.format("$ %.2f", currentlyLoggedUser.getInventory()));
        inventoryTextField.setPreferredSize(buttonsDimension);
        inventoryTextField.setForeground(inputColor);
        inventoryTextField.setBackground(mainColor);
        inventoryTextField.setEditable(false);
        inventoryTextField.setBorder(BorderFactory.createLineBorder(inputColor));
        inputsPanel.add(inventoryTextField);

        /****************************** Input ******************************/
        /***************************** Buttons *****************************/

        instructionLabel = new JLabel("Select from tables to add or remove:");
        instructionLabel.setFont(new Font("Calibri", Font.BOLD, 12));
        instructionLabel.setPreferredSize(lineDimension);
        inputsPanel.add(instructionLabel);

        addItemButton = new JButton("Add item");
        setButtonDesign(addItemButton);
        inputsPanel.add(addItemButton);

        removeItemButton = new JButton("Remove Item");
        setButtonDesign(removeItemButton);
        inputsPanel.add(removeItemButton);

        /***************************** Buttons *****************************/
        /****************************** Input ******************************/
        invisibleLabel2 = new JLabel();
        invisibleLabel2.setPreferredSize(lineDimension);
        inputsPanel.add(invisibleLabel2);

        invisibleLabel3 = new JLabel();
        invisibleLabel3.setPreferredSize(lineDimension);
        inputsPanel.add(invisibleLabel3);

        saleTableLable = new JLabel("Shopping cart ---->");
        saleTableLable.setForeground(semiColor);
        saleTableLable.setPreferredSize(lineDimension);
        saleTableLable.setFont(semiFont);
        inputsPanel.add(saleTableLable);
        /****************************** Input ******************************/
        /****************************** Search Panel ***********************/
        topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 20));
        topPanel.setPreferredSize(new Dimension(0, 70));
        topPanel.setBackground(mainColor);

        searchLabel = new JLabel("Specify a word to match:");
        searchLabel.setPreferredSize(new Dimension(150, 25));
        searchLabel.setForeground(Color.WHITE);
        topPanel.add(searchLabel);

        searchTextField = new JTextField();
        searchTextField.setPreferredSize(lineDimension);
        searchTextField.setBackground(Color.lightGray);
        searchTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, inputColor));
        topPanel.add(searchTextField);

        searchTextField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = searchTextField.getText();

                if (text.trim().isEmpty()) {
                    tableRowSorter.setRowFilter(null);
                } else {
                    tableRowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = searchTextField.getText();

                if (text.trim().isEmpty()) {
                    tableRowSorter.setRowFilter(null);
                } else {
                    tableRowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

        });

        resetButton = new JButton("reset");
        setButtonDesign(resetButton);
        topPanel.add(resetButton);

        invisibleLabel4 = new JLabel();
        invisibleLabel4.setPreferredSize(buttonsDimension);
        topPanel.add(invisibleLabel4);

        sortLabel = new JLabel("Sort by:");
        sortLabel.setPreferredSize(new Dimension(60, 25));
        sortLabel.setForeground(Color.WHITE);
        topPanel.add(sortLabel);

        sortComboBox = new JComboBox<>(new String[]{"name", "price", "id"});
        sortComboBox.setPreferredSize(buttonsDimension);
        sortComboBox.addActionListener(this);
        topPanel.add(sortComboBox);


        this.add(topPanel, BorderLayout.NORTH);
        /****************************** Search Panel ***********************/
        /************************** Products Table **************************/

        productsData = productDAO.readProductsTableData();
        productsTableModel = new DefaultTableModel(productsData, productsTableColumns);

        productsTable = new JTable(productsTableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        productsTable.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent event) {
                productsTable.clearSelection();
            }
        });

        productsTable.setForeground(inputColor);
        productsTable.setBackground(mainColor);
        productsTable.setSelectionBackground(Color.GREEN);
        productsTable.setSelectionForeground(inputColor);

        tableRowSorter = new TableRowSorter<>(productsTable.getModel());
        productsTable.setRowSorter(tableRowSorter);
        sortKeys = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            tableRowSorter.setSortable(i, false);

        }


        productsScrollPane = new JScrollPane(productsTable);
        productsScrollPane.setPreferredSize(tableDimension);
        productsScrollPane.getViewport().setBackground(mainColor);
        productsScrollPane.setBorder(BorderFactory.createLineBorder(mainColor));



        tablesPanel.add(productsScrollPane, BorderLayout.NORTH);

        /************************** Products Table **************************/
        /**************************** Sale Table ****************************/

        saleTableModel = new DefaultTableModel(null, saleTableColumns);
        saleTable = new JTable(saleTableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        saleTable.setForeground(inputColor);
        saleTable.setBackground(mainColor);
        saleTable.setSelectionBackground(Color.GREEN);
        saleTable.setSelectionForeground(mainColor);


        saleTable.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent event) {
                saleTable.clearSelection();
            }
        });

        saleScrollPane = new JScrollPane(saleTable);
        saleScrollPane.setPreferredSize(tableDimension);
        saleScrollPane.getViewport().setBackground(mainColor);
        saleScrollPane.setBorder(BorderFactory.createLineBorder(mainColor));


        tablesPanel.add(saleScrollPane, BorderLayout.SOUTH);

        /**************************** Sale Table ****************************/
        /****************************** Frame ******************************/

        bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 30, 10));
        bottomPanel.setPreferredSize(new Dimension(0, 50));
        bottomPanel.setBackground(mainColor);
        this.add(bottomPanel, BorderLayout.SOUTH);

        backButton = new JButton("Back");
        setButtonDesign(backButton);
        bottomPanel.add(backButton);

        saveSaleButton = new JButton("Save sale");
        setButtonDesign(saveSaleButton);
        saveSaleButton.setForeground(Color.BLUE);
        saveSaleButton.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        bottomPanel.add(saveSaleButton, BorderLayout.EAST);

        this.setVisible(true);

        /****************************** Frame ******************************/
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

    private void updateTable() {
        int currentRowCount = saleTableModel.getRowCount();
        saleTableModel.setRowCount(0);
        saleTableModel.setRowCount(currentRowCount);
        saleTableModel.setDataVector(saleData, saleTableColumns);


    }

    private String[][] getSaleItems() {
        int aux = 0;

        String[][] items = new String[tableRowsNumber][tableColumnsNumber];
        for (int i = 0; i < tableRowsNumber; i++) {
            for (int j = 0; j < tableColumnsNumber; j++) {
                items[i][j] = itemsList.get(aux++);
            }
        }
        return items;
    }

    private void addItem() {
        if (productsTable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "You must pick a line from the products table!",
                    "Selection error", JOptionPane.WARNING_MESSAGE);
        } else {
            int temp = -1;
            for (int i = 0; i < tableRowsNumber; i++) {
                if (saleTable.getValueAt(i, 1).equals(productsTable.getValueAt(productsTable.getSelectedRow(), 1))) {
                    temp = i;
                    break;
                }
            }
            if (temp == -1) {

                tableRowsNumber++;

                totalCost += Float.parseFloat(((String) productsData[productsTable
                        .getSelectedRow()][3]).replaceAll(",", "."));
                totalCostTextField.setText(String.format("$ %.2f", totalCost));

                itemsList.add((String) productsData[productsTable.getSelectedRow()][0]);
                itemsList.add((String) productsData[productsTable.getSelectedRow()][1]);
                itemsList.add((String) productsData[productsTable.getSelectedRow()][2]);
                itemsList.add((String) productsData[productsTable.getSelectedRow()][3]);
                itemsList.add("1");

                saleData = getSaleItems();
                updateTable();
            } else {
                String lastColumnValue = (String) (saleTable.getValueAt(temp, 4));
                String newColumnValue = String.valueOf(Integer.parseInt(lastColumnValue) + 1);
                saleTable.setValueAt(newColumnValue, temp, 4);
                itemsList.set(5 * temp + 4, newColumnValue);

                totalCost += Float.parseFloat(((String) productsData[productsTable
                        .getSelectedRow()][3]).replaceAll(",", "."));
                totalCostTextField.setText(String.format("$ %.2f", totalCost));


            }
        }
    }

    private void removeItem() {
        int selectedRow = saleTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "You must pick a line from the shopping cart!!",
                    "Selection error", JOptionPane.WARNING_MESSAGE);
        } else {
            tableRowsNumber--;

            totalCost -= Float.parseFloat(((String) saleData[saleTable.getSelectedRow()][3]).replaceAll(",", "."))
                    * Float.parseFloat((String) saleData[saleTable.getSelectedRow()][4]);
            totalCostTextField.setText(String.format("$ %.2f", totalCost));

            for (int i = 0; i < 5; i++) itemsList.remove(selectedRow * 5);


            saleData = getSaleItems();
            updateTable();
        }
    }

    private void saveSale() throws SQLException {
        for (int i = 0; i < tableRowsNumber; i++) {
            Integer idValue = Integer.parseInt((String) (saleTable.getValueAt(i, 0)));
            Integer saleNumValue = Integer.parseInt((String) (saleTable.getValueAt(i, 4)));
            Integer productNumValue = Integer.parseInt(productDAO.readProductNum(idValue));
            productDAO.updateProductNum(String.valueOf(productNumValue - saleNumValue), idValue);
            totalNumber += saleNumValue;
        }
        Float mode = currentlyLoggedUser.getInventory() - totalCost;
        if (mode > 0) {
            currentlyLoggedUser.setInventory(mode);
            String[] options = {"Yes", "No"};
            int option = JOptionPane.showOptionDialog(null, "Are you sure you want to finalize your sale?", "Save",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
            if (option == 0) {

                now = LocalDateTime.now();
                Sale sale = new Sale(0,
                        totalCost,
                        currentlyLoggedUser.getUsername(),
                        dateTimeFormatter.format(now), totalNumber);
                try {
                    if (saleDAO.createSale(sale)) {
                        if (userDAO.updateUserInventory(currentlyLoggedUser)) {
                            JOptionPane.showMessageDialog(null, "This sale has been saved successfully!");
                            backToMenu();
                        }
                    }
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }


        } else {
            JOptionPane.showMessageDialog(null, "Your balance is not enough!",
                    "Selection error", JOptionPane.WARNING_MESSAGE);

        }


    }

    private void backToMenu() {
        try {
            saleDAO.close();
            productDAO.close();
            userDAO.close();
            new MenuFrame();
        } catch (SQLException | IOException exception) {
            exception.printStackTrace();
        }
        this.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource().equals(addItemButton)) {
            addItem();
        } else if (event.getSource().equals(removeItemButton)) {
            removeItem();
        } else if (event.getSource().equals(saveSaleButton)) {
            try {
                saveSale();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (event.getSource().equals(backButton)) {
            backToMenu();
        } else if (event.getSource().equals(sortComboBox)) {
            if (Objects.equals(sortComboBox.getSelectedItem(), "name")) {
                columnIndexToSort = 1;
                tableRowSorter.setComparator(columnIndexToSort, new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return o1.compareTo(o2);
                    }
                });
            } else if (Objects.equals(sortComboBox.getSelectedItem(), "price")) {
                columnIndexToSort = 3;
                tableRowSorter.setComparator(columnIndexToSort, new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        Float firstFloat = Float.parseFloat(o1.replaceAll(",", "."));
                        Float secondFloat = Float.parseFloat(o2.replaceAll(",", "."));
                        if (firstFloat.equals(secondFloat)) return 0;
                        else {
                            while (((int) (firstFloat - secondFloat)) == 0) {
                                firstFloat *= 10;
                                secondFloat *= 10;
                            }
                            return (int) (firstFloat - secondFloat);
                        }
                    }
                });
            } else if (Objects.equals(sortComboBox.getSelectedItem(), "id")) {
                columnIndexToSort = 0;
                tableRowSorter.setComparator(columnIndexToSort, new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return Integer.parseInt(o1) > Integer.parseInt(o2) ? 1 : 0;
                    }
                });
            }

            if (sortKeys.isEmpty()) sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));
            else sortKeys.set(0, new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));


            tableRowSorter.setSortKeys(sortKeys);
            tableRowSorter.sort();
        } else if (event.getSource().equals(resetButton)) {
            searchTextField.setText("");

        }
    }


}
