package com.oooooomy.tinkerincaves.modifiers;

import com.oooooomy.tinkerincaves.modifiers.abilities.*;
import com.oooooomy.tinkerincaves.modifiers.slotless.GlowInkBomb;
import com.oooooomy.tinkerincaves.modifiers.slotless.InkBomb;
import com.oooooomy.tinkerincaves.modifiers.upgrades.Ortholance;
import com.oooooomy.tinkerincaves.modifiers.upgrades.PrimordialStrength;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;
import com.oooooomy.tinkerincaves.TinkerInCaves;

public class TinkerInCavesModifiers {
    public static final ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(TinkerInCaves.MODID);

    public static final StaticModifier<ResistorSlam> resistor_slam = MODIFIERS.register("resistor_slam",ResistorSlam::new);

    public static final StaticModifier<PrimordialStrength> primordial_strength = MODIFIERS.register("primordial_strength",PrimordialStrength::new);
    public static final StaticModifier<PrimitiveClub> primitive_club = MODIFIERS.register("primitive_club",PrimitiveClub::new);
    public static final StaticModifier<TremorsaurusGhosts> tremorsaurus_ghosts = MODIFIERS.register("tremorsaurus_ghosts", TremorsaurusGhosts::new);
    public static final StaticModifier<SubterranodonGhosts> subterranodon_ghosts = MODIFIERS.register("subterranodon_ghosts", SubterranodonGhosts::new);

    public static final StaticModifier<SeaStaff> sea_staff = MODIFIERS.register("sea_staff",SeaStaff::new);
    public static final StaticModifier<InkBomb> ink_bomb = MODIFIERS.register("ink_bomb",InkBomb::new);
    public static final StaticModifier<GlowInkBomb> glow_ink_bomb = MODIFIERS.register("glow_ink_bomb", GlowInkBomb::new);
    public static final StaticModifier<Ortholance> ortholance = MODIFIERS.register("ortholance", Ortholance::new);

}
