package dev.mayaqq.createestrogen.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.logistics.box.PackageStyles;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.regex.Pattern;

@Mixin(PackageStyles.PackageStyle.class)
class PackageStyleMixin {
    @Unique
    private final Pattern createestrogen$IdRegex = Pattern.compile("([A-Za-z]+):(.*)");
    @Shadow
    @Final
    private String type;

    /**
     * @author Niko Dale
     * @reason create doesn't normally support package textures from other namespaces
     */
    @WrapOperation(
            method = "getItemId()Lnet/minecraft/resources/ResourceLocation;",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/simibubi/create/Create;asResource(Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;"
            )
    )
    ResourceLocation getItemId(String path, Operation<ResourceLocation> original) {
        // if it has a namespace then there is no reason to convert it
        if (createestrogen$IdRegex.asMatchPredicate().test(path)) {
            return ResourceLocation.parse(path);
        } else return original.call(path);
    }

    /**
     * @author Niko Dale
     * @reason create doesn't normally support package textures from other namespaces
     */
    @WrapOperation(
            method = "getRiggingModel()Lnet/minecraft/resources/ResourceLocation;",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/simibubi/create/Create;asResource(Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;"
            )
    )
    ResourceLocation getRiggingModel(String path, Operation<ResourceLocation> original) {
        if (createestrogen$IdRegex.asMatchPredicate().test(type)) {
            return ResourceLocation.fromNamespaceAndPath(ResourceLocation.parse(type).getNamespace(), path);
        } else return original.call(path);
    }
}