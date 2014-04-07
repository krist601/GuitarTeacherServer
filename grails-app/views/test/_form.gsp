<%@ page import="teacher.Test" %>



<div class="fieldcontain ${hasErrors(bean: testInstance, field: 'theory', 'error')} ">
	<label for="theory">
		<g:message code="test.theory.label" default="Theory" />
		
	</label>
	<g:select id="theory" name="theory.id" from="${teacher.Theory.list()}" optionKey="id" value="${testInstance?.theory?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: testInstance, field: 'question', 'error')} ">
	<label for="question">
		<g:message code="test.question.label" default="Question" />
		
	</label>
	<g:select id="question" name="question.id" from="${teacher.Question.list()}" optionKey="id" value="${testInstance?.question?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: testInstance, field: 'note', 'error')} ">
	<label for="note">
		<g:message code="test.note.label" default="Note" />
		
	</label>
	<g:select id="note" name="note.id" from="${teacher.Note.list()}" optionKey="id" value="${testInstance?.note?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: testInstance, field: 'rhythmic', 'error')} ">
	<label for="rhythmic">
		<g:message code="test.rhythmic.label" default="Rhythmic" />
		
	</label>
	<g:select id="rhythmic" name="rhythmic.id" from="${teacher.Rhythmic.list()}" optionKey="id" value="${testInstance?.rhythmic?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: testInstance, field: 'chord', 'error')} ">
	<label for="chord">
		<g:message code="test.chord.label" default="Chord" />
		
	</label>
	<g:select id="chord" name="chord.id" from="${teacher.Chord.list()}" optionKey="id" value="${testInstance?.chord?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: testInstance, field: 'level', 'error')} required">
	<label for="level">
		<g:message code="test.level.label" default="Level" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="level" name="level.id" from="${teacher.Level.list()}" optionKey="id" required="" value="${testInstance?.level?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: testInstance, field: 'testType', 'error')} required">
	<label for="testType">
		<g:message code="test.testType.label" default="Test Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="testType" name="testType.id" from="${teacher.TestType.list()}" optionKey="id" required="" value="${testInstance?.testType?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: testInstance, field: 'title', 'error')} ">
	<label for="title">
		<g:message code="test.title.label" default="Title" />
		
	</label>
	<g:textField name="title" value="${testInstance?.title}"/>
</div>

