package study.wild.unittest.mock.common;

import lombok.RequiredArgsConstructor;
import study.wild.common.service.port.DatetimeHolder;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class TestDateTimeHolder implements DatetimeHolder {

    private final LocalDateTime now;

    @Override
    public LocalDateTime now() {
        return now;
    }
}
