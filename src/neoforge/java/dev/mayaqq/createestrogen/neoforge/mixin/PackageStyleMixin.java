package dev.mayaqq.createestrogen.neoforge.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.logistics.box.PackageStyles;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.regex.Pattern;

@Mixin(PackageStyles.PackageStyle.class)
class PackageStyleMixin {
    @Unique
    private final Pattern createestrogen$IdRegex = Pattern.compile("[A-Za-z]+:.*");
    @Shadow
    @Final
    private String type;

    /**
     * @author Niko Dale
     * @reason create doesn't normally support package textures from other namespaces
     */
    @Inject(method = "getItemId()Lnet/minecraft/resources/ResourceLocation;", at = @At(value = "RETURN", shift = At.Shift.BEFORE), cancellable = true)
    void getItemId(CallbackInfoReturnable<ResourceLocation> cir, @Local(name = "id") String id) {
        // if it has a namespace then there is no reason to convert it
        if (createestrogen$IdRegex.asMatchPredicate().test(id)) cir.setReturnValue(ResourceLocation.parse(id));
    }

    /**
     * @author Niko Dale
     * @reason create doesn't normally support package textures from other namespaces
     */
    @Inject(method = "getRiggingModel()Lnet/minecraft/resources/ResourceLocation;", at = @At(value = "RETURN", shift = At.Shift.BEFORE), cancellable = true)
    void getRiggingModel(CallbackInfoReturnable<ResourceLocation> cir, @Local(name = "size") String size) {
        if (createestrogen$IdRegex.asMatchPredicate().test(type)) {
            var namespace = ResourceLocation.parse(type).getNamespace();
            cir.setReturnValue(ResourceLocation.fromNamespaceAndPath(namespace, "item/package/rigging_" + size));
        }
    }
}
