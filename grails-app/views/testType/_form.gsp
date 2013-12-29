<%@ page import="teacher.TestType" %>



<div class="fieldcontain ${hasErrors(bean: testTypeInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="testType.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${testTypeInstance?.description}"/>
</div>

