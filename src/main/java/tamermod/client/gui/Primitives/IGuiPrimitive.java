package tamermod.client.gui.Primitives;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Widget;

public interface IGuiPrimitive extends Widget {
    IGuiPrimitive EMPTY = new IGuiPrimitive() {
        @Override
        public int getWidth() {
            return 0;
        }

        @Override
        public int getHeight() {
            return 0;
        }

        @Override
        public void render(PoseStack poseStack, int blitOffset) {

        }

        @Override
        public void render(PoseStack poseStack, int blitOffset, int x, int y) {

        }

        @Override
        public void render(PoseStack poseStack, int blitOffset, int x, int y, int width, int height) {

        }
        @Override
        public IGuiPrimitive getPart(int x, int y, int width, int height) {
            return null;
        }
    };
    int getWidth();
    int getHeight();
    default void render(PoseStack poseStack){render(poseStack,0);}
    void render(PoseStack poseStack, int blitOffset);
    default void render(PoseStack poseStack, int x,int y){render(poseStack,0,x,y);}
    void render(PoseStack poseStack,int blitOffset, int x,int y);
    default void render(PoseStack poseStack, int x, int y, int width, int height){render(poseStack,0,x,y,width,height);}
    void render(PoseStack poseStack,int blitOffset, int x, int y, int width, int height);
    default void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick){
        render(poseStack);
    }
    IGuiPrimitive getPart(int x,int y, int width, int height);
}
