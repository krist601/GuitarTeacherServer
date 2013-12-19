<%@ page import="teacher.PlayerAchievement" %>



<div class="fieldcontain ${hasErrors(bean: playerAchievementInstance, field: 'achievement', 'error')} required">
	<label for="achievement">
		<g:message code="playerAchievement.achievement.label" default="Achievement" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="achievement" name="achievement.id" from="${teacher.Achievement.list()}" optionKey="id" required="" value="${playerAchievementInstance?.achievement?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: playerAchievementInstance, field: 'player', 'error')} required">
	<label for="player">
		<g:message code="playerAchievement.player.label" default="Player" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="player" name="player.id" from="${teacher.Player.list()}" optionKey="id" required="" value="${playerAchievementInstance?.player?.id}" class="many-to-one"/>
</div>

