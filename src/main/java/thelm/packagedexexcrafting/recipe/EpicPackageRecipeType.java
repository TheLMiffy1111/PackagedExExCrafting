package thelm.packagedexexcrafting.recipe;

import java.util.List;
import java.util.stream.IntStream;

import com.blakebr0.extendedcrafting.crafting.recipe.ShapelessTableRecipe;
import com.blakebr0.extendedcrafting.init.ModBlocks;
import com.google.common.collect.ImmutableList;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntRBTreeSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import thelm.packagedauto.api.IPackageRecipeInfo;
import thelm.packagedauto.api.IPackageRecipeType;
import thelm.packagedauto.api.IRecipeSlotViewWrapper;
import thelm.packagedauto.api.IRecipeSlotsViewWrapper;
import thelm.packagedexcrafting.recipe.UltimatePackageRecipeType;

public class EpicPackageRecipeType implements IPackageRecipeType {

	public static final EpicPackageRecipeType INSTANCE = new EpicPackageRecipeType();
	public static final ResourceLocation NAME = new ResourceLocation("packagedexexcrafting:epic");
	public static final IntSet SLOTS;
	public static final IntSet SLOTS_BORDER;
	public static final List<ResourceLocation> CATEGORIES = ImmutableList.of(
			new ResourceLocation("extendedcrafting:epic_crafting"),
			new ResourceLocation("extendedcrafting:ultimate_crafting"),
			new ResourceLocation("extendedcrafting:elite_crafting"),
			new ResourceLocation("extendedcrafting:advanced_crafting"),
			new ResourceLocation("extendedcrafting:basic_crafting"));
	public static final Vec3i COLOR = new Vec3i(139, 139, 139);
	public static final Vec3i COLOR_BORDER = new Vec3i(139, 179, 139);
	public static final Vec3i COLOR_DISABLED = new Vec3i(64, 64, 64);

	static {
		SLOTS = new IntRBTreeSet();
		IntStream.range(0, 81).forEachOrdered(SLOTS::add);
		SLOTS_BORDER = new IntRBTreeSet(SLOTS);
		for(int i = 1; i < 8; ++i) {
			for(int j = 1; j < 8; ++j) {
				SLOTS_BORDER.remove(9*i+j);
			}
		}
	}

	protected EpicPackageRecipeType() {}

	@Override
	public ResourceLocation getName() {
		return NAME;
	}

	@Override
	public MutableComponent getDisplayName() {
		return Component.translatable("recipe.packagedexexcrafting.epic");
	}

	@Override
	public MutableComponent getShortDisplayName() {
		return Component.translatable("recipe.packagedexexcrafting.epic.short");
	}

	@Override
	public IPackageRecipeInfo getNewRecipeInfo() {
		return new EpicPackageRecipeInfo();
	}

	@Override
	public IntSet getEnabledSlots() {
		return SLOTS;
	}

	@Override
	public List<ResourceLocation> getJEICategories() {
		return CATEGORIES;
	}

	@Override
	public Int2ObjectMap<ItemStack> getRecipeTransferMap(IRecipeSlotsViewWrapper recipeLayoutWrapper) {
		Int2ObjectMap<ItemStack> map = new Int2ObjectOpenHashMap<>();
		List<IRecipeSlotViewWrapper> slotViews = recipeLayoutWrapper.getRecipeSlotViews();
		int tier = 0;
		boolean shapeless = false;
		if(recipeLayoutWrapper.getRecipe() instanceof ShapelessTableRecipe shapelessRecipe) {
			tier = shapelessRecipe.getTier();
			shapeless = true;
		}
		if(tier == 0 && slotViews.size() == 122 || tier == 5) {
			int index = 0;
			for(IRecipeSlotViewWrapper slotView : slotViews) {
				if(slotView.isInput()) {
					Object displayed = slotView.getDisplayedIngredient().orElse(null);
					if(displayed instanceof ItemStack stack && !stack.isEmpty()) {
						map.put(index, stack);
					}
					++index;
				}
				if(index >= 121) {
					break;
				}
			}
			return EpicPackageRecipeHelper.INSTANCE.matrixToEncoder(map, shapeless);
		}
		return UltimatePackageRecipeType.INSTANCE.getRecipeTransferMap(recipeLayoutWrapper);
	}

	@Override
	public Object getRepresentation() {
		return new ItemStack(ModBlocks.EPIC_TABLE.get());
	}

	@Override
	public Vec3i getSlotColor(int slot) {
		if(SLOTS_BORDER.contains(slot)) {
			return COLOR_BORDER;
		}
		if(slot >= 81 && slot != 81 && slot < 90) {
			return COLOR_DISABLED;
		}
		return COLOR;
	}
}
