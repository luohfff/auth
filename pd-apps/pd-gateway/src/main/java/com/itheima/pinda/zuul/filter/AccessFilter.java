package com.itheima.pinda.zuul.filter;

import com.itheima.pinda.authority.dto.auth.RoleResourceDTO;
import com.itheima.pinda.authority.entity.auth.Resource;
import com.itheima.pinda.base.R;
import com.itheima.pinda.context.BaseContextConstants;
import com.itheima.pinda.zuul.biz.service.RoleAuthService;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 权限验证过滤器
 */
@Component
@Slf4j
public class AccessFilter extends BaseFilter {
    private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

    @Autowired
    RoleAuthService roleAuthService;

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER + 10;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 验证当前用户 是否拥有某个URI 的访问权限
     * <p>
     * eurekaCode + uri + method + userId
     * <p>
     * <p>
     * 数据库存了 指定人某有某些权限
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        Route route = this.route();
        if (route == null) {
            log.info("ROUTE:{}", route);
            errorResponse("路由未注册", R.FAIL_CODE, 200);
            return null;
        }
        RequestContext ctx = RequestContext.getCurrentContext();

        // token校验失败，无需校验
        if (!ctx.sendZuulResponse()) {
            return null;
        }

        // 不进行拦截的地址
        if (isIgnoreToken()) {
            log.info("access filter not execute");
            return null;
        }

        if (isIgnoreResourceByRoute()) {
            log.info("access filter not execute by resource route");
            return null;
        }

        if (isIgnoreResource()) {
            log.info("access filter not execute by resource");
            return null;
        }

        HttpServletRequest request = ctx.getRequest();

        String userId = ctx.getZuulRequestHeaders().get(BaseContextConstants.JWT_KEY_USER_ID);
        // 当前登录用户id
        List<Long> roles = roleAuthService.findRoleByUserId(Long.parseLong(userId));

        List<RoleResourceDTO> allRoles = roleAuthService.findAllRoles();

        Set<Resource> resources = allRoles.stream().filter(item -> roles.contains(item.getId())).map(item -> item.getResources())
                .collect(() -> new HashSet<>(), (list, list1) -> list.addAll(list1), (list1, list2) -> list1.addAll(list2));

        List<String> resourceStr = resources.stream().filter(item -> item != null && item.getMethod() != null && item.getUrl() != null).map(item -> item.getMethod() + item.getUrl()).collect(Collectors.toList());
        String requestResource = request.getMethod().toUpperCase() + route.getPath();
        log.info("当前路径：{}", requestResource);
        if (resourceStr.contains(requestResource)) {
            log.info("权限校验通过,直接匹配：{}", requestResource);
            return null;
        } else {
            for (String resource : resourceStr) {
                if (check(requestResource, resource)) {
                    log.info("权限校验通过：{},{}", requestResource, resource);
                    return null;
                }
            }
        }
        log.info("权限不足 " + request.getMethod() + " " + route.getPath(), R.FAIL_CODE);
        errorResponse("权限不足 " + request.getMethod() + " " + route.getPath(), R.FAIL_CODE, 200);
        return null;
    }

    private boolean check(String requestUri, String currentUri) {
        if (StringUtils.isBlank(requestUri) || StringUtils.isBlank(currentUri)) {
            return false;
        }
        return currentUri.equals(requestUri) || ANT_PATH_MATCHER.match(currentUri, requestUri);
    }
}
