package thebetweenlands.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;

public class RenderUtils {
	private static int frameCounter = 0;
	private static int renderTickCounter = 0;
	public static boolean framebufferSupported;
	public static FboMode framebufferType;

	public static void drawGhostItemAtSlot(GuiGraphics graphics, ItemStack stack, Slot slot) {
		drawGhostItemAtSlot(graphics, stack, slot.x, slot.y, 16, true);
	}

	public static void drawGhostItemAtSlot(GuiGraphics graphics, ItemStack stack, int startX, int startY, int size, boolean renderNumber) {
		graphics.renderFakeItem(stack, startX, startY);

		// draw 50% gray rectangle over the item
		RenderSystem.disableDepthTest();
		graphics.pose().pushPose();
		graphics.pose().translate(0.0D, 0.0D, 200.0D);
		graphics.fill(startX, startY, startX + size, startY + size, 0x9f8b8b8b);
		graphics.pose().popPose();
		RenderSystem.enableDepthTest();
		if (renderNumber) {
			graphics.renderItemDecorations(Minecraft.getInstance().font, stack, startX, startY);
		}
	}

	public static int getRenderTickCounter() {
		return renderTickCounter;
	}

	public static int getFrameCounter() {
		return frameCounter;
	}

	public final void initType() {
		GLCapabilities capabilities = GL.getCapabilities();
		framebufferSupported = capabilities.OpenGL14 && (capabilities.GL_ARB_framebuffer_object || capabilities.GL_EXT_framebuffer_object || capabilities.OpenGL30);
		if (capabilities.OpenGL30) {
			framebufferType = FboMode.BASE;
		} else if (capabilities.GL_ARB_framebuffer_object) {
			framebufferType = FboMode.ARB;
		} else if (capabilities.GL_EXT_framebuffer_object) {
			framebufferType = FboMode.EXT;
		}
	}

	public static void tickFrameCounter(RenderLevelStageEvent event) {
		if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_LEVEL) {
			frameCounter++;
		}
	}

	public static void incrementTickCounter(ClientTickEvent.Post event) {
		renderTickCounter++;
	}

	public static void renderCuboid(PoseStack.Pose pose, VertexConsumer consumer, int light, int color, TextureAtlasSprite sprite, float xMin, float xMax, float yMin, float yMax, float zMin, float zMax) {
		float uMin = sprite.getU0();
		float uMax = sprite.getU1();
		float vMin = sprite.getV0();
		float vMax = sprite.getV1();
		float vHeight = vMax - vMin;

		// top
		renderTopQuad(pose, consumer, light, color, xMin, xMax, yMax, zMin, zMin, uMin, uMax, vMin, vMax);
		// north
		renderSideQuad(pose, consumer, light, color, xMin, xMax, yMin, yMax, zMin, zMin, uMin, uMax, vMin, vMin + (vHeight * yMax));
		// south
		renderSideQuad(pose, consumer, light, color, xMax, xMin, yMin, yMax, zMax, zMax, uMin, uMax, vMin, vMin + (vHeight * yMax));
		// east
		renderSideQuad(pose, consumer, light, color, xMin, xMin, yMin, yMax, zMax, zMin, uMin, uMax, vMin, vMin + (vHeight * yMax));
		// west
		renderSideQuad(pose, consumer, light, color, xMax, xMax, yMin, yMax, zMin, zMax, uMin, uMax, vMin, vMin + (vHeight * yMax));
	}

	public static void renderTopQuad(PoseStack.Pose pose, VertexConsumer consumer, int light, int color, float minX, float maxX, float height, float minZ, float maxZ, float minU, float maxU, float minV, float maxV) {
		addVertex(pose, consumer, light, color, maxX, height, minZ, minU, minV);
		addVertex(pose, consumer, light, color, minX, height, minZ, minU, maxV);
		addVertex(pose, consumer, light, color, minX, height, maxZ, maxU, maxV);
		addVertex(pose, consumer, light, color, maxX, height, maxZ, maxU, minV);
	}

	private static void renderSideQuad(PoseStack.Pose pose, VertexConsumer consumer, int light, int color, float minX, float maxX, float minY, float maxY, float minZ, float maxZ, float minU, float maxU, float minV, float maxV) {
		addVertex(pose, consumer, light, color, minX, minY, minZ, minU, minV);
		addVertex(pose, consumer, light, color, minX, maxY, minZ, minU, maxV);
		addVertex(pose, consumer, light, color, maxX, maxY, maxZ, maxU, maxV);
		addVertex(pose, consumer, light, color, maxX, minY, maxZ, maxU, minV);
	}

	private static void addVertex(PoseStack.Pose pose, VertexConsumer consumer, int light, int color, float x, float y, float z, float u, float v) {
		consumer.addVertex(pose, x, y, z).setColor(color).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0.0F, 1.0F, 0.0F);
	}

	/**
	 * Saves the texture of an FBO to the specified PNG file.
	 *
	 * @param file
	 * @param fbo
	 */
	public static void saveFboToFile(File file, RenderTarget fbo) {
		FramebufferStack.State state = null;
		try {
			FramebufferStack.push();

			fbo.bindWrite(false);

			GL11.glReadBuffer(GL11.GL_FRONT);
			int width = fbo.viewWidth;
			int height = fbo.viewHeight;
			int bpp = 4;
			ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
			GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

			String format = "PNG";
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					int i = (x + (width * y)) * bpp;
					int r = buffer.get(i) & 0xFF;
					int g = buffer.get(i + 1) & 0xFF;
					int b = buffer.get(i + 2) & 0xFF;
					int a = buffer.get(i + 3) & 0xFF;
					image.setRGB(x, height - (y + 1), (a << 24) | (r << 16) | (g << 8) | b);
				}
			}

			try {
				ImageIO.write(image, format, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} finally {
			if (state != null) {
				state.close();
			}
		}
	}

	enum FboMode {
		BASE,
		ARB,
		EXT
	}
}
