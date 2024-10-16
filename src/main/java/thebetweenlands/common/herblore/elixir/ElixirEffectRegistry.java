package thebetweenlands.common.herblore.elixir;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import thebetweenlands.api.BLRegistries;
import thebetweenlands.common.TheBetweenlands;
import thebetweenlands.common.herblore.elixir.effects.ElixirDecay;
import thebetweenlands.common.herblore.elixir.effects.ElixirDraining;
import thebetweenlands.common.herblore.elixir.effects.ElixirEffect;
import thebetweenlands.common.herblore.elixir.effects.ElixirEffect.ElixirPotionEffect;
import thebetweenlands.common.herblore.elixir.effects.ElixirFeasting;
import thebetweenlands.common.herblore.elixir.effects.ElixirHealing;
import thebetweenlands.common.herblore.elixir.effects.ElixirMasking;
import thebetweenlands.common.herblore.elixir.effects.ElixirPetrify;
import thebetweenlands.common.herblore.elixir.effects.ElixirRipening;
import thebetweenlands.common.herblore.elixir.effects.ElixirShocked;
import thebetweenlands.common.herblore.elixir.effects.ElixirStarvation;
import thebetweenlands.common.herblore.elixir.effects.ElixirSwiftarm;

import java.util.Map;

public class ElixirEffectRegistry {

	public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, TheBetweenlands.ID);
	public static final DeferredRegister<ElixirEffect> ELIXIRS = DeferredRegister.create(BLRegistries.Keys.ELIXIR_EFFECTS, TheBetweenlands.ID);

	//Elixirs
	public static final DeferredHolder<ElixirEffect, ElixirEffect> EFFECT_STRENGTH = registerElixir("strength", new ElixirEffect(TheBetweenlands.prefix("textures/item/green_dentrothyst_vial.png"), 0xFFFF0000).addAttributeModifier(Attributes.ATTACK_DAMAGE, TheBetweenlands.prefix("strength_buff"), 3.5D, AttributeModifier.Operation.ADD_VALUE).setShowInBook());
	public static final DeferredHolder<ElixirEffect, ElixirEffect> EFFECT_NIMBLEFEET = registerElixir("nimblefeet", new ElixirEffect(TheBetweenlands.prefix("textures/item/green_dentrothyst_vial.png"), 0xFFacaef0).addAttributeModifier(Attributes.MOVEMENT_SPEED, TheBetweenlands.prefix("nimblefeet_speedup"), 0.2D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).setShowInBook());
	public static final DeferredHolder<ElixirEffect, ElixirEffect> EFFECT_HEALING = registerElixir("healing", new ElixirHealing(TheBetweenlands.prefix("textures/item/green_dentrothyst_vial.png")).setShowInBook());
	public static final DeferredHolder<ElixirEffect, ElixirEffect> EFFECT_RIPENING = registerElixir("ripening", new ElixirRipening(TheBetweenlands.prefix("textures/item/green_dentrothyst_vial.png")).setShowInBook());
	public static final DeferredHolder<ElixirEffect, ElixirEffect> EFFECT_TOUGHSKIN = registerElixir("toughskin", new ElixirEffect(TheBetweenlands.prefix("textures/item/green_dentrothyst_vial.png"), 0xFF4311b1).setShowInBook());
	public static final DeferredHolder<ElixirEffect, ElixirEffect> EFFECT_FEASTING = registerElixir("feasting", new ElixirFeasting(TheBetweenlands.prefix("textures/item/green_dentrothyst_vial.png")).setShowInBook());
	public static final DeferredHolder<ElixirEffect, ElixirEffect> EFFECT_HUNTERSSENSE = registerElixir("hunter_sense", new ElixirEffect(TheBetweenlands.prefix("textures/item/green_dentrothyst_vial.png"), 0xFF6f175d).setShowInBook());
	public static final DeferredHolder<ElixirEffect, ElixirEffect> EFFECT_MASKING = registerElixir("masking", new ElixirMasking(TheBetweenlands.prefix("textures/item/green_dentrothyst_vial.png")).setShowInBook());
	public static final DeferredHolder<ElixirEffect, ElixirEffect> EFFECT_SWIFTARM = registerElixir("swiftarm", new ElixirSwiftarm(TheBetweenlands.prefix("textures/item/green_dentrothyst_vial.png")).setShowInBook());
	public static final DeferredHolder<ElixirEffect, ElixirEffect> EFFECT_CATSEYES = registerElixir("cats_eyes", new ElixirEffect(TheBetweenlands.prefix("textures/item/green_dentrothyst_vial.png"), 0xFF7aaa19).setShowInBook());
	public static final DeferredHolder<ElixirEffect, ElixirEffect> EFFECT_SAGITTARIUS = registerElixir("sagittarius", new ElixirEffect(TheBetweenlands.prefix("textures/item/green_dentrothyst_vial.png"), 0xFFea6731).setShowInBook());
	public static final DeferredHolder<ElixirEffect, ElixirEffect> EFFECT_SPIDERBREED = registerElixir("spiderbreed", new ElixirEffect(TheBetweenlands.prefix("textures/item/green_dentrothyst_vial.png"), 0xFF71c230).setShowInBook());
	public static final DeferredHolder<ElixirEffect, ElixirEffect> EFFECT_LIGHTWEIGHT = registerElixir("lightweight", new ElixirEffect(TheBetweenlands.prefix("textures/item/green_dentrothyst_vial.png"), 0xFF6528da).setShowInBook());
	public static final DeferredHolder<ElixirEffect, ElixirEffect> EFFECT_UNCLOUDED = registerElixir("unclouded", new ElixirEffect(TheBetweenlands.prefix("textures/item/green_dentrothyst_vial.png"), 0xFF0d9ddf).setShowInBook());
	public static final DeferredHolder<ElixirEffect, ElixirEffect> EFFECT_GILLSGROWTH = registerElixir("gillsgrowth", new ElixirEffect(TheBetweenlands.prefix("textures/item/green_dentrothyst_vial.png")));
	public static final DeferredHolder<ElixirEffect, ElixirEffect> EFFECT_WINGS = registerElixir("wings", new ElixirEffect(TheBetweenlands.prefix("textures/item/green_dentrothyst_vial.png")));
	public static final DeferredHolder<ElixirEffect, ElixirEffect> EFFECT_HUNTERSSENSEMASTER = registerElixir("hunter_sense_master", new ElixirEffect(TheBetweenlands.prefix("textures/item/green_dentrothyst_vial.png")));
	//Anti Elixirs
	public static final DeferredHolder<ElixirEffect, ElixirEffect> EFFECT_WEAKNESS = registerElixir("weakness", new ElixirEffect(TheBetweenlands.prefix("textures/item/green_dentrothyst_vial.png"), 0xFFFF0000).addAttributeModifier(Attributes.ATTACK_DAMAGE, TheBetweenlands.prefix("weakness_debuff"), -2.0D, AttributeModifier.Operation.ADD_VALUE).setAntiInfusion().setShowInBook());
	public static final DeferredHolder<ElixirEffect, ElixirEffect> EFFECT_LUMBERING = registerElixir("lumbering", new ElixirEffect(TheBetweenlands.prefix("textures/item/green_dentrothyst_vial.png"), 0xFFacaef0).addAttributeModifier(Attributes.MOVEMENT_SPEED, TheBetweenlands.prefix("lumbering_slowdown"), -0.2D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).setAntiInfusion().setShowInBook());
	public static final DeferredHolder<ElixirEffect, ElixirEffect> EFFECT_DRAINING = registerElixir("draining", new ElixirDraining(TheBetweenlands.prefix("textures/item/green_dentrothyst_vial.png")).setAntiInfusion().setShowInBook());
	public static final DeferredHolder<ElixirEffect, ElixirEffect> EFFECT_DECAY = registerElixir("decay", new ElixirDecay(TheBetweenlands.prefix("textures/item/green_dentrothyst_vial.png")).setAntiInfusion().setShowInBook());
	public static final DeferredHolder<ElixirEffect, ElixirEffect> EFFECT_POISONSTING = registerElixir("poisonsting", new ElixirEffect(TheBetweenlands.prefix("textures/item/green_dentrothyst_vial.png"), 0xFF4311b1).setAntiInfusion().setShowInBook());
	public static final DeferredHolder<ElixirEffect, ElixirEffect> EFFECT_STARVATION = registerElixir("starvation", new ElixirStarvation(TheBetweenlands.prefix("textures/item/green_dentrothyst_vial.png")).setAntiInfusion().setShowInBook());
	public static final DeferredHolder<ElixirEffect, ElixirEffect> EFFECT_DRUNKARD = registerElixir("drunkard", new ElixirEffect(TheBetweenlands.prefix("textures/item/green_dentrothyst_vial.png"), 0xFF6f175d).setAntiInfusion().setShowInBook());
	public static final DeferredHolder<ElixirEffect, ElixirEffect> EFFECT_STENCHING = registerElixir("stenching", new ElixirEffect(TheBetweenlands.prefix("textures/item/green_dentrothyst_vial.png"), 0xFF28ccd5).setAntiInfusion().setShowInBook());
	public static final DeferredHolder<ElixirEffect, ElixirEffect> EFFECT_SLUGARM = registerElixir("slugarm", new ElixirEffect(TheBetweenlands.prefix("textures/item/green_dentrothyst_vial.png"), 0xFFe8fc5b).setAntiInfusion().setShowInBook());
	public static final DeferredHolder<ElixirEffect, ElixirEffect> EFFECT_BLINDMAN = registerElixir("blindman", new ElixirEffect(TheBetweenlands.prefix("textures/item/green_dentrothyst_vial.png"), 0xFF7aaa19).setAntiInfusion().setShowInBook());
	public static final DeferredHolder<ElixirEffect, ElixirEffect> EFFECT_WEAKBOW = registerElixir("weakbow", new ElixirEffect(TheBetweenlands.prefix("textures/item/green_dentrothyst_vial.png"), 0xFFea6731).setAntiInfusion().setShowInBook());
	public static final DeferredHolder<ElixirEffect, ElixirEffect> EFFECT_BASILISK = registerElixir("basilisk", new ElixirPetrify(TheBetweenlands.prefix("textures/item/green_dentrothyst_vial.png")).setAntiInfusion().setShowInBook());
	public static final DeferredHolder<ElixirEffect, ElixirEffect> EFFECT_HEAVYWEIGHT = registerElixir("heavyweight", new ElixirEffect(TheBetweenlands.prefix("textures/item/green_dentrothyst_vial.png"), 0xFF6528da).setAntiInfusion().setShowInBook());
	public static final DeferredHolder<ElixirEffect, ElixirEffect> EFFECT_FOGGEDMIND = registerElixir("fogged_mind", new ElixirEffect(TheBetweenlands.prefix("textures/item/green_dentrothyst_vial.png"), 0xFF0d9ddf).setAntiInfusion().setShowInBook());
	public static final DeferredHolder<ElixirEffect, ElixirEffect> EFFECT_DEFORMED = registerElixir("deformed", new ElixirEffect(TheBetweenlands.prefix("textures/item/green_dentrothyst_vial.png")).setAntiInfusion());
	public static final DeferredHolder<ElixirEffect, ElixirEffect> EFFECT_LIMBLESS = registerElixir("limbless", new ElixirEffect(TheBetweenlands.prefix("textures/item/green_dentrothyst_vial.png")).setAntiInfusion());
	public static final DeferredHolder<ElixirEffect, ElixirEffect> EFFECT_ISOLATEDSENSES = registerElixir("isolated_senses", new ElixirEffect(TheBetweenlands.prefix("textures/item/green_dentrothyst_vial.png")).setAntiInfusion());
	//Other Elixirs
	public static final DeferredHolder<ElixirEffect, ElixirEffect> EFFECT_PETRIFY = registerElixir("petrify", new ElixirPetrify(TheBetweenlands.prefix("textures/item/green_dentrothyst_vial.png")));
	public static final DeferredHolder<ElixirEffect, ElixirEffect> EFFECT_BLESSED = registerElixir("blessed", new ElixirEffect(TheBetweenlands.prefix("textures/gui/effect_blessed.png")));
	public static final DeferredHolder<ElixirEffect, ElixirEffect> EFFECT_SHOCKED = registerElixir("shocked", new ElixirShocked(TheBetweenlands.prefix("textures/gui/effect_shocked.png")));

	//Potions
	public static final DeferredHolder<MobEffect, MobEffect> ROOT_BOUND = EFFECTS.register("root_bound", () -> new RootBoundEffect().addAttributeModifier(Attributes.MOVEMENT_SPEED, TheBetweenlands.prefix("root_bound_slowdown"), -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
	public static final DeferredHolder<MobEffect, MobEffect> ENLIGHTENED = EFFECTS.register("enlightened", EnlightenedEffect::new);


	public static DeferredHolder<ElixirEffect, ElixirEffect> registerElixir(String name, ElixirEffect elixir) {
		DeferredHolder<MobEffect, ElixirPotionEffect> effect = EFFECTS.register(name, () -> wrapAttributeModifiers(elixir, new ElixirEffect.ElixirPotionEffect(elixir, elixir.getColor(), elixir.getIcon())));
		elixir.setElixirEffect(effect);
		return ELIXIRS.register(name, () -> elixir);
	}

	private static ElixirPotionEffect wrapAttributeModifiers(ElixirEffect elixir, ElixirPotionEffect effect) {
		for(Map.Entry<Holder<Attribute>, ElixirEffect.AttributeTemplate> modifier : elixir.elixirAttributeModifiers.entrySet()) {
			effect.addAttributeModifier(modifier.getKey(), modifier.getValue().id(), modifier.getValue().amount(), modifier.getValue().operation());
		}
		return effect;
	}
}
