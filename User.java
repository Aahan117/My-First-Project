package Entities;
import java.io.*;
import Frames.*;

public class User 
{
    private String username;
    private String password;

    public User() 
	{
		
	}

    public User(String username, String password) 
	{
        this.username = username;
        this.password = password;
    }

    public String getUsername() 
	{
        return username;
    }

    public void setUsername(String username) 
	{
        this.username = username;
    }

    public String getPassword() 
	{
        return password;
    }

    public void setPassword(String password) 
	{
        this.password = password;
    }

    public boolean isValid() throws IOException 
	{
		BufferedReader br = new BufferedReader(new FileReader("users.txt"));
		String line;
		while ((line = br.readLine()) != null) 
		{
			String[] parts = line.split(",");
			if (parts.length >= 2 && parts[0].equals(username) && parts[1].equals(password)) 
			{
            br.close();
            return true;
			}
		}
		br.close();
		return false;
	}

	
	public boolean exists() throws IOException 
	{
		BufferedReader br = new BufferedReader(new FileReader("users.txt"));
		String line;
		while ((line = br.readLine()) != null) 
		{
			String[] parts = line.split(",");
			if (parts.length > 0 && parts[0].equals(username)) 
			{
				br.close();
				return true;
			}
		}
		br.close();
		return false;
	}

	
	public void save() throws IOException 
	{
        FileWriter fw = new FileWriter("users.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(username + "," + password);
        bw.newLine();
        bw.close();
    }
}
