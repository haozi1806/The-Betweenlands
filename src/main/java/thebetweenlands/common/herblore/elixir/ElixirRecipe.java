package thebetweenlands.common.herblore.elixir;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import thebetweenlands.api.BLRegistries;
import thebetweenlands.api.aspect.registry.AspectType;
import thebetweenlands.common.herblore.elixir.effects.ElixirEffect;
import thebetweenlands.common.registries.AspectTypeRegistry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public record ElixirRecipe(int infusionGradient, int infusionFinishedColor, int infusionFailedColor, int idealInfusionTime,
						   int infusionTimeVariation, int baseDuration, int durationModifier, int negativeBaseDuration, int negativeDurationModifier,
						   Holder<ElixirEffect> positiveElixir, Holder<ElixirEffect> negativeElixir, Optional<ResourceKey<AspectType>> strengthAspect, Optional<ResourceKey<AspectType>> durationAspect, List<ResourceKey<AspectType>> aspects) {

	public ElixirRecipe(int infusionGradient, int infusionFinishedColor, int infusionFailedColor, int idealInfusionTime,
						int infusionTimeVariation, int baseDuration, int durationModifier, int negativeBaseDuration, int negativeDurationModifier,
						Holder<ElixirEffect> positiveElixir, Holder<ElixirEffect> negativeElixir, List<ResourceKey<AspectType>> aspects) {
		this(infusionGradient, infusionFinishedColor, infusionFailedColor, idealInfusionTime,
			infusionTimeVariation, baseDuration, durationModifier, negativeBaseDuration, negativeDurationModifier,
			positiveElixir, negativeElixir, Optional.empty(), Optional.empty(), aspects);
	}

	public ElixirRecipe(int infusionGradient, int infusionFinishedColor, int infusionFailedColor, int idealInfusionTime,
						int infusionTimeVariation, int baseDuration, int durationModifier, int negativeBaseDuration, int negativeDurationModifier,
						Holder<ElixirEffect> positiveElixir, Holder<ElixirEffect> negativeElixir, ResourceKey<AspectType> strengthAspect, ResourceKey<AspectType> durationAspect, List<ResourceKey<AspectType>> aspects) {
		this(infusionGradient, infusionFinishedColor, infusionFailedColor, idealInfusionTime,
			infusionTimeVariation, baseDuration, durationModifier, negativeBaseDuration, negativeDurationModifier,
			positiveElixir, negativeElixir, Optional.of(strengthAspect), Optional.of(durationAspect), aspects);
	}

	public static final Codec<ElixirRecipe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Codec.INT.fieldOf("infusion_gradient").forGetter(ElixirRecipe::infusionGradient),
		Codec.INT.fieldOf("infusion_finished_color").forGetter(ElixirRecipe::infusionFinishedColor),
		Codec.INT.fieldOf("infusion_failed_color").forGetter(ElixirRecipe::infusionFailedColor),
		Codec.INT.fieldOf("infusion_time").forGetter(ElixirRecipe::idealInfusionTime),
		Codec.INT.fieldOf("infusion_time_variation").forGetter(ElixirRecipe::infusionTimeVariation),
		Codec.INT.fieldOf("base_duration").forGetter(ElixirRecipe::baseDuration),
		Codec.INT.fieldOf("duration_modifier").forGetter(ElixirRecipe::durationModifier),
		Codec.INT.fieldOf("negative_base_duration").forGetter(ElixirRecipe::negativeBaseDuration),
		Codec.INT.fieldOf("negative_duration_modifier").forGetter(ElixirRecipe::negativeDurationModifier),
		BLRegistries.ELIXIR_EFFECTS.holderByNameCodec().fieldOf("positive_elixir").forGetter(ElixirRecipe::positiveElixir),
		BLRegistries.ELIXIR_EFFECTS.holderByNameCodec().fieldOf("negative_elixir").forGetter(ElixirRecipe::negativeElixir),
		ResourceKey.codec(BLRegistries.Keys.ASPECT_TYPES).optionalFieldOf("strength_aspect").forGetter(ElixirRecipe::strengthAspect),
		ResourceKey.codec(BLRegistries.Keys.ASPECT_TYPES).optionalFieldOf("duration_aspect").forGetter(ElixirRecipe::durationAspect),
		ResourceKey.codec(BLRegistries.Keys.ASPECT_TYPES).listOf().fieldOf("aspects").forGetter(ElixirRecipe::aspects)
	).apply(instance, ElixirRecipe::new));

	@Nullable
	public static Holder<ElixirRecipe> getRecipeFor(Holder<ElixirEffect> effect, HolderLookup.Provider provider) {
		for (Holder<ElixirRecipe> recipe : provider.lookupOrThrow(BLRegistries.Keys.ELIXIR_RECIPES).listElements().toList()) {
			if (effect == recipe.value().negativeElixir() || effect == recipe.value().positiveElixir()) {
				return recipe;
			}
		}
		return null;
	}

	@Nullable
	public static Holder<ElixirRecipe> getFromAspects(List<Holder<AspectType>> aspects, HolderLookup.Provider provider) {
		for (Holder<ElixirRecipe> recipe : provider.lookupOrThrow(BLRegistries.Keys.ELIXIR_RECIPES).listElements().toList()) {
			boolean matches = true;
			checkAvailability:
			for (ResourceKey<AspectType> recipeAspect : recipe.value().aspects()) {
				for (Holder<AspectType> aspect : aspects) {
					if (aspect.is(AspectTypeRegistry.BYARIIS)) continue;
					if (recipe.value().durationAspect().isPresent() && aspect.is(recipe.value().durationAspect().get())) continue;
					if (recipe.value().strengthAspect().isPresent() && aspect.is(recipe.value().strengthAspect().get())) continue;
					boolean contains = false;
					for (ResourceKey<AspectType> a : recipe.value().aspects()) {
						if (a == aspect.getKey()) {
							contains = true;
							break;
						}
					}
					if (!contains) {
						matches = false;
						break checkAvailability;
					}
				}
				if (!aspects.stream().map(Holder::getKey).toList().contains(recipeAspect)) {
					matches = false;
					break;
				}
			}
			if (matches) {
				return recipe;
			}
		}
		return null;
	}

	public static List<Holder<ElixirRecipe>> getFromAspect(Holder<AspectType> aspectType, HolderLookup.Provider provider) {
		List<Holder<ElixirRecipe>> recipes = new ArrayList<>();
		for (Holder<ElixirRecipe> recipe : provider.lookupOrThrow(BLRegistries.Keys.ELIXIR_RECIPES).listElements().toList()) {
			for (ResourceKey<AspectType> recipeAspect : recipe.value().aspects()) {
				if (aspectType.is(recipeAspect))
					recipes.add(recipe);
			}
		}
		return recipes;
	}

	public float[] getRGBA(int color) {
		float a = (float) (color >> 24 & 255) / 255.0F;
		float r = (float) (color >> 16 & 255) / 255.0F;
		float g = (float) (color >> 8 & 255) / 255.0F;
		float b = (float) (color & 255) / 255.0F;
		return new float[]{r, g, b, a};
	}

	public static float[] getInfusionColor(@Nullable Holder<ElixirRecipe> holder, int infusionTime) {
		if (holder != null) {
			ElixirRecipe recipe = holder.value();
			if (infusionTime > recipe.idealInfusionTime + recipe.infusionTimeVariation) {
				return recipe.getRGBA(recipe.infusionFailedColor);
			} else if (infusionTime > recipe.idealInfusionTime - recipe.infusionTimeVariation
				&& infusionTime <= recipe.idealInfusionTime + recipe.infusionTimeVariation) {
				return recipe.getRGBA(recipe.infusionFinishedColor);
			} else {
				float startR = 0.2F;
				float startG = 0.6F;
				float startB = 0.4F;
				float startA = 0.9F;
				float[] targetColor = recipe.getRGBA(recipe.infusionGradient);
				int targetTime = recipe.idealInfusionTime - recipe.infusionTimeVariation;
				float infusingPercentage = (float) infusionTime / (float) targetTime;
				float interpR = startR + (targetColor[0] - startR) * infusingPercentage;
				float interpG = startG + (targetColor[1] - startG) * infusingPercentage;
				float interpB = startB + (targetColor[2] - startB) * infusingPercentage;
				float interpA = startA + (targetColor[3] - startA) * infusingPercentage;
				return new float[]{interpR, interpG, interpB, interpA};
			}
		} else {
			return new float[]{0.8F, 0.0F, 0.8F, 1.0F};
		}
	}
}
