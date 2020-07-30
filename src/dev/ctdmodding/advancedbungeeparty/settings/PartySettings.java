package dev.ctdmodding.advancedbungeeparty.settings;

public class PartySettings {

	private boolean allInvite;
	private boolean publicJoin;
	private boolean autoWarp;
	
	public PartySettings() {
		allInvite = false;
		publicJoin = false;
		autoWarp = false;
	}
	
	public void toggleAllInvite() {
		allInvite = !allInvite;
	}
	
	public void toggleAnyJoin() {
		publicJoin = !publicJoin;
	}
	
	public void toggleAutoWarp() {
		autoWarp = !autoWarp;
	}
	
	public boolean getAllInvite() {
		return allInvite;
	}
	
	public boolean getAnyJoin() {
		return publicJoin;
	}
	
	public boolean getAutoWarp() {
		return autoWarp;
	}
}
