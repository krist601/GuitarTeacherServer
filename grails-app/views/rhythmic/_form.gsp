<%@ page import="teacher.Rhythmic" %>



<div class="fieldcontain ${hasErrors(bean: rhythmicInstance, field: 'frequency', 'error')} ">
	<label for="frequency">
		<g:message code="rhythmic.frequency.label" default="Frequency" />
		
	</label>
	<g:textField name="frequency" value="${rhythmicInstance?.frequency}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: rhythmicInstance, field: 'image', 'error')} required">
	<label for="image">
		<g:message code="rhythmic.image.label" default="Image" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="image" name="image.id" from="${teacher.Image.list()}" optionKey="id" required="" value="${rhythmicInstance?.image?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: rhythmicInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="rhythmic.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${rhythmicInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: rhythmicInstance, field: 'range', 'error')} ">
	<label for="range">
		<g:message code="rhythmic.range.label" default="Range" />
		
	</label>
	<g:textField name="range" value="${rhythmicInstance?.range}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: rhythmicInstance, field: 'time', 'error')} ">
	<label for="time">
		<g:message code="rhythmic.time.label" default="Time" />
		
	</label>
	<g:textField name="time" value="${rhythmicInstance?.time}"/>
</div>

