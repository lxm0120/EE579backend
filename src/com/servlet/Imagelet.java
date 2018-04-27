package com.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;


@WebServlet("/Imagelet")
public class Imagelet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	public Imagelet() {
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
		String imageurl = requestParam.getString("imageurl");
		try {
			download(imageurl,userid);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
    private static void download(String urlString, String userid) throws Exception {       
        URL url = new URL(urlString);    
        URLConnection con = url.openConnection();       
        con.setConnectTimeout(5*1000);    
        InputStream is = con.getInputStream();
        String filepath="/root/training-images/"+userid;
    //    String filepath="D:\\eclipse\\java-neon\\eclipse\\workspace\\EE579backend\\images\\"+userid;
        // buffer 
        byte[] bs = new byte[1024*1024];       
        int len;
        int i=1;      
       File sf=new File(filepath);    
       if(!sf.exists()){    
           sf.mkdirs();    
       }
       System.out.println(filepath);
       
       String filename= userid+"_"+i+".jpg";
       File im= new File(filepath+"/"+filename);
       while (im.exists()){
    	   i=i+1;
    	   filename = userid+"_"+i+".jpg";
    	   im = new File(filepath+"/"+filename);
       }
       System.out.println(filename);
       OutputStream os = new FileOutputStream(sf.getPath()+"/"+filename);        
        while ((len = is.read(bs)) != -1) {    
          os.write(bs, 0, len);    
        }     
        os.close();    
        is.close();    
    }     
    
}    
