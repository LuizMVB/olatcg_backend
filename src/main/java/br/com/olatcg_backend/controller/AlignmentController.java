package br.com.olatcg_backend.controller;

import br.com.olatcg_backend.domain.factory.PairwiseAlignmentFactory;
import br.com.olatcg_backend.domain.pairwise.alignment.PairwiseAlignment;
import br.com.olatcg_backend.entity.Analysis;
import br.com.olatcg_backend.mapper.AlignmentMapper;
import br.com.olatcg_backend.service.PairwiseAlignmentService;
import io.tej.SwaggerCodgen.api.AlignmentApi;
import io.tej.SwaggerCodgen.model.AlignmentTypeEnumDTO;
import io.tej.SwaggerCodgen.model.PairwiseAlnReqDTO;
import io.tej.SwaggerCodgen.model.PairwiseAlnRespDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/api")
public class AlignmentController implements AlignmentApi {

    @Autowired
    private PairwiseAlignmentService pairwiseAlignmentService;

    @Autowired
    private AlignmentMapper alignmentMapper;

    @Override
    public ResponseEntity<PairwiseAlnRespDTO> align(AlignmentTypeEnumDTO type, PairwiseAlnReqDTO pairwiseAlnReqDTO) {
        PairwiseAlignment pairwiseAlignment = PairwiseAlignmentFactory.build(type, pairwiseAlnReqDTO);
        Analysis alnAnalysis = pairwiseAlignmentService.align(pairwiseAlignment);
        PairwiseAlnRespDTO respDTO = alignmentMapper.analysisIdAndAlignmentToPairAlnRespDTO(alnAnalysis.getId(), alnAnalysis.getAlignmentFromAlnAnalysis());
        return new ResponseEntity<>(respDTO, HttpStatus.CREATED);
    }

}
