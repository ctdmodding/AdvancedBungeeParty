package dev.ctdmodding.advancedbungeeparty.settings;

public class MuteSettings {
	
	private boolean joins;
	private boolean leaves;
	private boolean invites;
	private boolean broadcasts;
	private boolean chat;
	private boolean all;
	
	public MuteSettings() {
		joins = true;
		leaves = true;
		invites = true;
		broadcasts = true;
		chat = true;
		all = true;
	}

	public void toggleAll() {
		all = !all;
		joins = all;
		leaves = all;
		invites = all;
		broadcasts = all;
		chat = all;
	}
	
	public void toggleJoins() {
		joins = !joins;
	}
	
	public void toggleLeaves() {
		leaves = !leaves;
	}
	
	public void toggleInvites() {
		invites = !invites;
	}
	
	public void toggleBroadcasts() {
		broadcasts = !broadcasts;
	}
	
	public void toggleChat() {
		chat = !chat;
	}

	
	public boolean getJoins() {
		return joins; 
	}
	
	public boolean getLeaves() {
		return leaves; 
	}
	
	public boolean getInvites() {
		return invites; 
	}
	
	public boolean getBroadcasts() {
		return broadcasts; 
	}
	
	public boolean getChat() {
		return chat; 
	}
	
	public boolean getAll() {
		return all;
	}
}
