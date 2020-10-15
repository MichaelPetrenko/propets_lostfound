package telran.lostfound.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import telran.lostfound.domain.entities.LostFoundEntity;

//@Repository
//Можно не писать аннотацию, потому-что аннотация есть в монго репозиторий
public interface LostfoundRepository extends MongoRepository<LostFoundEntity, String>{
	

}
