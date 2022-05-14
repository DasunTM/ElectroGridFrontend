package com;

import java.sql.*;

public class Issue {

    public Connection connect(){
    	
        //database connection details
        String dbDriver = "com.mysql.jdbc.Driver";
        String dbURL = "jdbc:mysql://localhost:3307/";
        String dbName = "test";
        String dbUsername = "root";
        String dbPassword = "";
        
        Connection conn = null;
        
        try {
        	//connecting the database
        	Class.forName(dbDriver);
        	conn = DriverManager.getConnection(dbURL+dbName, dbUsername, dbPassword);
        	
        } catch(Exception e) {
        	e.printStackTrace();
        }
        
        return conn;
    }
    
    
    //method to insert data
    public String insertIssue(String code, String name, String price, String desc) {
    	Connection conn = connect();
    	
    	String Output = "";
    	
    	try {
        	if (conn == null) {
        		return "Database connection error";
        	}
        	
        	//SQL query
        	String query = "INSERT INTO issues (issueCode,empAllocated,systemID,issueDesc) VALUES (?,?,?,?)";
        	
        	//binding data to SQL query
        	PreparedStatement preparedStatement = conn.prepareStatement(query);
        	preparedStatement.setString(1, code);
        	preparedStatement.setString(2, name);
        	preparedStatement.setDouble(3, Double.parseDouble(price));
        	preparedStatement.setString(4, desc);
        	
        	//execute the SQL statement
        	preparedStatement.execute();
        	conn.close();

			String newIssues = readIssues(); 
			Output = "{\"status\":\"success\", \"data\": \"" + newIssues + "\"}";
        	
    	} catch(Exception e) {
			Output = "{\"status\":\"error\", \"data\": \"Failed to insert the issue\"}";
    		System.err.println(e.getMessage());
    	}
    	
    	return Output;
    }
    
    //method to update data
    public String updateIssue(String issueID, String code, String name, String price, String desc) {
    	Connection conn = connect();
    	
    	String Output = "";
    	
    	try {
        	if (conn == null) {
        		return "Database connection error";
        	}
        	
        	//SQL query
        	String query = "UPDATE issues SET issueCode = ?,empAllocated = ?,systemID = ?,issueDesc = ? WHERE issueID = ?";
        	
        	//binding data to SQL query
        	PreparedStatement preparedStatement = conn.prepareStatement(query);
        	preparedStatement.setString(1, code);
        	preparedStatement.setString(2, name);
        	preparedStatement.setDouble(3, Double.parseDouble(price));
        	preparedStatement.setString(4, desc);
        	preparedStatement.setInt(5, Integer.parseInt(issueID));
        	
        	//execute the SQL statement
        	preparedStatement.executeUpdate();
        	conn.close();
        	
        	String newIssues = readIssues(); 
      		Output = "{\"status\":\"success\", \"data\": \"" + newIssues + "\"}";
        	
    	} catch(Exception e) {
    		Output = "{\"status\":\"error\", \"data\":\"Failed to update the issue.\"}"; 
    		System.err.println(e.getMessage());
    	}
    	
    	return Output;
    }
    
    
    //method to read data
    public String readIssues() {
    	Connection conn = connect();
    	
    	String Output = "";
    	
    	try {
        	if (conn == null) {
        		return "Database connection error";
        	}
        	
        	//SQL query
        	String query = "SELECT * FROM issues";
        	
        	//executing the SQL query
        	Statement statement = conn.createStatement();
        	ResultSet resultSet = statement.executeQuery(query);
        	
        	// Prepare the HTML table to be displayed
    		Output = "<table class=\"table table-dark\" border='1'><tr ><th>Issue Code</th>" +"<th>Issue Name</th><th>Issue Price</th>"
    		+ "<th>Issue Description</th>"
    		+ "<th>Update</th><th>Remove</th></tr>";
        	
        	while(resultSet.next()) {
        		String issueID = Integer.toString(resultSet.getInt("issueID"));
        		String issueCode = resultSet.getString("issueCode");
        		String empAllocated = resultSet.getString("empAllocated");
        		String systemID = Double.toString(resultSet.getDouble("systemID"));
        		String issueDesc = resultSet.getString("issueDesc");
        		
        		// Add a row into the HTML table
        		Output += "<tr class=\"table-light\" style=\"color: black; \"><td>" + issueCode + "</td>"; 
        		Output += "<td>" + empAllocated + "</td>"; 
        		Output += "<td>" + systemID + "</td>"; 
        		Output += "<td>" + issueDesc + "</td>";
        		
        		// buttons
        		Output += "<td>"
						+ "<input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-sm btn-secondary' data-issueid='" + issueID + "'>"
						+ "</td>" 
        				+ "<td>"
						+ "<input name='btnRemove' type='button' value='Remove' class='btn btn-sm btn-danger btnRemove' data-issueid='" + issueID + "'>"
						+ "</td></tr>";
        	}

        	conn.close();
        	
        	// Complete the HTML table
        	Output += "</table>";
        	
    	} catch(Exception e) {
    		Output = "Failed to read the issues";
    		System.err.println(e.getMessage());
    	}
    	
    	return Output;
    }
    
    //method to delete data
    public String deleteIssue(String issueID) {
    	String Output = "";
    	Connection conn = connect();
    	
    	try {
        	if (conn == null) {
        		return "Database connection error";
        	}
        	
        	//SQL query
        	String query = "DELETE FROM issues WHERE issueID = ?";
        	
        	//binding data to the SQL query
        	PreparedStatement preparedStatement = conn.prepareStatement(query);
        	preparedStatement.setInt(1, Integer.parseInt(issueID));
        	
        	//executing the SQL statement
        	preparedStatement.execute();
        	conn.close();
        	
        	String newIssues = readIssues(); 
      		Output = "{\"status\":\"success\", \"data\": \"" + newIssues + "\"}"; 
        	
    	} catch(Exception e) {
			Output = "{\"status\":\"error\", \"data\":\"Failed to delete the issue.\"}";
    		System.err.println(e.getMessage());
    	}
    	return Output;
    }
}
