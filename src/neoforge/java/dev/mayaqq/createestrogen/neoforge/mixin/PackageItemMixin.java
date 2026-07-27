package dev.mayaqq.createestrogen.neoforge.mixin;

import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.box.PackageStyles;
import dev.mayaqq.createestrogen.content.packages.CreateEstrogenPackageStyles;
import dev.mayaqq.createestrogen.neoforge.extensions.ItemHandlerWrapper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PackageItem.class)
public class PackageItemMixin {
    @Shadow
    public PackageStyles.PackageStyle style;

    @Inject(method = "containing(Lnet/neoforged/neoforge/items/ItemStackHandler;)Lnet/minecraft/world/item/ItemStack;", at = @At("HEAD"), cancellable = true)
    private static void containing(ItemStackHandler stacks, CallbackInfoReturnable<ItemStack> cir) {
        var contained = CreateEstrogenPackageStyles.containing(new ItemHandlerWrapper(stacks));
        if (contained != null) {
            cir.setReturnValue(contained);
        }
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
    @Overwrite
    public String getDescriptionId() {
        return "item." + style.getItemId().getNamespace() + (style.rare() ? ".rare_package" : ".package");
    }
}
