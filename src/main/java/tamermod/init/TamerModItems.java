package tamermod.init;

import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tamermod.TamerMod;

public class TamerModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, TamerMod.MODID);
	public static final RegistryObject<Item> MOLECULAR_TRANSFORMER = createBlockItem(TamerModBlocks.MEGA_MACHINE_BLOCK, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> aaaa = createBlockItem(TamerModBlocks.BASE_INJECTOR_BLOCK, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> vbbad = createBlockItem(TamerModBlocks.BASE_FUSION_CORE_BLOCK, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> SOAP_WATER_BUCKET = REGISTRY.register("soap_water_bucket",
			() -> new BucketItem(TamerModFluids.SOURCE_SOAP_WATER,
					new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE).craftRemainder(Items.BUCKET).stacksTo(1)));
	private static RegistryObject<Item> createBlockItem(RegistryObject<Block> block, CreativeModeTab tab) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
	}
}
