package quaternary.incorporeal.tile.cygnus;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.cygnus.ICygnusStack;
import quaternary.incorporeal.cygnus.IncorporeticCygnusActions;

import java.util.function.Consumer;

public class TileCygnusWord extends TileCygnusBase {
	private Consumer<ICygnusStack> action = IncorporeticCygnusActions.NOTHING;
	
	public void accept(ICygnusStack stack) {
		action.accept(stack);
	}
	
	public Consumer<ICygnusStack> getAction() {
		return action;
	}
	
	public void setAction(Consumer<ICygnusStack> action) {
		this.action = action;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setString("Action", Incorporeal.API.getCygnusStackActionRegistry().nameOf(action).toString());
		return super.writeToNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		action = Incorporeal.API.getCygnusStackActionRegistry().get(
						new ResourceLocation(nbt.getString("Action"))
		);
		
		if(action == null) action = IncorporeticCygnusActions.NOTHING;
	}
}
