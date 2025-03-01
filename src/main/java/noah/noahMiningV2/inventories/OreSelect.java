package noah.noahMiningV2.inventories;

import dev.siea.uilabs.UILabs;
import dev.siea.uilabs.element.Button;
import dev.siea.uilabs.frame.Border;
import dev.siea.uilabs.gui.DefaultInventoryGui;
import noah.noahMiningV2.NoahMiningV2;
import noah.noahMiningV2.utils.OreUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;

public class OreSelect {

    UILabs ui = new UILabs(NoahMiningV2.INSTANCE);

    public OreSelect(Player p, Location loc){
        DefaultInventoryGui gui = ui.create("§cOre Select");
        gui.setBorder(Border.of(Material.GRAY_STAINED_GLASS_PANE));
        gui.addElement(new Button(Material.COAL_ORE, "Coal", List.of("", "Set this ore as coal")){
            @Override
            public void onButtonPressed(InventoryClickEvent e){ OreUtils.respawnSpecificOre(loc, Material.COAL_ORE); }
        });
        gui.addElement(new Button(Material.IRON_ORE, "Iron", List.of("", "Set this ore as iron")){
            @Override
            public void onButtonPressed(InventoryClickEvent e){ OreUtils.respawnSpecificOre(loc, Material.IRON_ORE); }
        });
        gui.addElement(new Button(Material.COPPER_ORE, "Copper", List.of("", "Set this ore as copper")){
            @Override
            public void onButtonPressed(InventoryClickEvent e){ OreUtils.respawnSpecificOre(loc, Material.COPPER_ORE); }
        });
        gui.addElement(new Button(Material.GOLD_ORE, "Gold", List.of("", "Set this ore as gold")){
            @Override
            public void onButtonPressed(InventoryClickEvent e){ OreUtils.respawnSpecificOre(loc, Material.GOLD_ORE); }
        });
        gui.addElement(new Button(Material.DIAMOND_ORE, "Diamond", List.of("", "Set this ore as diamond")){
            @Override
            public void onButtonPressed(InventoryClickEvent e){ OreUtils.respawnSpecificOre(loc, Material.DIAMOND_ORE); }
        });
        gui.addElement(new Button(Material.EMERALD_ORE, "Emerald", List.of("", "Set this ore as emerald")){
            @Override
            public void onButtonPressed(InventoryClickEvent e){ OreUtils.respawnSpecificOre(loc, Material.EMERALD_ORE); }
        });
        gui.addElement(new Button(Material.COAL_ORE, "Coal", List.of("", "Set this ore as random")){
            @Override
            public void onButtonPressed(InventoryClickEvent e){ OreUtils.respawnOre(loc); }
        });
        gui.view(p);
    }

}
