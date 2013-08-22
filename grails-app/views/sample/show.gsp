
<%@ page import="Teacher.Sample" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'sample.label', default: 'Sample')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-sample" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-sample" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list sample">
			
				<g:if test="${sampleInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="sample.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${sampleInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${sampleInstance?.score}">
				<li class="fieldcontain">
					<span id="score-label" class="property-label"><g:message code="sample.score.label" default="Score" /></span>
					
						<g:each in="${sampleInstance.score}" var="s">
						<span class="property-value" aria-labelledby="score-label"><g:link controller="score" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${sampleInstance?.sound}">
				<li class="fieldcontain">
					<span id="sound-label" class="property-label"><g:message code="sample.sound.label" default="Sound" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${sampleInstance?.tonality}">
				<li class="fieldcontain">
					<span id="tonality-label" class="property-label"><g:message code="sample.tonality.label" default="Tonality" /></span>
					
						<span class="property-value" aria-labelledby="tonality-label"><g:fieldValue bean="${sampleInstance}" field="tonality"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${sampleInstance?.id}" />
					<g:link class="edit" action="edit" id="${sampleInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
