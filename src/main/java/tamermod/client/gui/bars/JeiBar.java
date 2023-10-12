package tamermod.client.gui.bars;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import tamermod.client.gui.GuiHelper;
import tamermod.client.gui.Primitives.IGuiPrimitive;


public class  JeiBar<T extends AbstractBar> extends AbstractBar{
    @Override
    protected void fill(PoseStack poseStack) { }
    public void render(PoseStack poseStack,int mouseX,int mouseY, float pt){
        bar.render(poseStack,mouseX,mouseY,pt);
    }
    public void renderTooltipInternal(PoseStack poseStack, int mouseX, int mouseY, float pt){
        if(tooltip!=null)
            GuiHelper.renderTooltip(poseStack, Component.literal(tooltip), mouseX, mouseY);
    }
    public interface barCreator<TT extends AbstractBar>{
        public TT create(int x,int y);
    }
    T bar;
    public T getInternalBar(){
        return bar;
    }
    String tooltip;
//    public void renderTooltip(PoseStack ps,int mouseX, int mouseY, float pt){
//        Vector4f vec = new Vector4f(0,0,0,1);
//        vec.transform(ps.last().pose());
//        int x0 = (int)vec.x(), y0=(int)vec.y();
//        System.out.print(mouseX+" "+mouseY);
//        mouseX-=x0;
//        mouseY-=y0;
//        System.out.print(" "+x0+" "+y0);
//        System.out.println(" "+mouseX+" "+mouseY);
//        super.renderTooltip(ps,mouseX,mouseY,pt);
//    }
    public JeiBar(int x,int y,String tooltip,barCreator<T> barCreator){
        super(x,y, IGuiPrimitive.EMPTY);
        bar = barCreator.create(x,y);
        w=bar.w;
        h=bar.h;
        bar.value=1;
        bar.maxvalue=1;
        this.tooltip=tooltip;
    }
}
