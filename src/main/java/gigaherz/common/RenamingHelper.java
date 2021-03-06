package gigaherz.common;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLMissingMappingsEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Map;

public class RenamingHelper
{
    private final Map<ResourceLocation, Item> upgradeItemNames = Maps.newHashMap();
    private final Map<ResourceLocation, Block> upgradeBlockNames = Maps.newHashMap();

    public void addAlternativeName(Item item, ResourceLocation altName)
    {
        upgradeItemNames.put(altName, item);
    }

    public void addAlternativeName(Block block, ResourceLocation altName)
    {
        upgradeBlockNames.put(altName, block);
        Item item = Item.getItemFromBlock(block);
        if (item != null)
            addAlternativeName(item, altName);
    }

    public void process(FMLMissingMappingsEvent ev)
    {
        for (FMLMissingMappingsEvent.MissingMapping missing : ev.get())
        {
            if (missing.type == GameRegistry.Type.ITEM
                    && upgradeItemNames.containsKey(missing.resourceLocation))
            {
                missing.remap(upgradeItemNames.get(missing.resourceLocation));
            }

            if (missing.type == GameRegistry.Type.BLOCK
                    && upgradeBlockNames.containsKey(missing.resourceLocation))
            {
                missing.remap(upgradeBlockNames.get(missing.resourceLocation));
            }
        }
    }
}
