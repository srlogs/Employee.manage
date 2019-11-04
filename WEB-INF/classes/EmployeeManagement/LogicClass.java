package EmployeeManagement;

import java.io.File;
import java.io.*;
import java.util.Calendar;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.w3c.dom.Attr;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.*;

public class LogicClass
{
    AESAlgorithm s=new AESAlgorithm();
    Admin  a = new Admin();
  public void add(JSONArray jsondata) throws Exception
  {
    InputClass c = new InputClass();
    int j = 0;
    File file = new File("E:\\logesh\\execute.xml");
    DocumentBuilderFactory docBuildFactory= DocumentBuilderFactory.newInstance();
    DocumentBuilder docbuild= docBuildFactory.newDocumentBuilder();
    Document document;
    Element root;

    if(file.length()==0)
    {
      document=docbuild.newDocument();
      root= document.createElement("Company");
      document.appendChild(root);
      j=0;
    }
    else
    {
      document= docbuild.parse(file);
      root=document.getDocumentElement();
      NodeList nl=document.getElementsByTagName("employee");
      Element e = (Element) nl.item((nl.getLength())-1);
      String des = e.getAttribute("id");
      j=Integer.parseInt(des);
      j=j+1;
    }
    for(int i = 0; i<jsondata.length(); i++)
    {
      String Un = Integer.toString(j);
      JSONObject innerObj = jsondata.getJSONObject(i);

      Element employee = document.createElement("employee");
      root.appendChild(employee);

      Attr attr = document.createAttribute("id");
      attr.setValue(Un);
      employee.setAttributeNode(attr);

      Element name = document.createElement("name");
      name.appendChild(document.createTextNode(innerObj.getString("Name")));
      employee.appendChild(name);

      Element phone = document.createElement("phone");
      phone.appendChild(document.createTextNode(innerObj.getString("PhoneNumber")));
      employee.appendChild(phone);

      Element psw = document.createElement("psw");
      psw.appendChild(document.createTextNode(innerObj.getString("Password")));
      employee.appendChild(psw);

      Element regn = document.createElement("RegisterNum");
      regn.appendChild(document.createTextNode(innerObj.getString("RegisterNumber")));
      employee.appendChild(regn);

      Element fname = document.createElement("fatherName");
      fname.appendChild(document.createTextNode(innerObj.getString("FatherName")));
      employee.appendChild(fname);

      Element edob = document.createElement("DoB");
      edob.appendChild(document.createTextNode(innerObj.getString("DateOfBirth")));
      employee.appendChild(edob);

      Element bg = document.createElement("blood");
      bg.appendChild(document.createTextNode(innerObj.getString("Blood")));
      employee.appendChild(bg);

      Element addrs = document.createElement("address");
      addrs.appendChild(document.createTextNode(innerObj.getString("Address")));
      employee.appendChild(addrs);

      Element passStatus = document.createElement("passStatus");
      passStatus.appendChild(document.createTextNode("0"));
      employee.appendChild(passStatus);

      Element updStatus = document.createElement("updStatus");
      updStatus.appendChild(document.createTextNode("0"));
      employee.appendChild(updStatus);
      j++;
    }

    c.writexml(document, file);
    System.out.println("\nDone creating XML File");
  }
	public void validate(JSONObject jsonobj)
	{
		AESAlgorithm s = new AESAlgorithm();
    InputClass c = new InputClass();
		int count=0,z=0,the_end=0,m=1,fn=0,rg=0,bgs=0,ad=0,ph=0,dobs=0,f=0;
		int field=0,flag=0;
    JSONObject jsonobject = new JSONObject();
		try
		{
			BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
			File file = new File("E:\\logesh\\execute.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("employee");

			for (int temp = 0; temp < nList.getLength(); temp++)
			{
				if(the_end==1)
				{
				break;
				}
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE)
				{
					Element eElement=(Element)nNode;
					if((jsonobj.getString("Name").equals(eElement.getElementsByTagName("name").item(0).getTextContent())) && (jsonobj.getString("Password").equals(s.decrypt(eElement.getElementsByTagName("psw").item(0).getTextContent()))))
					{
            System.out.println("\n\t\t\t*** Information ***\n");
            System.out.format("\t%3s%14s  %s\n","Name" ,": " , eElement.getElementsByTagName("name").item(0).getTextContent());
            System.out.format("\t%3s%5s  %s\n","Father's name" ,": " , eElement.getElementsByTagName("fatherName").item(0).getTextContent());
            System.out.format("\t%3s%6s  %s\n","phone number" ,": " , eElement.getElementsByTagName("phone").item(0).getTextContent());
            System.out.format("\t%3s%10s %s\n","password ",":  " , s.decrypt(eElement.getElementsByTagName("psw").item(0).getTextContent()));
            System.out.format("\t%3s%4s  %s\n","Date of birth " ,": ", eElement.getElementsByTagName("DoB").item(0).getTextContent());
            System.out.format("\t%3s%6s  %s\n","Blood group " ,": " , eElement.getElementsByTagName("blood").item(0).getTextContent());
            System.out.format("\t%3s%2s  %s\n","Registration Id " ,": " , eElement.getElementsByTagName("RegisterNum").item(0).getTextContent());
            System.out.format("\t%3s%10s  %s\n","Address " ,": " , eElement.getElementsByTagName("address").item(0).getTextContent());

						System.out.println("\n\nenter the field to be update info");
						System.out.println("\n1. Change password\n2. Update information\n");
            System.out.print("\nEnter the number  :  ");
						field=Integer.parseInt(in.readLine());
            String id = eElement.getAttribute("id");

						switch(field)
						{
							case 1:
								if((eElement.getElementsByTagName("passStatus").item(0).getTextContent()).equals("0"))
								{
                  f=1;
                  Calendar cal = Calendar.getInstance();
        					Date date=cal.getTime();
									DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                	String strDate = dateFormat.format(date);
									System.out.println("\n\t*** Requested for password change ***");
                  eElement.getElementsByTagName("passStatus").item(0).setTextContent("1");
                  jsonobject.put("id", eElement.getAttribute("id"));
                  jsonobject.put("time", strDate);
                  jsonobject.put("value", Integer.toString(f));
                  a.writelist(jsonobject);
									the_end=1;
									break;
								}

								else if((eElement.getElementsByTagName("passStatus").item(0).getTextContent()).equals("2"))
								{
									System.out.println("\n\t*** your request was accepted! ***\n");
									eElement.getElementsByTagName("passStatus").item(0).setTextContent("0");
                  the_end=1;
									flag=1;
								}

                else if((eElement.getElementsByTagName("passStatus").item(0).getTextContent()).equals("3"))
                {
                  System.out.println("\n\t*** Your request was rejected ***\n");
                  eElement.getElementsByTagName("passStatus").item(0).setTextContent("0");
                  the_end=1;
                  break;
                }

                else if((eElement.getElementsByTagName("passStatus").item(0).getTextContent()).equals("1"))
                {
                  System.out.println("\n*** you already requested ***\n");
                  the_end=1;
                  break;
                }
								break;

							  case 2:
                if((eElement.getElementsByTagName("updStatus").item(0).getTextContent()).equals("2"))
                {
                  eElement.getElementsByTagName("updStatus").item(0).setTextContent("0");
                  System.out.println("your request was accepted\n");
                  System.out.println("\nChoose the options to update info\n");
                  System.out.println("\n1. phone number\n2. Date of birth\n3. Father's name\n4. Blood group\n5. Address\n6. Registration Id");
                  System.out.print("\nEnter your choice : ");
                  int choices = Integer.parseInt(in.readLine());
                  switch(choices)
                  {
                    case 1:
                      ph = 1;
                      break;
                    case 2:
                      dobs = 1;
                      break;
                    case 3:
                      fn = 1;
                      break;
                    case  4:
                      bgs = 1;
                      break;
                    case 5:
                      ad = 1;
                      break;
                    case 6:
                      rg = 1;
                      break;
                    default:
                      break;
                  }
                  flag=2;
                }

                else if((eElement.getElementsByTagName("updStatus").item(0).getTextContent()).equals("0"))
  							{
                  f=2;
                  eElement.getElementsByTagName("updStatus").item(0).setTextContent("1");
									Calendar cal = Calendar.getInstance();
        					Date date=cal.getTime();
									DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                	String strDate = dateFormat.format(date);
  								System.out.println("\n\t*** Requested for Update information change ***");
                  jsonobject.put("id", eElement.getAttribute("id"));
                  jsonobject.put("time", strDate);
                  jsonobject.put("value", Integer.toString(f));
                  a.writelist(jsonobject);
  								the_end=1;
  								break;
  							}

                else if((eElement.getElementsByTagName("updStatus").item(0).getTextContent()).equals("3"))
                {
                  System.out.println("\n\t*** Your request was rejected ***\n");
                  eElement.getElementsByTagName("updStatus").item(0).setTextContent("0");
                  the_end=1;
                  break;
                }

                else if((eElement.getElementsByTagName("updStatus").item(0).getTextContent()).equals("1"))
                {
                  System.out.println("\n\t*** you already requested ***\n");
                  the_end=1;
                  break;
                }
								break;

						  	default:
									break;
						}

						NodeList ls=eElement.getChildNodes();
						for(int j = 0 ; j < ls.getLength() ; j++)
						{
							Node x = ls.item(j);
							if(flag == 1)
							{
								if((x.getNodeName()).equals("psw"))
								{
									System.out.print("\nenter new password  : ");
									String newpsw = in.readLine();
									while(z == 0)
									{
										System.out.print("\nconfirm your password  : ");
										String repass = in.readLine();

										if(repass.equals(newpsw))
										{
											Element value = (Element)x;
                      String newpassword = s.encrypt(newpsw);
											value.setTextContent(newpassword);
											System.out.println("\nyour pAssword is got updated");
											z=1;
                      flag=0;
										}
										else
										{
											System.out.println("\nyour password got mismatched");
										}
									}
									count=0;
								  break;
								}
							}
							else if(flag==2)
							{
                if(ph == 1)
                {
  								if((x.getNodeName()).equals("phone"))
  								{
  									System.out.print("\nenter new phone number : ");
  									String newph = in.readLine();
  									Element value = (Element)x;
  									value.setTextContent(newph);
  									System.out.println("\nyour phone is got updated");
  									count = 0;
                    flag=0;
                    ph = 0;
  									break;
  								}
                }
                else if(dobs == 1)
                {
                  if((x.getNodeName()).equals("DoB"))
  								{
  									System.out.print("\nenter Date of Birth : ");
  									String newdob = in.readLine();
  									Element value = (Element)x;
  									value.setTextContent(newdob);
  									System.out.println("\nyour DOB is got updated");
  									count = 0;
                    dobs = 0;
  									break;
  								}
                }
                else if(fn == 1)
                {
                  if((x.getNodeName()).equals("fatherName"))
  								{
  									System.out.print("\nenter father name : ");
  									String newfn = in.readLine();
  									Element value = (Element)x;
  									value.setTextContent(newfn);
  									System.out.println("\nyour father name is got updated");
  									count = 0;
                    fn = 0;
  									break;
  								}
                }
                else if(bgs == 1)
                {
                  if((x.getNodeName()).equals("blood"))
  								{
  									System.out.print("\nenter your blood group : ");
  									String newbg = in.readLine();
  									Element value = (Element)x;
  									value.setTextContent(newbg);
  									System.out.println("\nyour blood group is got updated");
  									count = 0;
                    bgs = 0;
  									break;
  								}
                }
                else if(ad == 1)
                {
                  if((x.getNodeName()).equals("address"))
  								{
  									System.out.print("\nenter new address : ");
  									String newad = in.readLine();
  									Element value = (Element)x;
  									value.setTextContent(newad);
  									System.out.println("\nyour address is got updated");
  									count = 0;
                    ad = 0;
  									break;
  								}
                }
                else if(rg == 1)
                {
                  if((x.getNodeName()).equals("RegisterNum"))
  								{
  									System.out.print("\nenter new register number : ");
  									String newrg = in.readLine();
  									Element value = (Element)x;
  									value.setTextContent(newrg);
  									System.out.println("\nyour register number is got updated");
  									count = 0;
                    rg = 0;
  									break;
  								}
                }
							}
						}
            c.writexml(doc, file);
		  			break;
					}
					else
					{
						count=1;
					}
				}
			}
		  if(count==1)
			{
       if(the_end!=1)
       {
  			System.out.println("\nincorrecct password\n");
  			System.out.println("\nTry again!.\n");
  			c.reread(jsonobj.getString("Name"));
       }
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
  }
}
