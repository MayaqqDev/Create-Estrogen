package dev.mayaqq.createestrogen.mixin.client;

import com.google.common.cache.Cache;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.equipment.armor.CardboardArmorHandlerClient;
import com.simibubi.create.foundation.utility.TickBasedCache;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.mayaqq.createestrogen.client.content.blockRenderers.CreateEstrogenRenderer;
import dev.mayaqq.estrogen.content.EstrogenEffects;
import invoke.kitty.kritter.registry.api.entry.RegistryEntries;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Mixin(CardboardArmorHandlerClient.class)
public class CardboardArmorHandlerClientMixin {

    @Unique
    private static final Cache<UUID, Integer> ESTROGEN_BOXES_PLAYERS_ARE_HIDING_AS = new TickBasedCache<>(20, true);

    @Inject(
            method = "keepCacheAliveDesignDespiteNotRendering",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/simibubi/create/content/equipment/armor/CardboardArmorHandlerClient;getCurrentBoxIndex(Lnet/minecraft/world/entity/player/Player;)Ljava/lang/Integer;"
            )
    )
    private static void onRefreshCache(PlayerTickEvent.Post event, CallbackInfo ci) throws ExecutionException {
        estrogen$getCurrentEstrogenBoxIndex(event.getEntity());
    }

    @ModifyExpressionValue(
            method = "playerRendersAsBoxWhenSneaking",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;get(I)Ljava/lang/Object;"
            )
    )
    private static Object changeToEstrogenBox(Object original, @Local(name = "player") Player player) throws ExecutionException {
        if (player.hasEffect(RegistryEntries.getHolder(EstrogenEffects.getEstrogen()))) {
            return CreateEstrogenRenderer.ESTROGEN_PACKAGES_TO_HIDE_AS.get(estrogen$getCurrentEstrogenBoxIndex(player));
        }

        return original;
    }

    @Unique
    private static Integer estrogen$getCurrentEstrogenBoxIndex(Player player) throws ExecutionException {
        return ESTROGEN_BOXES_PLAYERS_ARE_HIDING_AS.get(player.getUUID(),
                () -> player.level().random.nextInt(CreateEstrogenRenderer.ESTROGEN_PACKAGES_TO_HIDE_AS.size()));
    }
}
