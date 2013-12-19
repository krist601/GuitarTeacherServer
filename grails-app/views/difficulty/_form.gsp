<%@ page import="Services.Difficulty" %>



<div class="fieldcontain ${hasErrors(bean: difficultyInstance, field: 'difficulty', 'error')} required">
	<label for="difficulty">
		<g:message code="difficulty.difficulty.label" default="Difficulty" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="difficulty" type="number" value="${difficultyInstance.difficulty}" required=""/>
</div>

