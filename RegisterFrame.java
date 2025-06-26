package Frames;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import Entities.*;

public class RegisterFrame extends JFrame implements ActionListener 
{
    JPanel panel;
    JLabel titleLbl, namlbl, passlbl, confirmlbl, imageLabel;
    JTextField nmfld;
    JPasswordField passfld, confirmfld;
    JButton signup, back;
    Font font;
    Color bgColor;

    public RegisterFrame() 
	{
        super("Registration");
        this.setSize(850, 450);
        this.setLocationRelativeTo(null);

        bgColor = new Color(176, 219, 156);
        font = new Font("Cambria", Font.PLAIN, 18);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(bgColor);

        titleLbl = new JLabel("Register");
        titleLbl.setBounds(150, 30, 200, 40);
        titleLbl.setFont(new Font("Cambria", Font.BOLD, 26));
        titleLbl.setForeground(Color.BLACK);
        panel.add(titleLbl);

        namlbl = new JLabel("Username:");
        namlbl.setBounds(50, 100, 120, 40);
        namlbl.setForeground(Color.BLACK);
        namlbl.setFont(font);
        panel.add(namlbl);

        nmfld = new JTextField();
        nmfld.setBounds(170, 100, 200, 40);
        panel.add(nmfld);

        passlbl = new JLabel("Password:");
        passlbl.setBounds(50, 160, 120, 40);
        passlbl.setForeground(Color.BLACK);
        passlbl.setFont(font);
        panel.add(passlbl);

        passfld = new JPasswordField();
        passfld.setBounds(170, 160, 200, 40);
        panel.add(passfld);

        confirmlbl = new JLabel("Confirm Password:");
        confirmlbl.setBounds(10, 220, 150, 40);
        confirmlbl.setForeground(Color.BLACK);
        confirmlbl.setFont(font);
        panel.add(confirmlbl);

        confirmfld = new JPasswordField();
        confirmfld.setBounds(170, 220, 200, 40);
        panel.add(confirmfld);

        signup = new JButton("Sign Up");
        signup.setBounds(100, 290, 110, 40);
        signup.setFont(font);
        signup.setBackground(Color.LIGHT_GRAY);
        signup.addActionListener(this);
        panel.add(signup);

        back = new JButton("Back");
        back.setBounds(280, 290, 110, 40);
        back.setFont(font);
        back.setBackground(Color.LIGHT_GRAY);
        back.addActionListener(this);
        panel.add(back);
		
		ImageIcon icon = new ImageIcon("D:\\Java Project\\Updated Project\\Image\\image2.png");
        imageLabel = new JLabel(icon);
        imageLabel.setBounds(425, 0, 425, 450);
        panel.add(imageLabel);
        
		this.add(panel);
    }

    public void actionPerformed(ActionEvent ae) 
	{
        if (ae.getSource() == signup) 
		{
            String username = nmfld.getText();
            String password = new String(passfld.getPassword());
            String confirmPassword = new String(confirmfld.getPassword());

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) 
			{
                JOptionPane.showMessageDialog(this, "Fill All The Fields", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!password.equals(confirmPassword)) 
			{
                JOptionPane.showMessageDialog(this, "Passwords Do Not Match!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            User user = new User(username, password);

            try 
			{
                if (user.exists()) 
				{
                    JOptionPane.showMessageDialog(this, "Username Already Exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
				
                user.save();

                JOptionPane.showMessageDialog(this, "Registration Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
                new LoginFrame().setVisible(true);
            } 
			catch (IOException ioe) 
			{
                JOptionPane.showMessageDialog(this, "Error Saving User: " + ioe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				ioe.printStackTrace();
			}
        } 
		else if (ae.getSource() == back) 
		{
            this.setVisible(false);
            new LoginFrame().setVisible(true);
        }
    }
}
