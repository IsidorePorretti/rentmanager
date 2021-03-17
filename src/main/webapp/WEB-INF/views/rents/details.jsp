<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <!-- Left side column. contains the logo and sidebar -->
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">

            <div class="row">
                <div class="col-md-4">

                    <!-- Profile Image -->
                    <div class="box box-primary">
                        <div class="box-body box-profile">
                            <h3 class="profile-reservation text-center"> 
                              Id # <c:forEach items="${reservations}" var="reservation">
                                    	${reservation.id}
                                    	</c:forEach> 
		                                  
							</h3>
							
                            <ul class="list-group list-group-unbordered">
                                <li class="list-group-item">
                                    <b>Date de début</b> <a class="pull-right">
                                    	<c:forEach items="${reservations}" var="reservation">
                                    	${reservation.debut}
                                    	</c:forEach> 
                                    </a>
                                </li>
                                <li class="list-group-item">
                                    <b>Date de fin</b> <a class="pull-right">
                                    	<c:forEach items="${reservations}" var="reservation">
                                   		${reservation.fin}
                                   		</c:forEach> 
                                    </a>
                                </li>
                            </ul>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.col -->
                <div class="col-md-8">
                    <div class="nav-tabs-custom">
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#clients" data-toggle="tab">Clients</a></li>
                            <li><a href="#cars" data-toggle="tab">Voitures</a></li>
                        </ul>
                        <div class="tab-content">
                            <div class="active tab-pane" id="clients">
                                <div class="box-body no-padding">
                                    <table class="table table-striped">
                                        <tr>
                                            <th style="width: 10px">#</th>
                                            <th>Nom</th>
                                            <th>Prenom</th>
                                            <th>Email</th>
                                            <th>Naissance</th>
                                        </tr>
		                                <c:forEach items="${reservations}" var="reservation">
	                                	<tr>
		                                	<td>${reservation.client.id}</td>
		                                    <td>${reservation.client.nom}</td>
		                                    <td>${reservation.client.prenom}</td>
		                                    <td>${reservation.client.email}</td>
		                                    <td>${reservation.client.naissance}</td>
	                                    </tr>
	                                    </c:forEach>    
	                                    
                                    </table>
                                </div>
                            </div>
                            <!-- /.tab-pane -->
                            <div class="tab-pane" id="cars">
                                <!-- /.box-header -->
                                <div class="box-body no-padding">
                                    <table class="table table-striped">
                                        <tr>
                                            <th style="width: 10px">#</th>
                                            <th>Modele</th>
                                            <th>Constructeur</th>
                                            <th style=>Nombre de places</th>
                                        </tr>
                                        
                                        <c:forEach items="${reservations}" var="reservation">
	                                	<tr>
		                                	<td>${reservation.vehicle.id}</td>
		                                    <td>${reservation.vehicle.constructeur}</td>
		                                    <td>${reservation.vehicle.modele}</td>
		                                    <td>${reservation.vehicle.nb_places}</td>
	                                    </tr>
	                                    </c:forEach>    
	                                                                           
                                    </table>
                                </div>
                            </div>
                            <!-- /.tab-pane -->
                        </div>
                        <!-- /.tab-content -->
                    </div>
                    <!-- /.nav-tabs-custom -->
                </div>
                <!-- /.col -->
            </div>
            <!-- /.row -->

        </section>
        <!-- /.content -->
    </div>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>
</html>
