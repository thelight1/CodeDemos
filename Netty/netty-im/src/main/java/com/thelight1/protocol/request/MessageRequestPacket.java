package com.thelight1.protocol.request;

import com.thelight1.protocol.Packet;
import com.thelight1.protocol.command.Command;
import lombok.Data;

@Data
public class MessageRequestPacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
