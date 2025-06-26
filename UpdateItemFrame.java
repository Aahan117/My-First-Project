package Frames;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import Entities.*;

public class UpdateItemFrame extends JFrame implements ActionListener 
{
    JLabel titleLabel, idLabel, nameLabel, quantityLabel;
    JTextField idField, nameField, quantityField;
    JButton updateButton, backButton;
    JPanel panel;
    Font font;
    Color bgColor;
    String username;

    public UpdateItemFrame(String username) 
	{
        super("Update Inventory Item");
        this.username = username;

        this.setSize(500, 400);
        this.setLocationRelativeTo(null);

        bgColor = new Color(176, 219, 156);
        font = new Font("Cambria", Font.PLAIN, 18);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(bgColor);

        titleLabel = new JLabel("Update Item");
        titleLabel.setFont(new Font("Cambria", Font.BOLD, 24));
        titleLabel.setBounds(150, 20, 200, 40);
        panel.add(titleLabel);

        idLabel = new JLabel("Item ID:");
        idLabel.setFont(font);
        idLabel.setBounds(50, 80, 100, 30);
        panel.add(idLabel);

        idField = new JTextField();
        idField.setBounds(180, 80, 250, 30);
        panel.add(idField);

        nameLabel = new JLabel("New Name:");
        nameLabel.setFont(font);
        nameLabel.setBounds(50, 130, 120, 30);
        panel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(180, 130, 250, 30);
        panel.add(nameField);

        quantityLabel = new JLabel("New Quantity:");
        quantityLabel.setFont(font);
        quantityLabel.setBounds(50, 180, 120, 30);
        panel.add(quantityLabel);

        quantityField = new JTextField();
        quantityField.setBounds(180, 180, 250, 30);
        panel.add(quantityField);

        updateButton = new JButton("Update");
        updateButton.setFont(font);
        updateButton.setBackground(Color.LIGHT_GRAY);
        updateButton.setBounds(100, 250, 120, 40);
        updateButton.addActionListener(this);
        panel.add(updateButton);

        backButton = new JButton("Back");
        backButton.setFont(font);
        backButton.setBackground(Color.LIGHT_GRAY);
        backButton.setBounds(260, 250, 120, 40);
        backButton.addActionListener(this);
        panel.add(backButton);

        this.add(panel);
    }


    private boolean itemExists(String id) throws IOException 
	{
        File file = new File("inventory.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) 
		{
            String line;
            while ((line = br.readLine()) != null) 
			{
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].equals(id)) 
				{
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isDuplicateName(String currentId, String newName) throws IOException 
	{
        File file = new File("inventory.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) 
		{
            String line;
            while ((line = br.readLine()) != null) 
			{
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String id = parts[0];
                    String name = parts[1];

                    if (!id.equals(currentId) && name.equalsIgnoreCase(newName)) 
					{
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void updateItem(String id, String name, int quantity) throws IOException 
	{
        File file = new File("inventory.txt");
        if (!file.exists()) return;

        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) 
		{
            String line;
            while ((line = br.readLine()) != null) 
			{
                String[] parts = line.split(",");
                if (parts.length == 3) 
				{
                    if (parts[0].equals(id)) 
					{
                        lines.add(id + "," + name + "," + quantity);
                    } 
					else 
					{
                        lines.add(line);
                    }
                }
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) 
		{
            for (String l : lines) 
			{
                bw.write(l);
                bw.newLine();
            }
        }
    }
    public void actionPerformed(ActionEvent ae) 
	{
        if (ae.getSource() == updateButton) 
		{
            String id = idField.getText();
            String name = nameField.getText();
            String quantityStr = quantityField.getText();

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
                JOptionPane.showMessageDialog(this, "Quantity Must Be A Positive Integer.", "Error", JOptionPane.ERROR_MESSAGE);
				noe.printStackTrace();
				return;
            }

            try 
			{
                if (!itemExists(id)) 
				{
                    JOptionPane.showMessageDialog(this, "Item ID Does Not Exist.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (isDuplicateName(id, name)) 
				{
                    JOptionPane.showMessageDialog(this, "Item Name Already Exists For Another Item!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                updateItem(id, name, quantity);

                JOptionPane.showMessageDialog(this, "Item Updated Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                idField.setText("");
                nameField.setText("");
                quantityField.setText("");

            } 
			catch (IOException ioe) 
			{
                JOptionPane.showMessageDialog(this, "Error Updating Item: " + ioe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				ioe.printStackTrace();
			}

        } 
		else if (ae.getSource() == backButton) 
		{
            this.setVisible(false);
            new HomePage(username).setVisible(true);
        }
    }
}
