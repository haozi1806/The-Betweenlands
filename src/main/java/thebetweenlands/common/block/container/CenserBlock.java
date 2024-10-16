package thebetweenlands.common.block.container;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import javax.annotation.Nullable;
import thebetweenlands.api.aspect.registry.AspectType;
import thebetweenlands.api.block.AspectFogBlock;
import thebetweenlands.api.block.DungeonFogBlock;
import thebetweenlands.api.recipes.CenserRecipe;
import thebetweenlands.common.block.misc.HorizontalBaseEntityBlock;
import thebetweenlands.common.block.entity.CenserBlockEntity;
import thebetweenlands.common.block.waterlog.SwampWaterLoggable;
import thebetweenlands.common.registries.BlockEntityRegistry;

import java.util.Optional;

public class CenserBlock extends HorizontalBaseEntityBlock implements DungeonFogBlock, AspectFogBlock, SwampWaterLoggable {

	public static final BooleanProperty ENABLED = BooleanProperty.create("enabled");
	private static final VoxelShape BOTTOM_BOX = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 5.0D, 14.0D);
	private static final VoxelShape MIDDLE_BOX = Block.box(3.0D, 5.0D, 3.0D, 13.0D, 11.0D, 13.0D);
	private static final VoxelShape TOP_BOX = Block.box(4.0D, 11.0D, 4.0D, 12.0D, 13.0D, 12.0D);
	private static final VoxelShape SHAPE = Shapes.or(BOTTOM_BOX, MIDDLE_BOX, TOP_BOX);

	public CenserBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(ENABLED, true).setValue(WATER_TYPE, WaterType.NONE));
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		Optional<IFluidHandler> fluidHandler = FluidUtil.getFluidHandler(level, pos, hitResult.getDirection());

		if (fluidHandler.isPresent() && FluidUtil.getFluidHandler(stack).isPresent()) {
			if (FluidUtil.interactWithFluidHandler(player, hand, level, pos, hitResult.getDirection())) {
				return ItemInteractionResult.sidedSuccess(level.isClientSide());
			}
		}

		return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		if (level.isClientSide()) {
			return InteractionResult.SUCCESS;
		} else {
			if (level.getBlockEntity(pos) instanceof CenserBlockEntity censer) {
				player.openMenu(censer, buf -> buf.writeBlockPos(pos));
			}
			return InteractionResult.CONSUME;
		}
	}

	@Override
	protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
		if (newState.getBlock() != this && level.getBlockEntity(pos) instanceof CenserBlockEntity censer) {
			if (CenserBlockEntity.INTERNAL_SLOT < censer.getContainerSize()) {
				censer.setItem(CenserBlockEntity.INTERNAL_SLOT, ItemStack.EMPTY);
			}
			Containers.dropContentsOnDestroy(state, newState, level, pos);
			level.updateNeighbourForOutputSignal(pos, this);
		}
		super.onRemove(state, level, pos, newState, movedByPiston);
	}

	@Override
	protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
		if (!level.isClientSide()) {
			boolean enabled = !level.hasNeighborSignal(pos);
			if (enabled != state.getValue(ENABLED)) {
				level.setBlockAndUpdate(pos, state.setValue(ENABLED, enabled));
			}
		}
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(WATER_TYPE, WaterType.getFromFluid(context.getLevel().getFluidState(context.getClickedPos()).getType()));
	}

	@Override
	protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
		if (state.getValue(WATER_TYPE) != WaterType.NONE) {
			level.scheduleTick(pos, state.getValue(WATER_TYPE).getFluid(), state.getValue(WATER_TYPE).getFluid().getTickDelay(level));
		}

		return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
	}

	@Override
	protected FluidState getFluidState(BlockState state) {
		return state.getValue(WATER_TYPE).getFluid().defaultFluidState();
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new CenserBlockEntity(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return createTickerHelper(type, BlockEntityRegistry.CENSER.get(), CenserBlockEntity::tick);
	}

	@Override
	public boolean isCreatingDungeonFog(LevelAccessor levelAccessor, BlockPos pos, BlockState state) {
		return levelAccessor.getBlockEntity(pos) instanceof CenserBlockEntity censer && censer.getDungeonFogStrength(1) >= 0.1F;
	}

	@Override
	public Holder<AspectType> getAspectFogType(LevelAccessor level, BlockPos pos, BlockState state) {
		if (level.getBlockEntity(pos) instanceof CenserBlockEntity censer) {
			if (censer.isRecipeRunning()) {
				CenserRecipe<Object> recipe = censer.getCurrentRecipe();

				if (recipe != null) {
					return recipe.getAspectFogType(censer.getCurrentRecipeContext(), censer);
				}
			}
		}
		return null;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder.add(ENABLED, WATER_TYPE));
	}
}
