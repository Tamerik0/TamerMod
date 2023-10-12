
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package tamermod.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.common.extensions.IForgeMenuType;

import net.minecraft.world.inventory.MenuType;

import tamermod.client.gui.MegaMachineGUIMenu;

import tamermod.TamerMod;

public class TamerModMenus {
	public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, TamerMod.MODID);
	public static final RegistryObject<MenuType<MegaMachineGUIMenu>> MEGA_MACHINE_GUI = REGISTRY.register("mega_machine_gui", () -> IForgeMenuType.create(MegaMachineGUIMenu::new));
}
