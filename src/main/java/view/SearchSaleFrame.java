package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;

import model.dao.SaleDAO;

public class SearchSaleFrame extends JFrame implements ActionListener {
    private final JPanel bottomPanel, tablePanel;
    private final JButton backButton, generateCSVButton;
    private final Dimension tableDimension = new Dimension(800, 500), buttonsDimension = new Dimension(100, 25);
    private final Color mainColor = Color.BLACK, inputColor = Color.magenta;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final JScrollPane scrollPane;
    private Object[][] tableData;
    private final String[] tableColumns = {"ID", "Total cost", "Attendant", "Date", "Number"};
    private final SaleDAO saleDAO;

    SearchSaleFrame() throws SQLException {
        saleDAO = new SaleDAO();

        /****************************** Frame ******************************/

        this.setTitle("Search Sale");
        this.setSize(1000, 720);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(new ImageIcon("images\\icons\\search_icon.png").getImage());
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        tablePanel = new JPanel(new GridBagLayout());
        tablePanel.setBackground(mainColor);
        this.add(tablePanel, BorderLayout.CENTER);


        /****************************** Frame ******************************/
        /**************************** Sale Table ****************************/

        tableModel = new DefaultTableModel(null, tableColumns);
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableData = saleDAO.readSalesTableData();
        int currentRowCount = tableModel.getRowCount();
        tableModel.setRowCount(0);
        tableModel.setRowCount(currentRowCount);
        tableModel.setDataVector(tableData, tableColumns);

        table.setForeground(inputColor);
        table.setBackground(mainColor);
        table.setSelectionBackground(Color.GREEN);
        table.setSelectionForeground(mainColor);


        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(tableDimension);
        tablePanel.add(scrollPane, new GridBagConstraints());

        /**************************** Sale Table ****************************/
        /****************************** Frame ******************************/

        bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 15));
        bottomPanel.setPreferredSize(new Dimension(0, 50));
        bottomPanel.setBackground(mainColor);
        this.add(bottomPanel, BorderLayout.SOUTH);

        backButton = new JButton("Back");
        setButtonDesign(backButton);
        bottomPanel.add(backButton);

        generateCSVButton = new JButton("Convert to CSV");
        setButtonDesign(generateCSVButton);
        generateCSVButton.setPreferredSize(new Dimension(200,25));
        bottomPanel.add(generateCSVButton);


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

    private void generateCSVFromTable() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileFilter() {
            public String getDescription() {
                return "CSV Files";
            }

            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return true;
                } else {
                    String filename = file.getName().toLowerCase();
                    return filename.endsWith(".csv");
                }
            }
        });
        int fileChooserResponse = fileChooser.showSaveDialog(null);

        if (fileChooserResponse == JFileChooser.APPROVE_OPTION) {
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            try {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write("ID,Total cost,Attendant,Date,Number");
                for (int i = 0; i < table.getRowCount(); i++) {
                    fileWriter.append("\n");
                    for (int j = 0; j < 4; j++) {
                        if (j > 0) fileWriter.append(",");
                        fileWriter.append((String) tableData[i][j]);
                    }
                }
                fileWriter.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
         if (event.getSource().equals(generateCSVButton)) {
            generateCSVFromTable();
        } else if (event.getSource().equals(backButton)) {
            try {
                new MenuFrame();
            } catch (IOException | SQLException exception) {
                exception.printStackTrace();
            }
            this.dispose();
        }
    }
}
