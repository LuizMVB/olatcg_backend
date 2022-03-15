package br.com.olatcg_backend.vision.dto;

import br.com.olatcg_backend.domain.vo.TaxonomySearchApiResponseVo;
import br.com.olatcg_backend.enumerator.ErrorEnum;

import java.util.List;
import java.util.stream.Collectors;

public class TaxonomySearchResponseDTO extends ErrorDefaultResponseDTO {
    private Long idAnalysis;
    private List<AlignmentWithTaxonomyDTO> alignments;

    public TaxonomySearchResponseDTO(Long idAnalysis, TaxonomySearchApiResponseVo vo) {
        this.idAnalysis = idAnalysis;
        this.alignments = vo.getAlignments().stream()
                .map(p -> new AlignmentWithTaxonomyDTO(p))
                .collect(Collectors.toList());
    }

    public TaxonomySearchResponseDTO(ErrorEnum errorEnum){
        super(errorEnum);
    }

    public Long getIdAnalysis() {
        return idAnalysis;
    }

    public void setIdAnalysis(Long idAnalysis) {
        this.idAnalysis = idAnalysis;
    }

    public List<AlignmentWithTaxonomyDTO> getAlignments() {
        return alignments;
    }

    public void setAlignments(List<AlignmentWithTaxonomyDTO> alignments) {
        this.alignments = alignments;
    }
}
