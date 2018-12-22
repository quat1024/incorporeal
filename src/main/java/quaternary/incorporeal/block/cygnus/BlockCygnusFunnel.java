package quaternary.incorporeal.block.cygnus;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import quaternary.incorporeal.api.cygnus.ICygnusFunnelable;
import quaternary.incorporeal.api.cygnus.ICygnusSparkable;
import quaternary.incorporeal.cygnus.CygnusStack;
import quaternary.incorporeal.cygnus.cap.IncorporeticCygnusCapabilities;
import quaternary.incorporeal.entity.cygnus.EntityCygnusMasterSpark;
import quaternary.incorporeal.etc.RedstoneDustCygnusFunnelable;
import quaternary.incorporeal.etc.helper.CygnusHelpers;
import vazkii.botania.api.state.BotaniaStateProps;

import javax.annotation.Nullable;
import java.util.List;

public class BlockCygnusFunnel extends BlockCygnusBase implements ICygnusSparkable {
	public static final PropertyEnum<EnumFacing> FACING = BotaniaStateProps.FACING;
	public static final PropertyBool POWERED = BotaniaStateProps.POWERED;
	
	public static final PropertyBool BACK_LIT = PropertyBool.create("back_lit");
	public static final PropertyBool FRONT_LIT = PropertyBool.create("front_lit");
	
	public BlockCygnusFunnel() {
		setDefaultState(
			getDefaultState()
				.withProperty(FACING, EnumFacing.UP)
				.withProperty(POWERED, false)
				.withProperty(BACK_LIT, false)
				.withProperty(FRONT_LIT, false)
		);
	}
	
	@Override
	public boolean acceptsCygnusSpark(World world, IBlockState state, BlockPos pos) {
		return true;
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos updaterPos) {
		boolean isPowered = state.getValue(POWERED);
		boolean shouldPower = world.isBlockPowered(pos);
		
		if(isPowered != shouldPower) {
			world.setBlockState(pos, state.withProperty(POWERED, shouldPower));
			if(shouldPower) {
				EnumFacing facing = state.getValue(FACING);
				BlockPos fromPos = pos.offset(facing.getOpposite());
				BlockPos toPos = pos.offset(facing);
				
				ICygnusFunnelable source = findCygnusFunnelable(world, fromPos);
				ICygnusFunnelable sink = findCygnusFunnelable(world, toPos);
				
				boolean sourceCanGive = source != null && source.canGiveCygnusItem();
				boolean sinkCanAccept = sink != null && sink.canAcceptCygnusItem();
				if(!sourceCanGive && !sinkCanAccept) return;
				
				if(sourceCanGive && sinkCanAccept) {
					//Move data from the source to the sink, no stack needed
					sink.acceptItemFromCygnus(source.giveItemToCygnus());
					return;
				}
				
				//Only 1 action is available (sourcing or sinking). So we will find a Cygnus stack
				//and use that as the other end of the action.
				EntityCygnusMasterSpark master = CygnusHelpers.getMasterSparkForSparkAt(world, pos);
				if(master == null) return; //Or not, since we're not even on a network apparently :p
				
				CygnusStack stack = master.getCygnusStack();
				if(sourceCanGive) {
					Object given = source.giveItemToCygnus();
					if(given != null)	stack.push(given);
				} else {
					stack.pop().ifPresent(sink::acceptItemFromCygnus);
				}
			}
		}
	}
	
	@Nullable
	private static ICygnusFunnelable findCygnusFunnelable(World world, BlockPos pos) {
		//Is it a block (as an interface?)
		IBlockState state = world.getBlockState(pos);
		Block b = state.getBlock();
		if(b instanceof ICygnusFunnelable) {
			return (ICygnusFunnelable) b;
		} else if(b == Blocks.REDSTONE_WIRE) {
			//Not my block to slap an interface on to, let's just hardcode it lmao
			return new RedstoneDustCygnusFunnelable(state);
		}
		
		//Is it a tile entity capability?
		TileEntity tile = world.getTileEntity(pos);
		if(tile != null) {
			ICygnusFunnelable capMaybe = tile.getCapability(IncorporeticCygnusCapabilities.FUNNEL_CAP, null);
			if(capMaybe != null) return capMaybe;
		}
		
		//Is it an entity capability?
		List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos));
		for(Entity e : entities) {
			ICygnusFunnelable capMaybe = e.getCapability(IncorporeticCygnusCapabilities.FUNNEL_CAP, null);
			if(capMaybe != null) return capMaybe;
		}
		
		//Idk where it is!
		return null;
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer).getOpposite());
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, POWERED, BACK_LIT, FRONT_LIT) ;
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getIndex() + (state.getValue(POWERED) ? 6 : 0);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta % 6)).withProperty(POWERED, meta >= 6);
	}
	
	//TODO this is dodgy as SHIT lolol
	@Override
	@SideOnly(Side.CLIENT)
	public IBlockState getActualState(IBlockState state, IBlockAccess access, BlockPos pos) {
		EnumFacing facing = state.getValue(FACING);
		if(access instanceof ChunkCache) {
			World world = Minecraft.getMinecraft().world;
			if(world == null) return state;
			
			ICygnusFunnelable back = findCygnusFunnelable(world, pos.offset(facing.getOpposite()));
			ICygnusFunnelable front = findCygnusFunnelable(world, pos.offset(facing.getOpposite()));
			
			return state
				.withProperty(BACK_LIT, back == null ? false : back.canGiveCygnusItem())
				.withProperty(FRONT_LIT, front == null ? false : front.canAcceptCygnusItem());
		} else return state;
	}
}
