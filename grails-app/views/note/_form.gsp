<%@ page import="teacher.Note" %>



<div class="fieldcontain ${hasErrors(bean: noteInstance, field: 'frequency', 'error')} ">
	<label for="frequency">
		<g:message code="note.frequency.label" default="Frequency" />
		
	</label>
	<g:textField name="frequency" value="${noteInstance?.frequency}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: noteInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="note.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${noteInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: noteInstance, field: 'range', 'error')} ">
	<label for="range">
		<g:message code="note.range.label" default="Range" />
		
	</label>
	<g:textField name="range" value="${noteInstance?.range}"/>
</div>

