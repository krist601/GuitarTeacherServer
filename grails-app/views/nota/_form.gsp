<%@ page import="teacher.Note" %>



<div class="fieldcontain ${hasErrors(bean: noteInstance, field: 'frecuencia', 'error')} ">
	<label for="frecuencia">
		<g:message code="note.frecuencia.label" default="Frecuencia" />
		
	</label>
	<g:textField name="frecuencia" value="${noteInstance?.frecuencia}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: noteInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="note.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${noteInstance?.name}"/>
</div>

