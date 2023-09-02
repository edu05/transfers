package org.example.transfersv6;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ActorRepository {

    private final Map<UUID, Actor> actors = new ConcurrentHashMap<>();
    public static final ActorRepository ACTOR_REPOSITORY = new ActorRepository();
    public static final TransferRejectionProcessor TRANSFER_REJECTION_PROCESSOR = new TransferRejectionProcessor();

    private ActorRepository() {
    }

    public Actor getActor(UUID id) {
        return actors.get(id);
    }

    public void putActor(UUID id, Actor actor) {
        actors.put(id, actor);
    }
}