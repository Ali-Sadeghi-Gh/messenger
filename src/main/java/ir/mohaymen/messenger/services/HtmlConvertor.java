package ir.mohaymen.messenger.services;

import ir.mohaymen.messenger.entities.EntityEntity;
import ir.mohaymen.messenger.entities.MessageEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HtmlConvertor {

    public String convert(MessageEntity message) {
        String messageContent = message.getMessage();
        List<EntityEntity> entities = message.getEntities();

        List<Integer> indices = new ArrayList<>();
        List<Character> characters = new ArrayList<>();
        List<String> urls = new ArrayList<>();

        for (EntityEntity entity : entities) {
            int beginIndex = entity.getEntityOffset();
            int endIndex = beginIndex + entity.getEntityLength();
            beginIndex = beginIndex < 0 ? 0 : Math.min(beginIndex, messageContent.length());
            endIndex = endIndex < 0 ? 0 : Math.min(endIndex, messageContent.length());

            char beginChar = 0;
            char endChar = 0;
            switch (entity.getType()) {
                case BOLD -> {
                    beginChar = 'B';
                    endChar = 'b';
                }
                case ITALIC -> {
                    beginChar = 'I';
                    endChar = 'i';
                }
                case UNDERLINED -> {
                    beginChar = 'U';
                    endChar = 'u';
                }
                case LINK -> {
                    beginChar = 'L';
                    endChar = 'l';
                }
            }

            indices.add(beginIndex);
            indices.add(endIndex);
            urls.add(entity.getUrl());
            urls.add(entity.getUrl());
            characters.add(beginChar);
            characters.add(endChar);
        }

        sort(indices, characters);

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html><html><head><title>Message</title></head><body>");

        int counter = 0;
        for (int i = 0; i < messageContent.length() + 1; i++) {
            if (counter < indices.size()) {
                Integer index = indices.get(counter);
                if (index == i) {
                    switch (characters.get(counter)) {
                        case 'B' -> html.append("<b>");
                        case 'b' -> html.append("</b>");
                        case 'I' -> html.append("<i>");
                        case 'i' -> html.append("</i>");
                        case 'U' -> html.append("<u>");
                        case 'u' -> html.append("</u>");
                        case 'L' -> html.append("<a href=\"").append(urls.get(counter)).append("\">");
                        case 'l' -> html.append("</a>");
                    }
                    i--;
                    counter++;
                    continue;
                }
            }

            if (i != messageContent.length()) {
                html.append(messageContent.charAt(i));
            }
        }
        html.append("</br>from: ").append(message.getSender().getName());
        html.append("</br>time: ").append(message.getDate());
        html.append("</body></html>");
        return html.toString();
    }

    private void sort(List<Integer> indices, List<Character> characters) {

    }
}
