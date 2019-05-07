package quaternary.incorporeal.etc.helper;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.registries.IForgeRegistryEntry;
import quaternary.incorporeal.Incorporeal;
import vazkii.botania.api.state.BotaniaStateProps;
import vazkii.botania.api.state.enums.CrateVariant;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.block.tile.TileCraftCrate;
import vazkii.botania.common.block.tile.TileOpenCrate;

import javax.annotation.Nonnull;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;

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
	
	public static double rangeRemap(double value, double low1, double high1, double low2, double high2) {
		//hey look mathhelper clamo only works on floats
		double clamped = (value < low1) ? low1 : (value > high1 ? high1 : value);
		return low2 + (clamped - low1) * (high2 - low2) / (high1 - low1);
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
	
	//I hate mods that setregname in the constructor!!!!!!
	//I can't extend any botania blocks without this method!!!!
	public static void fixRegistryNameDespacito(IForgeRegistryEntry.Impl<?> lmao) {
		Incorporeal.LOGGER.info("Ignore that forge warning lalalala");
		ResourceLocation name = ReflectionHelper.getPrivateValue(IForgeRegistryEntry.Impl.class, lmao, "registryName");
		ResourceLocation nameBetter = new ResourceLocation(Incorporeal.MODID, name.getPath());
		ReflectionHelper.setPrivateValue(IForgeRegistryEntry.Impl.class, lmao, nameBetter, "registryName");
	}
	
	public static <T> T[] fill(T[] arr, Function<Integer, T> instFactory) {
		for(int i = 0; i < arr.length; i++) {
			arr[i] = instFactory.apply(i);
		}
		return arr;
	}
	
	public static ItemStack skullOf(String name) {
		ItemStack stack = new ItemStack(Items.SKULL, 1, 3);
		
		NBTTagCompound yeet = new NBTTagCompound();
		yeet.setString("SkullOwner", name);
		stack.setTagCompound(yeet);
		
		return stack;
	}
}
