package com.service;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.db.*;
import net.sf.json.JSONObject;

public class Service {
	public JSONObject search(String userid,String macaddress) {
		
        JSONObject result = new JSONObject();
        // SQL sentence
        String logSql = "select * from users where macaddress ='" + macaddress + "'";
        String username=null;
        DBManager sql = DBManager.createInstance();
        sql.connectDB();
        String relation=null;
		String friends1=null;
		String friends2=null;
		String mutualfriends=null;
		String id1=userid;
		String id2=null;
		String[] friend1= null;
		String[] friend2= null;
		Boolean b1=false;
		String mutualfriend=null;
        try {
            ResultSet rs = sql.executeQuery(logSql);
                      
            if (rs.next()) {
            	id2=rs.getString("userid");
            	username=rs.getString("username");
            	b1=true;
            }
            if (!b1){
            	return null;
            }
            String lookupSql1="select friends from users where userid='"+id1+"'";
        	String lookupSql2="select friends from users where userid='"+id2+"'";
                /*result.put("username", rs.getString("username"));
                result.put("imageurl",rs.getString("imageurl"));
                result.put("friends", rs.getString("friends"));*/
            	
                
            	
            //rs.last();
            ResultSet rett=sql.executeQuery(lookupSql1);
            	
         	if (rett.next()){
         	    friends1=rett.getString("friends");
         	    	
         	}
         	//rett.last();
         	   
         	 
   	    	ResultSet rettt=sql.executeQuery(lookupSql2);
   	    	if(rettt.next()){
   	    		friends2=rettt.getString("friends");
   	    	}
   	    	//rettt.last();
   	    	if(friends1==null||friends2==null){
   	    		relation="you don't have mutual friends";
   	    	}else{
   	    		friend1 = friends1.split("-");
   	    		for (int i=0;i<friend1.length;i++){
   	    			System.out.println(friend1[i]);
   	    			System.out.println(id2);
   	    			if (id2.equals(friend1[i])){
   	    				relation="friend of you";
   	    				result.put("userid", id2);
   	    				result.put("username", username);
   	    	            result.put("relation",relation);
   	    	            System.out.print("Succss");
   	    	            	
   	    	            sql.closeDB();
   	    	                
   	    	            return result;
   	    			}
   	    		}
   	    		friend2=friends2.split("-");
   	    		ResultSet[] retttt=new ResultSet[friend1.length];
   	    		for (int i=0;i<friend1.length;i++){
   	    			for (int j=0;j<friend2.length;j++){
   	    				if (friend1[i].equals(friend2[j])){
   	    					System.out.println(friend1[i]);
   	    					String lookup="select username from users where userid='"+friend1[i]+"'";
   	    					retttt[i]=sql.executeQuery(lookup);
   	    					if(retttt[i].next()){
   	    		   	    		mutualfriend=retttt[i].getString("username");
   	    		   	    		System.out.println(mutualfriend);
   	    		   	    		//retttt[i].last();
   	    		   	    	}
   	    		   	    	
   	    		   	    	if (!(mutualfriend==null))
   	    					{
   	    		   	    		if(mutualfriends==null)
   	    					
   	    		   	    		{
   	    		   	    			mutualfriends=mutualfriend;
   	    		   	    			mutualfriend=null;
   	    		   	    			System.out.println("aaa"+mutualfriends);
   	    		   	    		}else{
   	    		   	    			mutualfriends=mutualfriends+","+mutualfriend;
   	    		   	    			mutualfriend=null;
   	    		   	    		}
   	    					}
   	    		   	    }
   	    			}
   	    		}
   	    		if (mutualfriends!=null){
   	    			relation="you have mutual friends:"+mutualfriends;
   	    		}else{
   	    			relation="you don't have mutual friends";
   	    		}
   	    	}   
        	   
            result.put("userid", id2);
            result.put("username", username);
            result.put("relation",relation);
            System.out.print("Succss");
            	
            sql.closeDB();
                
            return result;
            
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print("Failed");
        }
        sql.closeDB();
        return null;
    }
	public String searchmac(String userid) throws SQLException{
		String lookupsql="select macaddress from users where userid='"+userid+"'";
		
		DBManager sql = DBManager.createInstance();
        sql.connectDB();
        ResultSet rett=sql.executeQuery(lookupsql);
        if (rett.next()){
        	return rett.getString("macaddress");
        }
        return null;
	}
    public Boolean register(String userid, String macaddress,String username,String imageurl,String friends) throws SQLException {
    
        String regSql = "insert into users values('"+ userid+ "','"+ macaddress+ "','"+ username+ "','"+ imageurl+ "','"+ friends+"') ";
        String lookupSql="select * from users where userid='"+userid+"'";
        String updatSql="update users set macaddress='"+macaddress+"',username='"+username+"',imageurl='"+imageurl+"',friends ='"+friends+"'where userid ='"+userid+"'";
        DBManager sql = DBManager.createInstance();
        sql.connectDB();
        ResultSet rett=sql.executeQuery(lookupSql);
        if (!(rett.next())){
        	int ret = sql.executeUpdate(regSql);
        	if (ret != 0) {
        		sql.closeDB();
        		return true;
        	}
        }else{
        	System.out.print(updatSql);
        	int ret = sql.executeUpdate(updatSql);
        	if (ret != 0) {
        		sql.closeDB();
        		return true;
        	}
        }
        sql.closeDB();
        
        return false;
    }
   
}
