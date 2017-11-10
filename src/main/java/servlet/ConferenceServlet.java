package servlet;/* Copyright © 2015 Oracle and/or its affiliates. All rights reserved. */

import crud.ConferenceService;
import crud.DepartmentService;
import crud.EmployeeService;
import entity.Conference;
import entity.Department;
import entity.Employee;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.RequestDispatcher;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
        name = "servlet.ConferenceServlet",
        urlPatterns = {"/conference"}
)
public class ConferenceServlet extends HttpServlet {
    DepartmentService departmentService = new DepartmentService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EmployeeService employeeService = new EmployeeService();
        ConferenceService conferenceService = new ConferenceService();

        checkIfEmpty(departmentService,employeeService);

        String addForm = req.getParameter("addForm");
        addNewConferenceIfClicked(addForm,employeeService,departmentService,conferenceService);

        String theme = req.getParameter("themeName");
        String fromDate = req.getParameter("fromDate");
        String toDate = req.getParameter("toDate");
        String department = req.getParameter("department");
        String participant = req.getParameter("participant");

        if(theme!=null || department!=null || fromDate!=null || toDate!=null || participant!=null) {
            List result = conferenceService.getFilteredConferences(theme,fromDate,toDate,department,participant);
            forwardListConferences(req, resp, result,departmentService,employeeService);
        }else{
            List<Conference> result = conferenceService.getAllConferences();
            forwardListConferences(req, resp, result,departmentService,employeeService);
        }
    }

    private void checkIfEmpty(DepartmentService departmentService, EmployeeService employeeService){
        if (departmentService.getAllDepartments().size()==0 && employeeService.getAllEmployees().size()==0){
            Department blankDepartment = new Department("Не указано");
            Department addedDepartment = departmentService.add(blankDepartment);
            employeeService.add(new Employee("Не указано", "Не указано","Не указано",new Date(),addedDepartment));
        }
    }

    private void forwardListConferences(HttpServletRequest req, HttpServletResponse resp, List conferenceList, DepartmentService departmentService, EmployeeService employeeService)
            throws ServletException, IOException {
        String nextJSP = "/jsp/list-conference.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        req.setAttribute("conferenceList", conferenceList);
        List<Department> departmentList = departmentService.getAllDepartments();
        req.setAttribute("departmentList", departmentList);
        List<Employee> employeeList = employeeService.getAllEmployees();
        req.setAttribute("employeeList", employeeList);
        dispatcher.forward(req, resp);
    }

    private void addNewConferenceIfClicked(String addForm, EmployeeService employeeService, DepartmentService departmentService, ConferenceService conferenceService){
        if(addForm!=null){
            Conference conference = new Conference("New Conference", employeeService.get(1), departmentService.get(1), new Date(), new HashSet<>());
            conferenceService.add(conference);
        }
    }

}
