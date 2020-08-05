package jp.scriptarts.structureblockextends;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.StructureBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("structureblockextends")
public class Main {
    public Main()
    {
        Block structureBlockEx = register("structure_block",
                new StructureBlockEx(AbstractBlock.Properties.create(Material.IRON, MaterialColor.LIGHT_GRAY)
                        .func_235861_h_().hardnessAndResistance(-1.0F, 3600000.0F).noDrops()));
    }

    private static Block register(String key, Block blockIn) {
        return Registry.register(Registry.BLOCK, key, blockIn);
    }
}
