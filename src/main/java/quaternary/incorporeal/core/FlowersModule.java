package quaternary.incorporeal.core;

import net.minecraft.item.ItemStack;
import quaternary.incorporeal.core.etc.IncorporeticSubTileSignature;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.subtile.SubTileEntity;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;

import java.util.ArrayList;
import java.util.List;

public abstract class FlowersModule {
	public static final List<String> ALL_INCORPORETIC_FLOWERS = new ArrayList<>();
	
	protected static void register(String normalName, Class<? extends SubTileEntity> normalClass, Class<? extends SubTileEntity> smolClass) {
		String smolName = normalName + "_chibi";
		
		BotaniaAPI.registerSubTile(normalName, normalClass);
		BotaniaAPI.registerMiniSubTile(smolName, smolClass, normalName);
		
		BotaniaAPI.registerSubTileSignature(normalClass, new IncorporeticSubTileSignature(normalName));
		BotaniaAPI.registerSubTileSignature(smolClass, new IncorporeticSubTileSignature(smolName));
		
		ALL_INCORPORETIC_FLOWERS.add(normalName);
		ALL_INCORPORETIC_FLOWERS.add(smolName);
		
		//Throw them in Botania's creative, TODO this is the wrong way to fix them not showing up in Search LOL
		BotaniaAPI.addSubTileToCreativeMenu(normalName);
	}
	
	public static List<ItemStack> getAllIncorporeticFlowerStacks() {
		ArrayList<ItemStack> memes = new ArrayList<>();
		
		for(String a : ALL_INCORPORETIC_FLOWERS) {
			memes.add(ItemBlockSpecialFlower.ofType(a));
		}
		
		for(String a : ALL_INCORPORETIC_FLOWERS) {
			memes.add(ItemBlockSpecialFlower.ofType(new ItemStack(ModBlocks.floatingSpecialFlower), a));
		}
		return memes;
	}
}
