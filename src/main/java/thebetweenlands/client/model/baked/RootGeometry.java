package thebetweenlands.client.model.baked;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.annotation.Nullable;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.ChunkRenderTypeSet;
import net.neoforged.neoforge.client.model.IDynamicBakedModel;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.client.model.data.ModelProperty;
import net.neoforged.neoforge.client.model.geometry.IGeometryBakingContext;
import net.neoforged.neoforge.client.model.geometry.IGeometryLoader;
import net.neoforged.neoforge.client.model.geometry.IUnbakedGeometry;
import net.neoforged.neoforge.client.textures.UnitTextureAtlasSprite;
import thebetweenlands.common.block.terrain.StalactiteBlock;
import thebetweenlands.util.QuadBuilder;
import thebetweenlands.util.StalactiteHelper;
import thebetweenlands.util.StalactiteHelper.IStalactite;

public record RootGeometry(boolean emissiveBase, boolean emissiveOverlay) implements IUnbakedGeometry<RootGeometry> {

	public static final ModelProperty<Boolean> NO_BOTTOM = new ModelProperty<>(Predicates.notNull());
	public static final ModelProperty<Boolean> NO_TOP = new ModelProperty<>(Predicates.notNull());
	public static final ModelProperty<Integer> DIST_UP = new ModelProperty<>(Predicates.notNull());
	public static final ModelProperty<Integer> DIST_DOWN = new ModelProperty<>(Predicates.notNull());
	public static final ModelProperty<Integer> POS_X = new ModelProperty<>(Predicates.notNull());
	public static final ModelProperty<Integer> POS_Y = new ModelProperty<>(Predicates.notNull());
	public static final ModelProperty<Integer> POS_Z = new ModelProperty<>(Predicates.notNull());
	public static final ModelProperty<Boolean> HAS_OVERLAY = new ModelProperty<>(Predicates.notNull());

	@Override
	public BakedModel bake(IGeometryBakingContext context, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides) {
		TextureAtlasSprite textureTop = spriteGetter.apply(context.getMaterial("top"));
		TextureAtlasSprite textureMiddle = spriteGetter.apply(context.getMaterial("middle"));
		TextureAtlasSprite textureBottom = spriteGetter.apply(context.getMaterial("bottom"));
		TextureAtlasSprite textureOverlay = context.hasMaterial("overlay") ? spriteGetter.apply(context.getMaterial("overlay")) : UnitTextureAtlasSprite.INSTANCE;
		TextureAtlasSprite textureParticle = context.hasMaterial("particle") ? spriteGetter.apply(context.getMaterial("particle")) : textureTop;

		return new RootDynamicModel(textureTop, textureMiddle, textureBottom, textureOverlay, textureParticle, this.emissiveBase(), this.emissiveOverlay());
	}

	public record RootDynamicModel(TextureAtlasSprite textureTop, TextureAtlasSprite textureMiddle, TextureAtlasSprite textureBottom, TextureAtlasSprite textureOverlay, TextureAtlasSprite textureParticle, boolean emissiveBase, boolean emissiveOverlay) implements IDynamicBakedModel {

		@Override
		public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource random, ModelData extraData, @Nullable RenderType renderType) {
			List<BakedQuad> quads;

			if (side == null) {
				int distUp = Optional.ofNullable(extraData.get(DIST_UP)).orElse(0);
				int distDown = Optional.ofNullable(extraData.get(DIST_DOWN)).orElse(0);
				boolean noTop = Optional.ofNullable(extraData.get(NO_TOP)).orElse(true);
				boolean noBottom = Optional.ofNullable(extraData.get(NO_BOTTOM)).orElse(true);
				int posX = Optional.ofNullable(extraData.get(POS_X)).orElse(0);
				int posY = Optional.ofNullable(extraData.get(POS_Y)).orElse(0);
				int posZ = Optional.ofNullable(extraData.get(POS_Z)).orElse(0);
				float height = 1.0F;

				int totalHeight = 1 + distDown + distUp;
				float distToMidBottom, distToMidTop;

				double squareAmount = 1.2D;
				double halfTotalHeightSQ;

				if (noTop) {
					halfTotalHeightSQ = Math.pow(totalHeight, squareAmount);
					distToMidBottom = Math.abs(distUp + 1);
					distToMidTop = Math.abs(distUp);
				} else if (noBottom) {
					halfTotalHeightSQ = Math.pow(totalHeight, squareAmount);
					distToMidBottom = Math.abs(distDown);
					distToMidTop = Math.abs(distDown + 1);
				} else {
					float halfTotalHeight = totalHeight * 0.5F;
					halfTotalHeightSQ = Math.pow(halfTotalHeight, squareAmount);
					distToMidBottom = Math.abs(halfTotalHeight - distUp - 1);
					distToMidTop = Math.abs(halfTotalHeight - distUp);
				}

				int minValBottom = (noBottom && distDown == 0) ? 0 : 1;
				int minValTop = (noTop && distUp == 0) ? 0 : 1;
				int scaledValBottom = (int) (Math.pow(distToMidBottom, squareAmount) / halfTotalHeightSQ * (8 - minValBottom)) + minValBottom;
				int scaledValTop = (int) (Math.pow(distToMidTop, squareAmount) / halfTotalHeightSQ * (8 - minValTop)) + minValTop;

				float umin = 0;
				float umax = 16;
				float vmin = 0;
				float vmax = 16;

				float halfSize = (float) scaledValBottom / 16;
				float halfSizeTexW = halfSize * (umax - umin);
				float halfSize1 = (float) (scaledValTop) / 16;
				float halfSizeTex1 = halfSize1 * (umax - umin);

				StalactiteHelper core = StalactiteHelper.getValsFor(posX, posY, posZ);

				if (distDown == 0 && !noBottom) {
					core.bX = 0.5F;
					core.bZ = 0.5F;
				}
				if (distUp == 0 && !noTop) {
					core.tX = 0.5F;
					core.tZ = 0.5F;
				}

				boolean hasTop = distUp == 0 && !noTop;
				boolean hasBottom = distDown == 0 && !noBottom;

				boolean hasOverlay = Optional.ofNullable(extraData.get(HAS_OVERLAY)).orElse(false);

				QuadBuilder builder = new QuadBuilder(hasOverlay ? 40 : 24, renderType != null ? renderType.format : DefaultVertexFormat.BLOCK);


				for(int i = 0; i < (hasOverlay ? 2 : 1); ++i) {

					if(i == 0) {
						builder.setSprite(hasTop ? this.textureTop : hasBottom ? this.textureBottom : this.textureMiddle);
						if(emissiveBase)
							builder.setLightmap(15, 15);
						else
							builder.removeLightmap();
					}
					else {
						builder.setSprite(this.textureOverlay);
						if(emissiveOverlay)
							builder.setLightmap(15, 15);
						else
							builder.removeLightmap();
					}

					// front
					builder.addVertex(core.bX - halfSize, 0, core.bZ - halfSize, umin + halfSizeTexW * 2, vmax);
					builder.addVertex(core.bX - halfSize, 0, core.bZ + halfSize, umin, vmax);
					builder.addVertex(core.tX - halfSize1, height, core.tZ + halfSize1, umin, vmin);
					builder.addVertex(core.tX - halfSize1, height, core.tZ - halfSize1, umin + halfSizeTex1 * 2, vmin);
					// back
					builder.addVertex(core.bX + halfSize, 0, core.bZ + halfSize, umin + halfSizeTexW * 2, vmax);
					builder.addVertex(core.bX + halfSize, 0, core.bZ - halfSize, umin, vmax);
					builder.addVertex(core.tX + halfSize1, height, core.tZ - halfSize1, umin, vmin);
					builder.addVertex(core.tX + halfSize1, height, core.tZ + halfSize1, umin + halfSizeTex1 * 2, vmin);
					// left
					builder.addVertex(core.bX + halfSize, 0, core.bZ - halfSize, umin + halfSizeTexW * 2, vmax);
					builder.addVertex(core.bX - halfSize, 0, core.bZ - halfSize, umin, vmax);
					builder.addVertex(core.tX - halfSize1, height, core.tZ - halfSize1, umin, vmin);
					builder.addVertex(core.tX + halfSize1, height, core.tZ - halfSize1, umin + halfSizeTex1 * 2, vmin);
					// right
					builder.addVertex(core.bX - halfSize, 0, core.bZ + halfSize, umin + halfSizeTexW * 2, vmax);
					builder.addVertex(core.bX + halfSize, 0, core.bZ + halfSize, umin, vmax);
					builder.addVertex(core.tX + halfSize1, height, core.tZ + halfSize1, umin, vmin);
					builder.addVertex(core.tX - halfSize1, height, core.tZ + halfSize1, umin + halfSizeTex1 * 2, vmin);

					// Do not render overlay on top/bottom faces
					if(i == 1) continue;

					// top
					if (distUp == 0) {
						builder.addVertex(core.tX - halfSize1, height, core.tZ - halfSize1, umin, vmin);
						builder.addVertex(core.tX - halfSize1, height, core.tZ + halfSize1, umin + halfSizeTex1 * 2, vmin);
						builder.addVertex(core.tX + halfSize1, height, core.tZ + halfSize1, umin + halfSizeTex1 * 2, vmin + halfSizeTex1 * 2);
						builder.addVertex(core.tX + halfSize1, height, core.tZ - halfSize1, umin, vmin + halfSizeTex1 * 2);
					}

					// bottom
					if (distDown == 0) {
						builder.addVertex(core.bX - halfSize, 0, core.bZ + halfSize, umin + halfSizeTexW * 2, vmin);
						builder.addVertex(core.bX - halfSize, 0, core.bZ - halfSize, umin, vmin);
						builder.addVertex(core.bX + halfSize, 0, core.bZ - halfSize, umin, vmin + halfSizeTexW * 2);
						builder.addVertex(core.bX + halfSize, 0, core.bZ + halfSize, umin + halfSizeTexW * 2, vmin + halfSizeTexW * 2);
					}
				}

				quads = builder.build().nonCulledQuads;
			} else {
				quads = ImmutableList.of();
			}

			return quads;
		}

		@Override
		public ModelData getModelData(BlockAndTintGetter level, BlockPos pos, BlockState state, ModelData modelData) {
			ModelData coolAndGoodModelData = IDynamicBakedModel.super.getModelData(level, pos, state, modelData);

			final int maxLength = StalactiteBlock.MAX_LENGTH;
			int distUp = 0;
			int distDown = 0;
			boolean noTop = false;
			boolean noBottom = false;
			boolean hasOverlay = false;

			final int pos_getY = pos.getY();

			if(state.getBlock() instanceof IStalactite stalactite) {
				//TODO pool pos probably
				MutableBlockPos mutablePos = new MutableBlockPos(pos.getX(), pos_getY, pos.getZ());
				BlockState mutableBlockState = state;

				hasOverlay = stalactite.doesRenderOverlay(level, mutablePos, mutableBlockState);

				for (distUp = 0; distUp < maxLength; distUp++) {
					mutableBlockState = level.getBlockState(mutablePos.setY(pos_getY + (1 + distUp)));
					if (stalactite.doesConnect(level, mutablePos, mutableBlockState))
						continue;
					if (mutableBlockState.isAir() || !mutableBlockState.isSolidRender(level, pos))
						noTop = true;
					break;
				}
				for (distDown = 0; distDown < maxLength; distDown++) {
					mutableBlockState = level.getBlockState(mutablePos.setY(pos_getY - (1 + distDown)));
					if (stalactite.doesConnect(level, mutablePos, mutableBlockState))
						continue;
					if (mutableBlockState.isAir() || !mutableBlockState.isSolidRender(level, pos))
						noBottom = true;
					break;
				}

			}

			return coolAndGoodModelData.derive()
				.with(POS_X, pos.getX()).with(POS_Y, pos_getY).with(POS_Z, pos.getZ())
				.with(DIST_UP, distUp).with(DIST_DOWN, distDown)
				.with(NO_TOP, noTop).with(NO_BOTTOM, noBottom)
				.with(HAS_OVERLAY, hasOverlay)
				.build();
		}

		@Override
		public ChunkRenderTypeSet getRenderTypes(BlockState state, RandomSource rand, ModelData data) {
			return data.get(HAS_OVERLAY) ? ChunkRenderTypeSet.of(RenderType.TRANSLUCENT) : IDynamicBakedModel.super.getRenderTypes(state, rand, data);
		}

		@Override
		public boolean useAmbientOcclusion() {
			return true;
		}

		@Override
		public boolean isGui3d() {
			return false;
		}

		@Override
		public boolean usesBlockLight() {
			return true;
		}

		@Override
		public boolean isCustomRenderer() {
			return true;
		}

		@Override
		public TextureAtlasSprite getParticleIcon() {
			return this.textureParticle;
		}

		@Override
		public ItemOverrides getOverrides() {
			return ItemOverrides.EMPTY;
		}
	}

	public static class RootGeometryLoader implements IGeometryLoader<RootGeometry> {
		public static final RootGeometryLoader INSTANCE = new RootGeometryLoader();

		@Override
		public RootGeometry read(JsonObject jsonObject, JsonDeserializationContext deserializationContext) throws JsonParseException {
			boolean emissiveBase = jsonObject.has("emissive_base") && jsonObject.get("emissive_base").getAsBoolean();
			boolean emissiveOverlay = jsonObject.has("emissive_overlay") && jsonObject.get("emissive_overlay").getAsBoolean();

			return new RootGeometry(emissiveBase, emissiveOverlay);
		}
	}
}
