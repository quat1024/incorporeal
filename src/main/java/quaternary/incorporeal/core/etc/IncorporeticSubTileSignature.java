package quaternary.incorporeal.core.etc;

import net.minecraft.item.ItemStack;
import vazkii.botania.api.subtile.signature.BasicSignature;

//Just exists to get some of the flower names outside of Botania's namespace, not that it really matters.
public class IncorporeticSubTileSignature extends BasicSignature {
	public IncorporeticSubTileSignature(String name) {
		super(name);
	}
	
	@Override
	public String getUnlocalizedNameForStack(ItemStack stack) {
		return "tile.incorporeal.flower." + getName();
	}
	
	@Override
	public String getUnlocalizedLoreTextForStack(ItemStack stack) {
		return getUnlocalizedNameForStack(stack) + ".flavor";
	}
}
