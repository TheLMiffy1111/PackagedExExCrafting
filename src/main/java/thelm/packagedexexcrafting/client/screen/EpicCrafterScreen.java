package thelm.packagedexexcrafting.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import thelm.packagedauto.client.screen.BaseScreen;
import thelm.packagedexexcrafting.menu.EpicCrafterMenu;

public class EpicCrafterScreen extends BaseScreen<EpicCrafterMenu> {

	public static final ResourceLocation BACKGROUND = new ResourceLocation("packagedexexcrafting:textures/gui/epic_crafter.png");

	public EpicCrafterScreen(EpicCrafterMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
		imageWidth = 306;
		imageHeight = 310;
	}

	@Override
	protected ResourceLocation getBackgroundTexture() {
		return BACKGROUND;
	}

	@Override
	protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
		super.renderBg(graphics, partialTicks, mouseX, mouseY);
		graphics.blit(BACKGROUND, leftPos+246, topPos+107, 306, 0, menu.blockEntity.getScaledProgress(22), 16, 512, 512);
		int scaledEnergy = menu.blockEntity.getScaledEnergy(40);
		graphics.blit(BACKGROUND, leftPos+10, topPos+82+40-scaledEnergy, 306, 16+40-scaledEnergy, 12, scaledEnergy, 512, 512);
	}

	@Override
	protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
		String s = menu.blockEntity.getDisplayName().getString();
		graphics.drawString(font, s, imageWidth/2 - font.width(s)/2, 6, 0x404040, false);
		graphics.drawString(font, menu.inventory.getDisplayName().getString(), menu.getPlayerInvX(), menu.getPlayerInvY()-11, 0x404040, false);
		if(mouseX-leftPos >= 10 && mouseY-topPos >= 82 && mouseX-leftPos <= 21 && mouseY-topPos <= 121) {
			graphics.renderTooltip(font, Component.literal(menu.blockEntity.getEnergyStorage().getEnergyStored()+" / "+menu.blockEntity.getEnergyStorage().getMaxEnergyStored()+" FE"), mouseX-leftPos, mouseY-topPos);
		}
	}
}
