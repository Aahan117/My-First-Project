package Frames;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import Entities.*;

public class LoginFrame extends JFrame implements ActionListener 
{
    JLabel titleLbl, userLbl, passLbl, imageLabel;
    JTextField userFld;
    JPasswordField passFld;
    JButton loginBtn, registerBtn, exitBtn;
    JPanel panel;
    Font font;
    Color bgColor;

    public LoginFrame() 
	{
        super("Login");
        this.setSize(850, 450);
        this.setLocationRelativeTo(null);

        bgColor = new Color(176, 219, 156);
        font = new Font("Cambria", Font.PLAIN, 18);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(bgColor);

        ImageIcon icon = new ImageIcon("D:\\Java Project\\Updated Project\\Image\\image1.png");
        imageLabel = new JLabel(icon);
        imageLabel.setBounds(0, 0, 425, 450);
        panel.add(imageLabel);

        titleLbl = new JLabel("Login");
        titleLbl.setBounds(550, 30, 200, 40);
        titleLbl.setFont(new Font("Cambria", Font.BOLD, 26));
        titleLbl.setForeground(Color.BLACK);
        panel.add(titleLbl);

        userLbl = new JLabel("Username:");
        userLbl.setBounds(450, 100, 150, 40);
        userLbl.setFont(font);
        userLbl.setForeground(Color.BLACK);
        panel.add(userLbl);

        userFld = new JTextField();
        userFld.setBounds(570, 100, 220, 40);
        panel.add(userFld);

        passLbl = new JLabel("Password:");
        passLbl.setBounds(450, 160, 150, 40);
        passLbl.setFont(font);
        passLbl.setForeground(Color.BLACK);
        panel.add(passLbl);

        passFld = new JPasswordField();
        passFld.setBounds(570, 160, 220, 40);
        panel.add(passFld);

        loginBtn = new JButton("Login");
        loginBtn.setBounds(450, 230, 100, 40);
        loginBtn.setFont(font);
        loginBtn.setBackground(Color.LIGHT_GRAY);
        loginBtn.addActionListener(this);
        panel.add(loginBtn);

        registerBtn = new JButton("Register");
        registerBtn.setBounds(570, 230, 100, 40);
        registerBtn.setFont(font);
        registerBtn.setBackground(Color.LIGHT_GRAY);
        registerBtn.addActionListener(this);
        panel.add(registerBtn);

        exitBtn = new JButton("Exit");
        exitBtn.setBounds(690, 230, 100, 40);
        exitBtn.setFont(font);
        exitBtn.setBackground(Color.LIGHT_GRAY);
        exitBtn.addActionListener(this);
        panel.add(exitBtn);

        this.add(panel);
    }

    
	public void actionPerformed(ActionEvent ae) 
	{
        if (ae.getSource() == loginBtn) 
		{
            String username = userFld.getText();
            String password = new String(passFld.getPassword());

            User user = new User(username, password);

            if (user.getUsername().isEmpty() || user.getPassword().isEmpty()) 
			{
                JOptionPane.showMessageDialog(this, "Please Fill All Fields", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try 
			{
                if (user.isValid()) 
				{
                    JOptionPane.showMessageDialog(this, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    this.setVisible(false);
                    new HomePage(user.getUsername()).setVisible(true);
                } 
				else 
				{
                    JOptionPane.showMessageDialog(this, "Invalid Username or Password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } 
			catch (IOException ioe) 
			{
                JOptionPane.showMessageDialog(this, "Error Saving User: " + ioe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				ioe.printStackTrace();
			}

        } 
		else if (ae.getSource() == registerBtn) 
		{
            this.setVisible(false);
            new RegisterFrame().setVisible(true);
        } 
		else if (ae.getSource() == exitBtn) 
		{
            System.exit(0);
        }
    }
}
