package com.servlet;
import java.io.IOException;
import java.sql.SQLException;
import java.io.BufferedReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.Service;

import net.sf.json.JSONObject;


@WebServlet("/Reglet")
public class Reglet extends HttpServlet{
	  
	private static final long serialVersionUID = 1L;

	public Reglet() {
		super();
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		System.out.println("Don not support GET method");
	 	
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	BufferedReader read = request.getReader();
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = read.readLine()) != null) {
			sb.append(line);
		}

		String req = sb.toString();	
		System.out.println(req);
		JSONObject requestParam = JSONObject.fromObject(req);
		String userid = requestParam.getString("userid");
		String macaddress = requestParam.getString("macaddress");
		String username = requestParam.getString("username");
		String imageurl = requestParam.getString("imageurl");
		String friends = requestParam.getString("friends");
		System.out.print("Succss"+":"+userid+","+macaddress+","+username+","+imageurl+","+friends);
		Service serv = new Service();
		boolean loged=false;
		try {
			loged = serv.register(userid, macaddress,username,imageurl,friends);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (loged) {
            System.out.print("Succss"+":"+userid+","+macaddress+","+username+","+imageurl+","+friends);
            
        } else {
            System.out.print("Failed");
        }
    }
}
