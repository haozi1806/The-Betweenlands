package thebetweenlands.common.block.container;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import thebetweenlands.api.aspect.Aspect;
import thebetweenlands.client.particle.ParticleFactory;
import thebetweenlands.common.TheBetweenlands;
import thebetweenlands.common.block.misc.HorizontalBaseEntityBlock;
import thebetweenlands.common.block.entity.InfuserBlockEntity;
import thebetweenlands.common.component.item.AspectContents;
import thebetweenlands.common.component.item.InfusionBucketData;
import thebetweenlands.common.herblore.aspect.AspectManager;
import thebetweenlands.common.item.misc.LifeCrystalItem;
import thebetweenlands.common.item.misc.bucket.BLBucketItem;
import thebetweenlands.common.registries.*;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class InfuserBlock extends HorizontalBaseEntityBlock {

	public static final VoxelShape OUTSIDE_SHAPE = Shapes.or(
		Block.box(0.5D, 3.5D, 0.5D, 15.5D, 6.5D, 15.5D),
		Block.box(1.0D, 6.5D, 3.0D, 15.0D, 14.0D, 13.0D),
		Block.box(3.0D, 6.5D, 1.0D, 13.0D, 14.0D, 15.0D)
	);
	public static final VoxelShape INSIDE_SHAPE = Shapes.or(
		Block.box(3.0D, 5.0D, 5.0D, 13.0D, 14.0D, 11.0D),
		Block.box(5.0D, 5.0D, 3.0D, 11.0D, 14.0D, 13.0D)
	);
	public static final VoxelShape SHAPE = Shapes.join(OUTSIDE_SHAPE, INSIDE_SHAPE, BooleanOp.ONLY_FIRST);

	public InfuserBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if (level.getBlockEntity(pos) instanceof InfuserBlockEntity infuser) {

			Optional<IFluidHandler> fluidHandler = FluidUtil.getFluidHandler(level, pos, hitResult.getDirection());

			if (fluidHandler.isPresent() && FluidUtil.getFluidHandler(stack).isPresent()) {
				if (FluidUtil.interactWithFluidHandler(player, hand, level, pos, hitResult.getDirection())) {
					return ItemInteractionResult.sidedSuccess(level.isClientSide());
				}
			}

			if (!player.isShiftKeyDown()) {
				if (!stack.isEmpty() && !infuser.hasInfusion()) {
					List<Aspect> aspectContainer = AspectContents.getAllAspectsForItem(stack, level.registryAccess(), AspectManager.get(level));
					if (!aspectContainer.isEmpty()) {
						for (int i = 0; i < InfuserBlockEntity.MAX_INGREDIENTS; i++) {
							if (infuser.getItem(i).isEmpty()) {
								ItemStack singleIngredient = stack.copy();
								singleIngredient.setCount(1);
								infuser.setItem(i, singleIngredient);
								infuser.updateInfusingRecipe();
								if (!player.isCreative())
									singleIngredient.shrink(1);
								level.sendBlockUpdated(pos, state, state, 2);
								if (infuser.getWaterAmount() > 0) {
									level.playSound(null, pos, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS, 0.3f, 0.9f + level.getRandom().nextFloat() * 0.3f);
								} else {
									level.playSound(null, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 0.3f, 0.9f + level.getRandom().nextFloat() * 0.3f);
								}
								return ItemInteractionResult.SUCCESS;
							}
						}
					}
				}

				if (!stack.isEmpty() && stack.getItem() instanceof LifeCrystalItem) {
					if (infuser.getItem(InfuserBlockEntity.MAX_INGREDIENTS + 1).isEmpty()) {
						infuser.setItem(InfuserBlockEntity.MAX_INGREDIENTS + 1, stack);
						infuser.updateInfusingRecipe();
						if (!player.isCreative()) player.setItemInHand(hand, ItemStack.EMPTY);
					}
					return ItemInteractionResult.SUCCESS;
				}
			}

			if (player.isShiftKeyDown() && !infuser.hasInfusion()) {
				for (int i = InfuserBlockEntity.MAX_INGREDIENTS; i >= 0; i--) {
					if (!infuser.getItem(i).isEmpty()) {
						ItemEntity itemEntity = player.drop(infuser.getItem(i).copy(), false);
						if (itemEntity != null) itemEntity.setNoPickUpDelay();
						infuser.setItem(i, ItemStack.EMPTY);
						infuser.updateInfusingRecipe();
						level.sendBlockUpdated(pos, state, state, 2);
						return ItemInteractionResult.SUCCESS;
					}
				}
			}
		}
		return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		if (level.getBlockEntity(pos) instanceof InfuserBlockEntity infuser) {
			if (!player.isShiftKeyDown() && infuser.getStirProgress() >= 90) {
				infuser.setStirProgress(0);
				return InteractionResult.sidedSuccess(level.isClientSide());
			}

			if (player.isShiftKeyDown()) {
				if (!infuser.getItem(InfuserBlockEntity.MAX_INGREDIENTS + 1).isEmpty()) {
					ItemEntity itemEntity = player.drop(infuser.getItem(InfuserBlockEntity.MAX_INGREDIENTS + 1).copy(), false);
					if (itemEntity != null) itemEntity.setPickUpDelay(0);
					infuser.setItem(InfuserBlockEntity.MAX_INGREDIENTS + 1, ItemStack.EMPTY);
					infuser.updateInfusingRecipe();
					level.sendBlockUpdated(pos, state, state, 2);
					return InteractionResult.SUCCESS;
				}
			}
		}
		return super.useWithoutItem(state, level, pos, player, hitResult);
	}

	@Override
	protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
		Containers.dropContentsOnDestroy(state, newState, level, pos);
		super.onRemove(state, level, pos, newState, movedByPiston);
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (level.getBlockEntity(pos) instanceof InfuserBlockEntity infuser) {
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			if (infuser.getWaterAmount() > 0 && infuser.getTemperature() > 0) {
				int amount = infuser.tank.getFluidAmount();
				int capacity = infuser.tank.getCapacity();
				float size = 1.0F / capacity * amount;
				float xx = x + 0.5F;
				float yy = y + 0.35F + size * 0.5F;
				float zz = z + 0.5F;
				float fixedOffset = 0.25F;
				float randomOffset = random.nextFloat() * 0.6F - 0.3F;
				if (random.nextInt((101 - infuser.getTemperature())) / 4 == 0) {
					float[] colors = infuser.currentInfusionColor;
					TheBetweenlands.createParticle(ParticleRegistry.INFUSER_BUBBLE.get(), level, xx + 0.3F - random.nextFloat() * 0.6F, yy, zz + 0.3F - random.nextFloat() * 0.6F, ParticleFactory.ParticleArgs.get().withScale(0.3F).withColor(colors[0], colors[1], colors[2], 1));
					if (random.nextInt(10) == 0 && infuser.getTemperature() > 70)
						level.playSound(null, xx, yy, zz, SoundEvents.LAVA_AMBIENT, SoundSource.BLOCKS, 1.2F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * 0.5F);
				}
				if (infuser.getTemperature() >= 100) {
					float[] colors = infuser.currentInfusionColor;
					for (int i = 0; i < 2 + random.nextInt(3); i++) {
//						BatchedParticleRenderer.INSTANCE.addParticle(DefaultParticleBatches.TRANSLUCENT_GLOWING_NEAREST_NEIGHBOR, BLParticles.SMOOTH_SMOKE.create(level, pos.getX() + 0.5F, pos.getY() + 0.75F, pos.getZ() + 0.5F,
//							ParticleArgs.get()
//								.withMotion((random.nextFloat() * 0.25F - 0.125f) * 0.09f, random.nextFloat() * 0.02F + 0.01F, (random.nextFloat() * 0.25F - 0.125f) * 0.09f)
//								.withScale(1f + random.nextFloat() * 2.0F)
//								.withColor(colors[0], colors[1], colors[2], 1)
//								.withData(80, true, 0.01F, true)));
					}
				}
			}
		}
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new InfuserBlockEntity(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return createTickerHelper(type, BlockEntityRegistry.INFUSER.get(), InfuserBlockEntity::tick);
	}

	@Override
	public void handlePrecipitation(BlockState state, Level level, BlockPos pos, Biome.Precipitation precipitation) {
		if (level.dimension() == DimensionRegistries.DIMENSION_KEY && level.getBlockEntity(pos) instanceof InfuserBlockEntity infuser) {
			infuser.tank.fill(new FluidStack(FluidRegistry.SWAMP_WATER_STILL, FluidType.BUCKET_VOLUME), IFluidHandler.FluidAction.EXECUTE);
		}
	}
}
