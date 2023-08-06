package org.example.transfersv4.messages.transferprocessor;

import java.util.UUID;

public record TransferRequested(UUID id, int amount, UUID from, UUID to) {
}
