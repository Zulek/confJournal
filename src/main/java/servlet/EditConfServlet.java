package servlet;/* Copyright © 2015 Oracle and/or its affiliates. All rights reserved. */

import com.mysql.jdbc.StringUtils;
import crud.ConferenceService;
import crud.DepartmentService;
import crud.EmployeeService;
import entity.Conference;
import entity.Department;
import entity.Employee;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet(
        name = "servlet.EditConfServlet",
        urlPatterns = {"/editConf"}
)
public class EditConfServlet extends HttpServlet {

    ConferenceService conferenceService = new ConferenceService();
    DepartmentService departmentService = new DepartmentService();
    EmployeeService employeeService = new EmployeeService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String conferenceId = req.getParameter("conferenceId");
        Conference conferenceResult = conferenceService.get(Long.valueOf(conferenceId));
        forwardConference(req, resp, conferenceResult);
    }

    private void forwardConference(HttpServletRequest req, HttpServletResponse resp, Conference conference)
            throws ServletException, IOException {
        String nextJSP = "/jsp/edit-conference.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        req.setAttribute("conferenceId", conference.getId());
        req.setAttribute("confTheme", conference.getTheme());

        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
        req.setAttribute("confDate", fm.format(conference.getDate()));
        req.setAttribute("confDepartment", conference.getDepartment());
        req.setAttribute("confResponsible", conference.getResponsible());
        req.setAttribute("confEmployeeList", conference.getEmployees());
        List<Department> departmentList = departmentService.getAllDepartments();
        req.setAttribute("departmentList", departmentList);
        List<Employee> employeeList = employeeService.getAllEmployees();
        req.setAttribute("employeeList", employeeList);
        dispatcher.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("edit") != null) {
            String conferenceId = req.getParameter("conferenceId");
            String themeName = req.getParameter("themeName");
            String date = req.getParameter("date");
            String department = req.getParameter("department");
            String responsible = req.getParameter("responsible");
            postEdited(req,resp,conferenceId,themeName,date,department,responsible);
        }
        else if (req.getParameter("add") != null) {
            String conferenceId = req.getParameter("conferenceId");
            String employee = req.getParameter("addEmployee");
            postAdded(req,resp,conferenceId,employee);
        }
        else if (req.getParameter("delete") != null) {
            String conferenceId = req.getParameter("conferenceId");
            String[] employees = req.getParameterValues("checkboxToDelete");
            postDeleted(req,resp,conferenceId,employees);
        }
    }

    private void postEdited(HttpServletRequest req, HttpServletResponse resp, String conferenceId, String themeName, String date, String department, String responsible)
            throws ServletException, IOException {
        Conference updatedConference = conferenceService.get(Long.valueOf(conferenceId));
        if (!StringUtils.isNullOrEmpty(themeName))
            updatedConference.setTheme(themeName);
        else{
            updatedConference.setTheme("_");
        }
        updatedConference.setResponsible(employeeService.get(Long.valueOf(responsible)));
        updatedConference.setDepartment(departmentService.get(Long.valueOf(department)));
        try {
            updatedConference.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(date));
        }
        catch(ParseException ignored){}
        conferenceService.update(updatedConference);
        resp.sendRedirect("editConf?conferenceId="+conferenceId);
    }

    private void postAdded(HttpServletRequest req, HttpServletResponse resp, String conferenceId, String employee)
            throws ServletException, IOException {
        Conference conferenceToAdd = conferenceService.get(Long.valueOf(conferenceId));
        Set<Employee> confEmployeeList = conferenceToAdd.getEmployees();
        if (confEmployeeList.add(employeeService.get(Long.valueOf(employee))) && !Objects.equals(employee,"1"))
            conferenceService.update(conferenceToAdd);
        resp.sendRedirect("editConf?conferenceId="+conferenceId);
    }

    private void postDeleted(HttpServletRequest req, HttpServletResponse resp, String conferenceId, String[] employees)
            throws ServletException, IOException {
        if (employees != null) {
            Conference conferenceToDeleteFrom = conferenceService.get(Long.valueOf(conferenceId));
            Set<Employee> confEmployeeList = conferenceToDeleteFrom.getEmployees();

            for (Iterator<Employee> iter = confEmployeeList.iterator(); iter.hasNext(); ) {
                Employee a = iter.next();

                if (Arrays.asList(employees).contains(String.valueOf(a.getId()))) {
                    iter.remove();
                }
            }

            conferenceService.update(conferenceToDeleteFrom);
        }
        resp.sendRedirect("editConf?conferenceId="+conferenceId);
    }


}
