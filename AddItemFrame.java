package Frames;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import Entities.*;

public class AddItemFrame extends JFrame implements ActionListener 
{
    JLabel titleLbl, idLbl, nameLbl, quantityLbl;
    JTextField idFld, nameFld, quantityFld;
    JButton addBtn, backBtn;
    JPanel panel;
    Font font;
    Color bgColor;
    String username;

    public AddItemFrame(String username) 
	{
        super("Add Inventory Item");
        this.username = username;

        this.setSize(500, 400);
        this.setLocationRelativeTo(null);

        bgColor = new Color(176, 219, 156);
        font = new Font("Cambria", Font.PLAIN, 18);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(bgColor);

        titleLbl = new JLabel("Add New Item");
        titleLbl.setFont(new Font("Cambria", Font.BOLD, 24));
        titleLbl.setBounds(150, 20, 200, 40);
        panel.add(titleLbl);

        idLbl = new JLabel("Item ID:");
        idLbl.setFont(font);
        idLbl.setBounds(50, 90, 100, 30);
        panel.add(idLbl);

        idFld = new JTextField();
        idFld.setBounds(180, 90, 250, 30);
        panel.add(idFld);

        nameLbl = new JLabel("Item Name:");
        nameLbl.setFont(font);
        nameLbl.setBounds(50, 140, 100, 30);
        panel.add(nameLbl);

        nameFld = new JTextField();
        nameFld.setBounds(180, 140, 250, 30);
        panel.add(nameFld);

        quantityLbl = new JLabel("Quantity:");
        quantityLbl.setFont(font);
        quantityLbl.setBounds(50, 190, 100, 30);
        panel.add(quantityLbl);

        quantityFld = new JTextField();
        quantityFld.setBounds(180, 190, 250, 30);
        panel.add(quantityFld);

        addBtn = new JButton("Add Item");
        addBtn.setFont(font);
        addBtn.setBackground(Color.LIGHT_GRAY);
        addBtn.setBounds(100, 260, 120, 40);
        addBtn.addActionListener(this);
        panel.add(addBtn);

        backBtn = new JButton("Back");
        backBtn.setFont(font);
        backBtn.setBackground(Color.LIGHT_GRAY);
        backBtn.setBounds(260, 260, 120, 40);
        backBtn.addActionListener(this);
        panel.add(backBtn);

        this.add(panel);
    }
    private String checkDuplicate(String id, String name) throws IOException 
	{
        File file = new File("inventory.txt");

        boolean idExists = false;
        boolean nameExists = false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) 
		{
            String line;
            while ((line = br.readLine()) != null) 
			{
                String[] parts = line.split(",");
                if (parts.length == 3) 
				{
                    if (parts[0].equals(id)) idExists = true;
                    if (parts[1].equalsIgnoreCase(name)) nameExists = true;
                }
            }
        }

        if (idExists && nameExists) 
		{
			return "Item ID and Name already exist!";
		}
		if (idExists) 
		{
			return "Item ID already exists!";
		}
		if (nameExists) 
		{
			return "Item Name already exists!";
		}
        return null;
    }

    public void actionPerformed(ActionEvent ae) 
	{
        if (ae.getSource() == addBtn) 
		{
            String id = idFld.getText();
            String name = nameFld.getText();
            String quantityStr = quantityFld.getText();

            if (id.isEmpty() || name.isEmpty() || quantityStr.isEmpty()) 
			{
                JOptionPane.showMessageDialog(this, "Please Fill All Fields.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int quantity;
            try
			{
                quantity = Integer.parseInt(quantityStr);
                if (quantity < 0) throw new NumberFormatException();
            } 
			catch (NumberFormatException noe) 
			{
                JOptionPane.showMessageDialog(this, "Quantity must be a positive integer.", "Error", JOptionPane.ERROR_MESSAGE);
                noe.printStackTrace();
				return;
            }

            try 
			{
                String duplicateValue = checkDuplicate(id, name);
                if (duplicateValue != null) 
				{
                    JOptionPane.showMessageDialog(this, duplicateValue, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                BufferedWriter bw = new BufferedWriter(new FileWriter("inventory.txt", true));
                bw.write(id + "," + name + "," + quantity);
                bw.newLine();
                bw.close();

                JOptionPane.showMessageDialog(this, "Item Added Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                idFld.setText("");
                nameFld.setText("");
                quantityFld.setText("");
            } 
			catch (IOException ioe) 
			{
                JOptionPane.showMessageDialog(this, "Error Saving Item: " + ioe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				ioe.printStackTrace();
			}
        } 
		else if (ae.getSource() == backBtn) 
		{
            this.setVisible(false);
            new HomePage(username).setVisible(true);
        }
    }
}
