package EmployeeManagement;

import java.io.*;
public class MainClass
{
    public static void main(String args[])
    {
        int y=0;
    		InputClass c =  new InputClass();
        Admin a = new Admin();
    		try
    		{
    			for(;y==0;)
    			{
    				BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
    				System.out.println("\n\t\t\tSIGN IN");
    				System.out.print("1. HR \n2. Employee\n3. Admin\n4. exit \n\n choose the option :  ");
    				int n=Integer.parseInt(in.readLine());

    				switch(n)
    				{
    					case 1:
    						c.write();
    						break;
    					case 2:
    						c.read();
    						break;
              case 3:
                a.initiate();
                break;
    					case 4:
    						y=1;
    						break;
    					default:
    						break;
    				}
    			}
    		}
    		catch(Exception e)
    		{
    			System.out.println(e);
    		}
    }
}
