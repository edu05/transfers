package org.example.transfersv5.messages.transferprocessor;

import java.util.UUID;

public record TransferLegCommitted(UUID id, UUID accountId) {
}
