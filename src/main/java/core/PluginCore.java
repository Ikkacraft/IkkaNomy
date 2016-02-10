package core;

import events.PlayerConnect;
import org.slf4j.Logger;
import com.google.inject.Inject;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.EventListener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.text.Text;

@Plugin(
        id = "IkkaNomy",
        name = "IkkaNomy",
        version = "1.0"
)
// La class PluginCore permet d'initialiser le plugin mais permet aussi la gestion des différents évènements
public class PluginCore {
    @Inject
    private Game game;
    private Logger logger;

    @Listener
    // Lorsque le serveur minecraft est lancé, on initialise le plugin
    public void onServerStart(GameStartedServerEvent event) {
        this.getLogger().info("IkkaNomy a recu l'instruction start");

        // Evènement OpenChest qui permet l'achievement coffre caché
        EventListener<ClientConnectionEvent.Join> listenerOpenChest = new PlayerConnect(this);
        Sponge.getEventManager().registerListener(this, ClientConnectionEvent.Join.class, listenerOpenChest);
    }

    @Inject
    public PluginCore(Logger logger) {
        this.logger = logger;
    }

    public Logger getLogger() {
        return this.logger;
    }

    public Game getGame() {
        return this.game;
    }

    // Permet d'envoyer un message à tous les joueurs du serveur
    public void broadcastText(String text) {
        getGame().getServer().getBroadcastChannel().send(Text.of(text));
    }
}