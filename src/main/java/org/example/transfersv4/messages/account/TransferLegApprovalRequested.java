package org.example.transfersv4.messages.account;

import java.util.UUID;

public record TransferLegApprovalRequested(UUID id, int amount) {

}
