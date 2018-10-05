package quaternary.incorporeal.block.cygnus;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.cygnus.CygnusError;
import quaternary.incorporeal.cygnus.CygnusStack;
import quaternary.incorporeal.etc.helper.EtcHelpers;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;

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
	
	public static void registerBlocks(IForgeRegistry<Block> reg) {
		//A blank one that does nothing
		
		registerCygnusActionBlock("blank", stack -> {}, reg);
		
		//Untyped stack management operations
		
		//Duplicate
		//A] -> A A]
		registerCygnusActionBlock("duplicate", stack -> {
			Optional<Object> top = stack.peek();
			if(top.isPresent()) {
				stack.push(top.get());
			} else stack.push(new CygnusError());
		}, reg);
		
		//Math operations
		
		//Add
		//A B] -> (B+A)]
		registerCygnusActionBlock("number_add", stack -> {
			binaryMathOperation(stack, (top, under) -> Optional.of(under + top));
		}, reg);
		
		//Subtract
		//A B] -> (B-A)]
		registerCygnusActionBlock("number_subtract", stack -> {
			binaryMathOperation(stack, (top, under) -> Optional.of(under - top));
		}, reg);
		
		//Multiply
		//A B] -> (B*A)]
		registerCygnusActionBlock("number_multiply", stack -> {
			binaryMathOperation(stack, (top, under) -> Optional.of(under * top));
		}, reg);
		
		//Divide
		//A B] -> (B/A)] or CygnusError if divide by 0
		registerCygnusActionBlock("number_divide", stack -> {
			binaryMathOperation(stack, (top, under) -> top == 0 ? Optional.empty() : Optional.of(under / top));
		}, reg);
		
		//ItemStack operations
		
		//Set Count
		//A{stack} B{int}] -> A{stack}]
		registerCygnusActionBlock("stack_set_count", stack -> {
			Optional<Integer> topCount = stack.peekMatching(Integer.class, 0);
			Optional<ItemStack> underStack = stack.peekMatching(ItemStack.class, 1);
			if(topCount.isPresent() && underStack.isPresent()) {
				stack.popDestroy(2);
				ItemStack result = underStack.get().copy();
				int stackSize = topCount.get();
				if(stackSize > 0 && stackSize <= result.getItem().getItemStackLimit(result)) {
					result.setCount(topCount.get());
					stack.push(result);
				} else stack.push(new CygnusError());
			} else stack.push(new CygnusError());
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
			} else stack.push(new CygnusError());
		}, reg);
		
		//Extract Count
		//A{stack}] -> A{int}]
		registerCygnusActionBlock("stack_extract_count", stack -> {
			Optional<ItemStack> top = stack.peekMatching(ItemStack.class);
			if(top.isPresent()) {
				stack.popDestroy(1);
				stack.push(top.get().getCount());
			} else stack.push(new CygnusError());
		}, reg);
	}
	
	private static void binaryMathOperation(CygnusStack stack, BiFunction<Integer, Integer, Optional<Integer>> operation) {
		Optional<Integer> top = stack.peekMatching(Integer.class, 0);
		Optional<Integer> under = stack.peekMatching(Integer.class, 1);
		if(top.isPresent() && under.isPresent()) {
			stack.popDestroy(2);
			Optional<Integer> operationResult = operation.apply(top.get(), under.get());
			
			if(operationResult.isPresent()) {
				stack.push(operationResult.get());
			} else {
				stack.push(new CygnusError());
			}
		} else stack.push(new CygnusError());
	}
	
	private static void registerCygnusActionBlock(String name, Consumer<CygnusStack> action , IForgeRegistry<Block> reg) {
		BlockCygnusWord block = new BlockCygnusWord(name, action);
		
		String blockName = RegistryNames.wordPrefix + name;
		block.setRegistryName(new ResourceLocation(Incorporeal.MODID, blockName));
		block.setUnlocalizedName(Incorporeal.MODID + "." + blockName);
		block.setCreativeTab(Incorporeal.TAB);
		
		reg.register(block);
	}
}
