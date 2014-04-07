<%@ page import="teacher.Chord" %>



<div class="fieldcontain ${hasErrors(bean: chordInstance, field: 'frequency', 'error')} ">
	<label for="frequency">
		<g:message code="chord.frequency.label" default="Frequency" />
		
	</label>
	<g:textField name="frequency" value="${chordInstance?.frequency}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: chordInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="chord.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${chordInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: chordInstance, field: 'range', 'error')} ">
	<label for="range">
		<g:message code="chord.range.label" default="Range" />
		
	</label>
	<g:textField name="range" value="${chordInstance?.range}"/>
</div>

