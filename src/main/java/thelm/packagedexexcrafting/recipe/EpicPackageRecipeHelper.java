package thelm.packagedexexcrafting.recipe;

import java.util.ArrayList;
import java.util.List;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import thelm.packagedauto.api.IPackageItem;
import thelm.packagedauto.api.IPackageRecipeInfo;
import thelm.packagedauto.item.PackageItem;
import thelm.packagedauto.recipe.IPositionedProcessingPackageRecipeInfo;
import thelm.packagedauto.recipe.PositionedProcessingPackageRecipeType;

public class EpicPackageRecipeHelper {

	public static final EpicPackageRecipeHelper INSTANCE = new EpicPackageRecipeHelper();

	private EpicPackageRecipeHelper() {}

	public Int2ObjectMap<ItemStack> encoderToMatrix(List<ItemStack> input) {
		Int2ObjectMap<ItemStack> matrix = new Int2ObjectOpenHashMap<>();
		for(int i = 0; i < 81; ++i) {
			input.get(i).setCount(1);
		}
		List<ItemStack> cornerNW = itemToCorner(input.get(0), 3);
		matrix.put(0, cornerNW.get(0));
		matrix.put(1, cornerNW.get(1));
		matrix.put(11, cornerNW.get(2));
		matrix.put(12, cornerNW.get(3));
		List<ItemStack> cornerNE = itemToCorner(input.get(8), 2);
		matrix.put(9, cornerNE.get(0));
		matrix.put(10, cornerNE.get(1));
		matrix.put(20, cornerNE.get(2));
		matrix.put(21, cornerNE.get(3));
		List<ItemStack> cornerSW = itemToCorner(input.get(72), 1);
		matrix.put(99, cornerSW.get(0));
		matrix.put(100, cornerSW.get(1));
		matrix.put(110, cornerSW.get(2));
		matrix.put(111, cornerSW.get(3));
		List<ItemStack> cornerSE = itemToCorner(input.get(80), 0);
		matrix.put(108, cornerSE.get(0));
		matrix.put(109, cornerSE.get(1));
		matrix.put(119, cornerSE.get(2));
		matrix.put(120, cornerSE.get(3));
		for(int i = 1; i < 8; ++i) {
			List<ItemStack> sideN = itemToVertical(input.get(i), 1);
			matrix.put(1+i, sideN.get(0));
			matrix.put(12+i, sideN.get(1));
		}
		for(int i = 1; i < 8; ++i) {
			List<ItemStack> sideS = itemToVertical(input.get(72+i), 0);
			matrix.put(100+i, sideS.get(0));
			matrix.put(111+i, sideS.get(1));
		}
		for(int i = 1; i < 8; ++i) {
			List<ItemStack> sideW = itemToHorizontal(input.get(i*9), 1);
			matrix.put(11+i*11, sideW.get(0));
			matrix.put(12+i*11, sideW.get(1));
		}
		for(int i = 1; i < 8; ++i) {
			List<ItemStack> sideE = itemToHorizontal(input.get(8+i*9), 0);
			matrix.put(20+i*11, sideE.get(0));
			matrix.put(21+i*11, sideE.get(1));
		}
		for(int i = 1; i < 8; ++i) {
			for(int j = 1; j < 8; ++j) {
				matrix.put(12+i*11+j, input.get(i*9+j).copy());
			}
		}
		for(int i = 0; i < 121; ++i) {
			if(matrix.get(i).isEmpty()) {
				matrix.remove(i);
			}
		}
		return matrix;
	}

	public Int2ObjectMap<ItemStack> matrixToEncoder(Int2ObjectMap<ItemStack> matrix, boolean shapeless) {
		Int2ObjectMap<ItemStack> map = new Int2ObjectOpenHashMap<>();
		if(shapeless && matrix.size() <= 81) {
			List<ItemStack> list = new ArrayList<>();
			for(int i = 0; i < 121; ++i) {
				ItemStack stack = matrix.getOrDefault(i, ItemStack.EMPTY);
				if(!stack.isEmpty()) {
					list.add(stack);
				}
			}
			for(int i = 0; i < list.size(); ++i) {
				map.put(i, list.get(i));
			}
			return map;
		}
		ItemStack cornerNW = cornerToItem(
				matrix.getOrDefault(0, ItemStack.EMPTY), matrix.getOrDefault(1, ItemStack.EMPTY),
				matrix.getOrDefault(11, ItemStack.EMPTY), matrix.getOrDefault(12, ItemStack.EMPTY),
				3);
		map.put(0, cornerNW);
		ItemStack cornerNE = cornerToItem(
				matrix.getOrDefault(9, ItemStack.EMPTY), matrix.getOrDefault(10, ItemStack.EMPTY),
				matrix.getOrDefault(20, ItemStack.EMPTY), matrix.getOrDefault(21, ItemStack.EMPTY),
				2);
		map.put(8, cornerNE);
		ItemStack cornerSW = cornerToItem(
				matrix.getOrDefault(99, ItemStack.EMPTY), matrix.getOrDefault(100, ItemStack.EMPTY),
				matrix.getOrDefault(110, ItemStack.EMPTY), matrix.getOrDefault(111, ItemStack.EMPTY),
				1);
		map.put(72, cornerSW);
		ItemStack cornerSE = cornerToItem(
				matrix.getOrDefault(108, ItemStack.EMPTY), matrix.getOrDefault(109, ItemStack.EMPTY),
				matrix.getOrDefault(119, ItemStack.EMPTY), matrix.getOrDefault(120, ItemStack.EMPTY),
				0);
		map.put(80, cornerSE);
		for(int i = 1; i < 8; ++i) {
			ItemStack sideN = verticalToItem(
					matrix.getOrDefault(1+i, ItemStack.EMPTY), matrix.getOrDefault(12+i, ItemStack.EMPTY),
					1);
			map.put(i, sideN);
		}
		for(int i = 1; i < 8; ++i) {
			ItemStack sideS = verticalToItem(
					matrix.getOrDefault(100+i, ItemStack.EMPTY), matrix.getOrDefault(111+i, ItemStack.EMPTY),
					0);
			map.put(72+i, sideS);
		}
		for(int i = 1; i < 8; ++i) {
			ItemStack sideW = horizontalToItem(
					matrix.getOrDefault(11+i*11, ItemStack.EMPTY), matrix.getOrDefault(12+i*11, ItemStack.EMPTY),
					1);
			map.put(i*9, sideW);
		}
		for(int i = 1; i < 8; ++i) {
			ItemStack sideE = horizontalToItem(
					matrix.getOrDefault(20+i*11, ItemStack.EMPTY), matrix.getOrDefault(21+i*11, ItemStack.EMPTY),
					0);
			map.put(8+i*9, sideE);
		}
		for(int i = 1; i < 8; ++i) {
			for(int j = 1; j < 8; ++j) {
				map.put(i*9+j, matrix.getOrDefault(12+i*11+j, ItemStack.EMPTY).copy());
			}
		}
		for(int i = 0; i < 81; ++i) {
			if(map.get(i).isEmpty()) {
				map.remove(i);
			}
		}
		return map;
	}

	public List<ItemStack> itemToCorner(ItemStack input, int corner) {
		List<ItemStack> list = NonNullList.withSize(4, ItemStack.EMPTY);
		if(input.isEmpty()) {
			return list;
		}
		else if(input.getItem() instanceof IPackageItem packageItem) {
			IPackageRecipeInfo recipe = packageItem.getRecipeInfo(input);
			if(recipe instanceof IPositionedProcessingPackageRecipeInfo positionedRecipe) {
				Int2ObjectMap<ItemStack> matrix = positionedRecipe.getMatrix();
				list.set(0, matrix.getOrDefault(30, ItemStack.EMPTY).copy());
				list.set(1, matrix.getOrDefault(32, ItemStack.EMPTY).copy());
				list.set(2, matrix.getOrDefault(48, ItemStack.EMPTY).copy());
				list.set(3, matrix.getOrDefault(50, ItemStack.EMPTY).copy());
				for(ItemStack stack : list) {
					stack.setCount(1);
				}
				return list;
			}
		}
		list.set(corner, input);
		return list;
	}

	public ItemStack cornerToItem(ItemStack input0, ItemStack input1, ItemStack input2, ItemStack input3, int corner) {
		List<ItemStack> inputs = List.of(input0, input1, input2, input3);
		long nonEmptyCount = inputs.stream().filter(s->!s.isEmpty()).count();
		if(nonEmptyCount == 0) {
			return ItemStack.EMPTY;
		}
		if(nonEmptyCount == 1 && !inputs.get(corner).isEmpty()) {
			ItemStack stack = inputs.get(corner);
			if(!(stack.getItem() instanceof IPackageItem packageItem && packageItem.getRecipeInfo(stack) instanceof IPositionedProcessingPackageRecipeInfo)) {
				return stack;
			}
		}
		List<ItemStack> list = NonNullList.withSize(81, ItemStack.EMPTY);
		list.set(30, input0);
		list.set(32, input1);
		list.set(48, input2);
		list.set(50, input3);
		IPackageRecipeInfo recipe = PositionedProcessingPackageRecipeType.INSTANCE.getNewRecipeInfo();
		recipe.generateFromStacks(list, List.of(), null);
		return PackageItem.makePackage(recipe, 0);
	}

	public List<ItemStack> itemToVertical(ItemStack input, int side) {
		List<ItemStack> list = NonNullList.withSize(2, ItemStack.EMPTY);
		if(input.isEmpty()) {
			return list;
		}
		else if(input.getItem() instanceof IPackageItem packageItem) {
			IPackageRecipeInfo recipe = packageItem.getRecipeInfo(input);
			if(recipe instanceof IPositionedProcessingPackageRecipeInfo positionedRecipe) {
				Int2ObjectMap<ItemStack> matrix = positionedRecipe.getMatrix();
				list.set(0, matrix.getOrDefault(31, ItemStack.EMPTY).copy());
				list.set(1, matrix.getOrDefault(49, ItemStack.EMPTY).copy());
				for(ItemStack stack : list) {
					stack.setCount(1);
				}
				return list;
			}
		}
		list.set(side, input);
		return list;
	}

	public ItemStack verticalToItem(ItemStack input0, ItemStack input1, int side) {
		List<ItemStack> inputs = List.of(input0, input1);
		long nonEmptyCount = inputs.stream().filter(s->!s.isEmpty()).count();
		if(nonEmptyCount == 0) {
			return ItemStack.EMPTY;
		}
		if(nonEmptyCount == 1 && !inputs.get(side).isEmpty()) {
			ItemStack stack = inputs.get(side);
			if(!(stack.getItem() instanceof IPackageItem packageItem && packageItem.getRecipeInfo(stack) instanceof IPositionedProcessingPackageRecipeInfo)) {
				return stack;
			}
		}
		List<ItemStack> list = NonNullList.withSize(81, ItemStack.EMPTY);
		list.set(31, input0);
		list.set(49, input1);
		IPackageRecipeInfo recipe = PositionedProcessingPackageRecipeType.INSTANCE.getNewRecipeInfo();
		recipe.generateFromStacks(list, List.of(), null);
		return PackageItem.makePackage(recipe, 0);
	}

	public List<ItemStack> itemToHorizontal(ItemStack input, int side) {
		List<ItemStack> list = NonNullList.withSize(2, ItemStack.EMPTY);
		if(input.isEmpty()) {
			return list;
		}
		else if(input.getItem() instanceof IPackageItem packageItem) {
			IPackageRecipeInfo recipe = packageItem.getRecipeInfo(input);
			if(recipe instanceof IPositionedProcessingPackageRecipeInfo positionedRecipe) {
				Int2ObjectMap<ItemStack> matrix = positionedRecipe.getMatrix();
				list.set(0, matrix.getOrDefault(39, ItemStack.EMPTY).copy());
				list.set(1, matrix.getOrDefault(41, ItemStack.EMPTY).copy());
				for(ItemStack stack : list) {
					stack.setCount(1);
				}
				return list;
			}
		}
		list.set(side, input);
		return list;
	}

	public ItemStack horizontalToItem(ItemStack input0, ItemStack input1, int side) {
		List<ItemStack> inputs = List.of(input0, input1);
		long nonEmptyCount = inputs.stream().filter(s->!s.isEmpty()).count();
		if(nonEmptyCount == 0) {
			return ItemStack.EMPTY;
		}
		if(nonEmptyCount == 1 && !inputs.get(side).isEmpty()) {
			ItemStack stack = inputs.get(side);
			if(!(stack.getItem() instanceof IPackageItem packageItem && packageItem.getRecipeInfo(stack) instanceof IPositionedProcessingPackageRecipeInfo)) {
				return stack;
			}
		}
		List<ItemStack> list = NonNullList.withSize(81, ItemStack.EMPTY);
		list.set(39, input0);
		list.set(41, input1);
		IPackageRecipeInfo recipe = PositionedProcessingPackageRecipeType.INSTANCE.getNewRecipeInfo();
		recipe.generateFromStacks(list, List.of(), null);
		return PackageItem.makePackage(recipe, 0);
	}
}
