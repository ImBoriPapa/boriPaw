package com.boriworld.boriPaw.userAccountService.command.infrastructure.redis;

import com.boriworld.boriPaw.userAccountService.command.domain.repository.RefreshTokenRepository;

import com.boriworld.boriPaw.userAccountService.command.domain.model.RefreshToken;
import com.boriworld.boriPaw.userAccountService.command.domain.value.RefreshTokenId;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenRedisTemplateRepository implements RefreshTokenRepository {
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper mapper;

    @Override
    public void save(RefreshToken refreshToken) {
        log.info("start");
        RefreshTokenStrings refreshTokenStrings = RefreshTokenStrings.formModel(refreshToken);
        try {
            redisTemplate.opsForValue()
                    .set(refreshTokenStrings.getRefreshTokenId(), mapper.writeValueAsString(refreshTokenStrings), refreshToken.getTimeToLiveInMilliseconds(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Optional<RefreshToken> findRefreshTokenId(RefreshTokenId refreshTokenId) {
        log.info("get");
        log.info("id; {}",refreshTokenId.getId());

        String s = redisTemplate.opsForValue()
                .get(refreshTokenId.getId());
        log.info("s: {}",s);
        try {
            if (StringUtils.hasText(s)) {
                RefreshTokenStrings refreshTokenStrings = mapper.readValue(s, RefreshTokenStrings.class);
                return Optional.of(refreshTokenStrings.toModel());
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();

        }
        return Optional.empty();
    }

    @Override
    public void delete(RefreshToken refreshToken) {

    }
}
