package com.thelight1.protocol.response;

import com.thelight1.protocol.Packet;
import com.thelight1.protocol.command.Command;
import lombok.Data;

@Data
public class MessageResponsePacket extends Packet {

    private String fromUserId;
    private String fromUsername;
    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
