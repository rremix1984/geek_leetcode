package com.bilibili.juc.web;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.SwingUtilities;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bilibili.juc.web.model.CatalogItem;
import com.bilibili.juc.web.service.AnimationCatalogService;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class AnimationWebController {

    private final AnimationCatalogService catalogService;

    public AnimationWebController(AnimationCatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("status", "UP");
        map.put("time", Instant.now().toString());
        map.putAll(catalogService.summary());
        return map;
    }

    @GetMapping("/catalog")
    public List<CatalogItem> catalog() {
        return catalogService.curatedCatalog();
    }

    @GetMapping("/animations")
    public List<String> animations(@RequestParam(name = "q", required = false) String q) {
        List<String> all = catalogService.allAnimationNames();
        if (q == null || q.trim().isEmpty()) {
            return all;
        }
        String keyword = q.trim();
        return all.stream().filter(n -> n.contains(keyword)).collect(Collectors.toList());
    }

    @PostMapping("/launch")
    public ResponseEntity<Map<String, Object>> launch(@RequestBody LaunchRequest request) {
        String name = request == null ? null : request.getName();
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error("name 不能为空"));
        }
        String normalized = name.trim();
        Runnable launcher = catalogService.getLauncher(normalized);
        if (launcher == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error("未找到动画: " + normalized));
        }

        SwingUtilities.invokeLater(launcher);

        Map<String, Object> ok = new LinkedHashMap<>();
        ok.put("ok", true);
        ok.put("name", normalized);
        return ResponseEntity.ok(ok);
    }

    private Map<String, Object> error(String message) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("ok", false);
        map.put("error", message);
        return map;
    }

    public static class LaunchRequest {
        private String name;

        public LaunchRequest() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
