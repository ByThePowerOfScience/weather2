package weather2;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import weather2.client.tile.AnemometerEntityRenderer;
import weather2.client.tile.WindTurbineEntityRenderer;
import weather2.client.tile.WindVaneEntityRenderer;
import weather2.client.entity.model.AnemometerModel;
import weather2.client.entity.model.WindTurbineModel;
import weather2.client.entity.model.WindVaneModel;
import weather2.client.entity.render.LightningBoltWeatherNewRenderer;

@Mod(Weather.MODID)
public class ClientRegistry {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(EntityRegistry.LIGHTNING_BOLT.get(), render -> new LightningBoltWeatherNewRenderer(render));
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers e) {
        e.registerBlockEntityRenderer(WeatherBlocks.BLOCK_ENTITY_ANEMOMETER.get(), AnemometerEntityRenderer::new);
        e.registerBlockEntityRenderer(WeatherBlocks.BLOCK_ENTITY_WIND_VANE.get(), WindVaneEntityRenderer::new);
        e.registerBlockEntityRenderer(WeatherBlocks.BLOCK_ENTITY_WIND_TURBINE.get(), WindTurbineEntityRenderer::new);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(WindVaneModel.LAYER_LOCATION, WindVaneModel::createBodyLayer);
        event.registerLayerDefinition(AnemometerModel.LAYER_LOCATION, AnemometerModel::createBodyLayer);
        event.registerLayerDefinition(WindTurbineModel.LAYER_LOCATION, WindTurbineModel::createBodyLayer);
    }

}
