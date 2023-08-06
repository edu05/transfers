package org.example.transfersv5.messages.account;

import java.util.UUID;

public record TransferLegApprovalRequested(UUID id, int amount) {

}
