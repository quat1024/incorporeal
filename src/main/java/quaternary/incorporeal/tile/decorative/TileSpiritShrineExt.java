package quaternary.incorporeal.tile.decorative;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import quaternary.incorporeal.etc.IncorporeticSounds;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.common.Botania;
import vazkii.botania.common.block.tile.TileMod;

public class TileSpiritShrineExt extends TileMod implements ITickable {
	//This is based on "TileSpiritShrine" from Botania.
	//Git history leaves off on November 4, 2014.
	//Every single commit since then has been automated cleanup commits.
	//The tile entity is completely unused in modern Botania, and I don't think
	//it ever _was_ used. The changelog never mentions anything relating to it.
	//It's almost a software archaeological artifact?
	
	//I asked Vazkii, and apparently this was the original concept for the
	//Terrestrial Agglomeration Plate.
	
	//quat edit: enabling redstone support
	private boolean powered;
	private int ticks;
	
	//lets move most of these to static final fields too. why not
	private static final int chimeInterval = 12;
	
	private static final int totalSpiritCount = 6;
	private static final double tickIncrement = 360D / totalSpiritCount;
	
	private static final int liftTicks = 40 * (totalSpiritCount + 1);
	private static final int existTicks = liftTicks * 2;
	private static final int lowerTicks = existTicks + liftTicks;
	
	private static final float[][] colors = new float[][] {
		new float[] { 0F, 0.25F, 1F },
		new float[] { 1F, 0F, 0.2F },
		new float[] { 0F, 1F, 0.25F },
		new float[] { 1F, 1F, 0.25F },
		new float[] { 1F, 0.25F, 1F },
		new float[] { 0.25F, 1F, 1F }
	};
	
	//quat edit: sound pitches!
	private static final float[] pitches = new float[] {
		0.5f, //A3
		1 - .43877f, //B3
		1 - .33258f, //D4
		1 - .25085f, //E4
		1 - .15910f, //F#4
		1, //A4 (normal sample pitch)
	};
	
	@Override
	public void update() {
		//quat edit: enabling redstone support
		if(powered) {
			ticks++;
			
			if(ticks > existTicks + 40) {
				ticks = existTicks + 40;
			}
			
			markDirty();
		} else if(ticks > 0) {
			ticks -= 5;
			if(ticks < 0) ticks = 0;
			markDirty();
		}
		
		//quat edit: early-exit when the animation is over, instead of wasting time trying to draw a particle
		if(ticks == existTicks + 40) return;
		
		if(world.isRemote && ticks >= 40 && ticks < lowerTicks) {
			int speed = 5;
			double wticks = ticks * speed - tickIncrement;
			double r = Math.sin((ticks >= liftTicks ? (ticks - liftTicks) * speed - tickIncrement : -tickIncrement) * Math.PI / 180 * 0.75) + 1 * 1.25 + 0.5;
			double g = Math.sin(wticks * Math.PI / 180 * 0.55);
			
			for(int i = 0; i < totalSpiritCount; i++) {
				double x = pos.getX() + Math.sin(wticks * Math.PI / 180) * r + 0.5;
				double y = pos.getY() + (ticks > existTicks ? 40 - (double) (ticks - existTicks) : Math.min(80 + 40 * i, ticks) - 40 * (i + 1)) * 0.1;
				double z = pos.getZ() + Math.cos(wticks * Math.PI / 180) * r + 0.5;
				
				wticks += tickIncrement;
				float[] colorsfx = colors[i];
				//quat edit: making particles only spawn above the altar, instead of sinking below ground indefinitely
				//it happens that if one particle is below the altar, all the next ones will be too, so
				//i can break instead of continuing
				if(y < pos.getY()) break;
				
				Botania.proxy.wispFX(x, y, z, colorsfx[0], colorsfx[1], colorsfx[2], 0.85F, (float) g * 0.05F, 0.25F);
				Botania.proxy.wispFX(x, y, z, colorsfx[0], colorsfx[1], colorsfx[2], (float) Math.random() * 0.1F + 0.1F, (float) (Math.random() - 0.5) * 0.05F, (float) (Math.random() - 0.5) * 0.05F, (float) (Math.random() - 0.5) * 0.05F, 0.9F);
				
				//quat edit: play chimes!
				if(powered && (ticks + (int) (((float) i / totalSpiritCount * chimeInterval))) % chimeInterval == 0) {
					float detune = (ticks > existTicks) ? 0.002f * (ticks - existTicks) : 0;
					world.playSound(x, y, z, IncorporeticSounds.SHRINE, SoundCategory.BLOCKS, 1f / totalSpiritCount, pitches[i] + detune, false);
				}
			}
			
			//++ticks;
		}
	}
	
	//quat edits below this line too
	@Override
	public void writePacketNBT(NBTTagCompound cmp) {
		cmp.setBoolean("Powered", powered);
		cmp.setInteger("Ticks", ticks);
	}
	
	@Override
	public void readPacketNBT(NBTTagCompound cmp) {
		powered = cmp.getBoolean("Powered");
		ticks = cmp.getInteger("Ticks");
	}
	
	public void setPowered(boolean powered) {
		this.powered = powered;
		markDirty();
		VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
	}
}
