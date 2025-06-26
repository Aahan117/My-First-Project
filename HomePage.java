package Frames;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import Entities.*;

public class HomePage extends JFrame implements ActionListener, InventoryActions 
{
    JLabel titleLbl, userLbl;
    JTable table;
    DefaultTableModel tableModel;
    JScrollPane scrollPane;
    JButton addBtn, updateBtn, deleteBtn, logoutBtn;
    JPanel panel;
    Font font;
    Color bgColor;
    String username;

    public HomePage(String username) 
	{
        super("Inventory Home Page");
        this.username = username;

        this.setSize(850, 500);
        this.setLocationRelativeTo(null);

        bgColor = new Color(176, 219, 156);
        font = new Font("Cambria", Font.PLAIN, 20);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(bgColor);

        titleLbl = new JLabel("Inventory Items");
        titleLbl.setBounds(320, 10, 250, 40);
        titleLbl.setFont(new Font("Cambria", Font.BOLD, 26));
        titleLbl.setForeground(Color.BLACK);
        panel.add(titleLbl);

        userLbl = new JLabel("Logged in as: " + username);
        userLbl.setBounds(620, 15, 200, 30);
        userLbl.setFont(new Font("Cambria", Font.PLAIN, 14));
        userLbl.setForeground(Color.BLACK);
        panel.add(userLbl);

        String[] columns = {"Item ID", "Item Name", "Quantity"};
        tableModel = new DefaultTableModel(columns, 0) 
		{
            public boolean isCellEditable(int row, int column) 
			{
                return false;
            }
        };
        
		table = new JTable(tableModel);
        table.setFont(font);
        table.setRowHeight(30);

        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 60, 750, 280);
        panel.add(scrollPane);

        addBtn = new JButton("Add");
        addBtn.setBounds(50, 360, 150, 50);
        addBtn.setFont(font);
        addBtn.setBackground(Color.LIGHT_GRAY);
        addBtn.addActionListener(this);
        panel.add(addBtn);

        updateBtn = new JButton("Update");
        updateBtn.setBounds(250, 360, 150, 50);
        updateBtn.setFont(font);
        updateBtn.setBackground(Color.LIGHT_GRAY);
        updateBtn.addActionListener(this);
        panel.add(updateBtn);

        deleteBtn = new JButton("Delete");
        deleteBtn.setBounds(450, 360, 150, 50);
        deleteBtn.setFont(font);
        deleteBtn.setBackground(Color.LIGHT_GRAY);
        deleteBtn.addActionListener(this);
        panel.add(deleteBtn);

        logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(650, 360, 150, 50);
        logoutBtn.setFont(font);
        logoutBtn.setBackground(Color.LIGHT_GRAY);
        logoutBtn.addActionListener(this);
        panel.add(logoutBtn);

        this.add(panel);
        loadInventory();
    }

    public void loadInventory() 
	{
        tableModel.setRowCount(0);
        File file = new File("inventory.txt");

        try (Scanner scanner = new Scanner(file)) 
		{
            while (scanner.hasNextLine()) 
			{
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3) 
				{
                    String id = parts[0];
                    String name = parts[1];
                    String quantity = parts[2];
                    tableModel.addRow(new Object[]{id, name, quantity});
                }
            }
        } 
		catch (IOException ioe) 
		{
            JOptionPane.showMessageDialog(this, "Error Loading Inventory: " + ioe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			ioe.printStackTrace();
		}
    }

    public void actionPerformed(ActionEvent ae) 
	{
		if (ae.getSource() == addBtn) 
		{
			this.setVisible(false);
			new AddItemFrame(username).setVisible(true);
		} 
		else if (ae.getSource() == updateBtn) 
		{
			this.setVisible(false);
			new UpdateItemFrame(username).setVisible(true);
		} 
		else if (ae.getSource() == deleteBtn) 
		{
			this.setVisible(false);
			new DeleteItemFrame(username).setVisible(true);
		} 
		else if (ae.getSource() == logoutBtn) 
		{
			this.setVisible(false);
			new LoginFrame().setVisible(true);
		} 
		else 
		{
			loadInventory();
		}
	}
}
