package br.com.olatcg_backend.controller;

import br.com.olatcg_backend.entity.Analysis;
import br.com.olatcg_backend.mapper.AnalysisMapper;
import br.com.olatcg_backend.service.AnalysisFinderService;
import io.tej.SwaggerCodgen.api.AnalysisApi;
import io.tej.SwaggerCodgen.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/v1/api")
public class AnalysisController implements AnalysisApi {

    @Autowired
    private AnalysisFinderService analysisFinderService;

    @Autowired
    private AnalysisMapper analysisMapper;

    @Override
    public ResponseEntity<AnalysesRespDTO> findAll(Integer pageNumber, Integer pageSize, String sort) {
        Page<Analysis> analysisPage = analysisFinderService.findAll(pageNumber, pageSize, sort);
        AnalysesRespDTO analysisRespListDTO = analysisMapper.analysisRespListToAnalysisDTOList(analysisPage.getContent(),
                analysisPage.getTotalPages(), pageNumber, pageSize, sort);
        return ResponseEntity.ok(analysisRespListDTO);
    }

    @Override
    public ResponseEntity<AnalysisByIdRespDTO> findById(Integer id) {
        Optional<Analysis> analysisOpt = analysisFinderService.findById(id);
        AnalysisByIdRespDTO analysisByIdRespDTO = analysisMapper.analysisToAnalysisByIdRespDTO(
                analysisOpt.orElseGet(Analysis::new));
        return ResponseEntity.ok(analysisByIdRespDTO);
    }

    @Override
    public ResponseEntity<AnalysesRespDTO> findByType(AnalysisTypeEnumDTO type, Integer pageNumber, Integer pageSize, String sort) {
        Page<Analysis> analysisPage = analysisFinderService.findByType(type, pageNumber, pageSize, sort);
        AnalysesRespDTO analysisRespListDTO = analysisMapper.analysisRespListToAnalysisDTOList(analysisPage.getContent(),
                analysisPage.getTotalPages(), pageNumber, pageSize, sort);
        return ResponseEntity.ok(analysisRespListDTO);
    }
}
