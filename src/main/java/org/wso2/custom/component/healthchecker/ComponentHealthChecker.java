package org.wso2.custom.component.healthchecker;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.healthcheck.api.core.HealthChecker;
import org.wso2.carbon.healthcheck.api.core.exception.BadHealthException;
import org.wso2.carbon.healthcheck.api.core.model.HealthCheckerConfig;
import org.wso2.carbon.identity.oauth2.OAuth2Service;
import org.wso2.custom.component.healthchecker.internal.ComponentHealthCheckerServiceHolder;

import java.util.Properties;

public class ComponentHealthChecker implements HealthChecker {

    private static final Log log = LogFactory.getLog(ComponentHealthChecker.class);
    protected HealthCheckerConfig healthCheckerConfig = null;
    private static final String COMPONENT_HEALTH_CHECKER = "ComponentHealthChecker";

    private OAuth2Service oauth2Service;

    @Override
    public String getName() {

        return COMPONENT_HEALTH_CHECKER;
    }

    @Override
    public void init(HealthCheckerConfig healthCheckerConfig) {

        this.healthCheckerConfig = healthCheckerConfig;
    }

    @Override
    public Properties checkHealth() throws BadHealthException {
        Properties properties = new Properties();

        if (ComponentHealthCheckerServiceHolder.getInstance().getOauth2Service() == null ||
                ComponentHealthCheckerServiceHolder.getInstance().getEntitlementService() == null) {
            throw new BadHealthException("500", "Components have not started, A server Restart and DB check required.");
        } else {
            properties.put("component.status", "ACTIVE");
        }

        return properties;
    }

    @Override
    public boolean isEnabled() {

        return this.healthCheckerConfig == null || healthCheckerConfig.isEnable();
    }

    @Override
    public int getOrder() {

        if (this.healthCheckerConfig == null) {
            return 0;
        } else {
            return healthCheckerConfig.getOrder();
        }
    }
}