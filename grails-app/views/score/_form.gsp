<%@ page import="teacher.Score" %>



<div class="fieldcontain ${hasErrors(bean: scoreInstance, field: 'date', 'error')} required">
	<label for="date">
		<g:message code="score.date.label" default="Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="date" precision="day"  value="${scoreInstance?.date}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: scoreInstance, field: 'sample', 'error')} required">
	<label for="sample">
		<g:message code="score.sample.label" default="Sample" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="sample" name="sample.id" from="${teacher.Sample.list()}" optionKey="id" required="" value="${scoreInstance?.sample?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scoreInstance, field: 'score', 'error')} required">
	<label for="score">
		<g:message code="score.score.label" default="Score" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="score" type="number" value="${scoreInstance.score}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: scoreInstance, field: 'usuario', 'error')} required">
	<label for="usuario">
		<g:message code="score.usuario.label" default="Usuario" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="usuario" name="usuario.id" from="${teacher.Usuario.list()}" optionKey="id" required="" value="${scoreInstance?.usuario?.id}" class="many-to-one"/>
</div>

