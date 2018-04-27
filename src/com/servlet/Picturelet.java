package com.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import com.openface.*;
import com.service.Service;


@WebServlet("/Picturelet")
public class Picturelet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public Picturelet() {
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
		JSONObject result=new JSONObject();
		String userid = requestParam.getString("userid");
		String picture = requestParam.getString("picture");
		String mac=null;
		try {
			Storage(userid, picture);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DoOpenFace oface=new DoOpenFace();
		String findid=oface.doOpenFace(userid);
		Service serv=new Service();
		try {
			mac=serv.searchmac(findid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (mac !=null)
		{
			result=serv.search(userid, mac);
		}
		if (result!=null){
			String rs = JSONObject.fromObject(result).toString();
	        System.out.println(rs);
	        response.setCharacterEncoding("UTF-8");
	        response.setContentType("application/json");
	        PrintWriter out = response.getWriter();
	        out.write(rs);
	        out.flush();
	        out.close();
		}
		
    }
    private static void Storage(String userid, String picture) throws Exception {    
           
        byte[] picturefile=Base64.getDecoder().decode(picture);   
        //String filepath="D:\\eclipse\\java-neon\\eclipse\\workspace\\EE579backend\\images\\picture";
      String filepath="/root/testing-image";
   
        // Folder 
       File sf=new File(filepath);    
       if(!sf.exists()){    
           sf.mkdirs();    
       }
       System.out.println(filepath);
       String filename= userid+"_picture"+".jpg";

       System.out.println(filename);
       OutputStream os = new FileOutputStream(sf.getPath()+"/"+filename);    
        
         os.write(picturefile);    
        os.flush(); 
        os.close();    

    }     
    
}
