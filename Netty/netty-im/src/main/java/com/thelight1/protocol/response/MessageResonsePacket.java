package com.thelight1.protocol.response;

import com.thelight1.protocol.Packet;
import com.thelight1.protocol.command.Command;
import lombok.Data;

@Data
public class MessageResonsePacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
