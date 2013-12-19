<%@ page import="teacher.Theory" %>



<div class="fieldcontain ${hasErrors(bean: theoryInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="theory.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${theoryInstance?.description}"/>
</div>

