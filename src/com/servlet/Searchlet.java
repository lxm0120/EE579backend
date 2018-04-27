package com.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import com.service.*;
@WebServlet("/Searchlet")
public class Searchlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        String userid = request.getParameter("userid");
    	String macaddress = request.getParameter("macaddress");
        System.out.println(userid+"--" + macaddress);

        Service serv = new Service();

        JSONObject responsePara = serv.search(userid,macaddress);
        
        String rs = JSONObject.fromObject(responsePara).toString();
        // response to app
        System.out.println(rs);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.write(rs);
        out.flush();
        out.close();
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
