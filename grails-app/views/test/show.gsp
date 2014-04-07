
<%@ page import="teacher.Test" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'test.label', default: 'Test')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-test" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-test" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list test">
			
				<g:if test="${testInstance?.theory}">
				<li class="fieldcontain">
					<span id="theory-label" class="property-label"><g:message code="test.theory.label" default="Theory" /></span>
					
						<span class="property-value" aria-labelledby="theory-label"><g:link controller="theory" action="show" id="${testInstance?.theory?.id}">${testInstance?.theory?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${testInstance?.question}">
				<li class="fieldcontain">
					<span id="question-label" class="property-label"><g:message code="test.question.label" default="Question" /></span>
					
						<span class="property-value" aria-labelledby="question-label"><g:link controller="question" action="show" id="${testInstance?.question?.id}">${testInstance?.question?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${testInstance?.note}">
				<li class="fieldcontain">
					<span id="note-label" class="property-label"><g:message code="test.note.label" default="Note" /></span>
					
						<span class="property-value" aria-labelledby="note-label"><g:link controller="note" action="show" id="${testInstance?.note?.id}">${testInstance?.note?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${testInstance?.rhythmic}">
				<li class="fieldcontain">
					<span id="rhythmic-label" class="property-label"><g:message code="test.rhythmic.label" default="Rhythmic" /></span>
					
						<span class="property-value" aria-labelledby="rhythmic-label"><g:link controller="rhythmic" action="show" id="${testInstance?.rhythmic?.id}">${testInstance?.rhythmic?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${testInstance?.chord}">
				<li class="fieldcontain">
					<span id="chord-label" class="property-label"><g:message code="test.chord.label" default="Chord" /></span>
					
						<span class="property-value" aria-labelledby="chord-label"><g:link controller="chord" action="show" id="${testInstance?.chord?.id}">${testInstance?.chord?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${testInstance?.level}">
				<li class="fieldcontain">
					<span id="level-label" class="property-label"><g:message code="test.level.label" default="Level" /></span>
					
						<span class="property-value" aria-labelledby="level-label"><g:link controller="level" action="show" id="${testInstance?.level?.id}">${testInstance?.level?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${testInstance?.testType}">
				<li class="fieldcontain">
					<span id="testType-label" class="property-label"><g:message code="test.testType.label" default="Test Type" /></span>
					
						<span class="property-value" aria-labelledby="testType-label"><g:link controller="testType" action="show" id="${testInstance?.testType?.id}">${testInstance?.testType?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${testInstance?.title}">
				<li class="fieldcontain">
					<span id="title-label" class="property-label"><g:message code="test.title.label" default="Title" /></span>
					
						<span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${testInstance}" field="title"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${testInstance?.id}" />
					<g:link class="edit" action="edit" id="${testInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
