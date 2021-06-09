package lectureydjeon;

import org.springframework.data.repository.PagingAndSortingRepository;
// import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// @RepositoryRestResource(collectionResourceRel="counsels", path="counsels")
public interface CounselRepository extends PagingAndSortingRepository<Counsel, Long>{


}
