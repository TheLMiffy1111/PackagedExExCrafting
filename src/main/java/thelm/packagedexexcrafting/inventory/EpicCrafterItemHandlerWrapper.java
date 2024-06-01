package thelm.packagedexexcrafting.inventory;

import java.util.stream.IntStream;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import thelm.packagedauto.inventory.SidedItemHandlerWrapper;

public class EpicCrafterItemHandlerWrapper extends SidedItemHandlerWrapper<EpicCrafterItemHandler> {

	public static final int[] SLOTS = IntStream.rangeClosed(0, 121).toArray();

	public EpicCrafterItemHandlerWrapper(EpicCrafterItemHandler itemHandler, Direction direction) {
		super(itemHandler, direction);
	}

	@Override
	public int[] getSlotsForDirection(Direction direction) {
		return SLOTS;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack stack, Direction direction) {
		return false;
	}

	@Override
	public boolean canExtractItem(int index, Direction direction) {
		return itemHandler.blockEntity.isWorking ? index == 121 : true;
	}
}
