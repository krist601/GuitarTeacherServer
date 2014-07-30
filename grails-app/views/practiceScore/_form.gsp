<%@ page import="teacher.PracticeScore" %>



<div class="fieldcontain ${hasErrors(bean: practiceScoreInstance, field: 'note', 'error')} ">
	<label for="note">
		<g:message code="practiceScore.note.label" default="Note" />
		
	</label>
	<g:select id="note" name="note.id" from="${teacher.Note.list()}" optionKey="id" value="${practiceScoreInstance?.note?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: practiceScoreInstance, field: 'rhythmic', 'error')} ">
	<label for="rhythmic">
		<g:message code="practiceScore.rhythmic.label" default="Rhythmic" />
		
	</label>
	<g:select id="rhythmic" name="rhythmic.id" from="${teacher.Rhythmic.list()}" optionKey="id" value="${practiceScoreInstance?.rhythmic?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: practiceScoreInstance, field: 'chord', 'error')} ">
	<label for="chord">
		<g:message code="practiceScore.chord.label" default="Chord" />
		
	</label>
	<g:select id="chord" name="chord.id" from="${teacher.Chord.list()}" optionKey="id" value="${practiceScoreInstance?.chord?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: practiceScoreInstance, field: 'score', 'error')} required">
	<label for="score">
		<g:message code="practiceScore.score.label" default="Score" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="score" type="number" value="${practiceScoreInstance.score}" required=""/>
</div>

