package thebetweenlands.common.registries;

import java.util.UUID;

import com.mojang.serialization.Codec;

import net.minecraft.core.GlobalPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Unit;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import thebetweenlands.api.BLRegistries;
import thebetweenlands.api.item.amphibious.AmphibiousArmorUpgrade;
import thebetweenlands.common.TheBetweenlands;
import thebetweenlands.common.component.entity.circlegem.CircleGemType;
import thebetweenlands.common.component.item.AmphibiousUpgrades;
import thebetweenlands.common.component.item.AspectContents;
import thebetweenlands.common.component.item.CorrosionData;
import thebetweenlands.common.component.item.DiscoveryContainerData;
import thebetweenlands.common.component.item.ElixirContents;
import thebetweenlands.common.component.item.FishBaitStats;
import thebetweenlands.common.component.item.InfusionBucketData;
import thebetweenlands.common.component.item.OriginalItemData;
import thebetweenlands.common.component.item.ShieldSpitData;
import thebetweenlands.common.component.item.UpgradeDamage;

public class DataComponentRegistry {

	public static final DeferredRegister<DataComponentType<?>> COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, TheBetweenlands.ID);

	public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemContainerContents>> AMPHIBIOUS_ARMOR_FILTERS = COMPONENTS.register("amphibious_armor_filters", () -> DataComponentType.<ItemContainerContents>builder().persistent(ItemContainerContents.CODEC).networkSynchronized(ItemContainerContents.STREAM_CODEC).build());
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<AmphibiousUpgrades>> AMPHIBIOUS_UPGRADES = COMPONENTS.register("amphibious_upgrades", () -> DataComponentType.<AmphibiousUpgrades>builder().persistent(AmphibiousUpgrades.CODEC).networkSynchronized(AmphibiousUpgrades.STREAM_CODEC).build());
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<AspectContents>> ASPECT_CONTENTS = COMPONENTS.register("aspect_contents", () -> DataComponentType.<AspectContents>builder().persistent(AspectContents.CODEC).networkSynchronized(AspectContents.STREAM_CODEC).build());
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> BURN_TICKS = COMPONENTS.register("burn_ticks", () -> DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build());
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> CORROSIVE = COMPONENTS.register("corrosive", () -> DataComponentType.<Boolean>builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<CircleGemType>> CIRCLE_GEM = COMPONENTS.register("circle_gem", () -> DataComponentType.<CircleGemType>builder().persistent(CircleGemType.CODEC).networkSynchronized(CircleGemType.STREAM_CODEC).build());
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<DiscoveryContainerData>> DISCOVERY_DATA = COMPONENTS.register("discovery_data", () -> DataComponentType.<DiscoveryContainerData>builder().persistent(DiscoveryContainerData.CODEC).networkSynchronized(DiscoveryContainerData.STREAM_CODEC).build());
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<ElixirContents>> ELIXIR_CONTENTS = COMPONENTS.register("elixir_contents", () -> DataComponentType.<ElixirContents>builder().persistent(ElixirContents.CODEC).networkSynchronized(ElixirContents.STREAM_CODEC).build());
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<FishBaitStats>> FISH_BAIT = COMPONENTS.register("fish_bait", () -> DataComponentType.<FishBaitStats>builder().persistent(FishBaitStats.CODEC).networkSynchronized(FishBaitStats.STREAM_CODEC).build());
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> FISHING_ROD_BAIT = COMPONENTS.register("bait", () -> DataComponentType.<Boolean>builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> GLU = COMPONENTS.register("glu", () -> DataComponentType.<Unit>builder().persistent(Codec.unit(Unit.INSTANCE)).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)).build());
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<InfusionBucketData>> INFUSION_BUCKET_DATA = COMPONENTS.register("infusion_bucket_data", () -> DataComponentType.<InfusionBucketData>builder().persistent(InfusionBucketData.CODEC).networkSynchronized(InfusionBucketData.STREAM_CODEC).build());
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> PESTLE_ACTIVE = COMPONENTS.register("active", () -> DataComponentType.<Unit>builder().persistent(Codec.unit(Unit.INSTANCE)).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)).build());
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<OriginalItemData>> ROTTEN_FOOD = COMPONENTS.register("rotten_food", () -> DataComponentType.<OriginalItemData>builder().persistent(OriginalItemData.CODEC).networkSynchronized(OriginalItemData.STREAM_CODEC).build());
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<ResourceLocation>> SIMULACRUM_EFFECT = COMPONENTS.register("simulacrum_effect", () -> DataComponentType.<ResourceLocation>builder().persistent(ResourceLocation.CODEC).networkSynchronized(ResourceLocation.STREAM_CODEC).build());
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<Long>> ROT_TIME = COMPONENTS.register("rot_time", () -> DataComponentType.<Long>builder().persistent(Codec.LONG).networkSynchronized(ByteBufCodecs.VAR_LONG).build());
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<AmphibiousArmorUpgrade>> SELECTED_UPGRADE = COMPONENTS.register("selected_upgrade", () -> DataComponentType.<AmphibiousArmorUpgrade>builder().persistent(BLRegistries.AMPHIBIOUS_ARMOR_UPGRADES.byNameCodec()).networkSynchronized(ByteBufCodecs.registry(BLRegistries.Keys.AMPHIBIOUS_ARMOR_UPGRADES)).build());
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<ShieldSpitData>> SHIELD_SPIT = COMPONENTS.register("shield_spit", () -> DataComponentType.<ShieldSpitData>builder().persistent(ShieldSpitData.CODEC).networkSynchronized(ShieldSpitData.STREAM_CODEC).build());
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<SimpleFluidContent>> STORED_FLUID = COMPONENTS.register("stored_fluid", () -> DataComponentType.<SimpleFluidContent>builder().persistent(SimpleFluidContent.CODEC).networkSynchronized(SimpleFluidContent.STREAM_CODEC).build());
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<GlobalPos>> TALISMAN_LINK = COMPONENTS.register("talisman_link", () -> DataComponentType.<GlobalPos>builder().persistent(GlobalPos.CODEC).networkSynchronized(GlobalPos.STREAM_CODEC).build());
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> THROWING = COMPONENTS.register("throwing", () -> DataComponentType.<Unit>builder().persistent(Codec.unit(Unit.INSTANCE)).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)).build());
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> WALK_TICKS = COMPONENTS.register("walk_ticks", () -> DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build());
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<UpgradeDamage>> UPGRADE_DAMAGE = COMPONENTS.register("upgrade_damage", () -> DataComponentType.<UpgradeDamage>builder().persistent(UpgradeDamage.CODEC).networkSynchronized(UpgradeDamage.STREAM_CODEC).build());
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> WORMS = COMPONENTS.register("worms", () -> DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build());

	public static final DeferredHolder<DataComponentType<?>, DataComponentType<CorrosionData>> CORROSION = COMPONENTS.register("corrosion", () -> DataComponentType.<CorrosionData>builder().persistent(CorrosionData.CODEC).networkSynchronized(CorrosionData.STREAM_CODEC).cacheEncoding().build());
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> MAX_CORROSION = COMPONENTS.register("max_corrosion", () -> DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build());
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> MAX_COATING = COMPONENTS.register("max_coating", () -> DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build());

	// Transient Component
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<UUID>> INVENTORY_ITEM_UUID = COMPONENTS.register("inventory_item_uuid", () -> DataComponentType.<UUID>builder().networkSynchronized(UUIDUtil.STREAM_CODEC).build());
}
