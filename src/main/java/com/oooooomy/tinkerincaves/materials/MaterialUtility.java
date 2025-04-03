package com.oooooomy.tinkerincaves.materials;

import com.oooooomy.tinkerincaves.TinkerInCaves;
import slimeknights.tconstruct.library.materials.definition.MaterialId;

public class MaterialUtility {
    public static MaterialId createMaterial(String name){
        return new MaterialId(TinkerInCaves.getResource(name));
    }
}
