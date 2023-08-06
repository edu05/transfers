package org.example.transfersv4.messages.transferprocessor;

import java.util.UUID;

public record TransferLegCommitted(UUID id, UUID accountId) {
}
