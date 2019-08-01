package quaternary.incorporeal.core.client;

import com.google.common.base.Preconditions;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import quaternary.incorporeal.Incorporeal;
import vazkii.botania.api.BotaniaAPIClient;
import vazkii.botania.api.subtile.SubTileEntity;

public class ClientHelpers {
	public static void setSimpleModel(Item i) {
		ResourceLocation res = Preconditions.checkNotNull(i.getRegistryName());
		ModelResourceLocation mrl = new ModelResourceLocation(res, "inventory");
		ModelLoader.setCustomModelResourceLocation(i, 0, mrl);
	}
	
	public static void setTEISRModel(Item i, TileEntityItemStackRenderer rend) {
		ModelResourceLocation mrl = new ModelResourceLocation(new ResourceLocation(Incorporeal.MODID, "dummy_builtin_blocktransforms"), "inventory");
		ModelLoader.setCustomModelResourceLocation(i, 0, mrl);
		
		i.setTileEntityItemStackRenderer(rend);
	}
	
	public static void set16DataValuesPointingAtSameModel(Item i) {
		ResourceLocation res = Preconditions.checkNotNull(i.getRegistryName());
		
		for(int color = 0; color < 16; color++) {
			ModelResourceLocation mrl = new ModelResourceLocation(res, "inventory");
			ModelLoader.setCustomModelResourceLocation(i, color, mrl);
		}
	}
	
	public static void setIgnoreAllStateMapper(Block b) {
		ModelLoader.setCustomStateMapper(b, new IgnoreAllStateMapper(b));
	}
	
	public static void setIgnoringStateMapper(Block b, IProperty<?>... props) {
		StateMap.Builder builder = new StateMap.Builder();
		for(int i = 0; i < props.length; i++) {
			builder.ignore(props[i]);
		}
		ModelLoader.setCustomStateMapper(b, builder.build());
	}
	
	public static void setFlowerModel(Class<? extends SubTileEntity> flower, String name) {
		ModelResourceLocation mrl = new ModelResourceLocation(new ResourceLocation(Incorporeal.MODID, name), "normal");
		BotaniaAPIClient.registerSubtileModel(flower, mrl);
	}
}
