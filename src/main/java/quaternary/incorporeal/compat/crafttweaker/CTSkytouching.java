package quaternary.incorporeal.compat.crafttweaker;

import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.builder.ToStringBuilder;
import quaternary.incorporeal.Incorporeal;
import quaternary.incorporeal.recipe.skytouch.IncorporeticSkytouchingRecipes;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;

@ZenRegister
@ZenClass("mods.incorporeal.Skytouching")
public class CTSkytouching {
	//zen stuff
	
	@ZenMethod
	public static void addRecipe(IItemStack out, IItemStack in) {
		ACTIONS.add(new AddAction(InputHelper.toStack(out), InputHelper.toStack(in), 260, 300, 4));
	}
	
	@ZenMethod
	public static void addRecipe(IItemStack out, IItemStack in, int minY, int maxY, int multiplier) {
		ACTIONS.add(new AddAction(InputHelper.toStack(out), InputHelper.toStack(in), minY, maxY, multiplier));
	}
	
	@ZenMethod
	public static void remove(IItemStack removalTarget) {
		ACTIONS.add(new RemoveForAction(InputHelper.toStack(removalTarget)));
	}
	
	@ZenMethod
	public static void removeAll() {
		ACTIONS.add(new NukeAllAction());
	}
	
	//internal stuff
	public static List<IAction> ACTIONS = new LinkedList<>();
	
	public static void postinit() {
		try {
			ACTIONS.forEach(CraftTweakerAPI::apply);
		} catch(Exception e) {
			CraftTweakerAPI.logError("There was a problem applying an Incorporeal action");
			StringWriter out = new StringWriter();
			e.printStackTrace(new PrintWriter(out));
			CraftTweakerAPI.logError(out.toString());
		}
	}
	
	public static class AddAction implements IAction {
		public AddAction(ItemStack out, ItemStack in, int minY, int maxY, int multiplier) {
			this.out = out;
			this.in = in;
			this.minY = minY;
			this.maxY = maxY;
			this.multiplier = multiplier;
		}
		
		private final ItemStack out;
		private final ItemStack in;
		private final int minY;
		private final int maxY;
		private final int multiplier;
		
		@Override
		public void apply() {
			Incorporeal.API.registerSkytouchingRecipe(out, in, minY, maxY, multiplier);
		}
		
		@Override
		public String describe() {
			return new ToStringBuilder(this)
				.append("adding new skytouching recipe: ")
				.append("out", out)
				.append("in", in)
				.append("minY", minY)
				.append("maxY", maxY)
				.append("multiplier", multiplier)
				.build();
		}
	}
	
	public static class RemoveForAction implements IAction {
		public RemoveForAction(ItemStack removalTarget) {
			this.removalTarget = removalTarget;
		}
		
		private final ItemStack removalTarget;
		
		@Override
		public void apply() {
			IncorporeticSkytouchingRecipes.ALL.removeIf(r -> {
				return ItemStack.areItemsEqualIgnoreDurability(r.out, removalTarget);
			});
		}
		
		@Override
		public String describe() {
			return new ToStringBuilder(this)
				.append("removing skytouching recipe for: ")
				.append("removalTarget", removalTarget)
				.build();
		}
	}
	
	public static class NukeAllAction implements IAction {
		@Override
		public void apply() {
			IncorporeticSkytouchingRecipes.ALL.clear();
		}
		
		@Override
		public String describe() {
			return "removing all of the skytouching recipes >:D";
		}
	}
}
