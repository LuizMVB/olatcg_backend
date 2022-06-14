package br.com.olatcg_backend.service;

import br.com.olatcg_backend.data.IAnalysisData;
import br.com.olatcg_backend.data.IPhylogenySearchData;
import br.com.olatcg_backend.domain.Taxonomy;
import br.com.olatcg_backend.domain.vo.PhylogenyApiRequestVo;
import br.com.olatcg_backend.enumerator.ErrorEnum;
import br.com.olatcg_backend.util.CustomException;
import br.com.olatcg_backend.util.FileUtils;
import br.com.olatcg_backend.vision.dto.PhylogenyResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhylogenyService {
    @Autowired
    private IAnalysisData analysisRepository;
    @Autowired
    private FileUtils fileUtils;
    @Autowired
    private IPhylogenySearchData phylogenySearchRepository;

    public PhylogenyResponseDTO getNewickFormatFromTaxonomy(Long idAnalysis) throws CustomException {
        try{
            List<Taxonomy> taxonomies = analysisRepository.findById(idAnalysis).get().getTaxonomies();
            return new PhylogenyResponseDTO(phylogenySearchRepository.obtainNewickFormatFrom(
                    new PhylogenyApiRequestVo(fileUtils.generateEncodedFastaFileFrom(taxonomies))));
        }catch (CustomException e){
            throw new CustomException(e.getErrorEnum());
        }catch (Exception e) {
            throw new CustomException(ErrorEnum.GENERAL_ERROR);
        }
    }
}
