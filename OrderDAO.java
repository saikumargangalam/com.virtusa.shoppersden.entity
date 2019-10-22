package com.virtusa.shoppersden.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;




public class OrderDAO {
	
	Connection con;
	PreparedStatement pst;
	
	public String orderPlace(int productId,int qtyOrd) {
        con=DaoConnection.getConnection();
        String res="";
        ProductDetails product= new ProductDAO().searchProductDetails(productId);
        CustomerDetails customer = new CustomerDetails();
        OrderDetails order = new OrderDetails();
        if(order!=null) {
            int orderqty=product.getProductStock();
          
            if(orderqty>=qtyOrd) {
                String cmd="insert into Orders(orderId,productId,customerId,qtyord,billamt)values (?,?,?,?,?)";
                try {
                    int orderid=generateOrderId();
                    pst=con.prepareStatement(cmd);
                    int ordId;
                    pst.setInt(1, orderid);
                    int pid= product.getProductId();
                    pst.setInt(2,pid);
                    int cid= customer.getCustomerId();
                    pst.setInt(3, cid);
                    int qtyord=order.getQtyOrd();
                    pst.setInt(4, qtyOrd );
                    
                    double bill=qtyOrd*(product.getProductPrice());
                    pst.setDouble(5,bill);
                    pst.executeUpdate();
                    cmd="insert into Transaction(transactionId,orderTotalAmount)values (?,?)";
                    pst=con.prepareStatement(cmd);
                    pst.setInt(1,new TransactionDAO().generateTransactionid());
                    double orderTotalAmount=bill;
                    pst.setDouble(2, orderTotalAmount);
                    pst.executeUpdate();
                    res="order placed....";
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    res=e.getMessage();
                }
              
            }else {
                res="insufficient quantity";
            }
        }
        return res;
    }
	public int generateOrderId()
	{
		con=DaoConnection.getConnection();
		String cmd="select case when max(orderId) is NULL then 1 else max(orderId)+1 end ordid from orders";
		int orderId=0;
		try {
			pst= con.prepareStatement(cmd);
			ResultSet rs = pst.executeQuery();
			rs.next();
			orderId =rs.getInt("ordid");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orderId;
		
	}

}
