package tamermod.init;

import net.minecraftforge.common.SoundActions;
import tamermod.TamerMod;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tamermod.fluids.WaterBasedFluidType;

import java.util.function.Supplier;

public class TamerModFluidTypes {
    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, TamerMod.MODID);

    public static final RegistryObject<FluidType> SOAP_WATER_FLUID_TYPE = register("soap_water_fluid",()->new WaterBasedFluidType(
            FluidType.Properties.create().fallDistanceModifier(0F)
                    .canExtinguish(true)
                    .canConvertToSource(true)
                    .supportsBoating(true)
                    .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                    .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                    .sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH)
                    .canHydrate(true),0xFFFF007F));
    private static RegistryObject<FluidType> register(String name, Supplier<FluidType> sup) {
        return FLUID_TYPES.register(name, sup);
    }
    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}
