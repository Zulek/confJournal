<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf-8" %>
<html>
    <head>
       <link rel="stylesheet" href="../css/bootstrap.min.css"/>
       <script src="../js/bootstrap.min.js"></script>
    </head>

    <body>
        <div class="container">
            <h2>Совещания</h2>
            <!--Search Form -->
            <form action="/conference" method="get" id="filterForm" role="form">

                <div class="form-group col-xs-5">
                Введите тему совещания:
                    <input type="text" name="themeName" id="themeName" placeholder="тема" />
                </div>

                <div class="form-group col-xs-5">
                Выберите проведения с <input type="date" name="fromDate"> по <input type="date" name="toDate">
                </div>
                <div class="form-group col-xs-5">
                Выберите подразделение:
                <select name="department">
                    <c:choose>
                        <c:when test="${not empty departmentList}">
                            <c:forEach var="department" items="${departmentList}">
                                <option value=${department.id}>${department.name}</option>
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
                Выберите участника:
                <select name="participant">
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
                </div>
                <button type="submit" class="btn btn-info">
                    <span class="glyphicon glyphicon-search"></span> Применить фильтры
                </button>
            </form>
            <form action="/conference" method="get" id="clearForm" role="form">
                <button type="submit" class="btn btn-info">
                    <span class="glyphicon glyphicon-search"></span> Показать всё
                </button>
                <br></br>
                <br></br>


                <c:choose>
                    <c:when test="${not empty conferenceList}">
                        <table  class="table table-striped">
                            <thead>
                                <tr>
                                    <td>Время</td>
                                    <td>Тема</td>
                                    <td>Имя</td>
                                    <td>Подразделение</td>
                                    <td>Состав</td>
                                </tr>
                            </thead>
                            <c:forEach var="conference" items="${conferenceList}">
                                <tr>
                                    <td>${conference.date}</td>
                                    <td>
                                    <c:url var="editpage" value="editConf">
                                        <c:param name="conferenceId" value="${conference.id}" />
                                    </c:url>
                                    <a href="${editpage}">${conference.theme}</a>
                                    </td>
                                    <td>${conference.responsible}</td>
                                    <td>${conference.department}</td>
                                    <td>${conference.employees.size()}</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <br>
                        <div class="alert alert-info">
                            Пусто
                        </div>
                    </c:otherwise>
                </c:choose>
            </form>
                <form action="/conference" method="get" id="addForm" role="form">
                <button type="submit" class="btn btn-info">
                    <input type="text" name="addForm" value="addForm" hidden / >
                    <span class="glyphicon glyphicon-search"></span> Добавить совещание
                </button>
                </form>
        </div>
    </body>
</html>