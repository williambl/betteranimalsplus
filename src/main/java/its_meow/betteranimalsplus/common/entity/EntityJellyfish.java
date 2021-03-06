package its_meow.betteranimalsplus.common.entity;

import java.util.Random;

import javax.annotation.Nullable;

import its_meow.betteranimalsplus.init.ModEntities;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EntityJellyfish extends WaterMobEntity implements IVariantTypes {
	
    protected static final DataParameter<Integer> TYPE_NUMBER = EntityDataManager.<Integer>createKey(EntityJellyfish.class, DataSerializers.VARINT);
    protected static final DataParameter<Float> SIZE = EntityDataManager.<Float>createKey(EntityJellyfish.class, DataSerializers.FLOAT);
    protected int attackCooldown = 0;
    
    public float jellyYaw;
    public float prevJellyYaw;
    public float jellyRotation;
    public float prevJellyRotation;
    private float randomMotionSpeed;
    private float rotationVelocity;
    private float rotateSpeed;
    private float randomMotionVecX;
    private float randomMotionVecY;
    private float randomMotionVecZ;

    public EntityJellyfish(World worldIn) {
        super(ModEntities.JELLYFISH.entityType, worldIn);
        this.setSize(0.8F, 0.8F);
        rotationVelocity = (1.0F / (rand.nextFloat() + 1.0F) * 0.2F);
    }
    
    protected void registerAttributes() {
        super.registerAttributes();
        getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
    }
    
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new EntityJellyfish.AIMoveRandom(this));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.attackCooldown > 0) {
            this.attackCooldown--;
        }
    }
    
    @Override
    public void livingTick() {
        super.livingTick();

        prevJellyYaw = jellyYaw;
        prevJellyRotation = jellyRotation;

        jellyRotation += rotationVelocity;
        if (jellyRotation > 6.283185307179586D) {
            if (world.isRemote) {
                jellyRotation = 6.2831855F;
            } else {
                jellyRotation = ((float) (jellyRotation - 6.283185307179586D));
                if (rand.nextInt(10) == 0) {
                    rotationVelocity = (1.0F / (rand.nextFloat() + 1.0F) * 0.2F);
                }
                world.setEntityState(this, (byte) 19);
            }
        }
        if (isInWaterOrBubbleColumn()) {
            if (jellyRotation < 3.1415927F) {
                float lvt_1_1_ = jellyRotation / 3.1415927F;
                if (lvt_1_1_ > 0.75D) {
                    randomMotionSpeed = 1.0F;
                    rotateSpeed = 1.0F;
                } else {
                    rotateSpeed *= 0.8F;
                }
            } else {
                randomMotionSpeed *= 0.9F;
                rotateSpeed *= 0.99F;
            }
            if (!world.isRemote) {
                this.setMotion((randomMotionVecX * randomMotionSpeed), (randomMotionVecY * randomMotionSpeed), (randomMotionVecZ * randomMotionSpeed));
            }
            //float lvt_1_2_ = MathHelper.sqrt(this.getMotion().getX() * this.getMotion().getX() + this.getMotion().getZ() * this.getMotion().getZ());

            renderYawOffset += (-(float) MathHelper.atan2(this.getMotion().getX(), this.getMotion().getZ()) * 57.295776F - renderYawOffset) * 0.1F;
            rotationYaw = renderYawOffset;
            jellyYaw = ((float) (jellyYaw + 3.141592653589793D * rotateSpeed * 1.5D));
        } else {
            if (!world.isRemote) {
                this.setMotion(0, this.getMotion().getY(), 0); 
                if (isPotionActive(Effects.LEVITATION)) {
                    this.setMotion(this.getMotion().getX(), this.getMotion().getY() + 0.05D * (getActivePotionEffect(Effects.LEVITATION).getAmplifier() + 1) - this.getMotion().getY(), this.getMotion().getZ());
                } else if (!hasNoGravity()) {
                    this.setMotion(this.getMotion().getX(), this.getMotion().getY() - 0.08D, this.getMotion().getZ());
                }
                this.setMotion(this.getMotion().getX(), this.getMotion().getY() * 0.9800000190734863D, this.getMotion().getZ());
            }
        }
    }
    
    @Override
    public void onCollideWithPlayer(PlayerEntity entity) {
        super.onCollideWithPlayer(entity);
        if (!entity.isCreative() && this.attackCooldown == 0) {
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), 2.0F);
            entity.addPotionEffect(new EffectInstance(Effects.POISON, 200, 0, false, false));
            entity.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 90, 2, false, false));
            this.attackCooldown = 80;
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_SLIME_SQUISH;
    }
    
    @Override
    public void travel(Vec3d vec) {
        move(MoverType.SELF, this.getMotion());
    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte p_70103_1_) {
        if (p_70103_1_ == 19) {
            jellyRotation = 0.0F;
        } else {
            super.handleStatusUpdate(p_70103_1_);
        }
    }
    
    public void setMovementVector(float p_175568_1_, float p_175568_2_, float p_175568_3_) {
        randomMotionVecX = p_175568_1_;
        randomMotionVecY = p_175568_2_;
        randomMotionVecZ = p_175568_3_;
    }

    public boolean hasMovementVector() {
        return (randomMotionVecX != 0.0F) || (randomMotionVecY != 0.0F) || (randomMotionVecZ != 0.0F);
    }

    /* NBT AND TYPE CODE: */

    @Override
    protected void registerData() {
        super.registerData();
        this.registerTypeKey();
        this.dataManager.register(EntityJellyfish.SIZE, Float.valueOf(1));
    }

    @Override
    public EntitySize getSize(Pose pose) {
        float size = this.dataManager.get(EntityJellyfish.SIZE).floatValue();
        return EntitySize.flexible(size, size).scale(this.getRenderScale());
    }

    public void setSize(float width, float height) {
        this.dataManager.set(EntityJellyfish.SIZE, Float.valueOf(width));
    }

    @Override
    public boolean writeUnlessRemoved(CompoundNBT compound) {
        this.writeType(compound);
        compound.putFloat("Size", this.getSize(Pose.STANDING).width);
        return super.writeUnlessRemoved(compound);
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.readType(compound);
        float size = compound.getFloat("Size");
        this.setSize(size, size);
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IWorld world, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData livingdata, CompoundNBT compound) {
        livingdata = super.onInitialSpawn(world, difficulty, reason, livingdata, compound);
        if (!this.isChild()) {
            int i = this.rand.nextInt(6) + 1; // Values 1 to 6
            float rand = (this.rand.nextInt(30) + 1F) / 50F + 0.05F;

            if (livingdata instanceof JellyfishData) {
                i = ((JellyfishData) livingdata).typeData;
                rand = ((JellyfishData) livingdata).size;
            } else {
                livingdata = new JellyfishData(i, rand);
            }

            this.setType(i);
            this.setSize(rand, rand);
        }
        this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0F);
        return livingdata;
    }

    public static class JellyfishData implements ILivingEntityData {

        public int typeData;
        public float size;

        public JellyfishData(int type, float size) {
            this.typeData = type;
            this.size = size;
        }
    }

    static class AIMoveRandom extends Goal {

        private final EntityJellyfish entity;

        public AIMoveRandom(EntityJellyfish entityIn) {
            this.entity = entityIn;
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        @Override
        public boolean shouldExecute() {
            return true;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        @Override
        public void tick() {
            int i = this.entity.getIdleTime();

            if (i > 100) {
                this.entity.setMovementVector(0.0F, 0.0F, 0.0F);
            } else if (this.entity.getRNG().nextInt(50) == 0 || !this.entity.inWater
                    || !this.entity.hasMovementVector()) {
                float f = this.entity.getRNG().nextFloat() * ((float) Math.PI * 2F);
                float f1 = MathHelper.cos(f) * 0.2F;
                float f2 = -0.1F + this.entity.getRNG().nextFloat() * 0.2F;
                float f3 = MathHelper.sin(f) * 0.2F;
                this.entity.setMovementVector(f1 / 3, f2 / 3, f3 / 3);
            }
        }
    }

    @Override
    public DataParameter<Integer> getDataKey() {
        return TYPE_NUMBER;
    }

    @Override
    public int getVariantMax() {
        return 6;
    }

    @Override
    public boolean isChildI() {
        return this.isChild();
    }

    @Override
    public Random getRNGI() {
        return this.getRNG();
    }

    @Override
    public EntityDataManager getDataManagerI() {
        return this.getDataManager();
    }

    @Override
    public boolean canDespawn(double range) {
        return ModEntities.ENTITIES.containsKey("jellyfish") ? ModEntities.ENTITIES.get("jellyfish").despawn : false;
    }

}
