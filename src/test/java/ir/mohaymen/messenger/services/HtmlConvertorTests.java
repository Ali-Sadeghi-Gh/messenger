package ir.mohaymen.messenger.services;

import ir.mohaymen.messenger.entities.EntityEntity;
import ir.mohaymen.messenger.entities.EntityType;
import ir.mohaymen.messenger.entities.MessageEntity;
import ir.mohaymen.messenger.entities.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class HtmlConvertorTests {
    private final HtmlConvertor convertor;

    @Autowired
    public HtmlConvertorTests(HtmlConvertor convertor) {
        this.convertor = convertor;
    }

    @Test
    public void convert1() {
        //given
        String messageContent = "this is the test message.";
        Date date = new Date();
        UserEntity user = UserEntity.builder()
                .name("ali")
                .build();
        List<EntityEntity> entities = new ArrayList<>();
        entities.add(EntityEntity.builder()
                .type(EntityType.BOLD)
                .entityOffset(-10)
                .entityLength(1000)
                .build());
        entities.add(EntityEntity.builder()
                .type(EntityType.ITALIC)
                .entityOffset(3)
                .entityLength(10)
                .build());
        entities.add(EntityEntity.builder()
                .type(EntityType.UNDERLINED)
                .entityOffset(5)
                .entityLength(7)
                .build());
        entities.add(EntityEntity.builder()
                .type(EntityType.LINK)
                .entityOffset(9)
                .entityLength(20)
                .url("my-link.ir")
                .build());

        //when
        String html = convertor.convert(MessageEntity.builder()
                .message(messageContent)
                .date(date)
                .read(false)
                .sender(user)
                .entities(entities)
                .build());
        String expected = "<!DOCTYPE html><html><head><title>Message</title></head><body><b>thi<i>s <u>is t<a href=\"my-link.ir\">he </u>t</i>est message.</b></a></br>from: ali</br>time: " + date + "</body></html>";

        //then
        Assertions.assertThat(html).isNotNull();
        Assertions.assertThat(html).isEqualTo(expected);
    }

    @Test
    public void convert2() {
        //given
        String messageContent = "this is the biggggggggggggggggggg test message.";
        Date date = new Date();
        UserEntity user = UserEntity.builder()
                .name("ali")
                .build();
        List<EntityEntity> entities = new ArrayList<>();
        entities.add(EntityEntity.builder()
                .type(EntityType.BOLD)
                .entityOffset(-10)
                .entityLength(1000)
                .build());
        entities.add(EntityEntity.builder()
                .type(EntityType.ITALIC)
                .entityOffset(3)
                .entityLength(10)
                .build());
        entities.add(EntityEntity.builder()
                .type(EntityType.UNDERLINED)
                .entityOffset(5)
                .entityLength(7)
                .build());
        entities.add(EntityEntity.builder()
                .type(EntityType.LINK)
                .entityOffset(9)
                .entityLength(10)
                .url("my-link.ir")
                .build());
        entities.add(EntityEntity.builder()
                .type(EntityType.LINK)
                .entityOffset(20)
                .entityLength(10)
                .url("my-link2.ir")
                .build());
        entities.add(EntityEntity.builder()
                .type(EntityType.LINK)
                .entityOffset(32)
                .entityLength(10)
                .url("my-link3.ir")
                .build());

        //when
        String html = convertor.convert(MessageEntity.builder()
                .message(messageContent)
                .date(date)
                .read(false)
                .sender(user)
                .entities(entities)
                .build());
        String expected = "<!DOCTYPE html><html><head><title>Message</title></head><body><b>thi<i>s <u>is t<a href=\"my-link.ir\">he </u>b</i>iggggg</a>g<a href=\"my-link2.ir\">gggggggggg</a>gg<a href=\"my-link3.ir\">g test mes</a>sage.</b></br>from: ali</br>time: " + date + "</body></html>";

        //then
        Assertions.assertThat(html).isNotNull();
        Assertions.assertThat(html).isEqualTo(expected);
    }
}
