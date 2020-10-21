package telran.lostfound.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import telran.lostfound.domain.entities.LostFoundEntity;

//@Repository
//Можно не писать аннотацию, потому-что аннотация есть в монго репозиторий
public interface LostfoundRepository extends MongoRepository<LostFoundEntity, String>{

	// No metric: {'geoNear' : 'person', 'near' : [x, y], maxDistance : distance }
	// Location: {type: Point, coordinates: [35.35, 31.35]}	
	  GeoResults<LostFoundEntity> findByLocationNear(Point location, Distance distance);
	  
	  List<LostFoundEntity> findAllByTypePost(boolean typePost, Pageable pageable);
	

}
