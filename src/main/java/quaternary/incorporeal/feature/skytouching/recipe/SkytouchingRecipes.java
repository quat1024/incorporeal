package quaternary.incorporeal.feature.skytouching.recipe;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import quaternary.incorporeal.api.recipe.IRecipeSkytouching;
import quaternary.incorporeal.feature.skytouching.lexicon.SkytouchingLexicon;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.ILexicon;
import vazkii.botania.common.item.ModItems;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public final class SkytouchingRecipes {
	private SkytouchingRecipes() {
	}
	
	public static List<IRecipeSkytouching> ALL = new LinkedList<>();
	
	public static int LOWEST_SKYTOUCH_Y = Integer.MAX_VALUE;
	
	public static IRecipeSkytouching bookUpgrade;
	
	public static void registerBookUpgrade() {
		ItemStack stupidBook = new ItemStack(ModItems.lexicon);
		ItemStack smartBook = stupidBook.copy();
		((ILexicon) ModItems.lexicon).unlockKnowledge(smartBook, SkytouchingLexicon.knowledge);
		
		bookUpgrade = register(new RecipeSkytouching(
			smartBook,
			stupidBook
		) {
			@Override
			public boolean matches(EntityItem ent) {
				if(!super.matches(ent)) return false;
				
				ItemStack stack = ent.getItem/*Stack*/();
				Item item = stack.getItem();
				if(item instanceof ILexicon) {
					ILexicon lexyBoy = (ILexicon) item;
					return !lexyBoy.isKnowledgeUnlocked(stack, SkytouchingLexicon.knowledge) && lexyBoy.isKnowledgeUnlocked(stack, BotaniaAPI.elvenKnowledge);
				} else return false;
			}
			
			@Override
			public List<ItemStack> getOutputs(EntityItem ent) {
				List<ItemStack> stacks = super.getOutputs(ent);
				
				for(ItemStack stack : stacks) {
					Item item = stack.getItem();
					if(item instanceof ILexicon) {
						((ILexicon) item).unlockKnowledge(stack, SkytouchingLexicon.knowledge);
					}
				}
				
				return stacks;
			}
		});
	}
	
	public static IRecipeSkytouching register(IRecipeSkytouching r) {
		ALL.add(r);
		
		if(r.getMinY() < LOWEST_SKYTOUCH_Y) {
			LOWEST_SKYTOUCH_Y = r.getMinY();
		}
		
		return r;
	}
	
	//Literally just to save typing...
	public static IRecipeSkytouching register(Object out, Object in) {
		ItemStack outStack;
		ItemStack inStack;
		
		if(out instanceof Item) outStack = new ItemStack((Item) out);
		else if(out instanceof ItemStack) outStack = (ItemStack) out;
		else if(out instanceof Block) outStack = new ItemStack((Block) out);
		else throw new IllegalArgumentException("weird output");
		
		if(in instanceof Item) inStack = new ItemStack((Item) in);
		else if(in instanceof ItemStack) inStack = (ItemStack) in;
		else if(in instanceof Block) inStack = new ItemStack((Block) in);
		else throw new IllegalArgumentException("weird input");
		
		return register(new RecipeSkytouching(outStack, inStack));
	}
	
	public static void clear() {
		ALL.clear();
		LOWEST_SKYTOUCH_Y = Integer.MAX_VALUE;
	}
	
	public static void removeIf(Predicate<IRecipeSkytouching> cond) {
		ALL.removeIf(cond);
		
		LOWEST_SKYTOUCH_Y = Integer.MAX_VALUE;
		ALL.forEach(r -> {
			if(r.getMinY() < LOWEST_SKYTOUCH_Y) {
				LOWEST_SKYTOUCH_Y = r.getMinY();
			}
		});
	}
}
