package study.wild.common.domain;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String datasource, long id) {
        super(datasource + "에서 ID " + id + "가 이미 존재합니다.");
    }

    public DuplicateResourceException(String datasource, String id) {
        super(datasource + "에서 ID " + id + "가 이미 존재합니다.");
    }
}
