package org.example.transfersv3;

public record Message<T>(MessageType messageType, T payload) {

}
