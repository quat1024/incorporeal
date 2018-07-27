package quaternary.incorporeal.etc;

import net.minecraftforge.event.ServerChatEvent;

/**
 * A dummy class for HackyCorporeaInputHandler to extend. 
 * None of this is reached at runtime.
 * Don't move without fixing the path in TerribleHorribleNoGoodVeryBadAwfulCorporeaIndexInputHandlerTweak.java
 * */
public class SacrificialGoat {
	public void onChatMessage(ServerChatEvent event) {
		throw new RuntimeException("Apparently the ASM patch failed!");
	}
	
	public boolean shouldAutoComplete() {
		throw new RuntimeException("Apparently the ASM patch failed!");
	}
}
