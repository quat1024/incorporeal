package quaternary.incorporeal.core;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import quaternary.incorporeal.Incorporeal;

public abstract class TilesModule {
	protected static void reg(Class<? extends TileEntity> c, String name) {
		GameRegistry.registerTileEntity(c, new ResourceLocation(Incorporeal.MODID, name));
	}
}
