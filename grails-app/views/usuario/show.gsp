
<%@ page import="teacher.Usuario" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'usuario.label', default: 'Usuario')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-usuario" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-usuario" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list usuario">
			
				<g:if test="${usuarioInstance?.bornDate}">
				<li class="fieldcontain">
					<span id="bornDate-label" class="property-label"><g:message code="usuario.bornDate.label" default="Born Date" /></span>
					
						<span class="property-value" aria-labelledby="bornDate-label"><g:formatDate date="${usuarioInstance?.bornDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${usuarioInstance?.country}">
				<li class="fieldcontain">
					<span id="country-label" class="property-label"><g:message code="usuario.country.label" default="Country" /></span>
					
						<span class="property-value" aria-labelledby="country-label"><g:fieldValue bean="${usuarioInstance}" field="country"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${usuarioInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="usuario.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${usuarioInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${usuarioInstance?.password}">
				<li class="fieldcontain">
					<span id="password-label" class="property-label"><g:message code="usuario.password.label" default="Password" /></span>
					
						<span class="property-value" aria-labelledby="password-label"><g:fieldValue bean="${usuarioInstance}" field="password"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${usuarioInstance?.token}">
				<li class="fieldcontain">
					<span id="token-label" class="property-label"><g:message code="usuario.token.label" default="Token" /></span>
					
						<span class="property-value" aria-labelledby="token-label"><g:fieldValue bean="${usuarioInstance}" field="token"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${usuarioInstance?.userName}">
				<li class="fieldcontain">
					<span id="userName-label" class="property-label"><g:message code="usuario.userName.label" default="User Name" /></span>
					
						<span class="property-value" aria-labelledby="userName-label"><g:fieldValue bean="${usuarioInstance}" field="userName"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${usuarioInstance?.id}" />
					<g:link class="edit" action="edit" id="${usuarioInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
