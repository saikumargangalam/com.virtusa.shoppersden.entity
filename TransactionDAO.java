package com.virtusa.shoppersden.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.protocol.Resultset;
public class TransactionDAO {
	public String UpdateTransaction()
    {
        Connection con = DaoConnection.getConnection();
        int id = new TransactionDAO().search();
        String rs="";
        String cmd = "update transactions set status ='complete' where transactionId=?";
        PreparedStatement pst;
        try {
            pst = con.prepareStatement(cmd);
            pst.setInt(1,id);
            pst.executeUpdate();
            rs = "payment completed";
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
             //logger.error("error with SQL", e);
        }
        return rs;
    }
    public int search()
    {
        Connection con = DaoConnection.getConnection();
        int id=0;
        String cmd ="select max(transactionId) as tid from transactions";
        PreparedStatement pst;
        try {
            pst = con.prepareStatement(cmd);
            ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
            id = rs.getInt("tid");
            }
            } catch (SQLException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
             //logger.error("error with SQL", e);
        }
        return id;
    }

	public int generateTransactionid()
	{
		Connection con = DaoConnection.getConnection();
		PreparedStatement pst;
		int transactionId=0;
		String cmd="select case when max(transactionId) is NULL then 1 "
				+ "else max(transactionId)+1 end tid from transaction";
		try {
			pst = con.prepareStatement(cmd);
			ResultSet rs = pst.executeQuery();
			rs.next();
			transactionId=rs.getInt("tid");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return transactionId;
	
	}
	
}

