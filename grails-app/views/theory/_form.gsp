<%@ page import="teacher.Theory" %>



<div class="fieldcontain ${hasErrors(bean: theoryInstance, field: 'audio', 'error')} ">
	<label for="audio">
		<g:message code="theory.audio.label" default="Audio" />
		
	</label>
	<g:select id="audio" name="audio.id" from="${teacher.Audio.list()}" optionKey="id" value="${theoryInstance?.audio?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: theoryInstance, field: 'image', 'error')} ">
	<label for="image">
		<g:message code="theory.image.label" default="Image" />
		
	</label>
	<g:select id="image" name="image.id" from="${teacher.Image.list()}" optionKey="id" value="${theoryInstance?.image?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: theoryInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="theory.description.label" default="Description" />
		
	</label>
	<g:textArea name="description" cols="40" rows="5" maxlength="1000" value="${theoryInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: theoryInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="theory.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${theoryInstance?.name}"/>
</div>

