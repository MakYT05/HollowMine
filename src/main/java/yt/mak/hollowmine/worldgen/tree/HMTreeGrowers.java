package yt.mak.hollowmine.worldgen.tree;

import net.minecraft.world.level.block.grower.TreeGrower;
import yt.mak.hollowmine.HollowMine;
import yt.mak.hollowmine.worldgen.HMConfiguredFeatures;

import java.util.Optional;

public class HMTreeGrowers {
    public static final TreeGrower WALNUT = new TreeGrower(HollowMine.MODID + ":walnut",
            Optional.empty(), Optional.of(HMConfiguredFeatures.WALNUT_KEY), Optional.empty());
}