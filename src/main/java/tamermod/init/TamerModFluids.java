package tamermod.init;

import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tamermod.TamerMod;

public class TamerModFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, TamerMod.MODID);
    public static final RegistryObject<FlowingFluid> SOURCE_SOAP_WATER = FLUIDS.register("soap_water_fluid",
            () -> new ForgeFlowingFluid.Source(TamerModFluids.SOAP_WATER_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_SOAP_WATER = FLUIDS.register("flowing_soap_water",
            () -> new ForgeFlowingFluid.Flowing(TamerModFluids.SOAP_WATER_FLUID_PROPERTIES));
    public static final ForgeFlowingFluid.Properties SOAP_WATER_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            TamerModFluidTypes.SOAP_WATER_FLUID_TYPE, SOURCE_SOAP_WATER, FLOWING_SOAP_WATER)
            .block(TamerModBlocks.SOAP_WATER_BLOCK)
            .bucket(TamerModItems.SOAP_WATER_BUCKET);
    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
