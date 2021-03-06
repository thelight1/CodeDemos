package com.thelight1.protocol.request;

import com.thelight1.protocol.command.Command;
import com.thelight1.protocol.Packet;
import lombok.Data;

@Data
public class LoginRequestPacket extends Packet {

    private String username;
    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
