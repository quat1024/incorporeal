package quaternary.incorporeal.etc;

import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vazkii.botania.api.corporea.CorporeaHelper;
import vazkii.botania.api.corporea.ICorporeaAutoCompleteController;

/**
 * This is the worst class I've ever written in my whole life. Ever. There's no topping this.
 * 
 * This class is patched - *at runtime* - to extend TileCorporeaIndex$InputHandler.
 * It's final, so I can't extend it. The final modifier on that class is also removed at runtime.
 * Don't move this class without fixing the path in CorporeaIndexInputHandlerTweak.java.
 * */
@Mod.EventBusSubscriber
public class HackyCorporeaInputHandler extends SacrificialGoat implements ICorporeaAutoCompleteController {
	public HackyCorporeaInputHandler() {
		//The same sideeffecty constructor as the superclass has.
		//Well, that it *did*, until I asm patched it out...
		CorporeaHelper.registerAutoCompleteController(this);
	}
	
	@SubscribeEvent
	public void onChatMessage(ServerChatEvent event) {
		System.out.println("ASM HOOK FKIN WORKS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		
		ServerChatEvent wrapped = new ServerChatEvent(event.getPlayer(), event.getMessage() + "cows", event.getComponent());
		
		super.onChatMessage(wrapped);
		
		event.setCanceled(wrapped.isCanceled());
	}
	
	@Override
	public boolean shouldAutoComplete() {
		return super.shouldAutoComplete();
	}
}
