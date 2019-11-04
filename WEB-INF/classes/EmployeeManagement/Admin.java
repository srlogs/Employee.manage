package EmployeeManagement;

import java.io.*;
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
import java.util.Date;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.w3c.dom.Attr;


public class Admin
{
  AESAlgorithm s = new AESAlgorithm();
  public void initiate()
  {
    try
    {
      int y=0;
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      System.out.println("\n\t\tWelcome to admin mode");
      for(;y==0;)
      {
        System.out.println("\n\nChoose the options\n");
        System.out.println("1. View requests\n2. View history\n3. exit");
        System.out.print("\nEnter the number  :  ");
        int ch = Integer.parseInt(in.readLine());
        switch(ch)
        {
          case 1:
            View();
            break;
          case 2:
            viewallDetail();
            break;
          case 3:
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

  public void View() throws Exception
  {
    JSONObject jsonobj = new JSONObject();
    String id = " ", check = " ",Time = " ";
    File file = new File("E:\\logesh\\admin.xml");
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document doc = dBuilder.parse(file);
    doc.getDocumentElement().normalize();
    NodeList nList = doc.getElementsByTagName("employee");
    if(file.length() == 0 || nList.getLength() == 0)
    {
      System.out.println("\n\t\t*** There are no requests ***");
    }
    for (int temp = 0; temp < nList.getLength(); temp++)
    {
      Node nNode = nList.item(temp);

      if (nNode.getNodeType() == Node.ELEMENT_NODE)
      {
        Element eElement=(Element)nNode;

        jsonobj.put("id", eElement.getElementsByTagName("id").item(0).getTextContent());
        jsonobj.put("check",  eElement.getElementsByTagName("check").item(0).getTextContent());
        jsonobj.put("Time", eElement.getElementsByTagName("time").item(0).getTextContent());
      }
      ViewList(jsonobj);
    }

  }

  public void ViewList(JSONObject jsonobj) throws Exception
  {
    int end = 0;
    String name = " ";
    Date dates;
    String actime;
    DateFormat dateFormat;
    Calendar cal;
    File file = new File("E:\\logesh\\execute.xml");
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document doc = dBuilder.parse(file);
    doc.getDocumentElement().normalize();
    NodeList nList = doc.getElementsByTagName("employee");
    System.out.println("\n\t\t Password update requests\n");
    for (int temp = 0; temp < nList.getLength(); temp++)
    {
      Node nNode = nList.item(temp);

      if (nNode.getNodeType() == Node.ELEMENT_NODE)
      {
        Element eElement=(Element)nNode;
        if(jsonobj.getString("id").equals(eElement.getAttribute("id")))
        {
          System.out.format("%3s%14s  %s\n\n","Name" ,": " , eElement.getElementsByTagName("name").item(0).getTextContent());
          System.out.format("%3s%5s  %s\n\n","Father's name" ,": " , eElement.getElementsByTagName("fatherName").item(0).getTextContent());
          System.out.format("%3s%6s  %s\n\n","phone number" ,": " , eElement.getElementsByTagName("phone").item(0).getTextContent());
          System.out.format("%3s%10s  %s\n\n","password ",":  " , s.decrypt(eElement.getElementsByTagName("psw").item(0).getTextContent()));
          System.out.format("%3s%4s  %s\n\n","Date of birth " ,": ", eElement.getElementsByTagName("DoB").item(0).getTextContent());
          System.out.format("%3s%6s  %s\n\n","Blood group " ,": " , eElement.getElementsByTagName("blood").item(0).getTextContent());
          System.out.format("%3s%2s  %s\n\n","Registration Id " ,": " , eElement.getElementsByTagName("RegisterNum").item(0).getTextContent());
          System.out.format("%3s%10s  %s\n\n","Address " ,": " , eElement.getElementsByTagName("address").item(0).getTextContent());
          jsonobj.put("Name", eElement.getElementsByTagName("name").item(0).getTextContent());
          if(jsonobj.getString("check").equals("1"))
          {
            System.out.println("\nEmployee requested for password change\n");
          }
          else if(jsonobj.getString("check").equals("2"))
          {
            System.out.println("\nEmployee requested for update info\n");
          }
          System.out.println("\nChoose the options\n");
          System.out.println("1. Accept requests\n2. Deny requests\n3. exit\n");
          System.out.print("Enter your choice  :  ");
          int req = Integer.parseInt(in.readLine());
          switch(req)
          {
            case 1:
              jsonobj.put("status", 1);
              modify(jsonobj);
              cal = Calendar.getInstance();
              dates =  cal.getTime();
              dateFormat = new SimpleDateFormat("hh:mm:ss");
              actime = dateFormat.format(dates);
              jsonobj.put("actime", actime);
              Viewallwrite(jsonobj);
              break;
            case 2:
              jsonobj.put("status", 2);
              modify(jsonobj);
              cal = Calendar.getInstance();
              dates =  cal.getTime();
              dateFormat = new SimpleDateFormat("hh:mm:ss");
              actime = dateFormat.format(dates);
              jsonobj.put("actime", actime);
              Viewallwrite(jsonobj);
              break;
            case 3:
              end = 1;
              break;
          }
          if(end == 1)
          {
            break;
          }
        }
      }
    }
  }

  public void writelist(JSONObject jsonobject) throws Exception
  {
    File file = new File("E:\\logesh\\admin.xml");
    InputClass c = new InputClass();
    DocumentBuilderFactory docBuildFactory= DocumentBuilderFactory.newInstance();
  	DocumentBuilder docbuild= docBuildFactory.newDocumentBuilder();
		Document document;
		Element root;
  	if(file.length()==0)
  	{
  		document=docbuild.newDocument();
  		root= document.createElement("Company");
  		document.appendChild(root);
  	}
  	else
  	{
  		document= docbuild.parse(file);
  		root=document.getDocumentElement();
      NodeList nl=document.getElementsByTagName("employee");
  	}
    Element employee = document.createElement("employee");
    root.appendChild(employee);

    Element idvalue = document.createElement("id");
    idvalue.appendChild(document.createTextNode(jsonobject.getString("id")));
    employee.appendChild(idvalue);

    Element check = document.createElement("check");
    check.appendChild(document.createTextNode(jsonobject.getString("value")));
    employee.appendChild(check);

    Element reqtime = document.createElement("time");
    reqtime.appendChild(document.createTextNode(jsonobject.getString("time")));
    employee.appendChild(reqtime);

    c.writexml(document, file);
  }

  public void deleteNode(String s, String check)
  {
    int deletevalue=0;
    InputClass c = new InputClass();
    try
    {
      BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
      File file = new File("E:\\logesh\\admin.xml");
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(file);
      doc.getDocumentElement().normalize();
      NodeList nList = doc.getElementsByTagName("employee");
      Element root = doc.getDocumentElement();
      for (int temp = 0; temp < nList.getLength(); temp++)
      {
        Node nNode = nList.item(temp);
        if(nNode.getNodeType() == Node.ELEMENT_NODE)
        {
          Element eElement=(Element)nNode;

          if(s.equals(eElement.getElementsByTagName("id").item(0).getTextContent()) && check.equals(eElement.getElementsByTagName("check").item(0).getTextContent()))
          {
            root.removeChild(nNode);
          }
        }
      }
      c.writexml(doc, file);
    }
    catch(Exception e)
    {
      System.out.println(e);
    }
  }

  public void modify(JSONObject jsonobj)
  {
    InputClass c = new InputClass();
    int deletevalue=0;
    File file = new File("E:\\logesh\\execute.xml");
    try
    {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(file);
      doc.getDocumentElement().normalize();
      NodeList nList = doc.getElementsByTagName("employee");
      Element root = doc.getDocumentElement();

      for (int temp = 0; temp < nList.getLength(); temp++)
      {
        Node nNode = nList.item(temp);

        if (nNode.getNodeType() == Node.ELEMENT_NODE)
        {
          Element eElement=(Element)nNode;
          if(jsonobj.getString("id").equals(eElement.getAttribute("id")))
          {
            NodeList ls=eElement.getChildNodes();
            for(int j=0;j<ls.getLength();j++)
            {
            	Node x=ls.item(j);
              if(jsonobj.getInt("status") == 1)
              {
                if(jsonobj.getString("check").equals("1"))
                {
                  if((x.getNodeName()).equals("passStatus"))
                  {
                    Element value=(Element)x;
                    value.setTextContent("2");
                    deleteNode(jsonobj.getString("id"),jsonobj.getString("check"));
                    break;
                  }
                }
                else if(jsonobj.getString("check").equals("2"))
                {
                  if((x.getNodeName()).equals("updStatus"))
                  {
                    Element value=(Element)x;
                    value.setTextContent("2");
                    deleteNode(jsonobj.getString("id"),jsonobj.getString("check"));
                    break;
                  }
                }
              }
              else if(jsonobj.getInt("status") == 2)
              {
                if(jsonobj.getString("check").equals("1"))
                {
                  if((x.getNodeName()).equals("passStatus"))
                  {
                    Element value=(Element)x;
                    value.setTextContent("3");
                    deleteNode(jsonobj.getString("id"),jsonobj.getString("check"));
                    break;
                  }
                }
                else if(jsonobj.getString("check").equals("2"))
                {
                  if((x.getNodeName()).equals("updStatus"))
                  {
                    Element value=(Element)x;
                    value.setTextContent("3");
                    deleteNode(jsonobj.getString("id"),jsonobj.getString("check"));
                    break;
                  }
                }
              }

              }
            }
          }
        }
          c.writexml(doc, file);
      }
    catch(Exception e)
    {
      System.out.println(e);
    }
  }

  public void Viewallwrite(JSONObject jsonobj)
  {
    File file = new File("E:\\logesh\\viewall.xml");
    InputClass c = new InputClass();
    Info info[] = Info.values();
    try
    {
      DocumentBuilderFactory docBuildFactory= DocumentBuilderFactory.newInstance();
      DocumentBuilder docbuild= docBuildFactory.newDocumentBuilder();
      Document document;

      Element root;
      if(file.length()==0)
      {
        document=docbuild.newDocument();
        root= document.createElement("Company");
        document.appendChild(root);
      }
      else
      {
        document= docbuild.parse(file);
        root=document.getDocumentElement();
        NodeList nl=document.getElementsByTagName("employee");
      }
      Element employee = document.createElement("employee");
      root.appendChild(employee);

      Element names= document.createElement("id");
      names.appendChild(document.createTextNode(jsonobj.getString("id")));
      employee.appendChild(names);

      Element time = document.createElement("time");
      time.appendChild(document.createTextNode(jsonobj.getString("Time")));
      employee.appendChild(time);

      if(jsonobj.getString("check").equals("2"))
      {
        Element field = document.createElement("field");
        field.appendChild(document.createTextNode("0"));
        employee.appendChild(field);
      }
      else if(jsonobj.getString("check").equals("1"))
      {
        Element field = document.createElement("field");
        field.appendChild(document.createTextNode("1"));
        employee.appendChild(field);
      }

      if(jsonobj.getInt("status") == 1)
      {
        Element choice = document.createElement("choice");
        choice.appendChild(document.createTextNode("2"));
        employee.appendChild(choice);
      }
      else if(jsonobj.getInt("status") == 2)
      {
        Element choice = document.createElement("choice");
        choice.appendChild(document.createTextNode("3"));
        employee.appendChild(choice);
      }

      Element artime = document.createElement("artime");
      artime.appendChild(document.createTextNode(jsonobj.getString("actime")));
      employee.appendChild(artime);

      c.writexml(document, file);
    }
    catch(Exception e)
    {
      System.out.println(e);
    }
  }
  public void viewallDetail() throws Exception
  {
    Info info[] = Info.values();
    JSONObject jsonobj = new JSONObject();
    File file = new File("E:\\logesh\\viewall.xml");
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document doc = dBuilder.parse(file);
    doc.getDocumentElement().normalize();
    NodeList nList = doc.getElementsByTagName("employee");
    if(file.length()==0 || nList.getLength() == 0)
    {
      System.out.println("\n\t\t*** There are no requests ***");
    }
    System.out.println("\n\t\tHistory\n");
    System.out.println("______________________________________________________________________________");
    System.out.format("%7s%6s%4s%4s%15s%5s%10s%4s%5s%3s\n","Name","|","Request Time","|","Reason","|","Status","|","Response Time","|");
    System.out.println("______________________________________________________________________________|");
    for (int temp = 0; temp < nList.getLength(); temp++)
    {
      Node nNode = nList.item(temp);

      if (nNode.getNodeType() == Node.ELEMENT_NODE)
      {
        Element eElement=(Element)nNode;

        jsonobj.put("id", eElement.getElementsByTagName("id").item(0).getTextContent());
        jsonobj.put("rqtime", eElement.getElementsByTagName("time").item(0).getTextContent());
        jsonobj.put("reason", info[Integer.parseInt(eElement.getElementsByTagName("field").item(0).getTextContent())].toString());
        jsonobj.put("status", info[Integer.parseInt(eElement.getElementsByTagName("choice").item(0).getTextContent())].toString());
        jsonobj.put("rstime", eElement.getElementsByTagName("artime").item(0).getTextContent());

        viewalldetail(jsonobj);
      }
    }
    System.out.println("______________________________________________________________________________|\n");
  }
  public void viewalldetail(JSONObject jsonobj)
  {
    try
      {
        File file = new File("E:\\logesh\\execute.xml");

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("employee");
        if(file.length()==0 || nList.getLength() == 0)
        {
          System.out.println("\n\t\t*** There are no requests ***");
        }
        for (int temp = 0; temp < nList.getLength(); temp++)
        {
          Node nNode = nList.item(temp);

          if (nNode.getNodeType() == Node.ELEMENT_NODE)
          {
            Element eElement=(Element)nNode;
            if(jsonobj.getString("id").equals(eElement.getAttribute("id")))
            System.out.format("%7s%6s%12s%4s%12s%5s%10s%4s%5s%8s\n",eElement.getElementsByTagName("name").item(0).getTextContent(),"|",jsonobj.getString("rqtime"),"|",jsonobj.getString("reason"),"|",jsonobj.getString("status"),"|",jsonobj.getString("rstime"),"|");
          }
        }
      }
      catch(Exception e)
      {
        System.out.println(e);
      }
    }

    public enum Info
    {
      upd_Information, password_change, Accepted, Rejected;
    }
}
