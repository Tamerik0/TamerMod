package tamermod;


import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import tamermod.init.*;
import tamermod.init.TamerModRecipeTypes;
import tamermod.init.TamerModRecipes;
import tamermod.blocks.blockentities.*;

@Mod(TamerMod.MODID)
public class TamerMod {
    public static final String MODID = "tamermod";
    public TamerMod(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.register(this);
        TamerModBlocks.REGISTRY.register(bus);
        TamerModItems.REGISTRY.register(bus);
        TamerModBlockEntities.REGISTRY.register(bus);
        TamerModMenus.REGISTRY.register(bus);
        TamerModRecipeTypes.register(bus);
        TamerModRecipes.register(bus);
        TamerModFluidTypes.register(bus);
        TamerModFluids.register(bus);
    }
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ItemBlockRenderTypes.setRenderLayer(TamerModFluids.SOURCE_SOAP_WATER.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(TamerModFluids.FLOWING_SOAP_WATER.get(), RenderType.translucent());
        }
        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(TamerModBlockEntities.BASE_INJECTOR_BLOCK_ENTITY_TYPE.get(),
                    InjectorRenderer::new);
        }
    }
}
