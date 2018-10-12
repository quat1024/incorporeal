package quaternary.incorporeal.block.cygnus;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.commons.lang3.StringUtils;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.api.cygnus.ICygnusDatatypeInfo;
import quaternary.incorporeal.cygnus.CygnusError;
import quaternary.incorporeal.cygnus.CygnusStack;
import quaternary.incorporeal.etc.helper.EtcHelpers;

import java.math.BigInteger;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;

@GameRegistry.ObjectHolder(Incorporeal.MODID)
public final class IncorporeticCygnusBlocks {
	private IncorporeticCygnusBlocks() {}
	
	public static final class RegistryNames {
		private RegistryNames() {}
		
		static final String wordPrefix = "cygnus_word_";
		
		public static final String WORD_BLANK = wordPrefix + "blank";
		
		public static final String WORD_DUPLICATE = wordPrefix + "duplicate";
		
		public static final String WORD_NUMBER_ADD = wordPrefix + "number_add";
		public static final String WORD_NUMBER_SUBTRACT = wordPrefix + "number_subtract";
		public static final String WORD_NUMBER_MULTIPLY = wordPrefix + "number_multiply";
		public static final String WORD_NUMBER_DIVIDE = wordPrefix + "number_divide";
		
		public static final String WORD_STACK_SET_COUNT = wordPrefix + "stack_set_count";
		public static final String WORD_STACK_SET_ITEM = wordPrefix + "stack_set_item";
		public static final String WORD_STACK_EXTRACT_COUNT = wordPrefix + "stack_extract_count";
		
		static final String crystalPrefix = "cygnus_crystal_cube_";
		
		public static final String CUBE_BLANK = crystalPrefix + "blank";
		
		public static final String CUBE_EMPTY_STACK = crystalPrefix + "empty_stack";
		public static final String CUBE_FULL_STACK = crystalPrefix + "full_stack";
		public static final String CUBE_EQUAL_VALUE = crystalPrefix + "equal_value";
		public static final String CUBE_EQUAL_TYPE = crystalPrefix + "equal_type";
		
		public static final String CUBE_LESS = crystalPrefix + "less_than";
		public static final String CUBE_GREATER = crystalPrefix + "greater_than";
		
		public static final String CUBE_ERRORED = crystalPrefix + "errored";
		
		public static final String CYGNUS_FUNNEL = "cygnus_funnel";
		public static final String CYGNUS_RETAINER = "cygnus_retainer";
	}
	
	@GameRegistry.ObjectHolder(RegistryNames.WORD_BLANK)
	public static final BlockCygnusWord WORD_BLANK = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.WORD_DUPLICATE)
	public static final BlockCygnusWord WORD_DUPLICATE = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.WORD_NUMBER_ADD)
	public static final BlockCygnusWord WORD_NUMBER_ADD = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.WORD_NUMBER_SUBTRACT)
	public static final BlockCygnusWord WORD_NUMBER_SUBTRACT = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.WORD_NUMBER_MULTIPLY)
	public static final BlockCygnusWord WORD_NUMBER_MULTIPLY = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.WORD_NUMBER_DIVIDE)
	public static final BlockCygnusWord WORD_NUMBER_DIVIDE = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.WORD_STACK_SET_COUNT)
	public static final BlockCygnusWord WORD_STACK_SET_COUNT = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.WORD_STACK_SET_ITEM)
	public static final BlockCygnusWord WORD_STACK_SET_ITEM = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.WORD_STACK_EXTRACT_COUNT)
	public static final BlockCygnusWord WORD_STACK_EXTRACT_COUNT = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CUBE_BLANK)
	public static final BlockCygnusCrystalCube CUBE_BLANK = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CUBE_EMPTY_STACK)
	public static final BlockCygnusCrystalCube CUBE_EMPTY_STACK = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CUBE_FULL_STACK)
	public static final BlockCygnusCrystalCube CUBE_FULL_STACK = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CUBE_EQUAL_VALUE)
	public static final BlockCygnusCrystalCube CUBE_EQUAL_VALUE = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CUBE_EQUAL_TYPE)
	public static final BlockCygnusCrystalCube CUBE_EQUAL_TYPE = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CUBE_LESS)
	public static final BlockCygnusCrystalCube CUBE_LESS = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CUBE_GREATER)
	public static final BlockCygnusCrystalCube CUBE_GREATER = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CUBE_ERRORED)
	public static final BlockCygnusCrystalCube CUBE_ERRORED = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CYGNUS_FUNNEL)
	public static final BlockCygnusFunnel CYGNUS_FUNNEL = EtcHelpers.definitelyIsntNullISwear();
	
	@GameRegistry.ObjectHolder(RegistryNames.CYGNUS_RETAINER)
	public static final BlockCygnusRetainer CYGNUS_RETAINER = EtcHelpers.definitelyIsntNullISwear();
	
	public static void registerBlocks(IForgeRegistry<Block> reg) {
		//A blank one that does nothing
		registerCygnusActionBlock("blank", stack -> {}, reg);
		
		//Untyped stack management operations
		
		//Duplicate
		//A] -> A A]
		registerCygnusActionBlock("duplicate", stack -> {
			Optional<?> top = stack.peek();
			if(top.isPresent()) {
				stack.push(top.get());
			} else stack.push(new CygnusError(CygnusError.UNDERFLOW));
		}, reg);
		
		//Math operations
		
		//Add
		//A B] -> (B+A)]
		registerCygnusActionBlock("number_add", stack -> {
			binaryMathOperation(stack, (top, under) -> under.add(top));
		}, reg);
		
		//Subtract
		//A B] -> (B-A)]
		registerCygnusActionBlock("number_subtract", stack -> {
			binaryMathOperation(stack, (top, under) -> under.subtract(top));
		}, reg);
		
		//Multiply
		//A B] -> (B*A)]
		registerCygnusActionBlock("number_multiply", stack -> {
			binaryMathOperation(stack, (top, under) -> under.multiply(top));
		}, reg);
		
		//Divide
		//A B] -> (B/A)] or CygnusError if divide by 0
		registerCygnusActionBlock("number_divide", stack -> {
			binaryMathOperation(stack, (top, under) -> {
				if(top.compareTo(BigInteger.ZERO) == 0) {
					return new CygnusError(CygnusError.INVALID_MATH, CygnusError.INVALID_MATH + ".divide_by_0");
				} else return under.divide(top);
			});
		}, reg);
		
		//ItemStack operations
		
		//Set Count
		//A{stack} B{int}] -> A{stack}]
		registerCygnusActionBlock("stack_set_count", stack -> {
			Optional<BigInteger> topCount = stack.peekMatching(BigInteger.class, 0);
			Optional<ItemStack> underStack = stack.peekMatching(ItemStack.class, 1);
			if(topCount.isPresent() && underStack.isPresent()) {
				stack.popDestroy(2);
				ItemStack result = underStack.get().copy();
				int stackSize = topCount.get().intValue();
				if(stackSize > 0 && stackSize <= result.getItem().getItemStackLimit(result)) {
					result.setCount(stackSize);
					stack.push(result);
				} else stack.push(new CygnusError(CygnusError.OUT_OF_RANGE));
			} else {
				String message = stack.depth() >= 2 ? CygnusError.MISMATCH : CygnusError.UNDERFLOW;
				stack.push(new CygnusError(message));
			}
		}, reg);
		
		//Set Item
		//A{stack} B{stack}] -> A{stack with B's item}]
		registerCygnusActionBlock("stack_set_item", stack -> {
			Optional<ItemStack> topDonor = stack.peekMatching(ItemStack.class, 0);
			Optional<ItemStack> underAcceptor = stack.peekMatching(ItemStack.class, 1);
			if(topDonor.isPresent() && underAcceptor.isPresent()) {
				stack.popDestroy(2);
				ItemStack donor = topDonor.get();
				ItemStack acceptor = underAcceptor.get();
				
				ItemStack result = donor.copy();
				donor.setCount(acceptor.getCount());
				stack.push(result);
			} else {
				String message = stack.depth() >= 2 ? CygnusError.MISMATCH : CygnusError.UNDERFLOW;
				stack.push(new CygnusError(message));
			}
		}, reg);
		
		//Extract Count
		//A{stack}] -> A{int}]
		registerCygnusActionBlock("stack_extract_count", stack -> {
			Optional<ItemStack> top = stack.peekMatching(ItemStack.class);
			if(top.isPresent()) {
				stack.popDestroy(1);
				stack.push(BigInteger.valueOf(top.get().getCount()));
			} else {
				String message = stack.depth() >= 2 ? CygnusError.MISMATCH : CygnusError.UNDERFLOW;
				stack.push(new CygnusError(message));
			}
		}, reg);
		
		///////// Crystal cubes
		
		//Blank one
		registerCygnusCrystalCubeBlock("blank", stack -> false, reg);
		
		//is the stack empty?
		registerCygnusCrystalCubeBlock("empty_stack", CygnusStack::isEmpty, reg);
		
		//is it full?
		registerCygnusCrystalCubeBlock("full_stack", CygnusStack::isFull, reg);
		
		//do the top two items match?
		registerCygnusCrystalCubeBlock("equal_value", stack -> {
			Optional<?> top = stack.peek(0);
			Optional<?> under = stack.peek(1);
			if(!top.isPresent() || !under.isPresent()) return false;
			
			Object thingTop = top.get();
			Object thingUnder = under.get();
			if(thingTop.getClass() != thingUnder.getClass()) return false;
			
			ICygnusDatatypeInfo<?> topType = Incorporeal.API.getCygnusDatatypeInfoRegistry().getDatatypeForClass(thingTop.getClass());
			return topType.areEqualUnchecked(thingTop, thingUnder);
		}, reg);
		
		//are the top two items the same type?
		registerCygnusCrystalCubeBlock("equal_type", stack -> {
			Optional<?> top = stack.peek(0);
			Optional<?> under = stack.peek(1);
			if(!top.isPresent() || !under.isPresent()) return false;
			else return top.get().getClass() == under.get().getClass();
		}, reg);
		
		//is the under item smaller than the top item?
		registerCygnusCrystalCubeBlock("less_than", stack -> {
			Optional<?> top = stack.peek(0);
			Optional<?> under = stack.peek(1);
			if(!top.isPresent() || !under.isPresent()) return false;
			
			Object thingTop = top.get();
			Object thingUnder = under.get();
			if(thingTop.getClass() != thingUnder.getClass()) return false;
			
			ICygnusDatatypeInfo<?> topType = Incorporeal.API.getCygnusDatatypeInfoRegistry().getDatatypeForClass(thingTop.getClass());
			return topType.compareUnchecked(thingTop, thingUnder) < 0;
		}, reg);
		
		//is the under item larger than the top item?
		registerCygnusCrystalCubeBlock("greater_than", stack -> {
			Optional<?> top = stack.peek(0);
			Optional<?> under = stack.peek(1);
			if(!top.isPresent() || !under.isPresent()) return false;
			
			Object thingTop = top.get();
			Object thingUnder = under.get();
			if(thingTop.getClass() != thingUnder.getClass()) return false;
			
			ICygnusDatatypeInfo<?> topType = Incorporeal.API.getCygnusDatatypeInfoRegistry().getDatatypeForClass(thingTop.getClass());
			return topType.compareUnchecked(thingTop, thingUnder) > 0;
		}, reg);
		
		//is the top item an error?
		registerCygnusCrystalCubeBlock("errored", stack -> stack.peekMatching(CygnusError.class).isPresent(), reg);
		
		//and now for like the 2 actual blocks lol
		registerBlock(new BlockCygnusFunnel(), RegistryNames.CYGNUS_FUNNEL, reg);
		registerBlock(new BlockCygnusRetainer(), RegistryNames.CYGNUS_RETAINER, reg);
	}
	
	private static void binaryMathOperation(CygnusStack stack, BiFunction<BigInteger, BigInteger, ?> operation) {
		Optional<BigInteger> top = stack.peekMatching(BigInteger.class, 0);
		Optional<BigInteger> under = stack.peekMatching(BigInteger.class, 1);
		if(top.isPresent() && under.isPresent()) {
			stack.popDestroy(2);
			stack.push(operation.apply(top.get(), under.get()));
		} else {
			String message = stack.depth() >= 2 ? CygnusError.MISMATCH : CygnusError.UNDERFLOW;
			stack.push(new CygnusError(message));
		}
	}
	
	private static void registerCygnusActionBlock(String name, Consumer<CygnusStack> action, IForgeRegistry<Block> reg) {
		BlockCygnusWord block = new BlockCygnusWord(name, action);
		registerBlock(block, RegistryNames.wordPrefix + name, reg);
	}
	
	private static void registerCygnusCrystalCubeBlock(String name, Predicate<CygnusStack> condition, IForgeRegistry<Block> reg) {
		BlockCygnusCrystalCube block = new BlockCygnusCrystalCube(name, condition);
		registerBlock(block, RegistryNames.crystalPrefix + name, reg);
	}
	
	private static void registerBlock(Block block, String name, IForgeRegistry<Block> reg) {
		block.setRegistryName(new ResourceLocation(Incorporeal.MODID, name));
		block.setUnlocalizedName(Incorporeal.MODID + "." + name);
		block.setCreativeTab(Incorporeal.TAB);
		
		reg.register(block);
	}
}
