package dev.mayaqq.createestrogen.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.equipment.armor.BaseArmorItem;
import com.simibubi.create.content.equipment.armor.CardboardArmorItem;
import dev.mayaqq.estrogen.content.EstrogenEffects;
import invoke.kitty.kritter.registry.api.entry.RegistryEntries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BaseArmorItem.class)
public class BaseArmorItemMixin {
    @ModifyReturnValue(
            method = "getArmorTexture",
            at = @At("RETURN")
    )
    private ResourceLocation modifyArmorTexture(ResourceLocation original, @Local(argsOnly = true) ItemStack stack, @Local(argsOnly = true) Entity entity) {
        if (entity instanceof LivingEntity && stack.getItem() instanceof CardboardArmorItem) {
            LivingEntity livingEntity = (LivingEntity) entity;
            if (livingEntity.hasEffect(RegistryEntries.getHolder(EstrogenEffects.getEstrogen()))) {
                return ResourceLocation.fromNamespaceAndPath("createestrogen", original.getPath());
            }
        }
        return original;
    }
}
