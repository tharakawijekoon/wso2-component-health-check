package org.wso2.custom.component.healthchecker.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.*;
import org.wso2.carbon.healthcheck.api.core.HealthChecker;
import org.wso2.carbon.identity.entitlement.EntitlementService;
import org.wso2.carbon.identity.oauth2.OAuth2Service;
import org.wso2.custom.component.healthchecker.ComponentHealthChecker;

@Component(
        name = "org.wso2.custom.component.healthchecker",
        immediate = true
)
public class ComponentHealthCheckerServiceComponent {

    private static final Log log = LogFactory.getLog(ComponentHealthCheckerServiceComponent.class);

    @Activate
    protected void activate(ComponentContext context) {

        try {
            BundleContext bundleContext = context.getBundleContext();
            bundleContext.registerService(HealthChecker.class.getName(), new ComponentHealthChecker(), null);
            log.debug("org.wso2.custom.component.healthchecker bundle is activated");
        } catch (Throwable e) {
            log.error("Error while activating org.wso2.custom.component.healthchecker", e);
        }
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {
        log.info("org.wso2.custom.component.healthchecker bundle is deactivated");
    }

    @Reference(
            name = "identity.oauth2.component",
            service = org.wso2.carbon.identity.oauth2.OAuth2Service.class,
            cardinality = ReferenceCardinality.OPTIONAL,
            policy = ReferencePolicy.DYNAMIC,
            policyOption = ReferencePolicyOption.GREEDY,
            unbind = "unsetOAuth2Service")
    protected void setOAuth2Service(OAuth2Service oauth2Service) {
        log.info("Setting the OAuth2 Service");
        ComponentHealthCheckerServiceHolder.getInstance().setOauth2Service(oauth2Service);
    }

    protected void unsetOAuth2Service(OAuth2Service oauth2Service) {
        log.info("UnSetting the OAuth2 Service");
        ComponentHealthCheckerServiceHolder.getInstance().setOauth2Service(null);
    }

    @Reference(
            name = "identity.entitlement.component",
            service = org.wso2.carbon.identity.entitlement.EntitlementService.class,
            cardinality = ReferenceCardinality.OPTIONAL,
            policy = ReferencePolicy.DYNAMIC,
            policyOption = ReferencePolicyOption.GREEDY,
            unbind = "unsetEntitlementService")
    protected void setEntitlementService(EntitlementService entitlementService) {
        log.info("Setting the Entitlement Service");
        ComponentHealthCheckerServiceHolder.getInstance().setEntitlementService(entitlementService);
    }

    protected void unsetEntitlementService(EntitlementService entitlementService) {
        log.info("UnSetting the Entitlement Service");
        ComponentHealthCheckerServiceHolder.getInstance().setEntitlementService(null);
    }

}