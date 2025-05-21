package yt.mak.hollowmine;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import yt.mak.hollowmine.client.render.*;
import yt.mak.hollowmine.custom.tier.HMTiers;
import yt.mak.hollowmine.effect.HMEffects;
import yt.mak.hollowmine.init.blocks.HMBlocks;
import yt.mak.hollowmine.init.entity.HMEntities;
import yt.mak.hollowmine.init.items.HMItems;
import yt.mak.hollowmine.sound.HMSounds;
import yt.mak.hollowmine.worldgen.HMBiomeModifiers;

@Mod(HollowMine.MODID)
public class HollowMine {
    public static final String MODID = "hollowmine";

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final RegistryObject<CreativeModeTab> HOLLOW_TAB = CREATIVE_MODE_TABS.register("hollow_tab",
            () -> CreativeModeTab.builder()
                    .title(net.minecraft.network.chat.Component.translatable("itemGroup.hollow_tab"))
                    .icon(() -> HMItems.GEM.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(HMItems.HOLLOW_BOOK.get());
                        output.accept(HMItems.GEM.get());
                        output.accept(HMItems.HOLLOW_MASK.get());
                        output.accept(HMItems.HOLLOW.get());
                        output.accept(HMItems.DREAM_NAIL.get());
                        output.accept(HMItems.HOLLOW_MANA.get());
                        output.accept(HMItems.SLIPKNOT_MUSIC_DISC.get());
                        output.accept(HMItems.HOLLOW_AMULET_HP.get());
                        output.accept(HMItems.HOLLOW_AMULET_HEALTH.get());
                        output.accept(HMItems.HOLLOW_KEY.get());

                        output.accept(HMBlocks.HOLLOW_TREE_BLOCK.get());
                        output.accept(HMBlocks.HOLLOW_ORE.get());
                        output.accept(HMBlocks.HOLLOW_BED_BLOCK.get());
                        output.accept(HMBlocks.HOLLOW_BLOCK.get());
                        output.accept(HMBlocks.HOLLOW_MECH_BLOCK.get());
                    })
                    .build());

    public HollowMine() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        CREATIVE_MODE_TABS.register(modEventBus);

        HMItems.register(modEventBus);
        HMBlocks.register(modEventBus);
        HMEntities.register(modEventBus);
        HMEffects.register(modEventBus);
        HMSounds.register(modEventBus);

        HMBiomeModifiers.SPAWN_HOLLOW_TRADER.registryKey();

        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event)  {}

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(HMItems.HOLLOW_MASK);
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {}

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(HMEntities.HOLLOW_ENTITY.get(), HollowEntityRenderer::new);
            EntityRenderers.register(HMEntities.HOLLOW_TRADER.get(), HollowTraderRenderer::new);
            EntityRenderers.register(HMEntities.HOLLOW_KNIGHT.get(), HollowKnightRenderer::new);
            EntityRenderers.register(HMEntities.HOLLOW_BEATLE.get(), HollowBeatleRenderer::new);
            EntityRenderers.register(HMEntities.HOLLOW_DIE.get(), HollowDieRenderer::new);
            EntityRenderers.register(HMEntities.HOLLOW_SUN.get(), HollowSunRenderer::new);
            EntityRenderers.register(HMEntities.HOLLOW_GOOD_SUN.get(), HollowGoodSunRenderer::new);
        }
    }
}