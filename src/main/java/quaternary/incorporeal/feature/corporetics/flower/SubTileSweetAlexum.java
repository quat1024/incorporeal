package quaternary.incorporeal.feature.corporetics.flower;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import quaternary.incorporeal.core.IncorporeticPacketHandler;
import quaternary.incorporeal.core.etc.helper.DespacitoHelper;
import quaternary.incorporeal.feature.corporetics.lexicon.CorporeticsLexicon;
import quaternary.incorporeal.feature.corporetics.net.MessageSparkleLine;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.SubTileFunctional;

//based on real life "sweet alyssum" flower. This is so sad, Alexa play Despacito.
public class SubTileSweetAlexum extends SubTileFunctional implements ILexiconable {
	private int clock = -1;
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(getWorld().isRemote) return; //TODO: It sounds like garbage on mp because of lag
		                                //Actually it's kinda funny tho
		
		if(redstoneSignal == 15) {
			clock = -1;
		} else if(redstoneSignal > 0 || mana < 100) {
			//Ok boomer
		} else {
			clock++;
			
			World world = getWorld();
			int ticksBetween = (getTicksBetweenNotes() / (overgrowth || overgrowthBoost ? 2 : 1));
			if(ticksBetween == 0) ticksBetween = 1;
			
			int tick = clock;
			if(tick < 0 || tick % ticksBetween != 0) return;
			tick /= ticksBetween;
			
			BlockPos flutePos = null;
			BlockPos snarePos = null;
			BlockPos bassdrumPos = null;
			BlockPos bassguitarPos = null;
			BlockPos pos = getPos();
			
			int range = getRange();
			for(BlockPos bp : BlockPos.getAllInBoxMutable(pos.add(-range, 0, -range), pos.add(range, 1, range))) {
				if(world.getBlockState(bp).getBlock() == Blocks.NOTEBLOCK) {
					NoteBlockEvent.Instrument instUnder = DespacitoHelper.getInstrumentFromState(world.getBlockState(bp.down()));
					if(instUnder == NoteBlockEvent.Instrument.FLUTE && flutePos == null) {
						flutePos = bp.toImmutable();
						continue;
					}
					
					if(instUnder == NoteBlockEvent.Instrument.SNARE && snarePos == null) {
						snarePos = bp.toImmutable();
						continue;
					}
					
					if(instUnder == NoteBlockEvent.Instrument.BASSDRUM && bassdrumPos == null) {
						bassdrumPos = bp.toImmutable();
						continue;
					}
					
					if(instUnder == NoteBlockEvent.Instrument.BASSGUITAR && bassguitarPos == null) {
						bassguitarPos = bp.toImmutable();
						continue;
					}
				}
			}
			
			Vec3d particleSrc = world.getBlockState(pos).getOffset(world, pos).add(pos.getX() + .5, pos.getY() + getSparkleHeight(), pos.getZ() + .5);
			
			boolean dirtyMana = false;
			
			dirtyMana |= doIt(world, pos, tick, particleSrc, flutePos, NoteBlockEvent.Instrument.FLUTE);
			dirtyMana |= doIt(world, pos, tick, particleSrc, snarePos, NoteBlockEvent.Instrument.SNARE);
			dirtyMana |= doIt(world, pos, tick, particleSrc, bassdrumPos, NoteBlockEvent.Instrument.BASSDRUM);
			dirtyMana |= doIt(world, pos, tick, particleSrc, bassguitarPos, NoteBlockEvent.Instrument.BASSGUITAR);
			
			if(dirtyMana) sync();
		}
	}
	
	private boolean doIt(World world, BlockPos pos, int tick, Vec3d particleSrc, BlockPos noteblockPos, NoteBlockEvent.Instrument inst) {
		if(noteblockPos == null) return false;
		
		int[] notes = DespacitoHelper.getNotesForTick(tick, inst);
		if(notes.length > 0) {
			IncorporeticPacketHandler.sendToAllTracking(new MessageSparkleLine(particleSrc, noteblockPos, 2), world, pos);
			for(int note : notes) {
				world.addBlockEvent(noteblockPos, Blocks.NOTEBLOCK, inst.ordinal(), note + getPitchShift());
				mana -= 10;
			}
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean acceptsRedstone() {
		return true;
	}
	
	@Override
	public int getMaxMana() {
		return 2000;
	}
	
	@Override
	public int getColor() {
		return 0xBB4422;
	}
	
	@Override
	public void writeToPacketNBT(NBTTagCompound cmp) {
		cmp.setInteger("Clock", clock);
		super.writeToPacketNBT(cmp);
	}
	
	@Override
	public void readFromPacketNBT(NBTTagCompound cmp) {
		super.readFromPacketNBT(cmp);
		clock = cmp.getInteger("Clock");
	}
	
	protected int getRange() {
		return 4;
	}
	
	protected int getTicksBetweenNotes() {
		return 4;
	}
	
	protected int getPitchShift() {
		return 0;
	}
	
	protected double getSparkleHeight() {
		return 0.75;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public RadiusDescriptor getRadius() {
		return new RadiusDescriptor.Square(getPos(), getRange());
	}
	
	public static class Mini extends SubTileSweetAlexum {
		@Override
		protected int getRange() {
			return 2;
		}
		
		@Override
		protected int getTicksBetweenNotes() {
			return 3;
		}
		
		@Override
		protected int getPitchShift() {
			return 7;
		}
		
		@Override
		protected double getSparkleHeight() {
			return 0.6;
		}
	}
	
	@Override
	public LexiconEntry getEntry(World world, BlockPos blockPos, EntityPlayer entityPlayer, ItemStack itemStack) {
		return CorporeticsLexicon.sweetAlexum;
	}
}
