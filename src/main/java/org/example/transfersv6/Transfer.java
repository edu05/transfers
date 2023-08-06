package org.example.transfersv6;

import java.util.UUID;

public record Transfer(UUID id, UUID from, UUID to, int amount) {}