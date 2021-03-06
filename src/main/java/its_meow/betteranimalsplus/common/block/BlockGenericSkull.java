package its_meow.betteranimalsplus.common.block;

import its_meow.betteranimalsplus.util.HeadTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class BlockGenericSkull extends BlockAnimalSkull {

    public final HeadTypes type;

    public BlockGenericSkull(HeadTypes type, int i) {
        super();
        this.setRegistryName(type.name + "_" + i);
        this.type = type;
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader reader) {
        return type.teFactory.apply(type);
    }

}
