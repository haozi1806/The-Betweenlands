package thebetweenlands.common.item.armor;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import thebetweenlands.common.registries.ArmorMaterialRegistry;

import java.util.List;

public class AncientArmorItem extends ArmorItem {
	public AncientArmorItem(Type type, Properties properties) {
		super(ArmorMaterialRegistry.ANCIENT, type, properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.translatable("item.thebetweenlands.ancient_armor.desc").withStyle(ChatFormatting.GRAY));
		if (stack.getDamageValue() == stack.getMaxDamage()) {
			tooltip.add(Component.translatable("item.thebetweenlands.broken", stack.getDisplayName()).withStyle(ChatFormatting.GRAY));
		}
	}

	@Override
	public void setDamage(ItemStack stack, int damage) {
		int maxDamage = stack.getMaxDamage();
		if (damage > maxDamage) {
			stack.remove(DataComponents.ATTRIBUTE_MODIFIERS);
			damage = maxDamage;
		}
		super.setDamage(stack, damage);
	}
}
