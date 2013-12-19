<%@ page import="teacher.Follower" %>



<div class="fieldcontain ${hasErrors(bean: followerInstance, field: 'follower', 'error')} required">
	<label for="follower">
		<g:message code="follower.follower.label" default="Follower" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="follower" name="follower.id" from="${teacher.Player.list()}" optionKey="id" required="" value="${followerInstance?.follower?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: followerInstance, field: 'player', 'error')} required">
	<label for="player">
		<g:message code="follower.player.label" default="Player" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="player" name="player.id" from="${teacher.Player.list()}" optionKey="id" required="" value="${followerInstance?.player?.id}" class="many-to-one"/>
</div>

