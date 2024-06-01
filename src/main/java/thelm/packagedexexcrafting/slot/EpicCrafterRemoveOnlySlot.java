package thelm.packagedexexcrafting.slot;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import thelm.packagedexexcrafting.block.entity.EpicCrafterBlockEntity;

//Code from CoFHCore
public class EpicCrafterRemoveOnlySlot extends SlotItemHandler {

	public final EpicCrafterBlockEntity blockEntity;

	public EpicCrafterRemoveOnlySlot(EpicCrafterBlockEntity blockEntity, int index, int x, int y) {
		super(blockEntity.getItemHandler(), index, x, y);
		this.blockEntity = blockEntity;
	}

	@Override
	public boolean mayPickup(Player player) {
		return !blockEntity.isWorking;
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		return false;
	}
}
