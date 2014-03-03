<%@ page import="teacher.Question" %>



<div class="fieldcontain ${hasErrors(bean: questionInstance, field: 'image', 'error')} ">
	<label for="image">
		<g:message code="question.image.label" default="Image" />
		
	</label>
	<g:select id="image" name="image.id" from="${teacher.Image.list()}" optionKey="id" value="${questionInstance?.image?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: questionInstance, field: 'audio', 'error')} ">
	<label for="audio">
		<g:message code="question.audio.label" default="Audio" />
		
	</label>
	<g:select id="audio" name="audio.id" from="${teacher.Audio.list()}" optionKey="id" value="${questionInstance?.audio?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: questionInstance, field: 'answer', 'error')} ">
	<label for="answer">
		<g:message code="question.answer.label" default="Answer" />
		
	</label>
	<g:textField name="answer" value="${questionInstance?.answer}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: questionInstance, field: 'question', 'error')} ">
	<label for="question">
		<g:message code="question.question.label" default="Question" />
		
	</label>
	<g:textField name="question" value="${questionInstance?.question}"/>
</div>

