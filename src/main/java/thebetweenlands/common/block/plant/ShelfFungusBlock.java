package thebetweenlands.common.block.plant;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import javax.annotation.Nullable;

public class ShelfFungusBlock extends Block {

	public static final BooleanProperty TOP = BooleanProperty.create("is_top");

	public ShelfFungusBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.getStateDefinition().any().setValue(TOP, true));
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(TOP, !context.getLevel().getBlockState(context.getClickedPos().above()).isRedstoneConductor(context.getLevel(), context.getClickedPos().above()));
	}

	@Override
	protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
		if (direction == Direction.UP) {
			return state.setValue(TOP, !level.getBlockState(pos.above()).isRedstoneConductor(level, pos.above()));
		}
		return state;
	}


	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(TOP);
	}
}
