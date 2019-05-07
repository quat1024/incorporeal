package quaternary.incorporeal.tile.decorative;

import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.etc.IncorporeticSounds;
import vazkii.botania.common.Botania;
import vazkii.botania.common.block.tile.TileMod;

public class TileSpiritShrineExt extends TileMod implements ITickable {
	//This is based on "TileSpiritShrine" from Botania.
	//Git history leaves off on November 4, 2014.
	//Every single commit since then has been automated cleanup commits.
	//The tile entity is completely unused in modern Botania, and I don't think
	//it ever _was_ used. The changelog never mentions anything relating to it.
	//It's almost a software archaeological artifact?
	
	private int ticks;
	
	//quat edit: suppressing intellij warnings.
	//trying to leave the code as untouched as possible :P
	@SuppressWarnings({"ArrayCreationWithoutNewKeyword", "ConstantConditions"})
	@Override
	public void update() {
		if(world.isRemote) {
			if(ticks >= 40) {
				float[][] colors = new float[][] {
					{ 0F, 0.25F, 1F },
					{ 1F, 0F, 0.2F },
					{ 0F, 1F, 0.25F },
					{ 1F, 1F, 0.25F },
					{ 1F, 0.25F, 1F },
					{ 0.25F, 1F, 1F }
				};
				
				//quat edit: sound pitches!
				float[] pitches = new float[] {
					0.5f, //A3
					1 - .43877f, //B3
					1 - .33258f, //D4
					1 - .25085f, //E4
					1 - .15910f, //F#4
					1, //A4 (normal sample pitch)
				};
				int chimeInterval = 24;
				
				int totalSpiritCount = 6;
				double tickIncrement = 360D / totalSpiritCount;
				
				int liftTicks = 40 * (totalSpiritCount + 1);
				int existTicks = liftTicks * 2;
				int lowerTicks = existTicks + liftTicks;
				
				if(ticks < lowerTicks) {
					int speed = 5;
					double wticks = ticks * speed - tickIncrement;
					double r =  Math.sin((ticks >= liftTicks ? (ticks - liftTicks) * speed - tickIncrement : -tickIncrement) * Math.PI / 180 * 0.75) + 1 * 1.25 + 0.5;
					double g = Math.sin(wticks * Math.PI / 180 * 0.55);
					
					for(int i = 0; i < totalSpiritCount; i++) {
						double x = pos.getX() + Math.sin(wticks * Math.PI / 180) * r + 0.5;
						double y = pos.getY() + (ticks > existTicks ? 40 - (double) (ticks - existTicks) : Math.min(80 + 40 * i, ticks) - 40 * (i + 1)) * 0.1;
						double z = pos.getZ() + Math.cos(wticks * Math.PI / 180) * r + 0.5;
						
						wticks += tickIncrement;
						float[] colorsfx = colors[i >= colors.length ? 0 : i];
						//quat edit: making particles only spawn above the altar, instead of sinking below ground indefinitely
						if(y < pos.getY()) continue;
						Botania.proxy.wispFX(x, y, z, colorsfx[0], colorsfx[1], colorsfx[2], 0.85F, (float)g * 0.05F, 0.25F);
						Botania.proxy.wispFX(x, y, z, colorsfx[0], colorsfx[1], colorsfx[2], (float) Math.random() * 0.1F + 0.1F, (float) (Math.random() - 0.5) * 0.05F, (float) (Math.random() - 0.5) * 0.05F, (float) (Math.random() - 0.5) * 0.05F, 0.9F);
						
						//quat edit: play chimes!
						if((ticks + (int) (((float) i / totalSpiritCount * chimeInterval))) % chimeInterval == 0) {
							float detune = (ticks > existTicks) ? 0.002f * (ticks - existTicks) : 0;
							world.playSound(x, y, z, IncorporeticSounds.SHRINE, SoundCategory.BLOCKS, 1f / totalSpiritCount / 1.5f, pitches[i] + detune, false);
						}
					}
				}
			}
			
			++ticks;
		}
	}
}
