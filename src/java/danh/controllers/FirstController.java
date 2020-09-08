/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danh.controllers;

import danh.crawler.rideplus.RidePlusThread;
import danh.dao.AccessaryDAO;
import danh.dao.FrameSizeDAO;
import danh.db.Accessary;
import danh.db.Framesize;
import danh.xedap24h.XeDap24hThread;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import org.eclipse.persistence.jaxb.JAXBMarshaller;

/**
 *
 * @author apple
 */
public class FirstController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            System.out.println("Run");
            AccessaryDAO accessaryDAO = AccessaryDAO.getInstance();
            String listAccessary = accessaryDAO.getAllAccessary();
            request.setAttribute("LISTACCESSARY", listAccessary);
            System.out.println("DONE");
            response.getOutputStream().write(listAccessary.getBytes(StandardCharsets.UTF_8));
            
//        FrameSizeDAO framesizeDAO = new FrameSizeDAO();
//        for (int i = 44; i < 64; i++) {
//            Framesize framesize = new Framesize(i);
//            framesizeDAO.insert(framesize);
//        }
//        
//        XeDap24hThread xeDap24hThread = new XeDap24hThread(request.getServletContext());
//        xeDap24hThread.start();
//        RidePlusThread plusThread = new RidePlusThread(request.getServletContext());
//        plusThread.start();
        } catch (Exception e) {
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
