package tamermod.client.gui;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;
import net.minecraft.client.Minecraft;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import tamermod.blocks.blockentities.ComponentBlockEntity;
import tamermod.blocks.blockentities.components.EUBlockEntityComponent;
import tamermod.blocks.blockentities.components.FluidStorageBlockEntityComponent;
import tamermod.blocks.blockentities.components.MegaMachineCraftingComponent;
import tamermod.client.gui.Primitives.BaseTexture;
import tamermod.client.gui.Primitives.TextureSprite;
import tamermod.client.gui.bars.EUEnergyBar;
import tamermod.client.gui.bars.FluidBar;
import tamermod.client.gui.bars.ProgressArrow;
import tamermod.client.gui.bars.RFEnergyBar;

public class MegaMachineGUIScreen extends BaseScreen<MegaMachineGUIMenu> {
	private final Level world;
	private final BlockPos pos;
	private final Player entity;
	ComponentBlockEntity blockEntity;
	EUEnergyBar eubar;
	RFEnergyBar rfbar;
	FluidBar fluidbar;
	ProgressArrow progressbar;
	public MegaMachineGUIScreen(MegaMachineGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		pos=container.pos;
		this.entity = container.entity;
		this.imageWidth = 176;
		this.imageHeight = 166;
		if (world.getBlockEntity(pos) instanceof ComponentBlockEntity _blockEntity) {
			blockEntity = _blockEntity;
		}
	}
	@Override
	public void init() {
		super.init();
		this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
		var background = addRenderableOnly(new TextureSprite(new BaseTexture("tamermod:textures/gui/screens/mega_machine_gui.png",imageWidth,imageHeight)));
		background.x=leftPos;
		background.y=topPos;
		eubar = addRenderableOnly(new EUEnergyBar(leftPos + 7,topPos + 8));
		rfbar = addRenderableOnly(new RFEnergyBar(leftPos + 14,topPos + 8));
		fluidbar = addRenderableOnly(new FluidBar(leftPos + 22,topPos + 8));
		progressbar = addRenderableOnly(new ProgressArrow(leftPos + 70,topPos + 30));
		initSlots();
	}

	@Override
	public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderTooltip(ms, mouseX, mouseY);
	}
	@Override
	protected void renderBg(PoseStack ms, float partialTicks, int gx, int gy) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		EUBlockEntityComponent eu = blockEntity.getComponent(EUBlockEntityComponent.class);
		eubar.value = eu.getStoredEU();
		eubar.maxvalue = eu.getMaxEU();
		blockEntity.getCapability(ForgeCapabilities.ENERGY).ifPresent((storage)->{
			rfbar.value=storage.getEnergyStored();
			rfbar.maxvalue=storage.getMaxEnergyStored();
		});
		FluidStack fluidStack = blockEntity.getComponent(FluidStorageBlockEntityComponent.class).fluidTank.getFluid();
		fluidbar.fluid = fluidStack.getFluid();
		if(fluidStack != FluidStack.EMPTY) {
			fluidbar.value=fluidStack.getAmount();
		}
		fluidbar.maxvalue=blockEntity.getComponent(FluidStorageBlockEntityComponent.class).fluidTank.getCapacity();
		MegaMachineCraftingComponent craftingComponent = blockEntity.getComponent(MegaMachineCraftingComponent.class);
		progressbar.value=craftingComponent.progress;
		progressbar.maxvalue = craftingComponent.maxProgress;
	}
	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyPressed(key, b, c);
	}
	@Override
	public void containerTick() {
		super.containerTick();
	}
	@Override
	protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {	}
	@Override
	public void onClose() {
		super.onClose();
		Minecraft.getInstance().keyboardHandler.setSendRepeatsToGui(false);
	}
}
