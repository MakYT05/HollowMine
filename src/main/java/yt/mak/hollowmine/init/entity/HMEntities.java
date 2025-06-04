package yt.mak.hollowmine.init.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.custom.entity.*;

public class HMEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, HollowMine.MODID);

    public static final RegistryObject<EntityType<HollowEntity>> HOLLOW_ENTITY =
            ENTITY_TYPES.register("hollow_entity", () -> EntityType.Builder.of(HollowEntity::new, MobCategory.CREATURE)
                    .sized(1.5f, 1.5f).build("hollow_entity"));

    public static final RegistryObject<EntityType<HollowKnight>> HOLLOW_KNIGHT =
            ENTITY_TYPES.register("hollow_knight", () -> EntityType.Builder.of(HollowKnight::new, MobCategory.CREATURE)
                    .sized(1.5f, 1.5f).build("hollow_knight"));

    public static final RegistryObject<EntityType<HollowBeatle>> HOLLOW_BEATLE =
            ENTITY_TYPES.register("hollow_beatle", () -> EntityType.Builder.of(HollowBeatle::new, MobCategory.CREATURE)
                    .sized(1.5f, 1.5f).build("hollow_beatle"));

    public static final RegistryObject<EntityType<HollowDie>> HOLLOW_DIE =
            ENTITY_TYPES.register("hollow_die", () -> EntityType.Builder.of(HollowDie::new, MobCategory.CREATURE)
                    .sized(1.5f, 1.5f).build("hollow_die"));

    public static final RegistryObject<EntityType<HollowSun>> HOLLOW_SUN =
            ENTITY_TYPES.register("hollow_sun", () -> EntityType.Builder.of(HollowSun::new, MobCategory.CREATURE)
                    .sized(1.5f, 1.5f).build("hollow_sun"));

    public static final RegistryObject<EntityType<HollowGoodSun>> HOLLOW_GOOD_SUN =
            ENTITY_TYPES.register("hollow_good_sun", () -> EntityType.Builder.of(HollowGoodSun::new, MobCategory.CREATURE)
                    .sized(1.5f, 1.5f).build("hollow_good_sun"));

    public static final RegistryObject<EntityType<Mak>> MAK =
            ENTITY_TYPES.register("mak", () -> EntityType.Builder.of(Mak::new, MobCategory.CREATURE)
                    .sized(1f, 1.8f).build("mak"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}