package ru.practicum.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.practicum.config.WebConfig;
import ru.practicum.item.dto.AddItemRequest;
import ru.practicum.item.dto.GetItemRequest;
import ru.practicum.item.dto.ItemDto;
import ru.practicum.item.dto.ModifyItemRequest;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//Интеграционный тест для ItemController
@SpringJUnitWebConfig({ItemController.class, ItemControllerTestConfig.class, WebConfig.class})
public class ItemControllerTestWithContext {

    private final ItemService itemService;

    private final ObjectMapper mapper = new ObjectMapper();

    private MockMvc mvc;

    private ItemDto itemDto;

    private GetItemRequest request;

    @Autowired
    public ItemControllerTestWithContext(ItemService itemService) {
        this.itemService = itemService;
    }

    @BeforeEach
    void setUp(WebApplicationContext webContext) {
        mvc = MockMvcBuilders
                .webAppContextSetup(webContext)
                .build();

        itemDto = ItemDto.builder()
                .id(1L)
                .normalUrl("https://practicum.yandex.ru/java-developer/")
                .resolvedUrl("https://practicum.yandex.ru/java-developer/")
                .mimeType("text")
                .title("Ой!")
                .hasImage(true)
                .hasVideo(false)
                .unread(true)
                .dateResolved("2023.01.21 04:24:40")
                .tags(Set.of("tag_1"))
                .build();

        request = GetItemRequest.of(1L,
                "ALL",
                "ALL",
                "NEWEST",
                1,
                List.of());
    }

    @Test
    void get() throws Exception {
        when(itemService.addNewItem(any(Long.class), any(AddItemRequest.class)))
                .thenReturn(itemDto);

        mvc.perform(post("/items")
                        .header("X-Later-User-Id", 1)
                        .queryParam("state", "unread")
                        .queryParam("contentType", "all")
                        .queryParam("sort", "NEWEST")
                        .queryParam("limit", "1")
                        .queryParam("tags", "")
                        .content(mapper.writeValueAsString(request))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemDto.getId()), Long.class))
                .andExpect(jsonPath("$.normalUrl", is(itemDto.getNormalUrl())))
                .andExpect(jsonPath("$.resolvedUrl", is(itemDto.getResolvedUrl())))
                .andExpect(jsonPath("$.mimeType", is(itemDto.getMimeType())))
                .andExpect(jsonPath("$.mimeType", is(itemDto.getMimeType())))
                .andExpect(jsonPath("$.title", is(itemDto.getTitle())))
                .andExpect(jsonPath("$.hasImage", is(itemDto.isHasImage())))
                .andExpect(jsonPath("$.hasVideo", is(itemDto.isHasVideo())))
                .andExpect(jsonPath("$.unread", is(itemDto.isUnread())))
                .andExpect(jsonPath("$.dateResolved", is(itemDto.getDateResolved())))
                .andExpect(jsonPath("$.tags", notNullValue()));
    }

    @Test
    void add() throws Exception {
        when(itemService.addNewItem(any(Long.class), any(AddItemRequest.class)))
                .thenReturn(itemDto);

        AddItemRequest request = new AddItemRequest();
        request.setUrl("https://practicum.yandex.ru/java-developer/");

        mvc.perform(post("/items")
                        .header("X-Later-User-Id", 1)
                        .content(mapper.writeValueAsString(request))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemDto.getId()), Long.class))
                .andExpect(jsonPath("$.normalUrl", is(itemDto.getNormalUrl())))
                .andExpect(jsonPath("$.resolvedUrl", is(itemDto.getResolvedUrl())))
                .andExpect(jsonPath("$.mimeType", is(itemDto.getMimeType())))
                .andExpect(jsonPath("$.mimeType", is(itemDto.getMimeType())))
                .andExpect(jsonPath("$.title", is(itemDto.getTitle())))
                .andExpect(jsonPath("$.hasImage", is(itemDto.isHasImage())))
                .andExpect(jsonPath("$.hasVideo", is(itemDto.isHasVideo())))
                .andExpect(jsonPath("$.unread", is(itemDto.isUnread())))
                .andExpect(jsonPath("$.dateResolved", is(itemDto.getDateResolved())))
                .andExpect(jsonPath("$.tags", notNullValue()));
    }

    @Test
    void modify() throws Exception {
        when(itemService.changeItem(any(Long.class), any(ModifyItemRequest.class)))
                .thenReturn(itemDto);

        ModifyItemRequest request = new ModifyItemRequest();
        request.setItemId(1L);
        request.setRead(true);
        request.setReplaceTags(false);
        request.setTags(new HashSet<>());

        mvc.perform(patch("/items")
                        .header("X-Later-User-Id", 1L)
                        .content(mapper.writeValueAsString(request))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemDto.getId()), Long.class))
                .andExpect(jsonPath("$.normalUrl", is(itemDto.getNormalUrl())))
                .andExpect(jsonPath("$.resolvedUrl", is(itemDto.getResolvedUrl())))
                .andExpect(jsonPath("$.mimeType", is(itemDto.getMimeType())))
                .andExpect(jsonPath("$.mimeType", is(itemDto.getMimeType())))
                .andExpect(jsonPath("$.title", is(itemDto.getTitle())))
                .andExpect(jsonPath("$.hasImage", is(itemDto.isHasImage())))
                .andExpect(jsonPath("$.hasVideo", is(itemDto.isHasVideo())))
                .andExpect(jsonPath("$.unread", is(itemDto.isUnread())))
                .andExpect(jsonPath("$.dateResolved", is(itemDto.getDateResolved())))
                .andExpect(jsonPath("$.tags", notNullValue()));
    }

    @Test
    void deleteItem() throws Exception {
        mvc.perform(delete("/items/{itemId}", 1L)
                        .header("X-Later-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
