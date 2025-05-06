package com.info.api.service.impl;

import com.info.api.repository.CommonRepository;
import com.info.api.service.common.CommonService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService {

    public static final Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);

    private final CommonRepository commonRepository;

    @Override
    public boolean isAuthorizedRequest(String userId, String password) {
        boolean result = false;
        try {
            result = commonRepository.isUserExistByUserIdAndPassword(userId, password);
        } catch (Exception e) {
            logger.error("Error in isAuthorizedRequest Error: {} ", e.getMessage());
        }

        return result;
    }


}
