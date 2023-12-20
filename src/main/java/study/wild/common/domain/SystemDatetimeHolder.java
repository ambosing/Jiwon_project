package study.wild.common.domain;

import org.springframework.stereotype.Component;
import study.wild.common.service.DatetimeHolder;

import java.time.LocalDateTime;

@Component
public class SystemDatetimeHolder implements DatetimeHolder {
    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
