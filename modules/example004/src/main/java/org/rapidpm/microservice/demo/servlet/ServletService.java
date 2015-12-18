package org.rapidpm.microservice.demo.servlet;

import org.rapidpm.microservice.demo.service.BusinessModule;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by svenruppert on 28.07.15.
 */
@WebServlet(urlPatterns = "/servletservice")
public class ServletService extends HttpServlet {

  @Inject BusinessModule businessModule;

  @Override
  protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
    final String txt = req.getParameter("txt");
    final PrintWriter writer = resp.getWriter();
    writer.write(businessModule.doWork(txt));
    writer.close();
  }
}
