package com.oooooomy.tinkerincaves.registers;

import com.oooooomy.tinkerincaves.modifiers.*;
import com.oooooomy.tinkerincaves.modifiers.GlowInkBomb;
import com.oooooomy.tinkerincaves.modifiers.InkBomb;
import com.oooooomy.tinkerincaves.modifiers.Irradiated;
import com.oooooomy.tinkerincaves.modifiers.Magnetizing;
import com.oooooomy.tinkerincaves.modifiers.Ortholance;
import com.oooooomy.tinkerincaves.modifiers.PrimordialStrength;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;
import com.oooooomy.tinkerincaves.TinkerInCaves;

public class TinkerInCavesModifiers {
    public static final ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(TinkerInCaves.MODID);

    public static final StaticModifier<ResistorSlam> resistor_slam = MODIFIERS.register("resistor_slam", ResistorSlam::new);
    public static final StaticModifier<Magnetizing> magnetizing = MODIFIERS.register("magnetizing", Magnetizing::new);
    public static final StaticModifier<AzureMagnet> azure_magnet = MODIFIERS.register("azure_magnet", AzureMagnet::new);
    public static final StaticModifier<ScarletMagnet> scarlet_magnet = MODIFIERS.register("scarlet_magnet", ScarletMagnet::new);
    public static final StaticModifier<AzureMagnetArmor> azure_magnet_armor = MODIFIERS.register("azure_magnet_armor", AzureMagnetArmor::new);
    public static final StaticModifier<ScarletMagnetArmor> scarlet_magnet_armor = MODIFIERS.register("scarlet_magnet_armor", ScarletMagnetArmor::new);

    public static final StaticModifier<PrimordialStrength> primordial_strength = MODIFIERS.register("primordial_strength", PrimordialStrength::new);
    public static final StaticModifier<PrimitiveClub> primitive_club = MODIFIERS.register("primitive_club", PrimitiveClub::new);
    public static final StaticModifier<TremorsaurusGhosts> tremorsaurus_ghosts = MODIFIERS.register("tremorsaurus_ghosts", TremorsaurusGhosts::new);
    public static final StaticModifier<SubterranodonGhosts> subterranodon_ghosts = MODIFIERS.register("subterranodon_ghosts", SubterranodonGhosts::new);
    public static final StaticModifier<AtlatitanTrample> atlatitan_trample = MODIFIERS.register("atlatitan_trample", AtlatitanTrample::new);

    public static final StaticModifier<SeaStaff> sea_staff = MODIFIERS.register("sea_staff", SeaStaff::new);
    public static final StaticModifier<InkBomb> ink_bomb = MODIFIERS.register("ink_bomb", InkBomb::new);
    public static final StaticModifier<GlowInkBomb> glow_ink_bomb = MODIFIERS.register("glow_ink_bomb", GlowInkBomb::new);
    public static final StaticModifier<Ortholance> ortholance = MODIFIERS.register("ortholance", Ortholance::new);

    public static final StaticModifier<RayGun> ray_gun = MODIFIERS.register("ray_gun", RayGun::new);
    public static final StaticModifier<Irradiated> irradiated = MODIFIERS.register("irradiated", Irradiated::new);

    public static final StaticModifier<DesolateDagger> desolate_dagger = MODIFIERS.register("desolate_dagger", DesolateDagger::new);
    public static final StaticModifier<DarknessSuit> darkness_suit = MODIFIERS.register("darkness_suit", DarknessSuit::new);

    public static final StaticModifier<FrostmintSpear> frostmint_spear = MODIFIERS.register("frostmint_spear", FrostmintSpear::new);
    public static final StaticModifier<SugarMagic> sugar_magic = MODIFIERS.register("sugar_magic", SugarMagic::new);
    public static final StaticModifier<SugarStaff> sugar_staff = MODIFIERS.register("sugar_staff", SugarStaff::new);
    public static final StaticModifier<CaramelArmor> caramel_armor = MODIFIERS.register("caramel_armor", CaramelArmor::new);

}
