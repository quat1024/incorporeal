package quaternary.incorporeal.flower;

import net.minecraft.item.ItemStack;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.subtile.SubTileEntity;
import vazkii.botania.common.item.block.ItemBlockFloatingSpecialFlower;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;

import java.util.ArrayList;
import java.util.List;

public final class IncorporeticFlowers {
	private IncorporeticFlowers() {}
	
	private static final List<String> moddedFlowers = new ArrayList<>();
	
	public static void registerFlowers() {
		//sanvocalia!
		registerFlower("sanvocalia", SubTileSanvocalia.class, SubTileSanvocalia.Mini.class);
	}
	
	public static List<ItemStack> getAllIncorporeticFlowerStacks() {
		ArrayList<ItemStack> memes = new ArrayList<>();
		
		for(String a : moddedFlowers) {
			memes.add(ItemBlockSpecialFlower.ofType(a));
		}
		
		for(String a : moddedFlowers) {
			//TODO this doesnt actually work lmao.
			memes.add(ItemBlockFloatingSpecialFlower.ofType(a));
		}
		return memes;
	}
	
	private static void registerFlower(String normalName, Class<? extends SubTileEntity> normalClass, Class<? extends SubTileEntity> smolClass) {
		String smolName = normalName + "_chibi";
		
		BotaniaAPI.registerSubTile(normalName, normalClass);
		BotaniaAPI.registerMiniSubTile(smolName, smolClass, normalName);
		
		BotaniaAPI.registerSubTileSignature(normalClass, new IncorporeticSubTileSignature(normalName));
		BotaniaAPI.registerSubTileSignature(smolClass, new IncorporeticSubTileSignature(smolName));
		
		moddedFlowers.add(normalName);
		moddedFlowers.add(smolName);
	}
}
