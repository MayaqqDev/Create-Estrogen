package dev.mayaqq.createestrogen.mixin;

import com.simibubi.create.content.kinetics.base.IRotate;
import net.minecraft.ChatFormatting;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(IRotate.SpeedLevel.class)
public class SpeedLevelMixin {

    @Shadow
    @Final
    @Mutable
    private static IRotate.SpeedLevel[] $VALUES;

    @Unique
    private static final IRotate.SpeedLevel MAX = addSpeed("MAX", ChatFormatting.DARK_PURPLE, 11141290, 60);

    @Invoker("<init>")
    public static IRotate.SpeedLevel invokeInit(String internalName, int internalId, ChatFormatting textColor, int color, int particleSpeed) {
        throw new AssertionError();
    }

    private static IRotate.SpeedLevel addSpeed(String internalName, ChatFormatting textColor, int color, int particleSpeed) {
        ArrayList<IRotate.SpeedLevel> speeds = new ArrayList(Arrays.asList($VALUES));
        IRotate.SpeedLevel instrument = invokeInit(internalName, speeds.get(speeds.size() - 1).ordinal() + 1, textColor, color, particleSpeed);
        speeds.add(instrument);
        $VALUES = speeds.toArray(new IRotate.SpeedLevel[0]);
        return instrument;
    }

    @Inject(
            method = "getSpeedValue",
            at = @At("HEAD"),
            cancellable = true)
    private void getSpeed(CallbackInfoReturnable<Float> cir) {
        if ((Object) this == MAX) {
            cir.setReturnValue(256.0F);
        }
    }

    @Inject(
            method = "of",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/Math;abs(F)F",
                    shift = At.Shift.AFTER
            ),
            cancellable = true)
    private static void ofModify(float speed, CallbackInfoReturnable<IRotate.SpeedLevel> cir) {
        if (speed >= 256F) {
            cir.setReturnValue(MAX);
        }
    }
}
