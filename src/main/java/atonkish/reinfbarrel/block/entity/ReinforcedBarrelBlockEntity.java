package atonkish.reinfbarrel.block.entity;

import net.minecraft.block.BarrelBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ViewerCountManager;
import net.minecraft.entity.ContainerUser;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import atonkish.reinfcore.screen.ReinforcedStorageScreenHandler;
import atonkish.reinfcore.util.ReinforcingMaterial;

import atonkish.reinfbarrel.mixin.BlockEntityAccessor;

public class ReinforcedBarrelBlockEntity extends BarrelBlockEntity {
    private final ViewerCountManager stateManager;
    private final ReinforcingMaterial cachedMaterial;

    public ReinforcedBarrelBlockEntity(ReinforcingMaterial material, BlockPos pos, BlockState state) {
        super(pos, state);
        ((BlockEntityAccessor) this).setType(ModBlockEntityType.REINFORCED_BARREL_MAP.get(material));
        this.setHeldStacks(DefaultedList.ofSize(material.getSize(), ItemStack.EMPTY));
        this.stateManager = new ViewerCountManager() {
            @Override
            protected void onContainerOpen(World world, BlockPos pos, BlockState state) {
                ReinforcedBarrelBlockEntity.this.playSound(state, SoundEvents.BLOCK_BARREL_OPEN);
                ReinforcedBarrelBlockEntity.this.setOpen(state, true);
            }

            @Override
            protected void onContainerClose(World world, BlockPos pos, BlockState state) {
                ReinforcedBarrelBlockEntity.this.playSound(state, SoundEvents.BLOCK_BARREL_CLOSE);
                ReinforcedBarrelBlockEntity.this.setOpen(state, false);
            }

            @Override
            protected void onViewerCountUpdate(World world, BlockPos pos, BlockState state, int oldViewerCount,
                    int newViewerCount) {
            }

            @Override
            public boolean isPlayerViewing(PlayerEntity player) {
                if (player.currentScreenHandler instanceof ReinforcedStorageScreenHandler) {
                    Inventory inventory = ((ReinforcedStorageScreenHandler) player.currentScreenHandler).getInventory();
                    return inventory == ReinforcedBarrelBlockEntity.this;
                } else {
                    return false;
                }
            }
        };
        this.cachedMaterial = material;
    }

    @Override
    public int size() {
        return this.cachedMaterial.getSize();
    }

    @Override
    protected Text getContainerName() {
        String namespace = BlockEntityType.getId(this.getType()).getNamespace();
        return Text.translatable("container." + namespace + "." + this.cachedMaterial.getName() + "Barrel");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return ReinforcedStorageScreenHandler.createSingleBlockScreen(this.cachedMaterial, syncId, playerInventory,
                this);
    }

    @Override
    public void onOpen(ContainerUser user) {
        if (!this.removed && !user.asLivingEntity().isSpectator()) {
            this.stateManager.openContainer(user.asLivingEntity(), this.getWorld(), this.getPos(),
                    this.getCachedState(), user.getContainerInteractionRange());
        }

    }

    @Override
    public void onClose(ContainerUser user) {
        if (!this.removed && !user.asLivingEntity().isSpectator()) {
            this.stateManager.closeContainer(user.asLivingEntity(), this.getWorld(), this.getPos(),
                    this.getCachedState());
        }

    }

    @Override
    public void tick() {
        if (!this.removed) {
            this.stateManager.updateViewerCount(this.getWorld(), this.getPos(), this.getCachedState());
        }

    }

    private void setOpen(BlockState state, boolean open) {
        this.world.setBlockState(this.getPos(), state.with(BarrelBlock.OPEN, open), Block.NOTIFY_ALL);
    }

    private void playSound(BlockState state, SoundEvent soundEvent) {
        Vec3i vec3i = ((Direction) state.get(BarrelBlock.FACING)).getVector();
        double d = (double) this.pos.getX() + 0.5D + (double) vec3i.getX() / 2.0D;
        double e = (double) this.pos.getY() + 0.5D + (double) vec3i.getY() / 2.0D;
        double f = (double) this.pos.getZ() + 0.5D + (double) vec3i.getZ() / 2.0D;
        this.world.playSound((PlayerEntity) null, d, e, f, soundEvent, SoundCategory.BLOCKS, 0.5F,
                this.world.random.nextFloat() * 0.1F + 0.9F);
    }

    public ReinforcingMaterial getMaterial() {
        return this.cachedMaterial;
    }
}
