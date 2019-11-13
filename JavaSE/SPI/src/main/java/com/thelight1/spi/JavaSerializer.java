package com.thelight1.spi;

import java.io.*;

public class JavaSerializer implements ObjectSerializer {
    @Override
    public byte[] serialize(Object obj) throws ObjectSerializerException {
        ByteArrayOutputStream arrayOutputStream;
        try {
            arrayOutputStream = new ByteArrayOutputStream();
            ObjectOutput objectOutput = new ObjectOutputStream(arrayOutputStream);
            objectOutput.writeObject(obj);
            objectOutput.flush();
            objectOutput.close();
        } catch (IOException e) {
            throw new ObjectSerializerException("JAVA serialize error " + e.getMessage());
        }
        return arrayOutputStream.toByteArray();
    }

    @Override
    public <T> T deSerialize(byte[] param, Class<T> clazz) throws ObjectSerializerException {
        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(param);
        try {
            ObjectInput input = new ObjectInputStream(arrayInputStream);
            return (T) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new ObjectSerializerException("JAVA deSerialize error " + e.getMessage());
        }
    }

    @Override
    public String getSchemeName() {
        return "javaSerializer";
    }
}
