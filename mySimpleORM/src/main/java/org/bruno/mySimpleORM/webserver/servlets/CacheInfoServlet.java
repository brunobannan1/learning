package org.bruno.mySimpleORM.webserver.servlets;

import org.bruno.mySimpleORM.services.cache.CacheServiceImpl;
import org.bruno.mySimpleORM.webserver.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CacheInfoServlet extends HttpServlet {

    private static final String PAGE = "cacheinfo.html";

    private final CacheServiceImpl cacheService;

    public CacheInfoServlet(CacheServiceImpl cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = createPageVariablesMap();
        resp.getWriter().println(TemplateProcessor.instance().getPage(PAGE, pageVariables));
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private Map<String, Object> createPageVariablesMap() {

        Map<String, Object> pageVariables = new HashMap<>();

        pageVariables.put("size", cacheService.getSize());
        pageVariables.put("ife", cacheService.getLifeTimeMs());
        pageVariables.put("it", cacheService.getIdleTimeMs());
        pageVariables.put("iseternal", cacheService.isEternal());
        pageVariables.put("hit", cacheService.getHitCount());
        pageVariables.put("miss", cacheService.getMissCount());
        int sum = cacheService.getHitCount() + cacheService.getMissCount();
        if (sum != 0) {
            pageVariables.put("percent", cacheService.getHitCount() * 100 / sum);
        } else
            pageVariables.put("percent", -1);
        return pageVariables;
    }
}
