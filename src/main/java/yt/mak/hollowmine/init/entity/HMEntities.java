package yt.mak.hollowmine.init.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.custom.entity.HollowEntity;
import yt.mak.hollowmine.custom.entity.HollowTrader;

public class HMEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, HollowMine.MODID);

    public static final RegistryObject<EntityType<HollowEntity>> HOLLOW_ENTITY =
            ENTITY_TYPES.register("hollow_entity", () -> EntityType.Builder.of(HollowEntity::new, MobCategory.CREATURE)
                    .sized(1.5f, 1.5f).build("hollow_entity"));

    public static final RegistryObject<EntityType<HollowTrader>> HOLLOW_TRADER =
            ENTITY_TYPES.register("hollow_trader", () -> EntityType.Builder.of(HollowTrader::new, MobCategory.CREATURE)
                    .sized(1.5f, 1.5f).build("hollow_trader"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}