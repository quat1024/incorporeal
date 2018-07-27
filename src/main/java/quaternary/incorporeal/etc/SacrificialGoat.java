package quaternary.incorporeal.etc;

import net.minecraftforge.event.ServerChatEvent;

/**
 * A dummy class for HackyCorporeaInputHandler to extend. 
 * None of this is reached at runtime.
 * */
public class SacrificialGoat {
	public void onChatMessage(ServerChatEvent event) {
		throw new RuntimeException("Apparently the ASM patch failed!");
	}
	
	public boolean shouldAutoComplete() {
		return false;
	}
}
