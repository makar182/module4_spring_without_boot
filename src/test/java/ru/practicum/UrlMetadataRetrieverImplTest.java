package ru.practicum;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.practicum.item.UrlMetaDataRetriever;
import ru.practicum.item.UrlMetaDataRetrieverImpl;

import java.net.URISyntaxException;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringJUnitConfig(UrlMetaDataRetrieverImpl.class)
public class UrlMetadataRetrieverImplTest {
    private final UrlMetaDataRetriever urlMetaDataRetriever;

    @Test
    void retrieve() throws URISyntaxException {
        // given
        String url = "https://ya.ru";
        // when
        UrlMetaDataRetriever.UrlMetadata urlMetadata = urlMetaDataRetriever.retrieve(url);
        // then
        assertThat(urlMetadata.getNormalUrl(), equalTo("https://ya.ru"));
        assertThat(urlMetadata.getResolvedUrl(), containsString("https://ya.ru"));
        assertThat(urlMetadata.getTitle(), notNullValue(String.class));
        assertThat(urlMetadata.isHasImage(), equalTo(true));
        assertThat(urlMetadata.isHasVideo(), equalTo(false));
        assertThat(urlMetadata.getMimeType(), equalTo("text"));
        assertThat(urlMetadata.getDateResolved(), notNullValue());
    }
}
