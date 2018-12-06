package quaternary.incorporeal.block.cygnus;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.cygnus.ICygnusDatatype;
import quaternary.incorporeal.cygnus.CygnusError;
import quaternary.incorporeal.cygnus.CygnusStack;
import quaternary.incorporeal.etc.helper.CorporeaHelper2;
import quaternary.incorporeal.etc.helper.EtcHelpers;
import vazkii.botania.api.corporea.CorporeaRequest;

import java.math.BigInteger;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;

@GameRegistry.ObjectHolder(Incorporeal.MODID)
public final class IncorporeticCygnusBlocks {
	private IncorporeticCygnusBlocks() {}
	
	public static final class RegistryNames {
		private RegistryNames() {}
		
		public static final String WORD = "cygnus_word";
		public static final String CRYSTAL_CUBE = "cygnus_crystal_cube";
		
		public static final String FUNNEL = "cygnus_funnel";
		public static final String RETAINER = "cygnus_retainer";
	}
	
	@GameRegistry.ObjectHolder(RegistryNames.WORD)
	public static final BlockCygnusWord WORD = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CRYSTAL_CUBE)
	public static final BlockCygnusCrystalCube CRYSTAL_CUBE = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.FUNNEL)
	public static final BlockCygnusFunnel FUNNEL = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.RETAINER)
	public static final BlockCygnusRetainer RETAINER = EtcHelpers.definitelyIsntNullISwear();
	
	public static void registerBlocks(IForgeRegistry<Block> reg) {
		registerBlock(new BlockCygnusWord(), RegistryNames.WORD, reg);
		registerBlock(new BlockCygnusCrystalCube(), RegistryNames.CRYSTAL_CUBE, reg);
		
		registerBlock(new BlockCygnusFunnel(), RegistryNames.FUNNEL, reg);
		registerBlock(new BlockCygnusRetainer(), RegistryNames.RETAINER, reg);
	}
	
	private static void registerBlock(Block block, String name, IForgeRegistry<Block> reg) {
		block.setRegistryName(new ResourceLocation(Incorporeal.MODID, name));
		block.setTranslationKey(Incorporeal.MODID + "." + name);
		block.setCreativeTab(Incorporeal.TAB);
		
		reg.register(block);
	}
}
