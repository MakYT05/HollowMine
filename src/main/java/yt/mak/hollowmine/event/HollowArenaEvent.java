package yt.mak.hollowmine.event;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.command.HollowMechCommand;
import yt.mak.hollowmine.custom.entity.HollowSun;
import yt.mak.hollowmine.init.blocks.HMBlocks;
import yt.mak.hollowmine.init.entity.HMEntities;

import java.util.Random;

@Mod.EventBusSubscriber(modid = HollowMine.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HollowArenaEvent {
    private static final BlockPos ARENA_CENTER = new BlockPos(0, 200, 0);
    private static final int ARENA_SIZE = 25;
    private static boolean arenaBuilt = false;

    public static BlockPos getArenaCenter() {
        return ARENA_CENTER;
    }

    public static int getArenaSize() {
        return ARENA_SIZE;
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.player instanceof ServerPlayer player)) return;
        if (!HollowMechCommand.FINAL || arenaBuilt) return;

        ServerLevel level = player.serverLevel();
        BlockPos center = ARENA_CENTER;

        player.teleportTo(level, center.getX(), center.getY() + 2, center.getZ(), player.getYRot(), player.getXRot());

        buildArena(level, center);

        HollowSun hollowSun = HMEntities.HOLLOW_SUN.get().create(level);

        if (hollowSun != null) {
            hollowSun.moveTo(center.getX() + 0.5, center.getY(), center.getZ() + 0.5, 0, 0);
            level.addFreshEntity(hollowSun);
        }

        arenaBuilt = true;
    }

    private static void buildArena(ServerLevel level, BlockPos center) {
        Block floorBlock = HMBlocks.HOLLOW_BED_BLOCK.get();
        Block wallBlock = HMBlocks.HOLLOW_BLOCK.get();
        Block hideBlock = HMBlocks.HOLLOW_BLOCK.get();

        for (int x = -ARENA_SIZE; x <= ARENA_SIZE; x++) {
            for (int z = -ARENA_SIZE; z <= ARENA_SIZE; z++) {
                level.setBlock(center.offset(x, -1, z), floorBlock.defaultBlockState(), 3);

                if (Math.abs(x) == ARENA_SIZE || Math.abs(z) == ARENA_SIZE) {
                    for (int y = 0; y < 20; y++) {
                        level.setBlock(center.offset(x, y, z), wallBlock.defaultBlockState(), 3);
                    }
                }
            }
        }

        Random rand = new Random();
        for (int i = 0; i < 15; i++) {
            int rx = center.getX() + rand.nextInt(ARENA_SIZE * 2 + 1) - ARENA_SIZE;
            int rz = center.getZ() + rand.nextInt(ARENA_SIZE * 2 + 1) - ARENA_SIZE;
            int ry = center.getY();

            for (int y = 0; y < rand.nextInt(3) + 2; y++) {
                level.setBlock(new BlockPos(rx, ry + y, rz), hideBlock.defaultBlockState(), 3);
            }
        }
    }

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer)) return;
        if (!arenaBuilt) return;

        BlockPos pos = event.getPos();
        if (isInsideArena(pos)) {
            event.getLevel().setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        }
    }

    private static boolean isInsideArena(BlockPos pos) {
        return pos.getX() >= ARENA_CENTER.getX() - ARENA_SIZE &&
                pos.getX() <= ARENA_CENTER.getX() + ARENA_SIZE &&
                pos.getZ() >= ARENA_CENTER.getZ() - ARENA_SIZE &&
                pos.getZ() <= ARENA_CENTER.getZ() + ARENA_SIZE &&
                pos.getY() >= ARENA_CENTER.getY() &&
                pos.getY() <= ARENA_CENTER.getY() + 20;
    }

    public static void destroyArena(ServerLevel level) {
        BlockPos center = ARENA_CENTER;
        int size = ARENA_SIZE;

        for (int x = -size - 1; x <= size + 1; x++) {
            for (int z = -size - 1; z <= size + 1; z++) {
                for (int y = -1; y <= 20; y++) {
                    BlockPos pos = center.offset(x, y, z);
                    level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                }
            }
        }

        arenaBuilt = false;
    }
}