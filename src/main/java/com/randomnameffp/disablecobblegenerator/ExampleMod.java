package com.randomnameffp.disablecobblegenerator;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ExampleMod.ID)
public class ExampleMod {
    public static final String ID = "disablecobblegenerator";
    public static final Logger LOGGER = LogManager.getLogger(ID);

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(ID)
            .setTooltipModifierFactory(item ->
                    new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
                            .andThen(TooltipModifier.mapNull(KineticStats.create(item)))
            );

    public ExampleMod(IEventBus modBus) {
        REGISTRATE.registerEventListeners(modBus);

        modBus.addListener(this::onCommonSetup);
        modBus.addListener(this::onClientSetup);
    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(ID, path);
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
    }

    private void onClientSetup(FMLClientSetupEvent event) {
    }

    /**
     * Feeds the hand-authored language partials (assets/examplemod/lang/default/*.json)
     * into Registrate's lang provider so runData merges them with the generated block and
     * item names into a single en_us.json. Keeps English copy out of Java, mirroring how
     * Create authors its own translations.
     */


    /**
     * Feeds the Ponder scenes' text (titles and captions) into Registrate's lang provider
     * so runData writes it into the same en_us.json as the block and item names. The
     * registered callback only runs during data generation, so it is safe to touch the
     * client-only PonderIndex from here.
     */


    /**
     * Registers the data generators. Running gradlew runData writes their output into
     * src/generated/resources.
     */

}
