package net.minecraft.server;

import org.bukkit.craftbukkit.event.CraftEventFactory;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Random;

public class BlockVine extends Block {

    public static final BlockStateBoolean UP = BlockStateBoolean.of("up");
    public static final BlockStateBoolean NORTH = BlockStateBoolean.of("north");
    public static final BlockStateBoolean EAST = BlockStateBoolean.of("east");
    public static final BlockStateBoolean SOUTH = BlockStateBoolean.of("south");
    public static final BlockStateBoolean WEST = BlockStateBoolean.of("west");
    public static final BlockStateBoolean[] f = new BlockStateBoolean[] { BlockVine.UP, BlockVine.NORTH, BlockVine.SOUTH, BlockVine.WEST, BlockVine.EAST};
    protected static final AxisAlignedBB g = new AxisAlignedBB(0.0D, 0.9375D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB B = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0625D, 1.0D, 1.0D);
    protected static final AxisAlignedBB C = new AxisAlignedBB(0.9375D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB D = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.0625D);
    protected static final AxisAlignedBB E = new AxisAlignedBB(0.0D, 0.0D, 0.9375D, 1.0D, 1.0D, 1.0D);

    public BlockVine() {
        super(Material.REPLACEABLE_PLANT);
        this.w(this.blockStateList.getBlockData().set(BlockVine.UP, Boolean.FALSE).set(BlockVine.NORTH, Boolean.FALSE).set(BlockVine.EAST, Boolean.FALSE).set(BlockVine.SOUTH, Boolean.FALSE).set(BlockVine.WEST, Boolean.FALSE));
        this.a(true);
        this.a(CreativeModeTab.c);
    }

    public static BlockStateBoolean getDirection(EnumDirection enumdirection) {
        switch (BlockVine.SyntheticClass_1.a[enumdirection.ordinal()]) {
        case 1:
            return BlockVine.UP;

        case 2:
            return BlockVine.NORTH;

        case 3:
            return BlockVine.SOUTH;

        case 4:
            return BlockVine.EAST;

        case 5:
            return BlockVine.WEST;

        default:
            throw new IllegalArgumentException(enumdirection + " is an invalid choice");
        }
    }

    public static int i(IBlockData iblockdata) {
        int i = 0;
        BlockStateBoolean[] ablockstateboolean = BlockVine.f;
        int j = ablockstateboolean.length;

        for (BlockStateBoolean blockstateboolean : ablockstateboolean) {
            if (iblockdata.get(blockstateboolean)) {
                ++i;
            }
        }

        return i;
    }

    @SuppressWarnings("deprecation")
    @Nullable
    public AxisAlignedBB a(IBlockData iblockdata, World world, BlockPosition blockposition) {
        return BlockVine.k;
    }

    @SuppressWarnings("deprecation")
    public AxisAlignedBB a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        iblockdata = iblockdata.b(iblockaccess, blockposition);
        int i = 0;
        AxisAlignedBB axisalignedbb = BlockVine.j;

        if (iblockdata.get(BlockVine.UP)) {
            axisalignedbb = BlockVine.g;
            ++i;
        }

        if (iblockdata.get(BlockVine.NORTH)) {
            axisalignedbb = BlockVine.D;
            ++i;
        }

        if (iblockdata.get(BlockVine.EAST)) {
            axisalignedbb = BlockVine.C;
            ++i;
        }

        if (iblockdata.get(BlockVine.SOUTH)) {
            axisalignedbb = BlockVine.E;
            ++i;
        }

        if (iblockdata.get(BlockVine.WEST)) {
            axisalignedbb = BlockVine.B;
            ++i;
        }

        return i == 1 ? axisalignedbb : BlockVine.j;
    }

    @SuppressWarnings("deprecation")
    public IBlockData updateState(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        return iblockdata.set(BlockVine.UP, iblockaccess.getType(blockposition.up()).k());
    }

    @SuppressWarnings("deprecation")
    public boolean b(IBlockData iblockdata) {
        return false;
    }

    @SuppressWarnings("deprecation")
    public boolean c(IBlockData iblockdata) {
        return false;
    }

    public boolean a(IBlockAccess iblockaccess, BlockPosition blockposition) {
        return true;
    }

    public boolean canPlace(World world, BlockPosition blockposition, EnumDirection enumdirection) {
        switch (BlockVine.SyntheticClass_1.a[enumdirection.ordinal()]) {
        case 1:
            return this.x(world.getType(blockposition.up()));

        case 2:
        case 3:
        case 4:
        case 5:
            return this.x(world.getType(blockposition.shift(enumdirection.opposite())));

        default:
            return false;
        }
    }

    private boolean x(IBlockData iblockdata) {
        return iblockdata.h() && iblockdata.getMaterial().isSolid();
    }

    private boolean e(World world, BlockPosition blockposition, IBlockData iblockdata) {
        IBlockData iblockdata1 = iblockdata;
        Iterator iterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();

        //noinspection WhileLoopReplaceableByForEach
        while (iterator.hasNext()) {
            EnumDirection enumdirection = (EnumDirection) iterator.next();
            BlockStateBoolean blockstateboolean = getDirection(enumdirection);

            if (iblockdata.get(blockstateboolean) && !this.x(world.getType(blockposition.shift(enumdirection)))) {
                IBlockData iblockdata2 = world.getType(blockposition.up());

                if (iblockdata2.getBlock() != this || !iblockdata2.get(blockstateboolean)) {
                    iblockdata = iblockdata.set(blockstateboolean, Boolean.FALSE);
                }
            }
        }

        if (i(iblockdata) == 0) {
            return false;
        } else {
            if (iblockdata1 != iblockdata) {
                world.setTypeAndData(blockposition, iblockdata, 2);
            }

            return true;
        }
    }

    @SuppressWarnings("deprecation")
    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Block block) {
        if (!world.isClientSide && !this.e(world, blockposition, iblockdata)) {
            this.b(world, blockposition, iblockdata, 0);
            world.setAir(blockposition);
        }

    }

    public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
        if (!world.isClientSide) {
            if (world.random.nextInt(Math.max(1, (int) (100.0F / world.spigotConfig.vineModifier) * 4)) == 0) { // Spigot
                boolean flag = true;
                int i = 5;
                boolean flag1 = false;

                label188:
                for (int j = -4; j <= 4; ++j) {
                    for (int k = -4; k <= 4; ++k) {
                        for (int l = -1; l <= 1; ++l) {
                            if (world.getType(blockposition.a(j, l, k)).getBlock() == this) {
                                --i;
                                if (i <= 0) {
                                    flag1 = true;
                                    break label188;
                                }
                            }
                        }
                    }
                }

                EnumDirection enumdirection = EnumDirection.a(random);
                BlockPosition blockposition1 = blockposition.up();

                if (enumdirection == EnumDirection.UP && blockposition.getY() < 255 && world.isEmpty(blockposition1)) {
                    if (!flag1) {
                        IBlockData iblockdata1 = iblockdata;
                        Iterator iterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();

                        //noinspection WhileLoopReplaceableByForEach
                        while (iterator.hasNext()) {
                            EnumDirection enumdirection1 = (EnumDirection) iterator.next();

                            if (random.nextBoolean() || !this.x(world.getType(blockposition1.shift(enumdirection1)))) {
                                iblockdata1 = iblockdata1.set(getDirection(enumdirection1), Boolean.FALSE);
                            }
                        }

                        if (iblockdata1.get(BlockVine.NORTH) || iblockdata1.get(BlockVine.EAST) || iblockdata1.get(BlockVine.SOUTH) || iblockdata1.get(BlockVine.WEST)) {
                            // CraftBukkit start - Call BlockSpreadEvent
                            // world.setTypeAndData(blockposition1, iblockdata1, 2);
                            org.bukkit.block.Block source = world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
                            org.bukkit.block.Block block = world.getWorld().getBlockAt(blockposition1.getX(), blockposition1.getY(), blockposition1.getZ());
                            CraftEventFactory.handleBlockSpreadEvent(block, source, this, toLegacyData(iblockdata1));
                            // CraftBukkit end
                        }

                    }
                } else {
                    IBlockData iblockdata2;
                    Block block;
                    BlockPosition blockposition2;

                    if (enumdirection.k().c() && !iblockdata.get(getDirection(enumdirection))) {
                        if (!flag1) {
                            blockposition2 = blockposition.shift(enumdirection);
                            iblockdata2 = world.getType(blockposition2);
                            block = iblockdata2.getBlock();
                            if (block.material == Material.AIR) {
                                EnumDirection enumdirection2 = enumdirection.e();
                                EnumDirection enumdirection3 = enumdirection.f();
                                boolean flag2 = iblockdata.get(getDirection(enumdirection2));
                                boolean flag3 = iblockdata.get(getDirection(enumdirection3));
                                BlockPosition blockposition3 = blockposition2.shift(enumdirection2);
                                BlockPosition blockposition4 = blockposition2.shift(enumdirection3);

                                // CraftBukkit start - Call BlockSpreadEvent
                                org.bukkit.block.Block source = world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
                                org.bukkit.block.Block bukkitBlock = world.getWorld().getBlockAt(blockposition2.getX(), blockposition2.getY(), blockposition2.getZ());

                                if (flag2 && this.x(world.getType(blockposition3))) {
                                    // world.setTypeAndData(blockposition2, this.getBlockData().set(getDirection(enumdirection2), Boolean.valueOf(true)), 2);
                                    CraftEventFactory.handleBlockSpreadEvent(bukkitBlock, source, this, toLegacyData(this.getBlockData().set(getDirection(enumdirection2), Boolean.TRUE)));
                                } else if (flag3 && this.x(world.getType(blockposition4))) {
                                    // world.setTypeAndData(blockposition2, this.getBlockData().set(getDirection(enumdirection3), Boolean.valueOf(true)), 2);
                                    CraftEventFactory.handleBlockSpreadEvent(bukkitBlock, source, this, toLegacyData(this.getBlockData().set(getDirection(enumdirection3), Boolean.TRUE)));
                                } else if (flag2 && world.isEmpty(blockposition3) && this.x(world.getType(blockposition.shift(enumdirection2)))) {
                                    // world.setTypeAndData(blockposition3, this.getBlockData().set(getDirection(enumdirection.opposite()), Boolean.valueOf(true)), 2);
                                    bukkitBlock = world.getWorld().getBlockAt(blockposition3.getX(), blockposition3.getY(), blockposition3.getZ());
                                    CraftEventFactory.handleBlockSpreadEvent(bukkitBlock, source, this, toLegacyData(this.getBlockData().set(getDirection(enumdirection.opposite()), Boolean.TRUE)));
                                } else if (flag3 && world.isEmpty(blockposition4) && this.x(world.getType(blockposition.shift(enumdirection3)))) {
                                    // world.setTypeAndData(blockposition4, this.getBlockData().set(getDirection(enumdirection.opposite()), Boolean.valueOf(true)), 2);
                                    bukkitBlock = world.getWorld().getBlockAt(blockposition4.getX(), blockposition4.getY(), blockposition4.getZ());
                                    CraftEventFactory.handleBlockSpreadEvent(bukkitBlock, source, this, toLegacyData(this.getBlockData().set(getDirection(enumdirection.opposite()), Boolean.TRUE)));
                                } else if (this.x(world.getType(blockposition2.up()))) {
                                    // world.setTypeAndData(blockposition2, this.getBlockData(), 2);
                                    CraftEventFactory.handleBlockSpreadEvent(bukkitBlock, source, this, toLegacyData(this.getBlockData()));
                                }
                                // CraftBukkit end
                            } else if (block.material.k() && iblockdata2.h()) {
                                world.setTypeAndData(blockposition, iblockdata.set(getDirection(enumdirection), Boolean.TRUE), 2);
                            }

                        }
                    } else {
                        if (blockposition.getY() > 1) {
                            blockposition2 = blockposition.down();
                            iblockdata2 = world.getType(blockposition2);
                            block = iblockdata2.getBlock();
                            IBlockData iblockdata3;
                            Iterator iterator1;
                            EnumDirection enumdirection4;

                            if (block.material == Material.AIR) {
                                iblockdata3 = iblockdata;
                                iterator1 = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();

                                while (iterator1.hasNext()) {
                                    enumdirection4 = (EnumDirection) iterator1.next();
                                    if (random.nextBoolean()) {
                                        iblockdata3 = iblockdata3.set(getDirection(enumdirection4), Boolean.FALSE);
                                    }
                                }

                                if (iblockdata3.get(BlockVine.NORTH) || iblockdata3.get(BlockVine.EAST) || iblockdata3.get(BlockVine.SOUTH) || iblockdata3.get(BlockVine.WEST)) {
                                    // CraftBukkit start - Call BlockSpreadEvent
                                    // world.setTypeAndData(blockposition2, iblockdata3, 2);
                                    org.bukkit.block.Block source = world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
                                    org.bukkit.block.Block bukkitBlock = world.getWorld().getBlockAt(blockposition2.getX(), blockposition2.getY(), blockposition2.getZ());
                                    CraftEventFactory.handleBlockSpreadEvent(bukkitBlock, source, this, toLegacyData(iblockdata3));
                                    // CraftBukkit end
                                }
                            } else if (block == this) {
                                iblockdata3 = iblockdata2;
                                iterator1 = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();

                                while (iterator1.hasNext()) {
                                    enumdirection4 = (EnumDirection) iterator1.next();
                                    BlockStateBoolean blockstateboolean = getDirection(enumdirection4);

                                    if (random.nextBoolean() && iblockdata.get(blockstateboolean)) {
                                        iblockdata3 = iblockdata3.set(blockstateboolean, Boolean.TRUE);
                                    }
                                }

                                if (iblockdata3.get(BlockVine.NORTH) || iblockdata3.get(BlockVine.EAST) || iblockdata3.get(BlockVine.SOUTH) || iblockdata3.get(BlockVine.WEST)) {
                                    world.setTypeAndData(blockposition2, iblockdata3, 2);
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    public IBlockData getPlacedState(World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2, int i, EntityLiving entityliving) {
        IBlockData iblockdata = this.getBlockData().set(BlockVine.UP, Boolean.FALSE).set(BlockVine.NORTH, Boolean.FALSE).set(BlockVine.EAST, Boolean.FALSE).set(BlockVine.SOUTH, Boolean.FALSE).set(BlockVine.WEST, Boolean.FALSE);

        return enumdirection.k().c() ? iblockdata.set(getDirection(enumdirection.opposite()), Boolean.TRUE) : iblockdata;
    }

    @Nullable
    public Item getDropType(IBlockData iblockdata, Random random, int i) {
        return null;
    }

    public int a(Random random) {
        return 0;
    }

    public void a(World world, EntityHuman entityhuman, BlockPosition blockposition, IBlockData iblockdata, @Nullable TileEntity tileentity, @Nullable ItemStack itemstack) {
        if (!world.isClientSide && itemstack != null && itemstack.getItem() == Items.SHEARS) {
            entityhuman.b(StatisticList.a(this));
            a(world, blockposition, new ItemStack(Blocks.VINE, 1, 0));
        } else {
            super.a(world, entityhuman, blockposition, iblockdata, tileentity, itemstack);
        }

    }

    @SuppressWarnings("deprecation")
    public IBlockData fromLegacyData(int i) {
        return this.getBlockData().set(BlockVine.SOUTH, (i & 1) > 0).set(BlockVine.WEST, (i & 2) > 0).set(BlockVine.NORTH, (i & 4) > 0).set(BlockVine.EAST, (i & 8) > 0);
    }

    public int toLegacyData(IBlockData iblockdata) {
        int i = 0;

        if (iblockdata.get(BlockVine.SOUTH)) {
            i |= 1;
        }

        if (iblockdata.get(BlockVine.WEST)) {
            i |= 2;
        }

        if (iblockdata.get(BlockVine.NORTH)) {
            i |= 4;
        }

        if (iblockdata.get(BlockVine.EAST)) {
            i |= 8;
        }

        return i;
    }

    protected BlockStateList getStateList() {
        return new BlockStateList(this, BlockVine.UP, BlockVine.NORTH, BlockVine.EAST, BlockVine.SOUTH, BlockVine.WEST);
    }

    @SuppressWarnings("deprecation")
    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        switch (BlockVine.SyntheticClass_1.b[enumblockrotation.ordinal()]) {
        case 1:
            return iblockdata.set(BlockVine.NORTH, iblockdata.get(BlockVine.SOUTH)).set(BlockVine.EAST, iblockdata.get(BlockVine.WEST)).set(BlockVine.SOUTH, iblockdata.get(BlockVine.NORTH)).set(BlockVine.WEST, iblockdata.get(BlockVine.EAST));

        case 2:
            return iblockdata.set(BlockVine.NORTH, iblockdata.get(BlockVine.EAST)).set(BlockVine.EAST, iblockdata.get(BlockVine.SOUTH)).set(BlockVine.SOUTH, iblockdata.get(BlockVine.WEST)).set(BlockVine.WEST, iblockdata.get(BlockVine.NORTH));

        case 3:
            return iblockdata.set(BlockVine.NORTH, iblockdata.get(BlockVine.WEST)).set(BlockVine.EAST, iblockdata.get(BlockVine.NORTH)).set(BlockVine.SOUTH, iblockdata.get(BlockVine.EAST)).set(BlockVine.WEST, iblockdata.get(BlockVine.SOUTH));

        default:
            return iblockdata;
        }
    }

    @SuppressWarnings("deprecation")
    public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
        switch (BlockVine.SyntheticClass_1.c[enumblockmirror.ordinal()]) {
        case 1:
            return iblockdata.set(BlockVine.NORTH, iblockdata.get(BlockVine.SOUTH)).set(BlockVine.SOUTH, iblockdata.get(BlockVine.NORTH));

        case 2:
            return iblockdata.set(BlockVine.EAST, iblockdata.get(BlockVine.WEST)).set(BlockVine.WEST, iblockdata.get(BlockVine.EAST));

        default:
            //noinspection deprecation
            return super.a(iblockdata, enumblockmirror);
        }
    }

    static class SyntheticClass_1 {

        static final int[] a;
        static final int[] b;
        static final int[] c = new int[EnumBlockMirror.values().length];

        static {
            try {
                BlockVine.SyntheticClass_1.c[EnumBlockMirror.LEFT_RIGHT.ordinal()] = 1;
            } catch (NoSuchFieldError ignored) {
            }

            try {
                BlockVine.SyntheticClass_1.c[EnumBlockMirror.FRONT_BACK.ordinal()] = 2;
            } catch (NoSuchFieldError ignored) {
            }

            b = new int[EnumBlockRotation.values().length];

            try {
                BlockVine.SyntheticClass_1.b[EnumBlockRotation.CLOCKWISE_180.ordinal()] = 1;
            } catch (NoSuchFieldError ignored) {
            }

            try {
                BlockVine.SyntheticClass_1.b[EnumBlockRotation.COUNTERCLOCKWISE_90.ordinal()] = 2;
            } catch (NoSuchFieldError ignored) {
            }

            try {
                BlockVine.SyntheticClass_1.b[EnumBlockRotation.CLOCKWISE_90.ordinal()] = 3;
            } catch (NoSuchFieldError ignored) {
            }

            a = new int[EnumDirection.values().length];

            try {
                BlockVine.SyntheticClass_1.a[EnumDirection.UP.ordinal()] = 1;
            } catch (NoSuchFieldError ignored) {
            }

            try {
                BlockVine.SyntheticClass_1.a[EnumDirection.NORTH.ordinal()] = 2;
            } catch (NoSuchFieldError ignored) {
            }

            try {
                BlockVine.SyntheticClass_1.a[EnumDirection.SOUTH.ordinal()] = 3;
            } catch (NoSuchFieldError ignored) {
            }

            try {
                BlockVine.SyntheticClass_1.a[EnumDirection.EAST.ordinal()] = 4;
            } catch (NoSuchFieldError ignored) {
            }

            try {
                BlockVine.SyntheticClass_1.a[EnumDirection.WEST.ordinal()] = 5;
            } catch (NoSuchFieldError ignored) {
            }

        }
    }
}
