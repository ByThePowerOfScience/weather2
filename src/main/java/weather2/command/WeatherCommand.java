package weather2.command;

import com.corosus.coroutil.util.CULog;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.phys.Vec3;
import weather2.ServerTickHandler;
import weather2.weathersystem.WeatherManager;
import weather2.weathersystem.WeatherManagerServer;
import weather2.weathersystem.storm.StormObject;

import static net.minecraft.commands.Commands.literal;

public class WeatherCommand {
	public static void register(final CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(
				literal("weather2")
						.then(literal("killAll").requires(s -> s.hasPermission(2)).executes(c -> {
							WeatherManagerServer wm = ServerTickHandler.getWeatherManagerFor(c.getSource().getLevel().dimension());
							wm.clearAllStorms();
							c.getSource().sendSuccess(new TextComponent("Killed all storms"), true);
							return Command.SINGLE_SUCCESS;
						}))
						.then(literal("summon").requires(s -> s.hasPermission(2))
								.then(literal("tornado").executes(c -> {
									WeatherManagerServer wm = ServerTickHandler.getWeatherManagerFor(c.getSource().getLevel().dimension());
									StormObject stormObject = new StormObject(wm);

									stormObject.setupForcedTornado(c.getSource().getEntity());

									wm.addStormObject(stormObject);
									wm.syncStormNew(stormObject);

									c.getSource().sendSuccess(new TextComponent("Summoned Tornado"), true);
									return Command.SINGLE_SUCCESS;
								}))
								.then(literal("tornadoPlayer").executes(c -> {
									WeatherManagerServer wm = ServerTickHandler.getWeatherManagerFor(c.getSource().getLevel().dimension());
									StormObject stormObject = new StormObject(wm);

									stormObject.setupForcedTornado(c.getSource().getEntity());
									stormObject.setupPlayerControlledTornado(c.getSource().getEntity());

									wm.addStormObject(stormObject);
									wm.syncStormNew(stormObject);

									c.getSource().sendSuccess(new TextComponent("Summoned Player Tornado"), true);
									return Command.SINGLE_SUCCESS;
								}))
								.then(literal("sandstorm").executes(c -> {
									c.getSource().sendSuccess(new TextComponent("Summoned Sandstorm"), true);
									return Command.SINGLE_SUCCESS;
								}))

						)
		);

		/*dispatcher.register(
				literal("weather2")
						.then(literal("summon").requires(s -> s.hasPermission(2))
								.then(literal("sandstorm"))
									.executes(c -> {
										CULog.dbg("summon sandstorm at executor");

										return Command.SINGLE_SUCCESS;
									})
						)
		);*/
	}
}
