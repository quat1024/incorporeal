package quaternary.incorporeal.api.feature;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Collection;
import java.util.Collections;

public interface IFeature {
	String name();
	String description();
	
	default String subcategory() {
		return "";
	}
	
	default boolean enabledByDefault() {
		return true;
	}
	
	default boolean canDisable() {
		return true;
	}
	
	default Collection<String> requiredModIDs() {
		return Collections.emptyList();
	}
	
	default void preinit(FMLPreInitializationEvent e) {}
	default void init(FMLInitializationEvent e) {}
	default void postinit(FMLPostInitializationEvent e) {}
	
	default void blocks(IForgeRegistry<Block> blocks) {}
	default void tiles() {}
	default void items(IForgeRegistry<Item> items) {}
	default void entities(IForgeRegistry<EntityEntry> entities) {}
	default void sounds(IForgeRegistry<SoundEvent> sounds) {}
	
	default void petalRecipes() {}
	default void runeRecipes() {}
	default void lexicon() {}
	
	@SideOnly(Side.CLIENT)
	default IClientFeatureTwin client() {
		return IClientFeatureTwin.DUMMY;
	}
}
