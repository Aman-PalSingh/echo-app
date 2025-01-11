package com.aps.echo.message;

import java.util.List;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "message_by_id")
public class Message {
  @Id
  @PrimaryKeyColumn(name = "id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
  private UUID id;

  @CassandraType(userTypeName = "from", type = CassandraType.Name.TEXT)
  private String From;

  @CassandraType(type = CassandraType.Name.LIST, typeArguments = CassandraType.Name.TEXT)
  private List<String> to;

  @CassandraType(type = CassandraType.Name.TEXT)
  private String subject;

  @CassandraType(type = CassandraType.Name.TEXT)
  private String body;


  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getFrom() {
    return From;
  }

  public void setFrom(String from) {
    From = from;
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

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }


}
