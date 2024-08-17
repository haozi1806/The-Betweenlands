package thebetweenlands.common.registries;

import net.minecraft.core.Direction;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import thebetweenlands.common.TheBetweenlands;
import thebetweenlands.common.items.*;

public class ItemRegistry {

	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TheBetweenlands.ID);

	public static final DeferredItem<Item> CRIMSON_SNAIL_SHELL = ITEMS.register("crimson_snail_shell", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> OCHRE_SNAIL_SHELL = ITEMS.register("ochre_snail_shell", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> COMPOST = ITEMS.register("compost", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> DRAGONFLY_WING = ITEMS.register("dragonfly_wing", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> LURKER_SKIN = ITEMS.register("lurker_skin", () -> new HoverTextItem(new Item.Properties()));
	public static final DeferredItem<Item> DRIED_SWAMP_REED = ITEMS.register("dried_swamp_reed", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> REED_ROPE = ITEMS.register("reed_rope", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> MUD_BRICK = ITEMS.register("mud_brick", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> SYRMORITE_INGOT = ITEMS.register("syrmorite_ingot", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> DRY_BARK = ITEMS.register("dry_bark", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> SLIMY_BONE = ITEMS.register("slimy_bone", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> SNAPPER_ROOT = ITEMS.register("snapper_root", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> STALKER_EYE = ITEMS.register("stalker_eye", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> SULFUR = ITEMS.register("sulfur", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> VALONITE_SHARD = ITEMS.register("valonite_shard", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> WEEDWOOD_STICK = ITEMS.register("weedwood_stick", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> ANGLER_TOOTH = ITEMS.register("angler_tooth", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> WEEDWOOD_BOWL = ITEMS.register("weedwood_bowl", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> RUBBER_BALL = ITEMS.register("rubber_ball", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> TAR_BEAST_HEART = ITEMS.register("tar_beast_heart", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
	public static final DeferredItem<Item> ANIMATED_TAR_BEAST_HEART = ITEMS.register("animated_tar_beast_heart", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
	public static final DeferredItem<Item> TAR_DRIP = ITEMS.register("tar_drip", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> LIMESTONE_FLUX = ITEMS.register("limestone_flux", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> INANIMATE_TARMINION = ITEMS.register("inanimate_tarminion", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
	public static final DeferredItem<Item> POISON_GLAND = ITEMS.register("poison_gland", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> AMATE_PAPER = ITEMS.register("amate_paper", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> SHOCKWAVE_SWORD_PIECE_1 = ITEMS.register("shockwave_sword_piece_1", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> SHOCKWAVE_SWORD_PIECE_2 = ITEMS.register("shockwave_sword_piece_2", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> SHOCKWAVE_SWORD_PIECE_3 = ITEMS.register("shockwave_sword_piece_3", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> SHOCKWAVE_SWORD_PIECE_4 = ITEMS.register("shockwave_sword_piece_4", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> AMULET_SOCKET = ITEMS.register("amulet_socket", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
	public static final DeferredItem<Item> SCABYST = ITEMS.register("scabyst", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> ITEM_SCROLL = ITEMS.register("item_scroll", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
	public static final DeferredItem<Item> SYRMORITE_NUGGET = ITEMS.register("syrmorite_nugget", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> OCTINE_NUGGET = ITEMS.register("octine_nugget", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> VALONITE_SPLINTER = ITEMS.register("valonite_splinter", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> CREMAINS = ITEMS.register("cremains", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> UNDYING_EMBERS = ITEMS.register("undying_embers", () -> new UndyingEmberItem(new Item.Properties()));
	public static final DeferredItem<Item> INANIMATE_ANGRY_PEBBLE = ITEMS.register("inanimate_angry_pebble", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> ANCIENT_REMNANT = ITEMS.register("ancient_remnant", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> LOOT_SCRAPS = ITEMS.register("loot_scraps", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
	public static final DeferredItem<Item> FABRICATED_SCROLL = ITEMS.register("fabricated_scroll", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
	public static final DeferredItem<Item> ANADIA_SWIM_BLADDER = ITEMS.register("anadia_swim_bladder", () -> new HoverTextItem(new Item.Properties()));
	public static final DeferredItem<Item> ANADIA_EYE = ITEMS.register("anadia_eye", () -> new HoverTextItem(new Item.Properties()));
	public static final DeferredItem<Item> ANADIA_GILLS = ITEMS.register("anadia_gills", () -> new HoverTextItem(new Item.Properties()));
	public static final DeferredItem<Item> ANADIA_SCALES = ITEMS.register("anadia_scales", () -> new HoverTextItem(new Item.Properties()));
	public static final DeferredItem<Item> ANADIA_BONES = ITEMS.register("anadia_bones", () -> new HoverTextItem(new Item.Properties()));
	public static final DeferredItem<Item> ANADIA_REMAINS = ITEMS.register("anadia_remains", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> ANADIA_FINS = ITEMS.register("anadia_fins", () -> new HoverTextItem(new Item.Properties()));
	public static final DeferredItem<Item> SNOT = ITEMS.register("snot", () -> new HoverTextItem(new Item.Properties()));
	public static final DeferredItem<Item> URCHIN_SPIKE = ITEMS.register("urchin_spike", () -> new HoverTextItem(new Item.Properties()));
	public static final DeferredItem<Item> FISHING_FLOAT_AND_HOOK = ITEMS.register("fishing_float_and_hook", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> OLMLETTE_MIXTURE = ITEMS.register("olmlette_mixture", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> SILK_COCOON = ITEMS.register("silk_cocoon", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> SILK_THREAD = ITEMS.register("silk_thread", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> DIRTY_SILK_BUNDLE = ITEMS.register("dirty_silk_bundle", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> PHEROMONE_THORAXES = ITEMS.register("pheromone_thoraxes", () -> new PheromoneThoraxItem(new Item.Properties()));
	public static final DeferredItem<Item> SWAMP_TALISMAN = ITEMS.register("swamp_talisman", () -> new SwampTalismanItem(new Item.Properties().stacksTo(1)));
	public static final DeferredItem<Item> SWAMP_TALISMAN_PIECE_1 = ITEMS.register("swamp_talisman_piece_1", () -> new Item(new Item.Properties().stacksTo(1)));
	public static final DeferredItem<Item> SWAMP_TALISMAN_PIECE_2 = ITEMS.register("swamp_talisman_piece_2", () -> new Item(new Item.Properties().stacksTo(1)));
	public static final DeferredItem<Item> SWAMP_TALISMAN_PIECE_3 = ITEMS.register("swamp_talisman_piece_3", () -> new Item(new Item.Properties().stacksTo(1)));
	public static final DeferredItem<Item> SWAMP_TALISMAN_PIECE_4 = ITEMS.register("swamp_talisman_piece_4", () -> new Item(new Item.Properties().stacksTo(1)));
	public static final DeferredItem<Item> WEEDWOOD_ROWBOAT = ITEMS.register("weedwood_rowboat", () -> new WeedwoodRowboatItem(new Item.Properties().stacksTo(1)));
	public static final DeferredItem<Item> ORANGE_DENTROTHYST_SHARD = ITEMS.register("orange_dentrothyst_shard", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> GREEN_DENTROTHYST_SHARD = ITEMS.register("green_dentrothyst_shard", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> FISH_BAIT = ITEMS.register("fish_bait", () -> new FishBaitItem(new Item.Properties().stacksTo(1).durability(64)));
	public static final DeferredItem<Item> FUMIGANT = ITEMS.register("fumigant", () -> new FumigantItem(new Item.Properties()));
	public static final DeferredItem<Item> SAP_BALL = ITEMS.register("sap_ball", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().alwaysEdible().build())));


	//TODO proper armor item
	public static final DeferredItem<Item> LURKER_SKIN_BOOTS = ITEMS.register("lurker_skin_boots", () -> new Item(new Item.Properties().stacksTo(1)));

	public static final DeferredItem<Item> GERTS_DONUT = ITEMS.register("gerts_donut", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> OCTINE_INGOT = ITEMS.register("octine_ingot", () -> new OctineIngotItem(new Item.Properties()));
	public static final DeferredItem<Item> RUNE_DOOR_KEY = ITEMS.register("rune_door_key", () -> new Item(new Item.Properties().stacksTo(1)));
	public static final DeferredItem<Item> MIDDLE_FRUIT = ITEMS.register("middle_fruit", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.6F).build())));
	public static final DeferredItem<Item> SAP_SPIT = ITEMS.register("sap_spit", () -> new SapSpitItem(new Item.Properties()));
	public static final DeferredItem<Item> PESTLE = ITEMS.register("pestle", () -> new Item(new Item.Properties().stacksTo(1)));
	public static final DeferredItem<Item> ELIXIR = ITEMS.register("elixir", () -> new Item(new Item.Properties().stacksTo(1)));
	public static final DeferredItem<Item> BARK_AMULET = ITEMS.register("bark_amulet", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> WEEPING_BLUE_PETAL = ITEMS.register("weeping_blue_petal", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> ASPECTRUS_FRUIT = ITEMS.register("apsectrus_fruit", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> SHIMMER_STONE = ITEMS.register("shimmer_stone", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)));
	public static final DeferredItem<MobItem> ANADIA = ITEMS.register("anadia", () -> new AnadiaMobItem(new Item.Properties().stacksTo(1)));
	public static final DeferredItem<MobItem> BUBBLER_CRAB = ITEMS.register("bubbler_crab", () -> new MobItem(new Item.Properties().stacksTo(1), EntityRegistry.BUBBLER_CRAB.get(), null));
	public static final DeferredItem<MobItem> SILT_CRAB = ITEMS.register("silt_crab", () -> new MobItem(new Item.Properties().stacksTo(1), EntityRegistry.SILT_CRAB.get(), null));
	public static final DeferredItem<MobItem> TINY_SLUDGE_WORM = ITEMS.register("tiny_sludge_worm", () -> new MobItem(new Item.Properties().stacksTo(1), null, null));
	public static final DeferredItem<MobItem> TINY_SLUDGE_WORM_HELPER = ITEMS.register("tiny_sludge_worm_helper", () -> new MobItem(new Item.Properties().stacksTo(1), null, null));
	public static final DeferredItem<Item> WEEDWOOD_FISHING_ROD = ITEMS.register("weedwood_fishing_rod", () -> new Item(new Item.Properties().stacksTo(1)));
	public static final DeferredItem<Item> SILK_BUNDLE = ITEMS.register("silk_bundle", () -> new Item(new Item.Properties().stacksTo(1)));
	public static final DeferredItem<Item> SILK_GRUB = ITEMS.register("silk_grub", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> GLUE = ITEMS.register("glue", () -> new GlueItem(new Item.Properties()));
	public static final DeferredItem<Item> MOSS_FILTER = ITEMS.register("moss_filter", () -> new Item(new Item.Properties().stacksTo(1).durability(400)));
	public static final DeferredItem<Item> SILK_FILTER = ITEMS.register("silk_filter", () -> new Item(new Item.Properties().stacksTo(1).durability(2000)));
	public static final DeferredItem<Item> SKULL_MASK = ITEMS.register("skull_mask", () -> new Item(new Item.Properties()));

	public static final DeferredItem<Item> DAMP_TORCH = ITEMS.register("damp_torch", () -> new StandingAndWallBlockItem(BlockRegistry.DAMP_TORCH.get(), BlockRegistry.DAMP_WALL_TORCH.get(), new Item.Properties(), Direction.DOWN));
	public static final DeferredItem<Item> SULFUR_TORCH = ITEMS.register("sulfur_torch", () -> new StandingAndWallBlockItem(BlockRegistry.SULFUR_TORCH.get(), BlockRegistry.SULFUR_WALL_TORCH.get(), new Item.Properties(), Direction.DOWN));
	public static final DeferredItem<Item> EXTINGUISHED_SULFUR_TORCH = ITEMS.register("extinguished_sulfur_torch", () -> new StandingAndWallBlockItem(BlockRegistry.EXTINGUISHED_SULFUR_TORCH.get(), BlockRegistry.EXTINGUISHED_SULFUR_WALL_TORCH.get(), new Item.Properties(), Direction.DOWN));
	public static final DeferredItem<Item> WEEDWOOD_SIGN = ITEMS.register("weedwood_sign", () -> new SignItem(new Item.Properties(), BlockRegistry.WEEDWOOD_SIGN.get(), BlockRegistry.WEEDWOOD_WALL_SIGN.get()));

	public static final DeferredItem<Item> DIRTY_DENTROTHYST_VIAL = ITEMS.register("dirty_dentothyst_vial", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> ORANGE_DENTROTHYST_VIAL = ITEMS.register("orange_dentothyst_vial", () -> new DentrothystVialItem(ItemRegistry.ORANGE_ASPECT_VIAL, new Item.Properties()));
	public static final DeferredItem<Item> GREEN_DENTROTHYST_VIAL = ITEMS.register("green_dentothyst_vial", () -> new DentrothystVialItem(ItemRegistry.GREEN_ASPECT_VIAL, new Item.Properties()));
	public static final DeferredItem<Item> ORANGE_ASPECT_VIAL = ITEMS.register("orange_aspect_vial", () -> new AspectVialItem(new Item.Properties().craftRemainder(ORANGE_DENTROTHYST_VIAL.get())));
	public static final DeferredItem<Item> GREEN_ASPECT_VIAL = ITEMS.register("green_aspect_vial", () -> new AspectVialItem(new Item.Properties().craftRemainder(GREEN_DENTROTHYST_VIAL.get())));


	public static final DeferredItem<Item> SWAMP_WATER_BUCKET = ITEMS.register("swamp_water_bucket", () -> new BucketItem(FluidRegistry.SWAMP_WATER_STILL.get(), new Item.Properties().stacksTo(1)));
	public static final DeferredItem<Item> STAGNANT_WATER_BUCKET = ITEMS.register("stagnant_water_bucket", () -> new BucketItem(FluidRegistry.STAGNANT_WATER_STILL.get(), new Item.Properties().stacksTo(1)));
	public static final DeferredItem<Item> TAR_BUCKET = ITEMS.register("tar_bucket", () -> new BucketItem(FluidRegistry.TAR_STILL.get(), new Item.Properties().stacksTo(1)));
	public static final DeferredItem<Item> RUBBER_BUCKET = ITEMS.register("rubber_bucket", () -> new BucketItem(FluidRegistry.RUBBER_STILL.get(), new Item.Properties().stacksTo(1)));
	public static final DeferredItem<Item> DYE_BUCKET = ITEMS.register("dye_bucket", () -> new BucketItem(FluidRegistry.DYE_STILL.get(), new Item.Properties().stacksTo(1)));
	public static final DeferredItem<Item> CLEAN_WATER_BUCKET = ITEMS.register("clean_water_bucket", () -> new BucketItem(FluidRegistry.CLEAN_WATER_STILL.get(), new Item.Properties().stacksTo(1)));
	public static final DeferredItem<Item> FISH_OIL_BUCKET = ITEMS.register("fish_oil_bucket", () -> new BucketItem(FluidRegistry.FISH_OIL_STILL.get(), new Item.Properties().stacksTo(1)));
	public static final DeferredItem<Item> PLANT_TONIC_BUCKET = ITEMS.register("plant_tonic_bucket", () -> new Item(new Item.Properties().stacksTo(1)));

	public static final DeferredItem<Item> NETTLE_SOUP_BUCKET = ITEMS.register("nettle_soup_bucket", () -> new BucketItem(FluidRegistry.NETTLE_SOUP_STILL.get(), new Item.Properties().stacksTo(1)));
	public static final DeferredItem<Item> NETTLE_TEA_BUCKET = ITEMS.register("nettle_tea_bucket", () -> new BucketItem(FluidRegistry.NETTLE_TEA_STILL.get(), new Item.Properties().stacksTo(1)));
	public static final DeferredItem<Item> PHEROMONE_EXTREACT_BUCKET = ITEMS.register("pheromone_extract_bucket", () -> new BucketItem(FluidRegistry.PHEROMONE_EXTRACT_STILL.get(), new Item.Properties().stacksTo(1)));
	public static final DeferredItem<Item> SWAMP_BROTH_BUCKET = ITEMS.register("swamp_broth_bucket", () -> new BucketItem(FluidRegistry.SWAMP_BROTH_STILL.get(), new Item.Properties().stacksTo(1)));
	public static final DeferredItem<Item> STURDY_STOCK_BUCKET = ITEMS.register("sturdy_stock_bucket", () -> new BucketItem(FluidRegistry.STURDY_STOCK_STILL.get(), new Item.Properties().stacksTo(1)));
	public static final DeferredItem<Item> PEAR_CORDIAL_BUCKET = ITEMS.register("pear_cordial_bucket", () -> new BucketItem(FluidRegistry.PEAR_CORDIAL_STILL.get(), new Item.Properties().stacksTo(1)));
	public static final DeferredItem<Item> SHAMANS_BREW_BUCKET = ITEMS.register("shamans_brew_bucket", () -> new BucketItem(FluidRegistry.SHAMANS_BREW_STILL.get(), new Item.Properties().stacksTo(1)));
	public static final DeferredItem<Item> LAKE_BROTH_BUCKET = ITEMS.register("lake_broth_bucket", () -> new BucketItem(FluidRegistry.LAKE_BROTH_STILL.get(), new Item.Properties().stacksTo(1)));
	public static final DeferredItem<Item> SHELL_STOCK_BUCKET = ITEMS.register("shell_stock_bucket", () -> new BucketItem(FluidRegistry.SHELL_STOCK_STILL.get(), new Item.Properties().stacksTo(1)));
	public static final DeferredItem<Item> FROG_LEG_EXTRACT_BUCKET = ITEMS.register("frog_leg_extract_bucket", () -> new BucketItem(FluidRegistry.FROG_LEG_EXTRACT_STILL.get(), new Item.Properties().stacksTo(1)));
	public static final DeferredItem<Item> WITCH_TEA_BUCKET = ITEMS.register("witch_tea_bucket", () -> new BucketItem(FluidRegistry.WITCH_TEA_STILL.get(), new Item.Properties().stacksTo(1)));

	public static final DeferredItem<Item> SWAMP_HAG_SPAWN_EGG = ITEMS.register("swamp_hag_spawn_egg", () -> new DeferredSpawnEggItem(EntityRegistry.SWAMP_HAG, 0x5E4E2E, 0x18461A, new Item.Properties()));
	public static final DeferredItem<Item> GECKO_SPAWN_EGG = ITEMS.register("gecko_spawn_egg", () -> new DeferredSpawnEggItem(EntityRegistry.GECKO, 0xdc7202, 0x05e290, new Item.Properties()));
	public static final DeferredItem<Item> WIGHT_SPAWN_EGG = ITEMS.register("wight_spawn_egg", () -> new DeferredSpawnEggItem(EntityRegistry.WIGHT, 0x7d8378, 0x07190a, new Item.Properties()));

	public static final DeferredItem<Item> AMATE_MAP = ITEMS.register("amate_map", () -> new EmptyAmateMapItem(new Item.Properties().rarity(Rarity.UNCOMMON)));
	public static final DeferredItem<Item> FILLED_AMATE_MAP = ITEMS.register("filled_amate_map", () -> new AmateMapItem(new Item.Properties().rarity(Rarity.UNCOMMON)));
	public static final DeferredItem<Item> RECORD_ASTATOS = ITEMS.register("music_disc_astatos", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(MusicRegistry.ASTATOS)));
	public static final DeferredItem<Item> RECORD_BETWEEN_YOU_AND_ME = ITEMS.register("music_disc_between_you_and_me", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(MusicRegistry.BETWEEN_YOU_AND_ME)));
	public static final DeferredItem<Item> RECORD_CHRISTMAS_ON_THE_MARSH = ITEMS.register("music_disc_christmas_on_the_marsh", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(MusicRegistry.CHRISTMAS_ON_THE_MARSH)));
	public static final DeferredItem<Item> RECORD_THE_EXPLORER = ITEMS.register("music_disc_the_explorer", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(MusicRegistry.THE_EXPLORER)));
	public static final DeferredItem<Item> RECORD_HAG_DANCE = ITEMS.register("music_disc_hag_dance", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(MusicRegistry.HAG_DANCE)));
	public static final DeferredItem<Item> RECORD_LONELY_FIRE = ITEMS.register("music_disc_lonely_fire", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(MusicRegistry.LONELY_FIRE)));
	public static final DeferredItem<Item> MYSTERIOUS_RECORD = ITEMS.register("mysterious_record", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(MusicRegistry.MYSTERIOUS_RECORD)));
	public static final DeferredItem<Item> RECORD_ANCIENT = ITEMS.register("music_disc_ancient", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(MusicRegistry.ANCIENT)));
	public static final DeferredItem<Item> RECORD_BENEATH_A_GREEN_SKY = ITEMS.register("music_disc_beneath_a_green_sky", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(MusicRegistry.BENEATH_A_GREEN_SKY)));
	public static final DeferredItem<Item> RECORD_DJ_WIGHTS_MIXTAPE = ITEMS.register("dj_wights_mixtape", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(MusicRegistry.DJ_WIGHTS_MIXTAPE)));
	public static final DeferredItem<Item> RECORD_ONWARDS = ITEMS.register("music_disc_onwards", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(MusicRegistry.ONWARDS)));
	public static final DeferredItem<Item> RECORD_STUCK_IN_THE_MUD = ITEMS.register("music_disc_stuck_in_the_mud", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(MusicRegistry.STUCK_IN_THE_MUD)));
	public static final DeferredItem<Item> RECORD_WANDERING_WISPS = ITEMS.register("music_disc_wandering_wisps", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(MusicRegistry.WANDERING_WISPS)));
	public static final DeferredItem<Item> RECORD_WATERLOGGED = ITEMS.register("music_disc_waterlogged", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(MusicRegistry.WATERLOGGED)));
	public static final DeferredItem<Item> RECORD_DEEP_WATER_THEME = ITEMS.register("deep_water_theme", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(MusicRegistry.DEEP_WATER_THEME)));

}