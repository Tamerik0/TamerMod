
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package tamermod.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tamermod.TamerMod;
import tamermod.blocks.BaseFusionCoreBlock;
import tamermod.blocks.BaseInjectorBlock;
import tamermod.blocks.KekBlock;
import tamermod.blocks.MegaMachineBlock;

public class TamerModBlocks {
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, TamerMod.MODID);
	public static final RegistryObject<LiquidBlock> SOAP_WATER_BLOCK = REGISTRY.register("soap_water_block",
			() -> new LiquidBlock(TamerModFluids.SOURCE_SOAP_WATER, BlockBehaviour.Properties.of(Material.WATER)));
	public static final RegistryObject<Block> MEGA_MACHINE_BLOCK = REGISTRY.register("molecular_transformer", () -> new MegaMachineBlock(()->TamerModBlockEntities.MEGA_MACHINE_BLOCK_ENTITY_TYPE.get()));
	public static final RegistryObject<Block> BASE_INJECTOR_BLOCK = REGISTRY.register("base_injector", BaseInjectorBlock::new);
	public static final RegistryObject<Block> BASE_FUSION_CORE_BLOCK = REGISTRY.register("base_fusion_core", BaseFusionCoreBlock::new);
	public static final RegistryObject<Block> KEK = REGISTRY.register("kek", KekBlock::new);
}
