package com;

import java.sql.*;

public class item {

	public Connection connect() {
		Connection con = null;

		try {
			 Class.forName("com.mysql.cj.jdbc.Driver");
			 con= DriverManager.getConnection("jdbc:mysql://localhost:3306/paf_lab_03",
			 "root", "");
			// For testing
			System.out.print("Successfully connected");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}

	// insert method
	public String insertItem(String code, String name, String price, String desc) {
		Connection con = connect();
		String output = "";
		if (con == null) {
			return "Error while connecting to the database";
		}

		// create a prepared statement
		String query = " insert into items (`itemID`,`itemCode`,`itemName`,`itemPrice`,`itemDesc`)"
				+ " values (?, ?, ?, ?, ?)";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = con.prepareStatement(query);

			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, code);
			preparedStmt.setString(3, name);
			preparedStmt.setDouble(4, Double.parseDouble(price));
			preparedStmt.setString(5, desc);

			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (SQLException e) {
			output = "Error while inserting";
			System.err.println(e.getMessage());
		}
		// binding values

		return output;
	}

	// read items
	public String readItems() {
		String output = "";

		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}

			// Prepare the html table to be displayed
			output = "<table border='1'>" + "<tr><th>Item Code</th><th>Item Name</th><th>Item Price</th>"
					+ "<th>Item Description</th><th>Update</th><th>Remove</th></tr>";

			String query = "select * from items";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String itemID = Integer.toString(rs.getInt("itemID"));
				String itemCode = rs.getString("itemCode");
				String itemName = rs.getString("itemName");
				String itemPrice = Double.toString(rs.getDouble("itemPrice"));

				String itemDesc = rs.getString("itemDesc");
				// Add a row into the html table
				output += "<tr><td>" + itemCode + "</td>";
				output += "<td>" + itemName + "</td>";
				output += "<td>" + itemPrice + "</td>";
				output += "<td>" + itemDesc + "</td>";
				// buttons
				output += "<td>" + "<form method='post' action='items.jsp'>"
						+ "<input name='btnUpdate' type='submit' value='Update'></td>"
						+ "<input name='update_itemID' type='hidden' value='" + itemID + "'>" + "</form>" + "<td>"
						+ "<form method='post' action='items.jsp'>"
						+ "<input name='btnRemove' type='submit' value='Remove'>"
						+ "<input name='itemID' type='hidden' value='" + itemID + "'>" + "</form></td></tr>";
			}

		} catch (Exception e) {
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	// delete

	public String removeItem(int itemID) {
		String output = "";

		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}

			String query = "delete from items where itemID = ?";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, itemID);
			stmt.executeUpdate();
			output = "deleted!";

		} catch (Exception e) {
			output = "Error while updating the items.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	// delete

	public String updateItem(int itemID, String code, String name, String price, String desc) {
		Connection con = connect();
		String output = "";
		if (con == null) {
			return "Error while connecting to the database";
		}

		// create a prepared statement
		String query = " update items set itemCode= ? , itemName = ? , itemPrice = ? , itemDesc = ?  where itemID = ? ";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = con.prepareStatement(query);

			preparedStmt.setString(1, code);
			preparedStmt.setString(2, name);
			preparedStmt.setDouble(3, Double.parseDouble(price));
			preparedStmt.setString(4, desc);
			preparedStmt.setInt(5, itemID);

			preparedStmt.executeUpdate();
			con.close();
			output = "updated successfully";
		} catch (SQLException e) {
			output = "Error while inserting";
			System.err.println(e.getMessage());
		}
		// binding values

		return output;
	}

	public String[] readSingleItems(int itemID) {
		String output[] = new String[4];

		try {
			Connection con = connect();
			if (con == null) {
				System.err.println("Error while connecting to the database for reading.");
			}

			String query = "select * from items where itemID = ? ";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, itemID);

			ResultSet rs = stmt.executeQuery();
			// iterate through the rows in the result set
			while (rs.next()) {
				output[0] = (rs.getString("itemCode"));
				output[1] = (rs.getString("itemName"));
				output[2] = (rs.getString("itemPrice"));
				output[3] = (rs.getString("itemDesc"));
			}

		} catch (Exception e) {
			// output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}

		return output;
	}

}