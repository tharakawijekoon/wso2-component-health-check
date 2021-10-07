package org.wso2.custom.component.healthchecker.internal;

import org.wso2.carbon.identity.entitlement.EntitlementService;
import org.wso2.carbon.identity.oauth2.OAuth2Service;

public class ComponentHealthCheckerServiceHolder {

    private static ComponentHealthCheckerServiceHolder instance = new ComponentHealthCheckerServiceHolder();
    private OAuth2Service oauth2Service;
    private EntitlementService entitlementService;

    public static ComponentHealthCheckerServiceHolder getInstance() {

        return instance;
    }

    public OAuth2Service getOauth2Service() {
        return oauth2Service;
    }

    public void setOauth2Service(OAuth2Service oauth2Service) {
        this.oauth2Service = oauth2Service;
    }

    public EntitlementService getEntitlementService() {
        return entitlementService;
    }

    public void setEntitlementService(EntitlementService entitlementService) {
        this.entitlementService = entitlementService;
    }
}