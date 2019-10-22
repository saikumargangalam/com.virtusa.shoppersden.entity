package com.virtusa.shoppersden.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class ProductDAO {

	Connection con;
	PreparedStatement pst;
	
	public ProductDetails searchProductDetails(int productId)
	{
		con=DaoConnection.getConnection();
		String cmd = "select * from product where productid=?";
		ProductDetails product=null;
		try {
			pst=con.prepareStatement(cmd);
			pst.setInt(1, productId);
			ResultSet rs = pst.executeQuery();
			if(rs.next())
			{
				product=new ProductDetails();
				product.setProductId(rs.getInt("productId"));
				product.setProductName(rs.getString("productname"));
				product.setProductPrice(rs.getDouble("productPrice"));
				product.setProductImage(rs.getString("productImage"));
				product.setProductDescription(rs.getString("productDescription"));
				product.setProductStock(rs.getInt("productStock"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return product;
	}
	
	public String createProduct(ProductDetails product)
	{
		con = DaoConnection.getConnection();
		String cmd ="insert into product(productId,productName,productPrice,productImage,productDescription,productstock) values(?,?,?,?,?,?)";
		
		String result="";
		try {
			pst=con.prepareStatement(cmd);
			pst.setInt(1, product.getProductId());
			pst.setString(2, product.getProductName());
			pst.setDouble(3, product.getProductPrice());
			pst.setString(4, product.getProductImage());
			pst.setString(5, product.getProductDescription());
			pst.setInt(6, product.getProductStock());
			pst.executeUpdate();
			result ="product added successfully....";
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	
	}
	
	public int generateproductid()
	{
		con= DaoConnection.getConnection();
		String cmd = "select case when max(productId) is NULL  THEN 1 ELSE Max(productId)+1 END pid from product";
		
		int productId=0;
		  
		  try {
			pst = con.prepareStatement(cmd);
			ResultSet rs = pst.executeQuery();
			rs.next();
			productId= rs.getInt("pid");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return productId;
		  
		}
	
}

