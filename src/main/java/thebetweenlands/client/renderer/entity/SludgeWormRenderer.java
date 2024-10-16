package thebetweenlands.client.renderer.entity;

import javax.annotation.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import thebetweenlands.client.BLModelLayers;
import thebetweenlands.client.model.entity.SludgeWormModel;
import thebetweenlands.common.TheBetweenlands;
import thebetweenlands.common.entity.monster.SludgeWorm;
import thebetweenlands.common.entity.monster.SludgeWormMultipart;

public class SludgeWormRenderer extends MobRenderer<SludgeWorm, SludgeWormModel> {
	public static final ResourceLocation TEXTURE_HEAD = TheBetweenlands.prefix("textures/entity/sludge_worm_head.png");
	public static final ResourceLocation TEXTURE_BODY = TheBetweenlands.prefix("textures/entity/sludge_worm_body.png");


	public SludgeWormRenderer(EntityRendererProvider.Context context) {
		super(context, new SludgeWormModel(context.bakeLayer(BLModelLayers.SLUDGE_WORM)), 0F);
	}

	@Override
	public void render(SludgeWorm entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int packedLight) {
		Minecraft minecraft = Minecraft.getInstance();
		boolean isVisible = this.isBodyVisible(entity);
		boolean isTranslucentToPlayer = !isVisible && !entity.isInvisibleTo(minecraft.player);
		boolean isGlowing = minecraft.shouldEntityAppearGlowing(entity);
		int overlay = getOverlayCoords(entity, this.getWhiteOverlayProgress(entity, partialTicks));
		int colour = isTranslucentToPlayer ? 654311423 : -1;

		stack.pushPose();
		float totalAngleDiff = 0.0f;

		for(int i = 0; i < entity.parts.length; i++) {
			Entity prevPart = entity;
			if (i > 0 )
				prevPart = entity.parts[i - 1];
			SludgeWormMultipart part = entity.parts[i];
			double yawDiff = (prevPart.getYRot() - part.getYRot()) % 360.0F;
			double yawInterpolant = 2 * yawDiff % 360.0F - yawDiff;
			totalAngleDiff += (float) Math.abs(yawInterpolant);
		}

		float avgAngleDiff = totalAngleDiff;

		if(entity.parts.length > 0)
			avgAngleDiff /= (entity.parts.length - 1);

		float avgWibbleStrength = Math.clamp(1.0F - avgAngleDiff / 60.0F, 0, 1);
		float x = 0F;
		float y = 0F;
		float z = 0F;

		var renderType = this.getRenderType(entity, isVisible, isTranslucentToPlayer, isGlowing);
		if (renderType != null) {
			VertexConsumer consumer = buffer.getBuffer(renderType);
			this.renderHead(stack, consumer, packedLight, overlay, colour, entity, 1, x, y + 1.5F, z, entityYaw, avgWibbleStrength, partialTicks);

			double ex = entity.xOld + (entity.getX() - entity.xOld) * (double) partialTicks;
			double ey = entity.yOld + (entity.getY() - entity.yOld) * (double) partialTicks;
			double ez = entity.zOld + (entity.getZ() - entity.zOld) * (double) partialTicks;
			double rx = ex - x;
			double ry = ey - y;
			double rz = ez - z;
			float zOffset = 0;

			for (int i = 0; i < entity.parts.length - 1; i++) {
				this.renderBodyPart(stack, consumer, packedLight, overlay, colour, entity, entity.parts[i], i > 0 ? entity.parts[i - 1] : entity, rx, ry, rz, i, avgWibbleStrength, zOffset -= 0.001F, partialTicks);
			}

			this.renderTailPart(stack, consumer, packedLight, overlay, colour, entity, entity.parts[entity.parts.length - 1], entity.parts[entity.parts.length - 2], rx, ry, rz, entity.parts.length - 1, avgWibbleStrength, partialTicks);
		}
		stack.popPose();
	}

	private void renderHead(PoseStack stack, VertexConsumer consumer, int light, int overlay, int colour, SludgeWorm entity, int frame, double x, double y, double z, float yaw, float avgWibbleStrength, float partialTicks) {
		double yawDiff = (yaw - entity.parts[1].getYRot()) % 360.0F;
		double yawInterpolant = 2 * yawDiff % 360.0F - yawDiff;
		float wibbleStrength = Math.min(avgWibbleStrength, Math.clamp(1.0F - (float)Math.abs(yawInterpolant) / 60.0F, 0, 1));
		stack.pushPose();
		stack.translate(x, y, z);
		stack.scale(-1F, -1F, 1F);
		stack.mulPose(Axis.YP.rotationDegrees(180F + yaw));
		this.model.renderHead(stack, consumer, light, overlay, colour, entity, frame, wibbleStrength, partialTicks);
		stack.popPose();
	}

	protected void renderBodyPart(PoseStack stack, VertexConsumer consumer, int light, int overlay, int colour, SludgeWorm entity, SludgeWormMultipart part, Entity prevPart, double rx, double ry, double rz, int frame, float avgWibbleStrength, float zOffset, float partialTicks) {
		double x = part.xOld + (part.xo - part.xOld) * (double)partialTicks - rx;
		double y = part.yOld + (part.yo - part.yOld) * (double)partialTicks - ry;
		double z = part.zOld + (part.zo - part.zOld) * (double)partialTicks - rz;
		float yaw = part.yRotO + (part.getYRot() - part.yRotO) * partialTicks;
		double yawDiff = (prevPart.getYRot() - part.getYRot()) % 360.0F;
		double yawInterpolant = 2 * yawDiff % 360.0F - yawDiff;
		float wibbleStrength = Math.min(avgWibbleStrength, Math.clamp(1.0F - (float)Math.abs(yawInterpolant) / 60.0F, 0, 1));

		stack.pushPose();
		stack.translate(x, y - 1.125f + zOffset, z);
		stack.mulPose(Axis.YN.rotationDegrees(yaw));
		this.model.renderBody(stack, consumer, light, overlay, colour, entity, frame, wibbleStrength, partialTicks);
		stack.popPose();
	}

	protected void renderTailPart(PoseStack stack, VertexConsumer consumer, int light, int overlay, int colour, SludgeWorm entity, SludgeWormMultipart part, Entity prevPart, double rx, double ry, double rz, int frame, float avgWibbleStrength, float partialTicks) {
		double x = part.xOld + (part.xo - part.xOld) * (double)partialTicks - rx;
		double y = part.yOld + (part.yo - part.yOld) * (double)partialTicks - ry;
		double z = part.zOld + (part.zo - part.zOld) * (double)partialTicks - rz;
		float yaw = part.yRotO + (part.getYRot() - part.yRotO) * partialTicks;
		double yawDiff = (prevPart.getYRot() - part.getYRot()) % 360.0F;
		double yawInterpolant = 2 * yawDiff % 360.0F - yawDiff;
		float wibbleStrength = Math.min(avgWibbleStrength, Math.clamp(1.0F - (float)Math.abs(yawInterpolant) / 60.0F, 0, 1));

		stack.pushPose();
		stack.translate(x, y + 1.525f, z);
		stack.scale(-1F, -1F, 1F);
		stack.mulPose(Axis.YP.rotationDegrees(180F + yaw));
		this.model.renderTail(stack, consumer, light, overlay, colour, entity, frame, wibbleStrength, partialTicks);
		stack.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(SludgeWorm entity) {
		return TEXTURE_HEAD;
	}
}