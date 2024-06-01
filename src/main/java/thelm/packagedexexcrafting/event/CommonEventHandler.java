package thelm.packagedexexcrafting.event;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import thelm.packagedauto.util.ApiImpl;
import thelm.packagedexexcrafting.block.EpicCrafterBlock;
import thelm.packagedexexcrafting.block.entity.EpicCrafterBlockEntity;
import thelm.packagedexexcrafting.config.PackagedExExCraftingConfig;
import thelm.packagedexexcrafting.menu.EpicCrafterMenu;
import thelm.packagedexexcrafting.recipe.EpicPackageRecipeType;

public class CommonEventHandler {

	public static final CommonEventHandler INSTANCE = new CommonEventHandler();

	public static CommonEventHandler getInstance() {
		return INSTANCE;
	}

	public void onConstruct() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.register(this);
		PackagedExExCraftingConfig.registerConfig();

		DeferredRegister<Block> blockRegister = DeferredRegister.create(Registries.BLOCK, "packagedexexcrafting");
		blockRegister.register("epic_crafter", ()->EpicCrafterBlock.INSTANCE);
		blockRegister.register(modEventBus);

		DeferredRegister<Item> itemRegister = DeferredRegister.create(Registries.ITEM, "packagedexexcrafting");
		itemRegister.register("epic_crafter", ()->EpicCrafterBlock.ITEM_INSTANCE);
		itemRegister.register(modEventBus);

		DeferredRegister<BlockEntityType<?>> blockEntityRegister = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, "packagedexexcrafting");
		blockEntityRegister.register("epic_crafter", ()->EpicCrafterBlockEntity.TYPE_INSTANCE);
		blockEntityRegister.register(modEventBus);

		DeferredRegister<MenuType<?>> menuRegister = DeferredRegister.create(Registries.MENU, "packagedexexcrafting");
		menuRegister.register("epic_crafter", ()->EpicCrafterMenu.TYPE_INSTANCE);
		menuRegister.register(modEventBus);

		DeferredRegister<CreativeModeTab> creativeTabRegister = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "packagedexexcrafting");
		creativeTabRegister.register(modEventBus);
		creativeTabRegister.register("tab",
				()->CreativeModeTab.builder().
				title(Component.translatable("itemGroup.packagedexexcrafting")).
				icon(()->new ItemStack(EpicCrafterBlock.ITEM_INSTANCE)).
				displayItems((parameters, output)->{
					output.accept(EpicCrafterBlock.ITEM_INSTANCE);
				}).
				build());
	}

	@SubscribeEvent
	public void onCommonSetup(FMLCommonSetupEvent event) {
		ApiImpl.INSTANCE.registerRecipeType(EpicPackageRecipeType.INSTANCE);
	}

	@SubscribeEvent
	public void onModConfig(ModConfigEvent event) {
		switch(event.getConfig().getType()) {
		case SERVER -> PackagedExExCraftingConfig.reloadServerConfig();
		default -> {}
		}
	}
}
