package quaternary.incorporeal.tile.decorative;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.Vec3d;
import quaternary.incorporeal.block.decorative.BlockUnstableCube;
import vazkii.botania.api.state.BotaniaStateProps;
import vazkii.botania.common.Botania;
import vazkii.botania.common.core.helper.Vector3;

import javax.annotation.Nullable;

public class TileUnstableCube extends TileEntity implements ITickable {
	public float rotationAngle;
	public float rotationSpeed;
	
	private long nextLightningTick = 0;
	
	@Override
	public void update() {
		if(rotationSpeed == 0) rotationSpeed = 8;
		
		rotationAngle += rotationSpeed;
		rotationAngle %= 360f;
		if(rotationSpeed > 1f) rotationSpeed *= 0.96;
		
		if(world.isRemote) {
			if(world.getTotalWorldTime() >= nextLightningTick) {
				//Yeah yeah i could cache this whatever
				IBlockState state = world.getBlockState(pos);
				int color = state.getBlock() instanceof BlockUnstableCube ? state.getValue(BotaniaStateProps.COLOR).colorValue : 0xFF0000; //oof
				int red = (color & 0xFF0000) >> 16;
				int green = (color & 0x00FF00) >> 8;
				int blue = (color & 0x0000FF);
				int colorDarker = ((red / 2) << 16) | ((green / 2) << 8) | (blue / 2);
				
				Vector3 start = new Vector3(new Vec3d(pos)).add(.5, .5, .5);
				Vector3 end = start.add(world.rand.nextDouble() * 2 - 1, world.rand.nextDouble() * 2 - 1, world.rand.nextDouble() * 2 - 1);
				Botania.proxy.lightningFX(start, end, 5f, colorDarker, color);
				
				if(rotationSpeed > 1) {
					nextLightningTick = world.getTotalWorldTime() + (int) (60 - Math.min(60, rotationSpeed)) + 3;
				} else {
					nextLightningTick = world.getTotalWorldTime() + world.rand.nextInt(10) + 50;	
				}
			}
		}
	}
	
	public void punch() {
		rotationSpeed += 20;
		if(rotationSpeed > 70) rotationSpeed = 70;
		nextLightningTick = world.getTotalWorldTime();
		world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
	}
	
	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 6969, getUpdateTag());
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		handleUpdateTag(pkt.getNbtCompound());
		//TODO: Don't read RotationAngle, maybe?
		//Could cause odd jumps when the server and client disagree on the rotation angle
		//It's not like it matters anyways.
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setFloat("RotationAngle", rotationAngle);
		nbt.setFloat("RotationSpeed", rotationSpeed);
		nbt.setLong("NextLightingTick", nextLightningTick);
		return super.writeToNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		rotationAngle = nbt.getFloat("RotationAngle");
		rotationSpeed = nbt.getFloat("RotationSpeed");
		nextLightningTick = nbt.getLong("NextLightningTick");
	}
}
