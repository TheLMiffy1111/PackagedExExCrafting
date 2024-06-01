package thelm.packagedexexcrafting.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.items.SlotItemHandler;
import thelm.packagedauto.menu.BaseMenu;
import thelm.packagedauto.menu.factory.PositionalBlockEntityMenuFactory;
import thelm.packagedauto.slot.RemoveOnlySlot;
import thelm.packagedexexcrafting.block.entity.EpicCrafterBlockEntity;
import thelm.packagedexexcrafting.slot.EpicCrafterRemoveOnlySlot;

public class EpicCrafterMenu extends BaseMenu<EpicCrafterBlockEntity> {

	public static final MenuType<EpicCrafterMenu> TYPE_INSTANCE = IForgeMenuType.create(new PositionalBlockEntityMenuFactory<>(EpicCrafterMenu::new));

	public EpicCrafterMenu(int windowId, Inventory inventory, EpicCrafterBlockEntity blockEntity) {
		super(TYPE_INSTANCE, windowId, inventory, blockEntity);
		addSlot(new SlotItemHandler(itemHandler, 122, 8, 125));
		for(int i = 0; i < 11; ++i) {
			for(int j = 0; j < 11; ++j) {
				addSlot(new EpicCrafterRemoveOnlySlot(blockEntity, i*11+j, 44+j*18, 17+i*18));
			}
		}
		addSlot(new RemoveOnlySlot(itemHandler, 121, 278, 107));
		setupPlayerInventory();
	}

	@Override
	public int getPlayerInvX() {
		return 73;
	}

	@Override
	public int getPlayerInvY() {
		return 228;
	}
}
