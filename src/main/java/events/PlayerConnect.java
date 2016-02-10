package events;

import core.PluginCore;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.event.EventListener;

import java.util.UUID;

public class PlayerConnect implements EventListener<ClientConnectionEvent.Join> {
    PluginCore core;

    public PlayerConnect(PluginCore core) {
        this.core = core;
    }

    // Récupération de l'évènement lorsqu'un joueur se connecte
    public void handle(ClientConnectionEvent.Join event) throws Exception {
        core.broadcastText("un joueur s'est connecté");
        UUID uuid = event.getTargetEntity().getUniqueId();
        core.broadcastText(uuid.toString());

    }
}
