<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf-8" %>
<html>
    <head>
       <link rel="stylesheet" href="../css/bootstrap.min.css"/>
       <script src="../js/bootstrap.min.js"></script>
    </head>

    <body>
        <div class="container">
            <h2>Редактировать совещание</h2>
            <form action="/editConf" method="post" id="editForm" role="form">
                <input type="text" hidden name="conferenceId" id="conferenceId" value="${conferenceId}"/ >
                  <div class="form-group col-xs-5">
                Тема совещания:
                    <input type="text" name="themeName" id="themeName" value="${confTheme}"/ >
                </div>

                <div class="form-group col-xs-5">
                Время проведения <input type="date" name="date" value="${confDate}">
                </div>

                <div class="form-group col-xs-5">
                Подразделение:
                <select name="department">
                    <c:choose>
                        <c:when test="${not empty departmentList}">
                            <c:forEach var="department" items="${departmentList}">
                                <option value=${department.id} ${department.id == confDepartment.id ? 'selected' : ''} >${department.name}</option>
                            </c:forEach>
                          </c:when>
                          <c:otherwise>
                               <br>
                               <option value="0">Подразделения не найдены</option>
                           </c:otherwise>
                        </c:choose>
                </select>
                </div>

                <div class="form-group col-xs-5">
                Ответственный :
                <select name="responsible">
                    <c:choose>
                        <c:when test="${not empty employeeList}">
                            <c:forEach var="employee" items="${employeeList}">
                                <option value=${employee.id} ${employee.id == confResponsible.id ? 'selected' : ''} >${employee.name}</option>
                            </c:forEach>
                          </c:when>
                          <c:otherwise>
                               <br>
                               <option value="0">Рабочие не найдены</option>
                           </c:otherwise>
                        </c:choose>
                </select>
                </div>

                <button type="submit" name="edit" value="edit" class="btn btn-info">
                    <span class="glyphicon glyphicon-search"></span> Сохранить
                </button>
            </form>

            <!--Employees List-->
            <form action="/editConf" method="post" id="deleteForm" role="form">
                <input type="text" hidden name="conferenceId" id="conferenceId" value="${conferenceId}"/ >

            <input type="hidden" name="formType" values="delete"/>
                <c:choose>
                    <c:when test="${not empty confEmployeeList}">
                        <table  class="table table-striped">
                            <thead>
                                <tr>
                                    <td>Имя</td>
                                    <td>Дата рождения</td>
                                    <td>Подразделение</td>
                                </tr>
                            </thead>
                            <c:forEach var="employee" items="${confEmployeeList}">
                                    <td>${employee.surname} ${employee.name} ${employee.patronomyc}</td>
                                    <td>${employee.birthDate}</td>
                                    <td>${employee.department}</td>
                                    <td><input type="checkbox" name="checkboxToDelete" value="${employee.id}"> Удалить</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <br>
                        <div class="alert alert-info">
                            Not found
                        </div>
                    </c:otherwise>
                </c:choose>
                <div class="form-group col-xs-5">
                <button type="submit" name="delete" value="delete" class="btn btn-info">
                    <span class="glyphicon glyphicon-search"></span> Удалить отмеченные
                </button>
                </div>
            </form>


                <form action="/editConf" method="post" id="addForm" role="form">
                    <input type="text" hidden name="conferenceId" id="conferenceId" value="${conferenceId}"/ >

                <div class="form-group col-xs-5">
                Участник:
                <select name="addEmployee">
                    <c:choose>
                        <c:when test="${not empty employeeList}">
                            <c:forEach var="employee" items="${employeeList}">
                                <option value=${employee.id}>${employee.name}</option>
                            </c:forEach>
                          </c:when>
                          <c:otherwise>
                               <br>
                               <option value="0">Рабочие не найдены</option>
                           </c:otherwise>
                        </c:choose>
                </select>
                <button type="submit" name="add" value="add" class="btn btn-info">
                    <span class="glyphicon glyphicon-search"></span> Добавить
                </button>
                </div>
                </form>


                <form action="/conference" method="get">
                                    <button type="submit" class="btn btn-info">
                                        <span class="glyphicon glyphicon-search"></span>Выход
                                    </button>
                </form>

        </div>
    </body>
</html>