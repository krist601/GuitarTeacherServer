<%@ page import="teacher.Ritmica" %>



<div class="fieldcontain ${hasErrors(bean: ritmicaInstance, field: 'frecuencia', 'error')} required">
	<label for="frecuencia">
		<g:message code="ritmica.frecuencia.label" default="Frecuencia" />
		<span class="required-indicator">*</span>
	</label>
	
</div>

<div class="fieldcontain ${hasErrors(bean: ritmicaInstance, field: 'image', 'error')} required">
	<label for="image">
		<g:message code="ritmica.image.label" default="Image" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="image" name="image.id" from="${teacher.Image.list()}" optionKey="id" required="" value="${ritmicaInstance?.image?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: ritmicaInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="ritmica.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${ritmicaInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: ritmicaInstance, field: 'rango', 'error')} required">
	<label for="rango">
		<g:message code="ritmica.rango.label" default="Rango" />
		<span class="required-indicator">*</span>
	</label>
	
</div>

<div class="fieldcontain ${hasErrors(bean: ritmicaInstance, field: 'tiempo', 'error')} required">
	<label for="tiempo">
		<g:message code="ritmica.tiempo.label" default="Tiempo" />
		<span class="required-indicator">*</span>
	</label>
	
</div>

