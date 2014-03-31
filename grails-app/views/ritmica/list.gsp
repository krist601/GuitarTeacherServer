
<%@ page import="teacher.Ritmica" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'ritmica.label', default: 'Ritmica')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-ritmica" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-ritmica" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="frecuencia" title="${message(code: 'ritmica.frecuencia.label', default: 'Frecuencia')}" />
					
						<th><g:message code="ritmica.image.label" default="Image" /></th>
					
						<g:sortableColumn property="name" title="${message(code: 'ritmica.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="rango" title="${message(code: 'ritmica.rango.label', default: 'Rango')}" />
					
						<g:sortableColumn property="tiempo" title="${message(code: 'ritmica.tiempo.label', default: 'Tiempo')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${ritmicaInstanceList}" status="i" var="ritmicaInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${ritmicaInstance.id}">${fieldValue(bean: ritmicaInstance, field: "frecuencia")}</g:link></td>
					
						<td>${fieldValue(bean: ritmicaInstance, field: "image")}</td>
					
						<td>${fieldValue(bean: ritmicaInstance, field: "name")}</td>
					
						<td>${fieldValue(bean: ritmicaInstance, field: "rango")}</td>
					
						<td>${fieldValue(bean: ritmicaInstance, field: "tiempo")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${ritmicaInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
