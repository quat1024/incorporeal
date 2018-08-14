package quaternary.incorporeal.block.naturaldevices;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import quaternary.incorporeal.api.IncorporealNaturalDeviceRegistry;
import vazkii.botania.common.item.ModItems;

import java.util.ListIterator;
import java.util.Random;

public class BlockNaturalDeviceCrop extends BlockCrops {
	//don't have access to a world in withAge
	private static final Random naturalDeviceRandom = new Random();
	
	public static final AxisAlignedBB[] AABBS = new AxisAlignedBB[7];
	
	static {
		for(int i = 0; i < 7; i++) {
			double uhh = (7 - i) / 32d;
			AABBS[i] = new AxisAlignedBB(uhh, 0, uhh, 1 - uhh, 3/16d, 1 - uhh);
		}
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABBS[state.getValue(AGE)];
	}
	
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		//COPYPASTA from super
		
		//emulate the super's super call
		checkAndDropBlock(world, pos, state);
		
		if (!world.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
		if (world.getLightFromNeighbors(pos.up()) >= 9) {
			int i = this.getAge(state);
			
			if (i < this.getMaxAge()) {
				float f = getGrowthChance(this, world, pos);
				
				if(net.minecraftforge.common.ForgeHooks.onCropsGrowPre(world, pos, state, rand.nextInt((int)(25.0F / f) + 1) == 0)) {
					//basically copy from bonemeal but with a fixed +1, not a bonemeal age thing
					int newAge = this.getAge(state) + 1;
					int maxAge = this.getMaxAge();
					
					IBlockState newState;
					boolean causeBlockUpdate;
					if (newAge >= maxAge) {
						causeBlockUpdate = true;
						newState = IncorporealNaturalDeviceRegistry.pullRandomDevice(world.rand);
					} else {
						causeBlockUpdate = false;
						newState = withAge(newAge);
					}
					
					world.setBlockState(pos, newState, causeBlockUpdate ? 3 : 2);
					if(causeBlockUpdate) {
						world.neighborChanged(pos, newState.getBlock(), pos.down());
					}
					
					net.minecraftforge.common.ForgeHooks.onCropsGrowPost(world, pos, state, world.getBlockState(pos));
				}
			}
		}
	}
	
	@Override
	public void grow(World world, BlockPos pos, IBlockState state) {
		int newAge = this.getAge(state) + this.getBonemealAgeIncrease(world);
		int maxAge = this.getMaxAge();
		
		IBlockState newState;
		boolean causeBlockUpdate;
		if (newAge >= maxAge) {
			causeBlockUpdate = true;
			newState = IncorporealNaturalDeviceRegistry.pullRandomDevice(world.rand);
		} else {
			causeBlockUpdate = false;
			newState = withAge(newAge);
		}
		
		world.setBlockState(pos, newState, causeBlockUpdate ? 3 : 2);
		if(causeBlockUpdate) {
			world.neighborChanged(pos, newState.getBlock(), pos.down());
		}
	}
	
	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		super.getDrops(drops, world, pos, state, fortune);
		
		ListIterator<ItemStack> stackerator = drops.listIterator();
		while(stackerator.hasNext()) {
			ItemStack stack = stackerator.next();
			if(stack.getItem() == getSeed()) {
				stackerator.set(new ItemStack(ModItems.manaResource, 1, 6)); //6 is magic meta for redstone root
			}
		}
	}
	
	@Override
	protected Item getSeed() {
		return Items.REDSTONE; //A dummy item that will get stomped on in getDrops.
		//I would love to return the redstone root item, but it uses metadata, and this isn't a stack.
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(ModItems.manaResource, 1, 6); //Fix the boat meme
	}
	
	@Override
	protected Item getCrop() {
		return Items.AIR; //Will change into another block before it becomes harvestable.
	}
}
