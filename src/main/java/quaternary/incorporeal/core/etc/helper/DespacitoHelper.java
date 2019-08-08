package quaternary.incorporeal.core.etc.helper;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.event.world.NoteBlockEvent.Instrument;

//Haha yes type safety very good
@SuppressWarnings("unchecked")
public final class DespacitoHelper {
	private DespacitoHelper() { }
	
	private static final int[] EMPTY = new int[0];
	private static final int[] ONE = new int[1];
	private static final int[] TWO = new int[2];
	
	public static int[] getNotesForTick(int tick, Instrument inst) {
		int t = tick % 256;
		
		if(inst == Instrument.FLUTE) {
			int unpacked = biunpack(DATA, FLUTE_OFFSET, t);
			return unpacked == 2 ? EMPTY : one(unpacked);
		}
		
		if(inst == Instrument.SNARE) {
			boolean low = octunpack(DATA, SNARE_OFFSET, t * 2);
			boolean high = octunpack(DATA, SNARE_OFFSET, t * 2 + 1);
			if(!low && !high) return EMPTY;
			if(low && !high) return one(8);
			if(!low && high) return one(22);
			if(low && high) return two(8, 22);
		}
		
		if(inst == Instrument.BASSDRUM) {
			if(t % 2 == 1) return EMPTY;
			
			int packed = DATA[BASSDRUM_OFFSET + t / 2];
			if(packed == -1) return EMPTY;
			
			int a = (packed & 0xF0) >> 4;
			if((packed & 0xF) == 0xF) {
				return one(a);
			}
			int b = (packed & 0xF) - 1;
			return two(a, b);
		}
		
		if(inst == Instrument.BASSGUITAR) {
			if(t == 0 || t == 128) return EMPTY;
			
			int unpacked = biunpack(DATA, BASSGUITAR_OFFSET, t % 64);
			return unpacked == 2 ? EMPTY : one(unpacked);
		}
		
		return EMPTY;
	}
	
	public static NoteBlockEvent.Instrument getInstrumentFromState(IBlockState state) {
		Material m = state.getMaterial();
		Instrument ret = Instrument.PIANO;
		if(m == Material.ROCK) ret = Instrument.BASSDRUM;
		if(m == Material.SAND) ret = Instrument.SNARE;
		if(m == Material.GLASS) ret = Instrument.CLICKS;
		if(m == Material.WOOD) ret = Instrument.BASSGUITAR;
		
		Block b = state.getBlock();
		if(b == Blocks.CLAY) return Instrument.FLUTE;
		if(b == Blocks.GOLD_BLOCK) return Instrument.BELL;
		if(b == Blocks.WOOL) return Instrument.GUITAR;
		if(b == Blocks.PACKED_ICE) return Instrument.CHIME;
		if(b == Blocks.BONE_BLOCK) return Instrument.XYLOPHONE;
		
		return ret;
	}
	
	private static int[] one(int x) {
		ONE[0] = x;
		return ONE;
	}
	
	private static int[] two(int x, int y) {
		TWO[0] = x;
		TWO[1] = y;
		return TWO;
	}
	
	private static final int FLUTE_OFFSET = 0;
	private static final int SNARE_OFFSET = 128;
	private static final int BASSDRUM_OFFSET = 192;
	private static final int BASSGUITAR_OFFSET = 320;
	
	private static final byte[] DATA = new byte[] {-126, 34, 114, 34, 82, 2, 0, 0, 5, 85, 82, 53, 33, 34, 17, 17, 21, 85, 82, 120, 35, 34, 51, 51, 56, -120, -126, -86, 39, 34, 34, 34, -126, 34, 114, 34, 82, 2, 0, 0, 5, 85, 82, 53, 33, 34, 17, 17, 21, 85, 82, 120, 35, 34, 51, 51, 56, -120, -126, -86, 39, 34, 34, 34, 34, -89, -84, 34, -54, -54, -62, -84, 42, -54, -62, -35, 40, 34, 34, 40, 45, -35, 45, -3, 44, 34, 34, 34, 44, -52, 47, -36, 42, 34, -86, -86, -86, -86, 114, -126, -54, -54, -54, -62, 42, -54, -62, -35, 40, 34, 34, 40, 45, -35, -46, -3, 44, 34, 34, 34, 44, -52, 47, -36, 42, 34, 34, 34, 4, 4, 2, 8, 2, 8, 2, 8, 2, 8, 2, 8, 2, 8, 2, 8, 0, 0, 2, 8, 2, 8, 2, 8, 2, 8, 2, 8, 2, 8, 2, 8, 0, 0, 6, 12, 6, 12, 6, 12, 6, 12, 6, 12, 6, 8, 6, 12, 6, 12, 6, 12, 6, 12, 6, 12, 6, 12, 6, 12, 6, 12, 6, 12, -91, -1, -1, -1, -81, -1, 111, -1, 111, -1, 111, 111, 111, -1, 111, -1, 111, -1, 111, 111, 111, -1, 111, -1, 111, -1, 111, 111, 111, -1, 111, -1, 111, -1, -1, -1, -1, -1, 111, -1, 111, -1, 111, 111, 111, -1, 111, -1, 111, -1, 111, 111, 111, -1, 111, -1, 111, -1, 111, 111, 111, -1, 111, -1, 111, -1, -1, -1, 105, -1, 105, -1, 105, -1, 105, -1, 105, -1, 105, -1, 105, -1, 105, -1, 105, -1, 105, -1, 105, -1, -91, 111, 105, -1, 105, -1, -91, -1, -91, -1, 105, -1, 105, -1, 105, -1, 105, -1, 105, -1, 105, -1, 105, -1, 105, -1, 105, -1, 105, -1, 105, -1, -91, -1, 105, -1, 105, -1, 50, 34, 34, 34, 82, 34, -126, -62, 82, 34, -126, -62, 18, 34, 82, -126, 18, 34, 82, -126, -126, 34, -62, -14, -126, 34, -62, -14, 50, 34, 114, -94};
	
	private static byte biunpack(byte[] packed, int wholeOffset, int index) {
		boolean even = index % 2 == 0;
		return (byte) ((packed[wholeOffset + index / 2] & (even ? 0b11110000 : 0b00001111)) >>> (even ? 4 : 0));
	}
	
	private static boolean octunpack(byte[] packed, int wholeOffset, int index_) {
		int index = index_ / 8;
		int shift = 7 - (index_ % 8);
		int mask = 0b00000001 << shift;
		return ((packed[wholeOffset + index] & mask) >>> shift) == 1;
	}
}
