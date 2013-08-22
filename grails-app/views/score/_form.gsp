<%@ page import="Teacher.Score" %>



<div class="fieldcontain ${hasErrors(bean: scoreInstance, field: 'date', 'error')} required">
	<label for="date">
		<g:message code="score.date.label" default="Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="date" precision="day"  value="${scoreInstance?.date}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: scoreInstance, field: 'score', 'error')} required">
	<label for="score">
		<g:message code="score.score.label" default="Score" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="score" type="number" value="${scoreInstance.score}" required=""/>
</div>

