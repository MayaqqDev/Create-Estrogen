package dev.mayaqq.createestrogen.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.mayaqq.estrogen.client.content.screen.EstrogenMenuScreen;
import dev.mayaqq.estrogen.client.content.screen.config.ConfigCategorySelectionScreen;
import net.createmod.catnip.config.ui.ConfigModListScreen;
import net.createmod.catnip.config.ui.ConfigScreenList;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.List;

@Mixin(ConfigModListScreen.ModEntry.class)
public abstract class ConfigModListScreenMixin extends ConfigScreenList.LabeledEntry {

    public ConfigModListScreenMixin(String s) {
        super(s);
    }

    @ModifyExpressionValue(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/createmod/catnip/config/ui/ConfigHelper;hasAnyForgeConfig(Ljava/lang/String;)Z"
            )
    )
    private boolean detectOurMod(boolean original, @Local(argsOnly = true) String id) {
        if (id.equals("createestrogen") || id.equals("estrogen")) {
            return true;
        } else {
            return original;
        }
    }

    @ModifyArg(
            method = "lambda$new$1",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/createmod/catnip/gui/ScreenOpener;open(Lnet/minecraft/client/gui/screens/Screen;)V"
            ),
            index = 0
    )
    private static Screen changeScreen(Screen original, @Local(argsOnly = true) Screen parent, @Local(argsOnly = true) String id) {
        if (id.equals("createestrogen")) {
            return new ConfigCategorySelectionScreen(
                    parent, List.of(id + "/client", id + "/common", id + "/server"));
        }
        if (id.equals("estrogen")) {
            return new EstrogenMenuScreen(parent);
        }
        return original;
    }
}
