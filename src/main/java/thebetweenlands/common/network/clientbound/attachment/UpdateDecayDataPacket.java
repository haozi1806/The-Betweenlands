package thebetweenlands.common.network.clientbound.attachment;

import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import thebetweenlands.common.TheBetweenlands;
import thebetweenlands.common.component.entity.DecayData;
import thebetweenlands.common.handler.PlayerDecayHandler;
import thebetweenlands.common.registries.AttachmentRegistry;

public record UpdateDecayDataPacket(int level, int prevLevel, float saturation, float acceleration) implements CustomPacketPayload {

	public static final Type<UpdateDecayDataPacket> TYPE = new Type<>(TheBetweenlands.prefix("update_decay_data"));
	public static final StreamCodec<RegistryFriendlyByteBuf, UpdateDecayDataPacket> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.INT, UpdateDecayDataPacket::level,
		ByteBufCodecs.INT, UpdateDecayDataPacket::prevLevel,
		ByteBufCodecs.FLOAT, UpdateDecayDataPacket::saturation,
		ByteBufCodecs.FLOAT, UpdateDecayDataPacket::acceleration,
		UpdateDecayDataPacket::new
	);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}

	@SuppressWarnings("resource") // Here because otherwise java suggests a try-with-resources (would call Minecraft.close() and crash the game)
	public static void handle(UpdateDecayDataPacket message, IPayloadContext context) {
		context.enqueueWork(() -> {
			Player player = context.player();
			player.setData(AttachmentRegistry.DECAY, new DecayData(message.level(), message.prevLevel(), message.saturation(), message.acceleration()));
			float initialMax = player.getMaxHealth();
			// Prevent the game from playing the take damage animation
			PlayerDecayHandler.applyDecayAttributeModifiers(player);
			float finalMax = player.getMaxHealth();
			if(player.getHealth() > player.getMaxHealth()) {
				player.setHealth(player.getMaxHealth());
			}
			int difference = Math.round(finalMax - initialMax);
			// Hack to make the game update the max health when decay changes
			Minecraft.getInstance().gui.displayHealth += difference;
		});
	}
}
