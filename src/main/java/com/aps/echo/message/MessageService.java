package com.aps.echo.message;

import com.aps.echo.messagelist.MessageListItem;
import com.aps.echo.messagelist.MessageListItemKey;
import com.aps.echo.messagelist.MessageListItemRepository;
import com.datastax.oss.driver.api.core.uuid.Uuids;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

  @Autowired MessageRepository messageRepository;
  @Autowired MessageListItemRepository messageListItemRepository;

  public void sendMessage(String from, List<String> to, String subject, String body) {
    Message message = new Message();
    message.setTo(to);
    message.setFrom(from);
    message.setSubject(subject);
    message.setBody(body);
    message.setId(Uuids.timeBased());
    messageRepository.save(message);

    to.forEach(
        toId -> {
          MessageListItem messageListItem =
              createMessageListItem(to, subject, toId, message, "Inbox");
          messageListItemRepository.save(messageListItem);
        });
    MessageListItem messageSaveItemEntry =
        createMessageListItem(to, subject, from, message, "Sent Item");
    messageListItemRepository.save(messageSaveItemEntry);
  }

  private static MessageListItem createMessageListItem(
      List<String> to, String subject, String itemOwner, Message message, String folder) {
    MessageListItemKey key = new MessageListItemKey();
    key.setId(itemOwner);
    key.setLabel(folder);
    key.setTimeUUID(message.getId());
    MessageListItem messageListItem = new MessageListItem();
    messageListItem.setKey(key);
    messageListItem.setTo(to);
    messageListItem.setSubject(subject);
    messageListItem.setUnread(true);
    return messageListItem;
  }
}
