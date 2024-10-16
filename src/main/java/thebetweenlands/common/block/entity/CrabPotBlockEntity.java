package thebetweenlands.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.ticks.ContainerSingleItem;
import thebetweenlands.common.TheBetweenlands;
import thebetweenlands.common.block.container.CrabPotBlock;
import thebetweenlands.common.block.waterlog.SwampWaterLoggable;
import thebetweenlands.common.datagen.tags.BLFluidTagGenerator;
import thebetweenlands.common.entity.fishing.BubblerCrab;
import thebetweenlands.common.entity.fishing.SiltCrab;
import thebetweenlands.common.item.misc.MobItem;
import thebetweenlands.common.registries.*;
import thebetweenlands.common.world.storage.location.TokenBucket;

import javax.annotation.Nullable;
import java.util.*;

public class CrabPotBlockEntity extends SyncedBlockEntity implements ContainerSingleItem.BlockContainerSingleItem {

	public static final ResourceLocation CRAB_POT_SPAWNING_TOKEN_BUCKET_ID = TheBetweenlands.prefix("crab_pot_spawning");

	private boolean active;
	public int fallCounter = 16;
	public int fallCounterPrev;
	public int prevAnimationTicks;
	public int animationTicks;
	public boolean animate = false;
	@Nullable
	private Player placer;
	@Nullable
	private UUID placerUUID;
	private int catchTimer;
	private int catchTimerMax;
	private TokenBucket tokenBucket = new TokenBucket(CRAB_POT_SPAWNING_TOKEN_BUCKET_ID, 64, 48, 64, 1, 4, 0.99D, 1);
	private ItemStack item = ItemStack.EMPTY;

	public CrabPotBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegistry.CRAB_POT.get(), pos, state);
	}

	public static void tick(Level level, BlockPos pos, BlockState state, CrabPotBlockEntity entity) {
		//only perform logic if waterlogged
		if (state.getValue(CrabPotBlock.WATER_TYPE) == SwampWaterLoggable.WaterType.NONE) return;
		if (level.isClientSide()) {
			entity.fallCounterPrev = entity.fallCounter;
			if (!entity.hasBaitItem()) {
				if (entity.fallCounter > 0) {
					entity.fallCounter--;
				}
				if (entity.fallCounter <= 0) {
					entity.fallCounter = 0;
				}
			}
			if (entity.hasBaitItem() && entity.fallCounter != 32) {
				entity.fallCounter = 32;
			}

			entity.prevAnimationTicks = entity.animationTicks;

			if (entity.animate) {
				entity.animationTicks++;
			}
			if (!entity.animate && entity.animationTicks > 0) {
				entity.animationTicks = 0;
			}
		} else {
			if (entity.catchTimerMax == 0) {
				entity.resetCatchTimer(level);
			}

			// because the player is always null unless the world is loaded but block NBT is loaded before grrrrr
			if (entity.placerUUID != null && entity.getPlacer() == null && level.getGameTime() % 20 == 0) {
				if (entity.updatePlacerFromUUID(level)) {
					entity.setChanged();
				}
			}

			if (entity.hasBaitItem() && !entity.active) {
				entity.active = true;
				entity.setChanged();
			}

			if (!entity.hasBaitItem() && entity.active) {
				entity.active = false;
				entity.setChanged();
			}

			if (entity.active && !(entity.getItem(0).getItem() instanceof MobItem)) {
				if (level.getGameTime() % 20 == 0) {
					SiltCrab crab = null;

					entity.updateCatchTimer();

					int remainingCatchTicks = entity.getRemainingCatchTicks();

					if (remainingCatchTicks <= 0 && level.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING) && level.getNearestPlayer(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, 32.0D, false) == null && level.canSeeSky(pos.above())) {
						int checks = 64;
						int spawnableBlocks = 0;

						Map<Biome, Boolean> spawnableBiomes = new HashMap<>();

						BlockPos.MutableBlockPos checkPos = new BlockPos.MutableBlockPos();
						for (int i = 0; i < checks; i++) {
							checkPos.set(pos.getX() + level.getRandom().nextInt(13) - 6, pos.getY() + level.getRandom().nextInt(5) - 1, pos.getZ() + level.getRandom().nextInt(13) - 6);

							if (level.getFluidState(checkPos).is(BLFluidTagGenerator.UNDERWATER_PLANT_PLACEABLE) && level.canSeeSky(checkPos)) {
								Biome biome = level.getBiome(checkPos).value();

								if (spawnableBiomes.containsKey(biome)) {
									if (spawnableBiomes.get(biome)) {
										spawnableBlocks++;
									}
								} else {
									boolean biomeSpawnable = false;

									for (EntityType<?> type : biome.getMobSettings().getEntityTypes()) {
										if (type == EntityRegistry.BUBBLER_CRAB.get() || type == EntityRegistry.SILT_CRAB.get()) {
											biomeSpawnable = true;
											break;
										}
									}


									spawnableBiomes.put(biome, biomeSpawnable);

									if (biomeSpawnable) {
										spawnableBlocks++;
									}
								}
							}
						}

						float probability = Math.min(1, (float) Math.pow((float) spawnableBlocks / (float) checks * 2.0f, 2.0f) * 2.0f);

						if (level.getRandom().nextFloat() <= probability) {
							crab = entity.createRandomCatch(level);
						}

						entity.resetCatchTimer(level);
					}

					if (crab == null) {
						entity.lureCloseCrab(level, pos);
						crab = entity.checkCatch(level, pos);
					}

					if (crab != null) {
						MobItem<?> itemMob = crab.getCrabPotItem();

						if (itemMob != null) {
							ItemStack stack = itemMob.capture(crab);

							if (!stack.isEmpty()) {
								crab.discard();

								entity.setItem(0, stack);

								Player placer = entity.getPlacer();
								if (placer == null) {
									entity.updatePlacerFromUUID(level);
									placer = entity.getPlacer();
								}

								entity.setChanged();
								level.sendBlockUpdated(pos, state, state, 2);

								if ((entity.hasSiltCrab() || entity.hasBubblerCrab()) && placer instanceof ServerPlayer sp) {
									AdvancementCriteriaRegistry.CRAB_POT.get().trigger(sp);
								}
							}
						}
					}
				}
			} else {
				entity.resetCatchTimer(level);
			}

			if (!level.getBlockState(pos.below()).is(BlockRegistry.CRAB_POT_FILTER)) {
				level.blockEvent(pos, state.getBlock(), 1, 0);
				entity.setChanged();
			}
		}
	}

	@Override
	public boolean triggerEvent(int id, int type) {
		if (id == 1) {
			this.animate = type == 1;
			return true;
		}
		return super.triggerEvent(id, type);
	}

	@Nullable
	private Player getPlacer() {
		return this.placer;
	}

	public void setPlacer(Player player) {
		this.placer = player;
	}

	public void resetCatchTimer(Level level) {
		this.catchTimerMax = 24000 + level.getRandom().nextInt(36000);
		this.catchTimer = 0;
	}

	private void updateCatchTimer() {
		long increment = this.tokenBucket.consume();
		this.catchTimer = Mth.clamp(this.catchTimer + (int) increment, 0, this.catchTimerMax);
	}

	public int getRemainingCatchTicks() {
		return Mth.clamp(this.catchTimerMax - this.catchTimer, 0, this.catchTimerMax);
	}

	private boolean updatePlacerFromUUID(Level level) {
		if (this.placerUUID != null) {
			Player player = level.getPlayerByUUID(this.placerUUID);
			if (player != null && player != this.getPlacer()) {
				this.setPlacer(player);
				return true;
			}
		}
		return false;
	}

	private SiltCrab createRandomCatch(Level level) {
		return level.getRandom().nextBoolean() ? new SiltCrab(EntityRegistry.SILT_CRAB.get(), level) : new BubblerCrab(EntityRegistry.BUBBLER_CRAB.get(), level);
	}

	@Nullable
	private SiltCrab checkCatch(Level level, BlockPos pos) {
		List<SiltCrab> list = level.getEntitiesOfClass(SiltCrab.class, new AABB(pos.above()).inflate(-0.25D, 0F, -0.25D));
		if (!list.isEmpty()) {
			return list.get(level.getRandom().nextInt(list.size()));
		}
		return null;
	}

	public void lureCloseCrab(Level level, BlockPos pos) {
		List<SiltCrab> list = level.getEntitiesOfClass(SiltCrab.class, new AABB(pos).inflate(12D, 5D, 12D), Entity::isInWater);
		if (!list.isEmpty()) {
			list.get(level.getRandom().nextInt(list.size())).lureToCrabPot(pos);
		}
	}

	public boolean hasBaitItem() {
		ItemStack baitItem = this.getItem(0);
		return !baitItem.isEmpty() && baitItem.is(ItemRegistry.ANADIA_REMAINS);
	}

	public boolean hasSiltCrab() {
		ItemStack crabItem = this.getItem(0);
		return !crabItem.isEmpty() && crabItem.is(ItemRegistry.SILT_CRAB);
	}

	public boolean hasBubblerCrab() {
		ItemStack crabItem = this.getItem(0);
		return !crabItem.isEmpty() && crabItem.is(ItemRegistry.BUBBLER_CRAB);
	}

	@Nullable
	public EntityType<?> getEntity() {
		ItemStack stack = this.getItem(0);
		if (!stack.isEmpty() && stack.getItem() instanceof MobItem<?> mob && !mob.getEntityData(stack).isEmpty()) {
			return BuiltInRegistries.ENTITY_TYPE.get(mob.getCapturedEntityId(stack));
		}
		return null;
	}

	@Override
	public ItemStack getTheItem() {
		return this.item;
	}

	@Override
	public void setTheItem(ItemStack item) {
		this.item = item;
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		if (!this.getTheItem().isEmpty()) {
			tag.put("item", this.getTheItem().save(registries));
		}

		Player placer = this.getPlacer();
		if (placer != null) {
			tag.putUUID("owner_uuid", placer.getUUID());
		}

		tag.putInt("catch_timer", this.catchTimer);
		tag.putInt("catch_timer_max", this.catchTimerMax);
		tag.put("token_bucket", this.tokenBucket.writeToNBT(new CompoundTag()));
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		if (tag.contains("item", Tag.TAG_COMPOUND)) {
			this.item = ItemStack.parse(registries, tag.getCompound("item")).orElse(ItemStack.EMPTY);
		} else {
			this.item = ItemStack.EMPTY;
		}

		if (tag.contains("owner_uuid", Tag.TAG_INT_ARRAY)) {
			this.placerUUID = tag.getUUID("owner_uuid");
		}

		this.catchTimer = tag.getInt("catch_timer");
		this.catchTimerMax = tag.getInt("catch_timer_max");

		if (tag.contains("token_bucket", Tag.TAG_COMPOUND)) {
			this.tokenBucket = new TokenBucket(tag.getCompound("token_bucket"));
		}
	}

	@Override
	public BlockEntity getContainerBlockEntity() {
		return this;
	}
}
