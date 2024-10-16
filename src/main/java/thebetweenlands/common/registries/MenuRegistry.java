package thebetweenlands.common.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import thebetweenlands.common.TheBetweenlands;
import thebetweenlands.common.inventory.*;

public class MenuRegistry {

	public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, TheBetweenlands.ID);

	public static final DeferredHolder<MenuType<?>, MenuType<AmphibiousArmorMenu>> AMPHIBIOUS_ARMOR = MENUS.register("amphibious_armor", () -> IMenuTypeExtension.create(AmphibiousArmorMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<AnimatorMenu>> ANIMATOR = MENUS.register("animator", () -> IMenuTypeExtension.create(AnimatorMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<BarrelMenu>> BARREL = MENUS.register("barrel", () ->  IMenuTypeExtension.create(BarrelMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<CenserMenu>> CENSER = MENUS.register("censer", () -> IMenuTypeExtension.create(CenserMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<CrabPotFilterMenu>> CRAB_POT_FILTER = MENUS.register("crab_pot_filter", () -> new MenuType<>(CrabPotFilterMenu::new, FeatureFlags.REGISTRY.allFlags()));
	public static final DeferredHolder<MenuType<?>, MenuType<DruidAltarMenu>> DRUID_ALTAR = MENUS.register("druid_altar", () -> IMenuTypeExtension.create(DruidAltarMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<FishingTackleBoxMenu>> FISHING_TACKLE_BOX = MENUS.register("fishing_tackle_box", () -> new MenuType<>(FishingTackleBoxMenu::new, FeatureFlags.REGISTRY.allFlags()));
	public static final DeferredHolder<MenuType<?>, MenuType<FishTrimmingTableMenu>> FISH_TRIMMING_TABLE = MENUS.register("fish_trimming_table", () -> IMenuTypeExtension.create(FishTrimmingTableMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<LurkerSkinPouchMenu>> LURKER_SKIN_POUCH = MENUS.register("lurker_skin_pouch", () -> IMenuTypeExtension.create(LurkerSkinPouchMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<MortarMenu>> MORTAR = MENUS.register("mortar", () -> new MenuType<>(MortarMenu::new, FeatureFlags.REGISTRY.allFlags()));
	public static final DeferredHolder<MenuType<?>, MenuType<PurifierMenu>> PURIFIER = MENUS.register("purifier", () -> new MenuType<>(PurifierMenu::new, FeatureFlags.REGISTRY.allFlags()));
	public static final DeferredHolder<MenuType<?>, MenuType<SilkBundleMenu>> SILK_BUNDLE = MENUS.register("silk_bundle", () -> IMenuTypeExtension.create(SilkBundleMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<SmokingRackMenu>> SMOKING_RACK = MENUS.register("smoking_rack", () -> new MenuType<>(SmokingRackMenu::new, FeatureFlags.REGISTRY.allFlags()));
	public static final DeferredHolder<MenuType<?>, MenuType<WeedwoodCraftingMenu>> WEEDWOOD_CRAFTING_TABLE = MENUS.register("weedwood_crafting_table", () -> IMenuTypeExtension.create(WeedwoodCraftingMenu::new));
}
