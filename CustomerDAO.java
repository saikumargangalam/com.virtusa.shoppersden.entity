package com.virtusa.shoppersden.entity;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.protocol.Resultset;


public class CustomerDAO {
	Connection con;
	PreparedStatement pst;
    
	public CustomerDetails searchCustomer(String customerEmail)
	{
		con=DaoConnection.getConnection();
		String cmd= "select * from customer";
		CustomerDetails customer = null;
		
		
		try {
			pst=con.prepareStatement(cmd);
			
			ResultSet rs = pst.executeQuery();
			if(rs.next())
			{
				
				customer = new CustomerDetails();
				customer.setCustomerEmail(rs.getString("customerEmail"));
				customer.setCustomerPassword(rs.getString("customerPassword"));
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customer;
		
		
	}
	public String createCustomer(CustomerDetails customer)
	{
		con=DaoConnection.getConnection();
		String cmd="insert into customerDetails(customeeId,customerName,customerEmail,customerPassword,"
				+ "customerDOB,customerAddress,customerContact,customerState,customerCity)values(?,?,?,?,?,?,?,?,?) ";
		String res="";
		try {
			pst =con.prepareStatement(cmd);
			pst.setInt(1, customer.getCustomerId());
			pst.setString(2, customer.getCustomerName());
			pst.setString(3, customer.getCustomerEmail());
			pst.setString(4, customer.getCustomerPassword());
			java.sql.Date sqldate = new java.sql.Date(CustomerDetails.getCustomerDOB().getTime());
			
			pst.setDate(5, sqldate);
			pst.setString(6, customer.getCustomerAddress());
			pst.setLong(7, customer.getCustomerContact());
			pst.setString(8, customer.getCustomerState());
			pst.setString(9, customer.getCustomerCity());
			pst.executeUpdate();
			res="cutomer added successfully";
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(NullPointerException e) {
			e.printStackTrace();
		}
		return res;
		
	}
	
	public int generateCustomerId()
	{
		con=DaoConnection.getConnection();
		String cmd= "select case when max(customerId) is NULL"
				+ " THEN 1 ELSE Max(customerId)+1 END cid from customer";
		int customerId=0;
		
		try {
			pst = con.prepareStatement(cmd);
			ResultSet rs = pst.executeQuery();
			rs.next();
			customerId=rs.getInt("cid");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customerId;
		
	}
	
}
