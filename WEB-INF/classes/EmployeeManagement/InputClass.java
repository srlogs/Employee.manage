package EmployeeManagement;

import java.io.*;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.Iterator;
import org.json.*;

public class InputClass
{
  int g,flags=0;
	String nAme,pAssword,psw;
	LogicClass l=new LogicClass();
  AESAlgorithm s=new AESAlgorithm();

	public void write()
	{
		try
		{
			int i=0;
			BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
			System.out.println("\n\t\t\tWELCOME");
			System.out.println("\n1. listout the employees\n2. Add the employees\n");
			System.out.print("enter your choice  :");
			int choice=Integer.parseInt(in.readLine());

			switch(choice)
			{
				case 1:
					reading();
					break;
				case 2:
					add();
					break;
				default:
					break;
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
  public int arraysize()
  {
    NodeList nList;
    int i=0;
    try
    {
      File file = new File("E:\\logesh\\execute.xml");

      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(file);
      doc.getDocumentElement().normalize();
      nList = doc.getElementsByTagName("employee");
      i=nList.getLength();

    }
    catch(Exception e)
    {
      System.out.println(e);
    }
    return i;
  }
	public List<Object> printout()
	{
		JSONObject Employee = new JSONObject();

		List<Object> EmployeeDetails = new ArrayList<>();
		try
		{
			File file = new File("E:\\logesh\\execute.xml");

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("employee");

			for (int temp = 0; temp < nList.getLength(); temp++)
			{
				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE)
				{

					Element eElement=(Element)nNode;
          Employee = new JSONObject();
				  Employee.put("name",eElement.getElementsByTagName("name").item(0).getTextContent());
					Employee.put("phone number",eElement.getElementsByTagName("phone").item(0).getTextContent());
					Employee.put("password",eElement.getElementsByTagName("psw").item(0).getTextContent());
				}
          EmployeeDetails.add(Employee);
      }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
      return EmployeeDetails;
	}

	public void reading()
	{
    try
    {
    System.out.println("______________________________________________________________________");
    System.out.format("%15s%3s%17s%6s%20s%10s\n","Employee Name","|","Phone Number","|","Password","|");
    System.out.println("______________________________________________________________________|");
    JSONArray jsonarr  =  new JSONArray(printout());
    for(int j=0;j<arraysize();j++)
    {
        JSONObject innerObj =  jsonarr.getJSONObject(j);

        System.out.format("%12s%6s%17s%6s%20s%6s\n",innerObj.get("name"),"|",innerObj.get("phone number"),"|",innerObj.get("password"),"|");

      }
      	System.out.println("______________________________________________________________________|");
      }
      catch(Exception e)
      {
        System.out.println(e);
      }
	}
	public void add()
	{
		try
		{
			int i=0,j=0;
      String names, phoneno, passWord, password, regno, emfather, dob, blood, addr;
      JSONObject data = new JSONObject();
      JSONArray arrdata = new JSONArray();
			BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
			System.out.print("\nEnter the number of employees :  ");
			g=Integer.parseInt(in.readLine());
      while(i<g)
      {
        data = new JSONObject();
        System.out.println("\n\t\tEmployee "+(i+1)+" Details\n");
				System.out.print("\nenter the employee name   :   ");
        data.put("Name", in.readLine());

				System.out.print("\nenter the phone number  :  ");
        data.put("PhoneNumber", in.readLine());

				System.out.print("\nenter the password  :  ");
        data.put("Password", s.encrypt(in.readLine()));

        System.out.print("\nenter the reg.no  :  ");
        data.put("RegisterNumber", in.readLine());

        System.out.print("\nenter the employee's father name  :  ");
        data.put("FatherName", in.readLine());

        System.out.print("\nenter the date.of.birth  :  ");
        data.put("DateOfBirth", in.readLine());

        System.out.print("\nenter the blood group  :  ");
        data.put("Blood",in.readLine());

        System.out.print("\nenter the address  :  ");
        data.put("Address", in.readLine());
        i++;
        arrdata.put(data);
      }
      l.add(arrdata);

		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}

	public void read() throws Exception
	{
      JSONObject readData = new JSONObject();
			BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
			System.out.print("\nenter your name :  ");
      readData.put("Name", in.readLine());
			System.out.print("\nenter your password :  ");
      readData.put("Password", in.readLine());
      l.validate(readData);
	}
	public void reread(String name)
	{
		try
		{
      JSONObject readData = new JSONObject();
			BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
      readData.put("Name", name);
			System.out.print("\nenter your password  :  ");
      readData.put("Password", in.readLine());
      l.validate(readData);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
  public void writexml(Document doc, File f) throws TransformerException
  {
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
    DOMSource domSource = new DOMSource(doc);
    StreamResult streamResult = new StreamResult(f);
    transformer.transform(domSource, streamResult);
  }
}
