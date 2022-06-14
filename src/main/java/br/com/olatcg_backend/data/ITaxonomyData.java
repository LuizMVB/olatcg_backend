package br.com.olatcg_backend.data;

import br.com.olatcg_backend.domain.Analysis;
import br.com.olatcg_backend.domain.Taxonomy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ITaxonomyData extends CrudRepository<Taxonomy, Integer> {

    @Query("SELECT t FROM Taxonomy t " +
            "JOIN t.alignment.inputBiologicalSequence bioSeq " +
            "WHERE bioSeq.id = :bioSeqId")
    Taxonomy findByBiologicalSequenceId(@Param("bioSeqId") Long bioSeqId);
}
