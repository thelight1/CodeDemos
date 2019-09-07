package com.thelight1.protocol.response;

import com.thelight1.protocol.Packet;
import com.thelight1.protocol.command.Command;
import lombok.Data;

@Data
public class LoginResponsePacket extends Packet {

    private boolean success;
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
