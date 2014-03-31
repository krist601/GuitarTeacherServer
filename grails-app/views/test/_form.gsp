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

<div class="fieldcontain ${hasErrors(bean: testInstance, field: 'nota', 'error')} ">
	<label for="nota">
		<g:message code="test.nota.label" default="Nota" />
		
	</label>
	<g:select id="nota" name="nota.id" from="${teacher.Nota.list()}" optionKey="id" value="${testInstance?.nota?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: testInstance, field: 'ritmica', 'error')} ">
	<label for="ritmica">
		<g:message code="test.ritmica.label" default="Ritmica" />
		
	</label>
	<g:select id="ritmica" name="ritmica.id" from="${teacher.Ritmica.list()}" optionKey="id" value="${testInstance?.ritmica?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: testInstance, field: 'acorde', 'error')} ">
	<label for="acorde">
		<g:message code="test.acorde.label" default="Acorde" />
		
	</label>
	<g:select id="acorde" name="acorde.id" from="${teacher.Acorde.list()}" optionKey="id" value="${testInstance?.acorde?.id}" class="many-to-one" noSelection="['null': '']"/>
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

