package com.agacostays.branch.producer;
import com.agacostays.branch.event.*;
import org.springframework.kafka.core.KafkaTemplate; import org.springframework.stereotype.Component;
import java.time.Instant; import java.util.UUID;
@Component public class BranchEventProducer{
 private final KafkaTemplate<String,Object> kafka;
 public BranchEventProducer(KafkaTemplate<String,Object> k){kafka=k;}
 public void created(Long id,String name,Long city){send(new BranchCreatedEvent(UUID.randomUUID().toString(),id,name,city,Instant.now()),id);}
 public void updated(Long id,String name){send(new BranchUpdatedEvent(UUID.randomUUID().toString(),id,name,Instant.now()),id);}
 public void status(Long id,String s){send(new BranchStatusChangedEvent(UUID.randomUUID().toString(),id,s,Instant.now()),id);}
 public void photo(Long b,Long p,String op){send(new BranchPhotoUpdatedEvent(UUID.randomUUID().toString(),b,p,op,Instant.now()),b);}
 public void receptionist(Long b,Long c,String op){send(new ReceptionistContactUpdatedEvent(UUID.randomUUID().toString(),b,c,op,Instant.now()),b);}
 public void emergency(Long b,Long c,String op){send(new EmergencyContactUpdatedEvent(UUID.randomUUID().toString(),b,c,op,Instant.now()),b);}
 private void send(Object e,Long key){try{kafka.send("branch.events",String.valueOf(key),e);}catch(Exception ignored){}}
}
