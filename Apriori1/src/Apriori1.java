import java.sql.*;
import java.util.*;

class Connec       // class to obtain connection
{
	private static Connection connect;
	public static Connection getConnection()
	{
		if (connect == null)
		{
			try
			{
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/database","root",""); //establishing connection
				return connect;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			return connect;
		}
		return null;
	}
}
public class Apriori1
{
	public static void main(String[] args)      
	{
		Connection con = Connec.getConnection(); // making connection
		PreparedStatement statement;
		ResultSet rs;
		ArrayList<HashSet<String>> trans= new ArrayList<HashSet<String>>();;
		HashSet<String> Item_Set ;
		try
		{
			Scanner sc = new Scanner(System.in);  //taking the day input
			System.out.println("Select a day");
			System.out.println("1. Day 1");
			System.out.println("2. Day 2");
			System.out.println("3. Day 3");
			System.out.println("4. Day 4");
			System.out.println("5. Day 5");
			if(sc.hasNext())
			{
				String db = sc.next();
				if(db!=null && db!="")
				{
					switch (db) //using switch case to select day
					{
					case "1":
						statement = con.prepareStatement("SELECT Items FROM day_1");
						rs = statement.executeQuery();
						while(rs.next())
						{
							String transList =rs.getString(1);
							Item_Set = new HashSet<String>();
							String[] key = transList.split(",");
							for(int i = 0; i < key.length; i++)
								Item_Set.add(key[i].trim());
							trans.add(Item_Set);
						}
						break;

					case "2":
						statement = con.prepareStatement("SELECT Items FROM day_2");
						rs = statement.executeQuery();
						while(rs.next())
						{
							String transList =rs.getString(1);
							Item_Set = new HashSet<String>();
							String[] key = transList.split(",");
							for(int i = 0; i < key.length; i++)
								Item_Set.add(key[i].trim());
							trans.add(Item_Set);
						}
						break;

					case "3":
						statement = con.prepareStatement("SELECT Items FROM day_3");
						rs = statement.executeQuery();
						while(rs.next())
						{
							String transList =rs.getString(1);
							Item_Set = new HashSet<String>();
							String[] key = transList.split(",");
							for(int i = 0; i < key.length; i++)
								Item_Set.add(key[i].trim());
							trans.add(Item_Set);
						}
						break;

					case "4":
						statement = con.prepareStatement("SELECT Items FROM day_4");
						rs = statement.executeQuery();
						while(rs.next())
						{
							String transList =rs.getString(1);
							Item_Set = new HashSet<String>();
							String[] key = transList.split(",");
							for(int i = 0; i < key.length; i++)
								Item_Set.add(key[i].trim());
							trans.add(Item_Set);
						}
						break;

					case "5":
						statement = con.prepareStatement("SELECT Items FROM day_5");
						rs = statement.executeQuery();
						while(rs.next())
						{
							String transList =rs.getString(1);
							Item_Set = new HashSet<String>();
							String[] key = transList.split(",");
							for(int i = 0; i < key.length; i++)
								Item_Set.add(key[i].trim());
							trans.add(Item_Set);
						}
						break;

					default: // default case
						System.out.println("Invalid selection");  

					}
				}
			}
		}
		catch (SQLException e) { }
		Scanner in = new Scanner(System.in);
		System.out.println("Enter minimum support-");
		int support = Integer.parseInt(in.nextLine());
		System.out.println("Enter mininum confidence-");
		int conf = Integer.parseInt(in.nextLine());
		Apriori3 res = new Apriori3(trans, support,conf);
		res.flow();
	}
}

