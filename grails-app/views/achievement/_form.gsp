<%@ page import="teacher.Achievement" %>



<div class="fieldcontain ${hasErrors(bean: achievementInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="achievement.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${achievementInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: achievementInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="achievement.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${achievementInstance?.name}"/>
</div>

