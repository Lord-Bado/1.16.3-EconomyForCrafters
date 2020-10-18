package com.bado.economyforcrafters.client.gui;

import com.bado.economyforcrafters.EconomyForCrafters;
import com.bado.economyforcrafters.container.MarketizerContainer;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class MarketizerScreen extends ContainerScreen<MarketizerContainer> {

	public MarketizerScreen(MarketizerContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		this.guiLeft = 0;
		this.guiTop = 0;
		this.xSize = 275;
		this.ySize = 165;
	}

	private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(EconomyForCrafters.MOD_ID,
			"textures/gui/market.png");

	// This is the gross unmapped renderer
	@Override
	public void func_230430_a_(MatrixStack matrix, final int mouseX, final int mouseY, final float partialTicks) {
		super.func_230430_a_(matrix, mouseX, mouseY, partialTicks);

	}

	@Override
	protected void func_230450_a_(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {

	}

}
