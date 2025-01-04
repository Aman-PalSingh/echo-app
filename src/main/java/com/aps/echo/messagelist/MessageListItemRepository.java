package com.aps.echo.messagelist;

import java.util.List;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface MessageListItemRepository
    extends CassandraRepository<MessageListItem, MessageListItemKey> {

  //    List<MessageListItem> findAllById(MessageListItemKey id);
  List<MessageListItem> findAllByKey_IdAndKey_Label(String id, String label);
}
