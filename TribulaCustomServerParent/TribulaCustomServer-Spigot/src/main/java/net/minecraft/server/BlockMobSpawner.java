package net.minecraft.server;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockMobSpawner extends BlockTileEntity {

    protected BlockMobSpawner() {
        super(Material.STONE);
    }

    public TileEntity a(World world, int i) {
        return new TileEntityMobSpawner();
    }

    @Nullable
    public Item getDropType(IBlockData iblockdata, Random random, int i) {
        return null;
    }

    public int a(Random random) {
        return 0;
    }

    public void dropNaturally(World world, BlockPosition blockposition, IBlockData iblockdata, float f, int i) {
        super.dropNaturally(world, blockposition, iblockdata, f, i);
        /* CraftBukkit start - Delegate to getExpDrop
        int j = 15 + world.random.nextInt(15) + world.random.nextInt(15);

        this.dropExperience(world, blockposition, j);
        */
    }

    @Override
    public int getExpDrop(World world, IBlockData iblockdata, int enchantmentLevel) {

        return 15 + world.random.nextInt(15) + world.random.nextInt(15);
        // CraftBukkit end
    }

    public boolean b(IBlockData iblockdata) {
        return false;
    }

    public EnumRenderType a(IBlockData iblockdata) {
        return EnumRenderType.MODEL;
    }

    @Nullable
    public ItemStack a(World world, BlockPosition blockposition, IBlockData iblockdata) {
        return null;
    }
}
