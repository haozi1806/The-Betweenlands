package thebetweenlands.common.block.container;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import javax.annotation.Nullable;
import thebetweenlands.common.block.misc.HorizontalBaseEntityBlock;
import thebetweenlands.common.block.entity.CrabPotBlockEntity;
import thebetweenlands.common.block.waterlog.SwampWaterLoggable;
import thebetweenlands.common.registries.BlockEntityRegistry;
import thebetweenlands.common.registries.BlockRegistry;

import java.util.List;

public class CrabPotBlock extends HorizontalBaseEntityBlock implements SwampWaterLoggable {

	public static final VoxelShape X_AXIS_SHAPE = Block.box(0.0D, 0.0D, 1.0D, 16.0D, 16.0D, 15.0D);
	public static final VoxelShape Z_AXIS_SHAPE = Block.box(1.0D, 0.0D, 0.0D, 15.0D, 16.0D, 16.0D);

	public CrabPotBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(WATER_TYPE, WaterType.NONE));
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return state.getValue(FACING).getAxis() == Direction.Axis.X ? X_AXIS_SHAPE : Z_AXIS_SHAPE;
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		BlockState state = super.getStateForPlacement(context);
		if (level.getBlockState(pos.below()).is(BlockRegistry.CRAB_POT_FILTER)) {
			state = state.setValue(FACING, level.getBlockState(pos.below()).getValue(CrabPotFilterBlock.FACING));
		}
		return state.setValue(WATER_TYPE, WaterType.getFromFluid(context.getLevel().getFluidState(context.getClickedPos()).getType()));
	}

	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		super.setPlacedBy(level, pos, state, placer, stack);
		if (placer instanceof Player player && level.getBlockEntity(pos) instanceof CrabPotBlockEntity pot) {
			pot.setPlacer(player);
			level.sendBlockUpdated(pos, state, state, 3);
		}
	}

	@Override
	protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if (level.getBlockEntity(pos) instanceof CrabPotBlockEntity pot) {
			if (pot.getTheItem().isEmpty()) {
				pot.setTheItem(stack.copyWithCount(1));
				stack.consume(1, player);
				level.sendBlockUpdated(pos, state, state, 3);
				return ItemInteractionResult.sidedSuccess(level.isClientSide());
			}
		}
		return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		if (level.getBlockEntity(pos) instanceof CrabPotBlockEntity pot) {
			if (!pot.getTheItem().isEmpty()) {
				ItemEntity item = new ItemEntity(level, pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D, pot.getTheItem());
				item.setDeltaMovement(Vec3.ZERO);
				level.addFreshEntity(item);
				pot.setTheItem(ItemStack.EMPTY);
				level.sendBlockUpdated(pos, state, state, 3);
				return InteractionResult.sidedSuccess(level.isClientSide());
			}
		}
		return super.useWithoutItem(state, level, pos, player, hitResult);
	}

	@Override
	protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
		if (state.getValue(WATER_TYPE) != WaterType.NONE) {
			level.scheduleTick(pos, state.getValue(WATER_TYPE).getFluid(), state.getValue(WATER_TYPE).getFluid().getTickDelay(level));
		}

		return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.translatable(this.getDescriptionId() + ".desc").withStyle(ChatFormatting.GRAY));
	}

	@Override
	protected FluidState getFluidState(BlockState state) {
		return state.getValue(WATER_TYPE).getFluid().defaultFluidState();
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (level.getBlockEntity(pos) instanceof CrabPotBlockEntity pot) {
			if (!pot.hasBaitItem() && !pot.getItem(0).isEmpty() && pot.getEntity() != null) {
				if (random.nextInt(3) == 0) {
					level.addParticle(ParticleTypes.BUBBLE, pos.getX() + 0.5D, pos.getY() + 0.5D + (float) pot.fallCounter * 0.03125F, pos.getZ() + 0.5D, 0.0D, 0.3D, 0.0D);
				}
				if (pot.fallCounter >= 1 && pot.fallCounter < 16) {
					for (int count = 0; count < 10; ++count) {
//						BLParticles.ITEM_BREAKING.spawn(level, pos.getX() + 0.5D + (random.nextDouble() - 0.5D), pos.getY() + 0.5D + random.nextDouble(), pos.getZ() + 0.5D + (random.nextDouble() - 0.5D), ParticleArgs.get().withData(new ItemStack(ItemRegistry.ANADIA_REMAINS.get())));
					}
				}
			}
		}
	}

	@Override
	protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
		Containers.dropContentsOnDestroy(state, newState, level, pos);
		super.onRemove(state, level, pos, newState, movedByPiston);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new CrabPotBlockEntity(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return createTickerHelper(type, BlockEntityRegistry.CRAB_POT.get(), CrabPotBlockEntity::tick);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder.add(WATER_TYPE));
	}
}
