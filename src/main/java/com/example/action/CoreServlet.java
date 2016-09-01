package com.example.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.service.CoreService;
import com.example.util.WeiXinUtil;

@WebServlet(name = "core", urlPatterns = { "/core" })
public class CoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CoreService coreService = new CoreService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");
		File file = new File(request.getSession().getServletContext().getRealPath("/info.txt"));
		BufferedWriter bfw = new BufferedWriter(new FileWriter(file));
		bfw.write(signature + ":" + timestamp + ":" + nonce + ":" + echostr);
		bfw.flush();
		bfw.close();
		PrintWriter out = response.getWriter();
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		if (WeiXinUtil.checkSignature(signature, timestamp, nonce)) {
			out.println(echostr);
		}
		out.close();
		out = null;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置字符编码
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter pw = response.getWriter();
		String result = coreService.processRequest(request);
		pw.println(result);
		
		pw.flush();
		pw.close();
	}

}
