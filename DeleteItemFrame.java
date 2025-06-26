package Frames;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import Entities.*;

public class DeleteItemFrame extends JFrame implements ActionListener 
{
    JLabel titleLbl, idLbl;
    JTextField idFld;
    JButton deleteBtn, backBtn;
    JPanel panel;
    Font font;
    Color bgColor;
    String username;

    public DeleteItemFrame(String username) 
	{
        super("Delete Inventory Item");
        this.username = username;

        this.setSize(400, 250);
        this.setLocationRelativeTo(null);

        bgColor = new Color(176, 219, 156);
        font = new Font("Cambria", Font.PLAIN, 18);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(bgColor);

        titleLbl = new JLabel("Delete Item");
        titleLbl.setFont(new Font("Cambria", Font.BOLD, 24));
        titleLbl.setBounds(120, 20, 200, 40);
        panel.add(titleLbl);

        idLbl = new JLabel("Item ID:");
        idLbl.setFont(font);
        idLbl.setBounds(50, 80, 100, 30);
        panel.add(idLbl);

        idFld = new JTextField();
        idFld.setBounds(140, 80, 180, 30);
        panel.add(idFld);

        deleteBtn = new JButton("Delete");
        deleteBtn.setFont(font);
        deleteBtn.setBackground(Color.LIGHT_GRAY);
        deleteBtn.setBounds(70, 140, 100, 40);
        deleteBtn.addActionListener(this);
        panel.add(deleteBtn);

        backBtn = new JButton("Back");
        backBtn.setFont(font);
        backBtn.setBackground(Color.LIGHT_GRAY);
        backBtn.setBounds(210, 140, 100, 40);
        backBtn.addActionListener(this);
        panel.add(backBtn);

        this.add(panel);
    }
    private void deleteItem(String id) throws IOException 
	{
        File file = new File("inventory.txt");

        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) 
		{
            String line;
            while ((line = br.readLine()) != null) 
			{
                String[] parts = line.split(",");
                if (!(parts.length == 3 && parts[0].equals(id))) 
				{
                    lines.add(line);
                }
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) 
		{
            for (String line : lines) 
			{
                bw.write(line);
                bw.newLine();
            }
        }
	}	

    public void actionPerformed(ActionEvent ae) 
	{
        if (ae.getSource() == deleteBtn) 
		{
            String id = idFld.getText();
            if (id.isEmpty()) 
			{
                JOptionPane.showMessageDialog(this, "Please Enter Item ID.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try 
			{
                if (!itemExists(id)) 
				{
                    JOptionPane.showMessageDialog(this, "Item ID Does Not Exist.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                deleteItem(id);

                JOptionPane.showMessageDialog(this, "Item Deleted Successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                idFld.setText("");

            } 
			catch (IOException ioe) 
			{
                JOptionPane.showMessageDialog(this, "Error Deleting Item: " + ioe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				ioe.printStackTrace();
			}

        } 
		else if (ae.getSource() == backBtn) 
		{
            this.setVisible(false);
            new HomePage(username).setVisible(true);
        }
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
}
