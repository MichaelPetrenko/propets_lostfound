package telran.lostfound.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import telran.lostfound.domain.entities.LostFoundEntity;

//@Repository
//Можно не писать аннотацию, потому-что аннотация есть в монго репозиторий
public interface LostfoundRepository extends MongoRepository<LostFoundEntity, String> {

	List<LostFoundEntity> findAllByTypePost(boolean typePost, Pageable pageable);
	List<LostFoundEntity> findAllByTypePost(boolean typePost);
	
	List<LostFoundEntity> findByLocationNear(Point p, Distance d);
	List<LostFoundEntity> findByLocationNear(Point p, Distance d, Pageable pageable);
	List<LostFoundEntity> findByLocationNearAndTypePostAndType(Point p, Distance d, boolean typePost, String type);
	List<LostFoundEntity> findByLocationNearAndTypePostAndType(Point p, Distance d, boolean typePost, String type, Pageable pageable);
	
}
