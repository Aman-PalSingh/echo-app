package com.aps.echo.messagelist;

import java.util.List;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "messages_by_user_folder")
public class MessageListItem {

  @PrimaryKey private MessageListItemKey key;

  @CassandraType(type = CassandraType.Name.LIST, typeArguments = CassandraType.Name.TEXT)
  private List<String> to;

  @CassandraType(type = CassandraType.Name.TEXT)
  private String subject;

  @CassandraType(type = CassandraType.Name.BOOLEAN)
  private boolean isUnread;

  @Transient private String receivedAgo;

  public MessageListItemKey getKey() {
    return key;
  }

  public void setKey(MessageListItemKey key) {
    this.key = key;
  }

  public List<String> getTo() {
    return to;
  }

  public void setTo(List<String> to) {
    this.to = to;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public boolean isUnread() {
    return isUnread;
  }

  public void setUnread(boolean unread) {
    isUnread = unread;
  }

  public String getReceivedAgo() {
    return receivedAgo;
  }

  public void setReceivedAgo(String receivedAgo) {
    this.receivedAgo = receivedAgo;
  }
}
