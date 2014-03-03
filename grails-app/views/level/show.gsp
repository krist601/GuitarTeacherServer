
<%@ page import="teacher.Level" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'level.label', default: 'Level')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-level" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-level" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list level">
			
				<g:if test="${levelInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="level.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${levelInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${levelInstance?.difficulty}">
				<li class="fieldcontain">
					<span id="difficulty-label" class="property-label"><g:message code="level.difficulty.label" default="Difficulty" /></span>
					
						<span class="property-value" aria-labelledby="difficulty-label"><g:fieldValue bean="${levelInstance}" field="difficulty"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${levelInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="level.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${levelInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${levelInstance?.normal}">
				<li class="fieldcontain">
					<span id="normal-label" class="property-label"><g:message code="level.normal.label" default="Normal" /></span>
					
						<span class="property-value" aria-labelledby="normal-label"><g:link controller="image" action="show" id="${levelInstance?.normal?.id}">${levelInstance?.normal?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${levelInstance?.success}">
				<li class="fieldcontain">
					<span id="success-label" class="property-label"><g:message code="level.success.label" default="Success" /></span>
					
						<span class="property-value" aria-labelledby="success-label"><g:link controller="image" action="show" id="${levelInstance?.success?.id}">${levelInstance?.success?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${levelInstance?.id}" />
					<g:link class="edit" action="edit" id="${levelInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
