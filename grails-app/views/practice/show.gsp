
<%@ page import="teacher.Practice" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'practice.label', default: 'Practice')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-practice" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-practice" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list practice">
			
				<g:if test="${practiceInstance?.audio}">
				<li class="fieldcontain">
					<span id="audio-label" class="property-label"><g:message code="practice.audio.label" default="Audio" /></span>
					
						<span class="property-value" aria-labelledby="audio-label"><g:link controller="audio" action="show" id="${practiceInstance?.audio?.id}">${practiceInstance?.audio?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${practiceInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="practice.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${practiceInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${practiceInstance?.type}">
				<li class="fieldcontain">
					<span id="type-label" class="property-label"><g:message code="practice.type.label" default="Type" /></span>
					
						<span class="property-value" aria-labelledby="type-label"><g:fieldValue bean="${practiceInstance}" field="type"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${practiceInstance?.id}" />
					<g:link class="edit" action="edit" id="${practiceInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
