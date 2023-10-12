package tamermod.fluids;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
public class WaterBasedFluidType extends FluidType {
    int tintColor;
    public WaterBasedFluidType(final Properties properties, int color) {
        super(properties);
        tintColor = color;
    }

    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer)
    {
        consumer.accept(new IClientFluidTypeExtensions()
        {
            private static final ResourceLocation
                    WATER_STILL = new ResourceLocation("block/water_still"),
                    WATER_FLOW = new ResourceLocation("block/water_flow");
            @Override
            public ResourceLocation getStillTexture()
            {
                return WATER_STILL;
            }
            @Override
            public ResourceLocation getFlowingTexture()
            {
                return WATER_FLOW;
            }
            @Override
            public int getTintColor()
            {
                return tintColor;
            }
        });
    }
}
