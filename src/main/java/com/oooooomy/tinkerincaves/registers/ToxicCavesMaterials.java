package com.oooooomy.tinkerincaves.registers;

import com.oooooomy.tinkerincaves.TinkerInCaves;
import slimeknights.tconstruct.library.materials.definition.MaterialId;

public class ToxicCavesMaterials {
    public static MaterialId createMaterial(String name){
        return new MaterialId(TinkerInCaves.getResource(name));
    }

    public static final MaterialId polymer_plate = createMaterial("polymer_plate");

    public static final MaterialId  tough_hide = createMaterial("tough_hide");
    public static final MaterialId  heavy_bone = createMaterial("heavy_bone");
    public static final MaterialId  cycad = createMaterial("cycad");
    public static final MaterialId  archaic_vine = createMaterial("archaic_vine");
    public static final MaterialId  tectonic_shard = createMaterial("tectonic_shard");

    public static final MaterialId  scarlet_neodymium = createMaterial("scarlet_neodymium");
    public static final MaterialId  azure_neodymium = createMaterial("azure_neodymium");

    public static final MaterialId  dark_tatters = createMaterial("dark_tatters.json");
    public static final MaterialId  shadow_silk = createMaterial("shadow_silk");

}
