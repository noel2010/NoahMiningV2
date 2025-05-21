package noah.noahMiningV2.data;

import lombok.Getter;
import lombok.SneakyThrows;
import noah.noahMiningV2.NoahMiningV2;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ItemData {

    @Getter
    private List<ItemStack> items = new ArrayList<>();

    @Getter
    private final File itemFile = new File(NoahMiningV2.INSTANCE.getDataFolder(), "data/items.yml");
    private YamlConfiguration itemData;


    @SneakyThrows
    public ItemData(){
        if (!itemFile.exists()) itemFile.createNewFile();
        itemData = YamlConfiguration.loadConfiguration(itemFile);
    }


    public ItemStack getItem(String id){
        if(itemData.contains("items."+id))
            return deserializeItem(itemData.getString("items."+id));
        return null;
    }

    @SneakyThrows
    public void addItem(String id, ItemStack item){
        if (itemData.contains("items."+id))
            NoahMiningV2.INSTANCE.getLogger().severe("ITEM ID IS ALREADY ASSIGNED, IF YOU WISH TO RE-ASSIGN, MAKE SURE TO DELETE IT");
        else {
            itemData.set("items."+id, serializeItem(item));
            itemData.save(itemFile);
        }
    }

    public void removeItem(String id){
        if(itemData.contains("items."+id))
            itemData.set("items."+id, null);
        else
            NoahMiningV2.INSTANCE.getLogger().warning("EMPTY ID SLOT");
    }


    private ItemStack deserializeItem(String base64){
        return ItemStack.deserializeBytes(Base64.getDecoder().decode(base64));
    }

    private String serializeItem(ItemStack item){
        ItemStack itemStack = item.clone();
        return Base64.getEncoder().encodeToString(item.serializeAsBytes());
    }

}
