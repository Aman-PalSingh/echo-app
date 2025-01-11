package com.aps.echo.message;

import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface MessageRepository extends CassandraRepository<Message, UUID> {}
