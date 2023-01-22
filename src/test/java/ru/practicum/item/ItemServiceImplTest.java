package ru.practicum.item;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.practicum.UserDto;
import ru.practicum.UserService;
import ru.practicum.UserServiceImpl;
import ru.practicum.UserState;
import ru.practicum.config.PersistenceConfig;
import ru.practicum.item.dto.AddItemRequest;
import ru.practicum.item.dto.ItemDto;
import ru.practicum.item.dto.ModifyItemRequest;
import ru.practicum.item.model.Item;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestPropertySource(properties = {"db.name=test"})
@SpringJUnitConfig({PersistenceConfig.class, ItemServiceImpl.class, UrlMetaDataRetrieverImpl.class, UserServiceImpl.class})
public class ItemServiceImplTest {
    private final EntityManager em;
    private final ItemService itemService;
    private final UserService userService;


    @Test
    void addItemTest() {
        UserDto userDto = makeUserDto();
        UserDto newUserDto = userService.saveUser(userDto);

        AddItemRequest addItemRequest = new AddItemRequest();
        addItemRequest.setUrl("https://practicum.yandex.ru/java-developer/");
        ItemDto itemDto = itemService.addNewItem(newUserDto.getId(), addItemRequest);

        TypedQuery<Item> query = em.createQuery("Select i from Item i where i.id = :id", Item.class);
        Item item = query.setParameter("id", itemDto.getId()).getSingleResult();

        assertThat(item.getId(), notNullValue());
        assertThat(item.getResolvedUrl(), equalTo(itemDto.getResolvedUrl()));
        assertThat(item.getUrl(), equalTo(itemDto.getNormalUrl()));
        assertThat(item.getTitle(), equalTo(itemDto.getTitle()));
        assertThat(item.getMimeType(), equalTo(itemDto.getMimeType()));
        assertThat(item.isUnread(), equalTo(itemDto.isUnread()));
        assertThat(item.isHasVideo(), equalTo(itemDto.isHasVideo()));
        assertThat(item.isHasImage(), equalTo(item.isHasImage()));
    }

    @Test
    void deleteItemTest() {
        UserDto newUserDto = userService.saveUser(makeUserDto());
        AddItemRequest addItemRequest = new AddItemRequest();
        addItemRequest.setUrl("https://practicum.yandex.ru/java-developer/");
        ItemDto itemDto = itemService.addNewItem(newUserDto.getId(), addItemRequest);

        itemService.deleteItem(newUserDto.getId(), itemDto.getId());
        List<ItemDto> items = itemService.getItems(newUserDto.getId());
        assertThat(items.size(), equalTo(0));
    }

    @Test
    void changeItemTest() {
        UserDto newUserDto = userService.saveUser(makeUserDto());
        AddItemRequest addItemRequest = new AddItemRequest();
        addItemRequest.setUrl("https://practicum.yandex.ru/java-developer/");
        ItemDto itemDto = itemService.addNewItem(newUserDto.getId(), addItemRequest);

        ModifyItemRequest request = new ModifyItemRequest();
        request.setItemId(itemDto.getId());
        request.setRead(false);
        request.setReplaceTags(false);
        request.setTags(new HashSet<>());


    }

    private UserDto makeUserDto() {
        UserDto dto = new UserDto();
        dto.setEmail("ivanov@yandex.com");
        dto.setFirstName("Иван");
        dto.setLastName("Иванов");
        dto.setState(UserState.DELETED);

        return dto;
    }
}
