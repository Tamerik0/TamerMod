package tamermod.fluids;

import com.google.gson.*;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class FluidUtils {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    public static FluidStack getFluidStackFromJson(@NotNull JsonObject json) {
        JsonElement count = json.get("amount");
        int amount = count.getAsJsonPrimitive().getAsInt();
        ResourceLocation resourceLocation = new ResourceLocation(GsonHelper.getAsString(json, "fluid"));
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(resourceLocation);
        if (fluid == null || fluid == Fluids.EMPTY) {
            return null;
        }
        CompoundTag nbt = null;
        if (json.has("nbt")) {
            JsonElement jsonNBT = json.get("nbt");
            try {
                if (jsonNBT.isJsonObject()) {
                    nbt = TagParser.parseTag(GSON.toJson(jsonNBT));
                } else {
                    nbt = TagParser.parseTag(GsonHelper.convertToString(jsonNBT, "nbt"));
                }
            } catch (CommandSyntaxException e) {
                throw new JsonSyntaxException("Invalid NBT entry for fluid '" + resourceLocation + "'");
            }
        }
        return new FluidStack(fluid, amount, nbt);
    }
    public static TextureAtlasSprite getFluidTexture(Fluid fluid) {
        return Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(IClientFluidTypeExtensions.of(fluid).getStillTexture());
    }
    public static int getFluidColor(Fluid fluid){
        return IClientFluidTypeExtensions.of(fluid).getTintColor();
    }
}
