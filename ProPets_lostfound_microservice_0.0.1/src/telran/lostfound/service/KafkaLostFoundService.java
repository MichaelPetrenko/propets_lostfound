package telran.lostfound.service;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import telran.lostfound.api.LostFoundKafkaDto;

@Service
@EnableBinding(Source.class)
public class KafkaLostFoundService {

	@Autowired
	Source source;
	
	public String addPost(LostFoundKafkaDto post) {
		source.output().send(MessageBuilder.withPayload(post).build());
		return post + " " + LocalTime.now();
	}
}
