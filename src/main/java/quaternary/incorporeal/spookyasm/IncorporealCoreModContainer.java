package quaternary.incorporeal.spookyasm;

import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.EventBus;
import net.minecraftforge.fml.common.*;

@SuppressWarnings("unused")
public class IncorporealCoreModContainer extends DummyModContainer {
	public IncorporealCoreModContainer() {
		super(new ModMetadata());
		ModMetadata meta = getMetadata(); //Thanks java requiring super to be first
		
		meta.version = "69.69.69";
		meta.modId = "incorporeal_core";
		meta.name = "In-core-poreal";
		meta.credits = "Your MOM";
		meta.authorList = ImmutableList.of("quaternary");
		meta.description = "Don't mind me, just a little coremod used by Incorporeal!";
		meta.screenshots = new String[0];
		meta.logoFile = "";
	}
	
	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		return true;
	}
}
