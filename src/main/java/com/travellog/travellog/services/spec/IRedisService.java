package com.travellog.travellog.services.spec;

import java.util.concurrent.TimeUnit;

public interface IRedisService {
    void addValue(String key, String value, long timeout, TimeUnit timeUnit);

    String getValue(String key);

    void deleteValue(String key);
}
