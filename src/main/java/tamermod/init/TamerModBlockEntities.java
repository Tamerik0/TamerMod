
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package tamermod.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tamermod.TamerMod;
import tamermod.blocks.blockentities.*;

public class TamerModBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TamerMod.MODID);
	public static final RegistryObject<BlockEntityType<MegaMachineBlockEntity>> MEGA_MACHINE_BLOCK_ENTITY_TYPE = register("molecular_transformer",
			TamerModBlocks.MEGA_MACHINE_BLOCK,
			(pos, state) -> new MegaMachineBlockEntity(TamerModBlockEntities.MEGA_MACHINE_BLOCK_ENTITY_TYPE.get(), pos, state));
	public static final RegistryObject<BlockEntityType<BaseInjectorBlockEntity>> BASE_INJECTOR_BLOCK_ENTITY_TYPE = register("base_injector",
			TamerModBlocks.BASE_INJECTOR_BLOCK,
			(pos, state) -> new BaseInjectorBlockEntity(TamerModBlockEntities.BASE_INJECTOR_BLOCK_ENTITY_TYPE.get(), pos, state));
	public static final RegistryObject<BlockEntityType<BaseFusionCoreBlockEntity>> BASE_FUSION_CORE_BLOCK_ENTITY_TYPE = register("base_fusion_core",
			TamerModBlocks.BASE_FUSION_CORE_BLOCK,
			(pos, state) -> new BaseFusionCoreBlockEntity(TamerModBlockEntities.BASE_FUSION_CORE_BLOCK_ENTITY_TYPE.get(), pos, state));


	private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String registryname, RegistryObject<Block> block, BlockEntityType.BlockEntitySupplier<T> supplier) {
		return REGISTRY.register(registryname, () -> BlockEntityType.Builder.of(supplier, block.get()).build(null));
	}
}
