package org.example.transfersv6;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ActorRepository {

    private final Map<UUID, Actor> actors = new HashMap<>();
    public static final TransferRejectionProcessor TRANSFER_REJECTION_PROCESSOR = new TransferRejectionProcessor();
    public static final TransferCommittedProcessor TRANSFER_COMMITTED_PROCESSOR = new TransferCommittedProcessor();
    public static final ActorRepository ACTOR_REPOSITORY = new ActorRepository();

    private ActorRepository() {}

    public Actor getActor(UUID id) {
        return actors.get(id);
    }

    public void putActor(UUID id, Actor actor) {
        actors.put(id, actor);
    }

    public Collection<Actor> getActors() {
        return actors.values();
    }
}