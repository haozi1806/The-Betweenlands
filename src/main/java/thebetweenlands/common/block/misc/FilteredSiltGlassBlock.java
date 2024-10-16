package thebetweenlands.common.block.misc;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import thebetweenlands.api.client.ConnectedTextureBlock;
import thebetweenlands.api.client.ConnectionRules;

public class FilteredSiltGlassBlock extends TransparentBlock implements ConnectedTextureBlock {

	public FilteredSiltGlassBlock(Properties properties) {
		super(properties);
	}

	@Override
	public ConnectionRules createConnectionRules(BlockAndTintGetter level, BlockPos pos, BlockState state) {
		final FilteredSiltGlassBlock parent = this;
		return new ConnectionRules() {
			@Override
			public boolean canTextureConnectTo(BlockAndTintGetter world, BlockPos pos, Direction face, BlockPos to) {
				Axis axis = face.getAxis();
				boolean onSamePlane = (axis != Axis.X || (to.getX() - pos.getX()) == 0) && (axis != Axis.Y || (to.getY() - pos.getY()) == 0) && (axis != Axis.Z || (to.getZ() - pos.getZ()) == 0);
				return onSamePlane && world.getBlockState(to).getBlock() == parent;
			}

			@Override
			public boolean canConnectThrough(BlockAndTintGetter world, BlockPos pos, Direction face, BlockPos to) {
				Axis axis = face.getAxis();
				if((axis == Axis.X && to.getX() - pos.getX() != 0) || (axis == Axis.Y && to.getY() - pos.getY() != 0) || (axis == Axis.Z && to.getZ() - pos.getZ() != 0)) {
					return !this.doesOccludeSide(world, pos, face, to, face.getOpposite()) && !(world.getBlockState(to).getBlock() == parent);
				}
				return false;
			}

			@Override
			public boolean doesOccludeSide(BlockAndTintGetter world, BlockPos pos, Direction face, BlockPos to, Direction toFace) {
				return ConnectionRules.super.doesOccludeSide(world, pos, face, to, toFace) || (world.getBlockState(to).getBlock() == parent);
			}
		};
	}

}
