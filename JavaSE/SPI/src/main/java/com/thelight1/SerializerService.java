package com.thelight1;

import com.thelight1.spi.JavaSerializer;
import com.thelight1.spi.ObjectSerializer;

import java.util.Optional;
import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

public class SerializerService {

    public ObjectSerializer getObjectSerializer() {
        //会将所有ObjectSerializer的实现类找出来
        ServiceLoader<ObjectSerializer> serializers = ServiceLoader.load(ObjectSerializer.class);

        //只返回第一个ObjectSerializer的实现类
        final Optional<ObjectSerializer> serializer = StreamSupport.stream(serializers.spliterator(), false)
                .findFirst();

        return serializer.orElse(new JavaSerializer());
    }
}
