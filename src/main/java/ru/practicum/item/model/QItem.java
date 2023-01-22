package ru.practicum.item.model;

import com.querydsl.core.annotations.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItem extends EntityPathBase<Item> {

    private static final long serialVersionUID = 2084451991L;

    public static final QItem item = new QItem("item");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final SetPath<String, StringPath> tags = this.<String
            , StringPath>createSet("tags", String.class
            , StringPath.class, PathInits.DIRECT2);

    public final StringPath url = createString("url");

    public final NumberPath<Long> userId = createNumber("userId", Long.class);
    public final BooleanPath unread = createBoolean("true");
    public final StringPath mimeType = createString("text/");



    public QItem(String variable) {
        super(Item.class, forVariable(variable));
    }

    public QItem(Path<? extends Item> path) {
        super(path.getType(), path.getMetadata());
    }

    public QItem(PathMetadata metadata) {
        super(Item.class, metadata);
    }

}
