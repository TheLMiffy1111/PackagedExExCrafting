package thelm.packagedexexcrafting.inventory;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandlerModifiable;
import thelm.packagedauto.inventory.BaseItemHandler;
import thelm.packagedexexcrafting.block.entity.EpicCrafterBlockEntity;

public class EpicCrafterItemHandler extends BaseItemHandler<EpicCrafterBlockEntity> {

	public EpicCrafterItemHandler(EpicCrafterBlockEntity blockEntity) {
		super(blockEntity, 123);
	}

	@Override
	public boolean isItemValid(int slot, ItemStack stack) {
		if(slot == 122) {
			return stack.getCapability(ForgeCapabilities.ENERGY).isPresent();
		}
		return false;
	}

	@Override
	public IItemHandlerModifiable getWrapperForDirection(Direction side) {
		return wrapperMap.computeIfAbsent(side, s->new EpicCrafterItemHandlerWrapper(this, s));
	}

	@Override
	public int get(int id) {
		return switch(id) {
		case 0 -> blockEntity.remainingProgress;
		case 1 -> blockEntity.isWorking ? 1 : 0;
		case 2 -> blockEntity.getEnergyStorage().getEnergyStored();
		default -> 0;
		};
	}

	@Override
	public void set(int id, int value) {
		switch(id) {
		case 0 -> blockEntity.remainingProgress = value;
		case 1 -> blockEntity.isWorking = value != 0;
		case 2 -> blockEntity.getEnergyStorage().setEnergyStored(value);
		}
	}

	@Override
	public int getCount() {
		return 3;
	}
}
