package quaternary.incorporeal.etc.helper;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import vazkii.botania.api.state.BotaniaStateProps;
import vazkii.botania.api.state.enums.CrateVariant;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.block.tile.TileCraftCrate;
import vazkii.botania.common.block.tile.TileOpenCrate;

import javax.annotation.Nonnull;

public final class EtcHelpers {
	private EtcHelpers() {}
	
	@Nonnull
	@SuppressWarnings({"ConstantConditions", "SameReturnValue"})
	public static <T> T definitelyIsntNullISwear() {
		//Used to make IntelliJ shut up about "xxx may be null" inspections.
		//Thanks, IJ, but they're hit with an ObjectHolder.
		//If that's not happening we have bigger fish to fry.
		//(And it sure doesn't justify null checking every single field every time lmao)
		return null;
	}
	
	public static float sinDegrees(float degreesIn) {
		return MathHelper.sin((degreesIn % 360) * (float) (Math.PI / 180f));
	}
	
	public static float cosDegrees(float degreesIn) {
		return MathHelper.cos((degreesIn % 360) * (float) (Math.PI / 180f));
	}
	
	public static boolean isOpenCrate(IBlockState state, TileEntity tile) {
		return state.getBlock() == ModBlocks.openCrate && 
						state.getValue(BotaniaStateProps.CRATE_VARIANT) == CrateVariant.OPEN &&
						tile instanceof TileOpenCrate &&
						!(tile instanceof TileCraftCrate) &&
						//Wacky hack to work around not being able to extend TileCraftCrate with custom crates in botaniatweaks
						//I'm allowed to do this since botaniatweaks is my own mod :^)
						!state.getBlock().getClass().getName().startsWith("quaternary.botania");
	}
	
	public static void sendMessage(EntityPlayer player, String message, TextFormatting color) {
		TextComponentTranslation txt = new TextComponentTranslation(message);
		txt.getStyle().setColor(color);
		player.sendStatusMessage(txt, true);
	}
	
	public static TileEntity getTileEntityThreadsafe(IBlockAccess world, BlockPos pos) {
		//see https://mcforge.readthedocs.io/en/latest/blocks/states/#actual-states
		if(world instanceof ChunkCache) {
			return ((ChunkCache)world).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK);
		} else return world.getTileEntity(pos);
	}
	
	public static float rangeRemap(float value, float low1, float high1, float low2, float high2) {
		float value2 = MathHelper.clamp(value, low1, high1);
		return low2 + (value2 - low1) * (high2 - low2) / (high1 - low1);
	}
	
	/**
	 * Appends ".vowel" to the end of `key` if `stringToInsert` starts with a vowel sound in English.
	 */
	public static String vowelizeTranslationKey(String key, String stringToInsert) {
		if(stringToInsert.isEmpty()) return key;
		
		char first = Character.toLowerCase(stringToInsert.charAt(0));
		switch(first) {
			case 'a':
			case 'e':
			case 'i':
			case 'o':
			case 'u':
			case 'y':
				return key + ".vowel";
			default:
				return key;
		}
	}
}
