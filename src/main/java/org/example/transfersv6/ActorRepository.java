package org.example.transfersv6;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ActorRepository {

    private final Map<UUID, Actor> actors = new HashMap<>();
    public static final TransferRejectionProcessor TRANSFER_REJECTION_PROCESSOR = new TransferRejectionProcessor();
    public static final ActorRepository ACTOR_REPOSITORY = new ActorRepository();

    private ActorRepository() {
    }

    public Actor getActor(UUID id) {
        return actors.get(id);
    }

    public void putActor(UUID id, Actor actor) {
        actors.put(id, actor);
    }
}