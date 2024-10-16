package thebetweenlands.common;

import java.util.Locale;

import javax.annotation.Nullable;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Style;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.common.NeoForgeMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import thebetweenlands.client.BetweenlandsClient;
import thebetweenlands.client.event.ClientRegistrationEvents;
import thebetweenlands.client.particle.ParticleFactory;
import thebetweenlands.common.config.BetweenlandsConfigSetup;
import thebetweenlands.common.event.CommonRegistrationEvents;
import thebetweenlands.common.handler.OverworldItemHandler;
import thebetweenlands.common.herblore.elixir.ElixirEffectRegistry;
import thebetweenlands.common.registries.*;
import thebetweenlands.common.world.BetweenlandsSurfaceRules;

@Mod(TheBetweenlands.ID)
public class TheBetweenlands {
	public static final String ID = "thebetweenlands";

	public static final Logger LOGGER = LogManager.getLogger();
	public static final ItemAbility SICKLE_HARVEST = ItemAbility.get("sickle_harvest");
	public static final Style HERBLORE_FONT = Style.EMPTY.withFont(prefix("herblore"));

	public static final GameRules.Key<GameRules.BooleanValue> FOOD_SICKNESS_GAMERULE = GameRules.register("blFoodSickness", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));
	public static final GameRules.Key<GameRules.BooleanValue> ROTTEN_FOOD_GAMERULE = GameRules.register("blRottenFood", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));
	public static final GameRules.Key<GameRules.BooleanValue> DECAY_GAMERULE = GameRules.register("blDecay", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));
	public static final GameRules.Key<GameRules.BooleanValue> CORROSION_GAMERULE = GameRules.register("blCorrosion", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));
	public static final GameRules.Key<GameRules.BooleanValue> TOOL_WEAKNESS_GAMERULE = GameRules.register("blToolWeakness", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));
	public static final GameRules.Key<GameRules.BooleanValue> TORCH_BLACKLIST_GAMERULE = GameRules.register("blTorchBlacklist", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));
	public static final GameRules.Key<GameRules.BooleanValue> FIRE_TOOL_GAMERULE = GameRules.register("blFireToolBlacklist", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));
	public static final GameRules.Key<GameRules.BooleanValue> POTION_GAMERULE = GameRules.register("blPotionBlacklist", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));
	public static final GameRules.Key<GameRules.BooleanValue> FERTILIZER_GAMERULE = GameRules.register("blFertilizerBlacklist", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));
	public static final GameRules.Key<GameRules.BooleanValue> TIMED_EVENT_GAMERULE = GameRules.register("blTimedEvents", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));

	public static final int LAYER_HEIGHT = 120;
	public static final int CAVE_WATER_HEIGHT = 15;
	public static final int PITSTONE_HEIGHT = CAVE_WATER_HEIGHT + 30;
	public static final int CAVE_START = LAYER_HEIGHT - 10;

	public TheBetweenlands(IEventBus eventbus, Dist dist) {
		if (dist.isClient()) {
			ClientRegistrationEvents.initClient(eventbus);
		}
		CommonRegistrationEvents.init(eventbus);
		OverworldItemHandler.init(dist);
		NeoForgeMod.enableMilkFluid();

		SoundRegistry.SOUNDS.register(eventbus);
		ParticleRegistry.PARTICLES.register(eventbus);
		CarverRegistry.CARVER_TYPES.register(eventbus);
		BlockRegistry.BLOCKS.register(eventbus);
		EntityRegistry.SPAWN_EGGS.register(eventbus);
		ItemRegistry.ITEMS.register(eventbus);
		FluidRegistry.FLUIDS.register(eventbus);
		FluidTypeRegistry.FLUID_TYPES.register(eventbus);
		CreativeGroupRegistry.CREATIVE_TABS.register(eventbus);
		AttributeRegistry.ATTRIBUTES.register(eventbus);
		EntityRegistry.ENTITY_TYPES.register(eventbus);
		ElixirEffectRegistry.ELIXIRS.register(eventbus);
		ElixirEffectRegistry.EFFECTS.register(eventbus);
		AttachmentRegistry.ATTACHMENT_TYPES.register(eventbus);
		AttachmentRegistry.SYNCHED_ATTACHMENT_TYPES.register(eventbus);
		ArmorMaterialRegistry.MATERIALS.register(eventbus);
		BlockEntityRegistry.BLOCK_ENTITIES.register(eventbus);
		DataComponentRegistry.COMPONENTS.register(eventbus);
		RecipeRegistry.RECIPE_TYPES.register(eventbus);
		RecipeRegistry.RECIPE_SERIALIZERS.register(eventbus);
		FeatureRegistry.FEATURES.register(eventbus);
		SimulacrumEffectRegistry.EFFECTS.register(eventbus);
		EnvironmentEventRegistry.EVENTS.register(eventbus);
		AdvancementCriteriaRegistry.TRIGGERS.register(eventbus);
		BetweenlandsSurfaceRules.SOURCES.register(eventbus);
		MapDecorationRegistry.DECORATIONS.register(eventbus);
		MenuRegistry.MENUS.register(eventbus);
		EntityPredicateRegistry.PREDICATES.register(eventbus);
		LootFunctionRegistry.FUNCTIONS.register(eventbus);
		AspectCalculatorRegistry.CALCULATORS.register(eventbus);
		CenserRecipeRegistry.RECIPES.register(eventbus);
		AmphibiousArmorUpgradeRegistry.UPGRADES.register(eventbus);
		EntityDataSerializerRegistry.DATA_SERIALIZERS.register(eventbus);

		BetweenlandsConfigSetup.init(eventbus, dist);

		StorageRegistry.preInit();
	}

	public static ResourceLocation prefix(String name) {
		return ResourceLocation.fromNamespaceAndPath(ID, name.toLowerCase(Locale.ROOT));
	}

	@Nullable
	public static Level getLevelWorkaround(ResourceKey<Level> dimension) {
		MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
		if (server != null) {
			return server.getLevel(dimension);
		} else if (FMLEnvironment.dist.isClient()) {
			return BetweenlandsClient.getClientLevel();
		}
		return null;
	}

	@Nullable
	public static ServerLevel tryGetServer(ResourceKey<Level> dimension) {
		MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
		if (server != null) {
			return server.getLevel(dimension);
		}
		return null;
	}


	@Nullable
	public static HolderLookup.Provider tryGetRegistryAccess() {
		MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
		if (server != null) {
			return server.registryAccess();
		} else if (FMLEnvironment.dist.isClient() && BetweenlandsClient.getClientLevel() instanceof Level level) {
			return level.registryAccess();
		}
		return null;
	}

	public static void createParticle(ParticleOptions options, Level level, double x, double y, double z) {
		createParticle(options, level, x, y, z, null);
	}

	public static void createParticle(ParticleOptions options, Level level, double x, double y, double z, @Nullable ParticleFactory.ParticleArgs<?> args) {
		if (level.isClientSide()) {
			BetweenlandsClient.createParticle(options, level, x, y, z, args);
		} else {
			//TODO send particles to client
		}
	}
}


