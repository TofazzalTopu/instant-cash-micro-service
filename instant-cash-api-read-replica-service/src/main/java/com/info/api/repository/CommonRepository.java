package com.info.api.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;

@Component
public class CommonRepository {
    private static final Logger logger = LoggerFactory.getLogger(CommonRepository.class);
    @PersistenceContext
    private EntityManager em;

    public boolean isUserExistByUserIdAndPassword(String userId, String password) {
        boolean result = false;
        try {
            Query query;
            String sql = "select count(*) FROM RMS_API_CHANNELS  WHERE CBSAPI_USERID	= ? AND CBSAPI_USER_PASSWORD = ?";
            query = em.createNativeQuery(sql);
            query.setParameter(1, userId);
            query.setParameter(2, password);

            BigDecimal rowCount = (BigDecimal) query.getSingleResult();

            if (rowCount.compareTo(BigDecimal.ZERO) > 0) {
                result = true;
            }

        } catch (Exception ex) {
            logger.error("Error in isUserExistByUserIdAndPassword()", ex);
        }
        return result;
    }


}
