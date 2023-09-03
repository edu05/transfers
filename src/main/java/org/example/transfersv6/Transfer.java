package org.example.transfersv6;

import java.util.UUID;

public record Transfer(UUID from, UUID to, int amount) {

}
