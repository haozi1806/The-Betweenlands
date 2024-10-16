package thebetweenlands.common.item.armor.amphibious.upgrades;

import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.Tags;
import thebetweenlands.api.item.amphibious.TickingAmphibiousArmorUpgrade;
import thebetweenlands.common.entity.fishing.anadia.Anadia;
import thebetweenlands.common.item.armor.amphibious.AmphibiousArmorItem;

import java.util.function.Predicate;

public class FishSightArmorUpgrade extends SimpleAmphibiousArmorUpgrade implements TickingAmphibiousArmorUpgrade {
	public FishSightArmorUpgrade(int maxDamage, DamageEvent damageEvent, Predicate<ItemStack> matcher, EquipmentSlot... armorTypes) {
		super(maxDamage, damageEvent, matcher, armorTypes);
	}

	@Override
	public void onArmorTick(Level level, Player player, ItemStack stack, int upgradeCount, int armorCount) {
		if (!player.isEyeInFluid(Tags.Fluids.WATER) && player.tickCount % 40 == 0) {
			int radius = upgradeCount * 8;

			AABB aabb = new AABB(player.blockPosition()).inflate(radius);

			for(Anadia anadia : level.getEntitiesOfClass(Anadia.class, aabb, a -> a.distanceToSqr(player) <= radius * radius)) {
				if(anadia.getGlowTimer() <= 0) {
					anadia.setGlowTimer(200);
					AmphibiousArmorItem.damageUpgrade(stack, player, this, 1, DamageEvent.ON_USE, false);
				}
			}
		}
	}
}
