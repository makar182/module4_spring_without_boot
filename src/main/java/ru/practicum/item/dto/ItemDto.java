package ru.practicum.item.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder(toBuilder = true)
public class ItemDto {
    private final Long id;
    private final String normalUrl;
    private final String resolvedUrl;
    private final String mimeType;
    private final String title;
    private final boolean hasImage;
    private final boolean hasVideo;
    private final boolean unread;
    private final String dateResolved;
    private final Set<String> tags;
}