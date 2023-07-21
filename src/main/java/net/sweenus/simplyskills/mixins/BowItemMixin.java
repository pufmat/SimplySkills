package net.sweenus.simplyskills.mixins;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import net.sweenus.simplyskills.util.AbilityEffects;
import net.sweenus.simplyskills.util.HelperMethods;
import net.sweenus.simplyskills.util.SkillReferencePosition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BowItem.class)
public abstract class BowItemMixin {

    @Inject(at = @At("HEAD"), method = "onStoppedUsing", cancellable = true)
    public void simplyskills$onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks, CallbackInfo ci) {
        if (user instanceof PlayerEntity player) {
            if (player instanceof ServerPlayerEntity serverPlayer) {

                // Effect - Elemental Arrows
                if (HelperMethods.isUnlocked("simplyskills_ranger",
                        SkillReferencePosition.rangerSpecialisationElementalArrows, player)) {
                    if (player.getItemUseTime() > 20) {

                        if (AbilityEffects.effectRangerElementalArrows(player))
                            ci.cancel();
                    }
                }


                // Effect - Arrow Rain
                else if (HelperMethods.isUnlocked("simplyskills_ranger",
                        SkillReferencePosition.rangerSpecialisationArrowRain, player)) {
                    if (player.getItemUseTime() > 20) {

                        if (AbilityEffects.effectRangerArrowRain(player))
                            ci.cancel();
                    }
                }
            }
        }
    }


}