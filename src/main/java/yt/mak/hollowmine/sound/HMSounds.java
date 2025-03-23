package yt.mak.hollowmine.sound;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;
import yt.mak.hollowmine.HollowMine;

public class HMSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, HollowMine.MODID);

    public static final RegistryObject<SoundEvent> SLIPKNOT = registerSoundEvent("slipknot");

    public static final ResourceKey<JukeboxSong> SLIPKNOT_KEY = ResourceKey.create(Registries.JUKEBOX_SONG,
            ResourceLocation.fromNamespaceAndPath(HollowMine.MODID, "slipknot"));

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(HollowMine.MODID, name)));
    }

    @SubscribeEvent
    public static void onRegisterSounds(RegisterEvent event) {
        event.register(ForgeRegistries.SOUND_EVENTS.getRegistryKey(), helper -> {
            helper.register(HMSounds.SLIPKNOT.getId(), HMSounds.SLIPKNOT.get());
        });
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}