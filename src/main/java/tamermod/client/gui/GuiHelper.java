package tamermod.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GuiHelper extends GuiComponent {
    public static final GuiHelper INSTANCE = new GuiHelper();
    static {
        var texture = Minecraft.getInstance().getTextureManager().getTexture(new ResourceLocation("tamermod:textures/gui/screens/mega_machine_gui.png"));
        texture.bind();
    }
    public static int[] hexToIntArray(int color){
        int [] ans = new int[4];
        ans[0]=(int)((color&0xFF000000l)>>24);
        ans[1]=(int)((color&0x00FF0000l)>>16);
        ans[2]=(int)((color&0x0000FF00l)>>8);
        ans[3]=(int)((color&0x000000FFl)>>0);
        return ans;
    }
    public static float[] hexToFloatArray(int color){
        int[] t = hexToIntArray(color);
        float ans[]=new float[4];
        for(int i = 0;i<4;i++)
            ans[i]=(float)t[i]/255;
        return ans;
    }

    public static void blitTexturePartial(PoseStack poseStack, int blitOffset, int x, int y, int width, int height, int textureX,int textureY,int texturePartWidth,int texturePartHeight,int textureWidth,int textureHeight,ResourceLocation texture){
        if(textureWidth==0||textureHeight==0)
            return;
        RenderSystem.setShaderTexture(0,texture);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        Matrix4f matrix = poseStack.last().pose();
        bufferBuilder.vertex(matrix, x, y+height,         (float)blitOffset).uv((float)textureX/textureWidth, (float)(textureY+texturePartHeight)/textureHeight).endVertex();
        bufferBuilder.vertex(matrix, x+width, y+height,(float)blitOffset).uv((float)(textureX+texturePartWidth)/textureWidth, (float)(textureY+texturePartHeight)/textureHeight).endVertex();
        bufferBuilder.vertex(matrix, x+width, y,          (float)blitOffset).uv((float)(textureX+texturePartWidth)/textureWidth, (float)textureY/textureHeight).endVertex();
        bufferBuilder.vertex(matrix, x, y,                   (float)blitOffset).uv((float)textureX/textureWidth, (float)textureY/textureHeight).endVertex();
        BufferUploader.drawWithShader(bufferBuilder.end());
    }
    public static void blitSpritePartial(PoseStack poseStack, int blitOffset, int x, int y, int width, int height, int spriteX,int spriteY,int spriteWidth,int spriteHeight, TextureAtlasSprite sprite){
        double atlasWidth = sprite.getWidth()/(sprite.getU1()-sprite.getU0());
        double atlasHeight = sprite.getHeight()/(sprite.getV1()-sprite.getV0());
        blitTexturePartial(poseStack,blitOffset,x,y,width,height,(int)(sprite.getU0()*atlasWidth+spriteX),(int)(sprite.getV0()*atlasHeight+spriteY),spriteWidth,spriteHeight,(int)atlasWidth,(int)atlasHeight,sprite.atlas().location());
    }
    public static Screen getCurrentScreen(){
        return Minecraft.getInstance().screen;
    }
    public static void renderTooltip(PoseStack poseStack, List<Component> textComponents, Optional<TooltipComponent> tooltipComponent, int x, int y, @Nullable Font font, ItemStack stack) {
        getCurrentScreen().renderTooltip(poseStack, textComponents,tooltipComponent,x,y,font,stack);
    }
    public static void renderTooltip(PoseStack poseStack, List<Component> tooltips, Optional<TooltipComponent> visualTooltipComponent, int mouseX, int mouseY) {
        getCurrentScreen().renderTooltip(poseStack,tooltips,visualTooltipComponent,mouseX,mouseY);
    }
    public static List<Component> getTooltipFromItem(ItemStack itemStack) {
        return itemStack.getTooltipLines(Minecraft.getInstance().player, Minecraft.getInstance().options.advancedItemTooltips ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL);
    }
    public static void renderTooltip(PoseStack poseStack, Component text, int mouseX, int mouseY) {
        getCurrentScreen().renderTooltip(poseStack,text,mouseX,mouseY);
    }
    public static void  renderComponentTooltip(PoseStack poseStack, List<Component> tooltips, int mouseX, int mouseY) {
        getCurrentScreen().renderComponentTooltip(poseStack,tooltips,mouseX,mouseY);
    }
    public static void renderComponentTooltip(PoseStack poseStack, List<? extends FormattedText> tooltips, int mouseX, int mouseY, ItemStack stack) {
        getCurrentScreen().renderComponentTooltip(poseStack,tooltips,mouseX,mouseY,stack);
    }
    public static void renderComponentTooltip(PoseStack poseStack, List<? extends FormattedText> tooltips, int mouseX, int mouseY, @Nullable Font font) {
        getCurrentScreen().renderComponentTooltip(poseStack,tooltips,mouseX,mouseY,font);
    }
    public static void  renderComponentTooltip(PoseStack poseStack, List<? extends FormattedText> tooltips, int mouseX, int mouseY, @Nullable Font font, ItemStack stack) {
        getCurrentScreen().renderComponentTooltip(poseStack,tooltips,mouseX,mouseY,font,stack);
    }
    public static void renderTooltip(PoseStack poseStack, List<? extends FormattedCharSequence> tooltips, int mouseX, int mouseY) {
        getCurrentScreen().renderTooltip(poseStack,tooltips,mouseX,mouseY);
    }
    public static void  renderTooltip(PoseStack poseStack, List<? extends FormattedCharSequence> lines, int x, int y, Font font) {
        getCurrentScreen().renderTooltip(poseStack,lines,x,y,font);
    }
    public static void initSlots(AbstractContainerMenu menu, GUIContainer gui){
        for(int i = 0;i<menu.slots.size();i++) {
            var slot = gui.addWidget(new SlotGui(18,18));
            slot.x=menu.slots.get(i).x;
            slot.y=menu.slots.get(i).y;
        }
    }
    public static List<SlotGui> getSlotsGui(AbstractContainerMenu menu){
        var ans = new ArrayList<SlotGui>();
        for(int i = 0;i<menu.slots.size();i++) {
            var slot = new SlotGui(18,18);
            slot.x=menu.slots.get(i).x;
            slot.y=menu.slots.get(i).y;
            ans.add(slot);
        }
        return ans;
    }

}
