package quaternary.incorporeal.etc.helper;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import vazkii.botania.api.state.BotaniaStateProps;
import vazkii.botania.api.state.enums.CrateVariant;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.block.tile.TileCraftCrate;
import vazkii.botania.common.block.tile.TileOpenCrate;

public final class EtcHelpers {
	private EtcHelpers() {}
	
	public static float sinDegrees(float degreesIn) {
		return MathHelper.sin((degreesIn % 360) * (float) (Math.PI / 180f));
	}
	
	public static float cosDegrees(float degreesIn) {
		return MathHelper.cos((degreesIn % 360) * (float) (Math.PI / 180f));
	}
	
	public static boolean isOpenCrate(IBlockState state, TileEntity tile) {
		return state.getBlock() == ModBlocks.openCrate && 
						state.getValue(BotaniaStateProps.CRATE_VARIANT) == CrateVariant.OPEN &&
						tile != null &&
						tile instanceof TileOpenCrate &&
						!(tile instanceof TileCraftCrate) &&
						//Wacky hack to work around not being able to extend TileCraftCrate with custom crates in botaniatweaks
						//I'm allowed to do this since botaniatweaks is my own mod :^)
						!state.getBlock().getClass().getName().contains("quaternary.botania");
	}
	
	public static void sendMessage(EntityPlayer player, String message, TextFormatting color) {
		TextComponentTranslation txt = new TextComponentTranslation(message);
		txt.getStyle().setColor(color);
		player.sendStatusMessage(txt, true);
	}
}
