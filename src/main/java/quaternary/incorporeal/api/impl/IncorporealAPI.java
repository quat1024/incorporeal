package quaternary.incorporeal.api.impl;

import net.minecraft.item.ItemStack;
import quaternary.incorporeal.api.IIncorporealAPI;
import quaternary.incorporeal.api.INaturalDeviceRegistry;
import quaternary.incorporeal.api.ISimpleRegistry;
import quaternary.incorporeal.api.cygnus.ICygnusAction;
import quaternary.incorporeal.api.cygnus.ICygnusCondition;
import quaternary.incorporeal.api.cygnus.ICygnusDatatype;
import quaternary.incorporeal.api.cygnus.ILooseCygnusFunnelable;
import quaternary.incorporeal.feature.cygnusnetwork.CygnusRegistries;
import quaternary.incorporeal.feature.cygnusnetwork.lexicon.CygnusNetworkLexicon;
import quaternary.incorporeal.feature.naturaldevices.NaturalDeviceRegistry;
import quaternary.incorporeal.feature.skytouching.recipe.RecipeSkytouching;
import quaternary.incorporeal.feature.skytouching.recipe.SkytouchingRecipes;
import vazkii.botania.api.lexicon.LexiconPage;

import java.util.List;
import java.util.function.Consumer;

public class IncorporealAPI implements IIncorporealAPI {
	@Override
	public int apiVersion() {
		return 2;
	}
	
	private static final INaturalDeviceRegistry naturalDeviceRegistry = new NaturalDeviceRegistry();
	
	@Override
	public INaturalDeviceRegistry getNaturalDeviceRegistry() {
		return naturalDeviceRegistry;
	}
	
	@Override
	public ISimpleRegistry<ICygnusDatatype<?>> getCygnusDatatypeRegistry() {
		return CygnusRegistries.DATATYPES;
	}
	
	@Override
	public ISimpleRegistry<ICygnusAction> getCygnusStackActionRegistry() {
		return CygnusRegistries.ACTIONS;
	}
	
	@Override
	public ISimpleRegistry<ICygnusCondition> getCygnusStackConditionRegistry() {
		return CygnusRegistries.CONDITIONS;
	}
	
	@Override
	public void documentCygnusFunnelable(Consumer<List<LexiconPage>> doc) {
		CygnusNetworkLexicon.FUNNELABLE_DOCUMENTERS.add(doc);
	}
	
	@Override
	public void registerLooseFunnelable(ILooseCygnusFunnelable loose) {
		CygnusRegistries.LOOSE_FUNNELABLES.add(loose);
	}
	
	@Override
	public void registerSkytouchingRecipe(ItemStack out, ItemStack in, int minY, int maxY, int multiplier) {
		SkytouchingRecipes.register(new RecipeSkytouching(out, in, minY, maxY, multiplier));
	}
	
	@Override
	public void registerSkytouchingRecipe(ItemStack out, ItemStack in) {
		SkytouchingRecipes.register(new RecipeSkytouching(out, in));
	}
}
