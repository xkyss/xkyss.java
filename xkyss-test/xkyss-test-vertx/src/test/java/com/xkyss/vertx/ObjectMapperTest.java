package com.xkyss.vertx;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.json.jackson.DatabindCodec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.LongAdder;

public class ObjectMapperTest {
    @Test
    public void test_01() throws JsonProcessingException {
        ObjectMapper om = DatabindCodec.mapper();
        om.setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_DOT_CASE);

        String s = om.writeValueAsString(new Foo("nameValue", "codeValue"));
        Assertions.assertEquals("{\"name.key\":\"nameValue\",\"code.key\":\"codeValue\"}", s);
    }

    @Test
    public void test_02() {
        ObjectMapper om = DatabindCodec.mapper();
        om.setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_DOT_CASE);

        Map<String, String> map = om
            .convertValue(new Foo("nameValue", "codeValue"), new TypeReference<Map<String, String>>() {});

        Assertions.assertNotNull(map);
        Assertions.assertEquals(2, map.size());
        Assertions.assertTrue(map.containsKey("name.key"));
        Assertions.assertTrue(map.containsKey("code.key"));
    }

    @Test
    public void test_03() {

        ObjectMapper om = DatabindCodec.mapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        om.findAndRegisterModules();
        om.registerModule(new TestModule());

        Bar bar = new Bar();
        bar.setCounter(new LongAdder());
        bar.getCounter().increment();

        {
            String s = Json.encode(bar);
            Assertions.assertEquals("{\"counter\":1}", s);

            Bar b = Json.decodeValue(s, Bar.class);
            Assertions.assertNotNull(b);
        }

        {
            JsonObject jo = JsonObject.mapFrom(bar);
            Assertions.assertEquals("{\"counter\":1}", jo.toString());

            Bar b = jo.mapTo(Bar.class);
            Assertions.assertNotNull(b);
            Assertions.assertEquals(1L, b.getCounter().sum());
        }

    }

    static class TestModule extends SimpleModule {
        public TestModule() {
            addSerializer(Bar.class, new BarSerializer());
            addDeserializer(Bar.class, new BarDeserializer());
        }
    }


    static class Foo {
        public Foo() {
        }

        public Foo(String name, String code) {
            this.nameKey = name;
            this.codeKey = code;
        }
        public String nameKey;
        public String codeKey;
    }

    static class Bar {
        private LongAdder counter;

        public LongAdder getCounter() {
            return counter;
        }

        public void setCounter(LongAdder counter) {
            this.counter = counter;
        }
    }

    static class BarSerializer extends JsonSerializer<Bar> {
        @Override
        public void serialize(Bar bar, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("counter", bar.getCounter().sum());
            jsonGenerator.writeEndObject();
        }
    }

    static class BarDeserializer extends JsonDeserializer<Bar> {

        @Override
        public Bar deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
            Bar bar = new Bar();
            bar.setCounter(new LongAdder()); // 初始化 LongAdder 对象

            // 解析 JSON 数据
            JsonNode node = jsonParser.getCodec().readTree(jsonParser);
            if (node.has("counter")) {
                long counterValue = node.get("counter").asLong(); // 获取 counter 字段的值
                bar.getCounter().add(counterValue); // 将值添加到 LongAdder 对象中
            }

            return bar;
        }
    }
}
