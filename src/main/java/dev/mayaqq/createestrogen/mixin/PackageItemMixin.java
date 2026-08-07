package dev.mayaqq.createestrogen.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.box.PackageStyles;
import dev.mayaqq.createestrogen.content.packages.CreateEstrogenPackageStyles;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PackageItem.class)
public class PackageItemMixin {
    @Shadow
    public PackageStyles.PackageStyle style;

    @ModifyReturnValue(method = "containing(Lnet/neoforged/neoforge/items/ItemStackHandler;)Lnet/minecraft/world/item/ItemStack;", at = @At("RETURN"))
    private static ItemStack containing(ItemStack original, @Local(argsOnly = true) ItemStackHandler stacks) {
        var contained = CreateEstrogenPackageStyles.containing(stacks);
        if (contained != null) {
            return contained;
        }
        return original;
    }

    /**
     * @reason Create will try to add our new boxes to the pool of all boxes, we don't want that
     */
    @Inject(method = "<init>(Lnet/minecraft/world/item/Item$Properties;Lcom/simibubi/create/content/logistics/box/PackageStyles$PackageStyle;)V",
            at = @At(value = "RETURN"))
    private void constructor(Item.Properties properties, PackageStyles.PackageStyle style, CallbackInfo ci) {
        if (CreateEstrogenPackageStyles.INSTANCE.getEstrogenPillStyles().contains(style))
            (style.rare() ? PackageStyles.RARE_BOXES : PackageStyles.STANDARD_BOXES).remove((PackageItem) (Object) this);
    }

    /**
     * @author Niko Dale
     * @reason create estrogen's custom translations
     */
    @ModifyReturnValue(
            method = "getDescriptionId",
            at = @At("RETURN")
    )
    private String modifyDescriptionId(String original) {
        if (!this.style.getItemId().getNamespace().equals("create")) {
            return original.replaceAll("create", this.style.getItemId().getNamespace());
        } else return original;
    }
}