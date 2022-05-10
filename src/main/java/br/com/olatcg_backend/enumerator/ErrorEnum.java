package br.com.olatcg_backend.enumerator;

public enum ErrorEnum {
    GENERAL_ERROR(1),
    INVALID_FILE_TYPE(100),
    INVALID_CHARACTERS_IN_SEQUENCE_FILE_ERROR(101),
    DECODE_FILE_ERROR(102),
    TAXONOMY_SEARCH_API_ERROR(103),
    CONVERT_TAXONOMY_SEARCH_API_RESPONSE_TO_TAXONOMY_ERROR(104),
    PHYLOGENY_API_ERROR(104),
    PERSISTENCE_DATABASE_ERROR(105),
    SEQUENCE_ALIGNMENT_API_ERROR(106),
    SEQUENCE_ALIGNMENT_ERROR(107),
    REQUEST_TIMEOUT_EXCEPTION(108),
    REQUEST_GENERAL_ERROR(109);

    private Integer errorCode;

    ErrorEnum(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
