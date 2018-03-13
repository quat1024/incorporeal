package quaternary.incorporeal.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import quaternary.incorporeal.etc.CorporeaHelper2;
import vazkii.botania.common.entity.EntityCorporeaSpark;

import javax.annotation.Nonnull;
import java.util.*;

public class TileCorporeaSparkTinkerer extends TileEntity {
	private EnumDyeColor myNetwork = EnumDyeColor.WHITE; //the default setting	
	
	public TileCorporeaSparkTinkerer() {}
	
	//Thx jamie
	public boolean shouldRefresh(World world, BlockPos pos, @Nonnull IBlockState oldState, @Nonnull IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}
	
	public void doSwap() {
		if(world.isRemote) return;
		
		//look for other corporea sparks nearby
		List<EntityCorporeaSpark> nearbySparks = new ArrayList<>();
		for(EnumFacing offset : EnumFacing.HORIZONTALS) {
			EntityCorporeaSpark spork = CorporeaHelper2.getSparkEntityForBlock(world, pos.offset(offset));
			if(spork != null) nearbySparks.add(spork);
		}
		
		if(nearbySparks.size() == 0) return;
		
		//choose one
		Collections.shuffle(nearbySparks);
		EntityCorporeaSpark spork = nearbySparks.get(0);
		
		//switch my network color with that one
		EnumDyeColor itsNetwork = spork.getNetwork();
		
		spork.setNetwork(myNetwork);
		myNetwork = itsNetwork;
		
		//this causes the corporea spark to relink
		//for some reason just changing the network is not enough...
		//also this is private...
		ReflectionHelper.setPrivateValue(EntityCorporeaSpark.class, spork, null, "master");
		ReflectionHelper.setPrivateValue(EntityCorporeaSpark.class, spork, true, "firstTick");
	}
	
	public void setNetwork(EnumDyeColor color) {
		myNetwork = color;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {		
		myNetwork = EnumDyeColor.byMetadata(MathHelper.clamp(nbt.getInteger("Network"), 0, 15));
		
		super.readFromNBT(nbt);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("Network", myNetwork.getMetadata());
		return super.writeToNBT(nbt);
	}
}
