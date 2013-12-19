<%@ page import="teacher.Score" %>



<div class="fieldcontain ${hasErrors(bean: scoreInstance, field: 'date', 'error')} required">
	<label for="date">
		<g:message code="score.date.label" default="Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="date" precision="day"  value="${scoreInstance?.date}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: scoreInstance, field: 'level', 'error')} required">
	<label for="level">
		<g:message code="score.level.label" default="Level" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="level" name="level.id" from="${teacher.Level.list()}" optionKey="id" required="" value="${scoreInstance?.level?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scoreInstance, field: 'player', 'error')} required">
	<label for="player">
		<g:message code="score.player.label" default="Player" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="player" name="player.id" from="${teacher.Player.list()}" optionKey="id" required="" value="${scoreInstance?.player?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: scoreInstance, field: 'score', 'error')} required">
	<label for="score">
		<g:message code="score.score.label" default="Score" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="score" type="number" value="${scoreInstance.score}" required=""/>
</div>

