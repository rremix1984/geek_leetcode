package com.bilibili.juc.web;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bilibili.juc.web.model.CatalogItem;
import com.bilibili.juc.web.service.AnimationCatalogService;
import com.bilibili.juc.web.service.CodeExecutionService;
import com.bilibili.juc.web.service.CodeExecutionService.RunCodeResult;
import com.bilibili.juc.web.service.ProblemProgressService;
import com.bilibili.juc.web.service.ProblemSubmitService;
import com.bilibili.juc.web.service.WebAnimationService;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class AnimationWebController {

    private final AnimationCatalogService catalogService;
    private final CodeExecutionService codeExecutionService;
    private final WebAnimationService webAnimationService;
    private final ProblemSubmitService problemSubmitService;
    private final ProblemProgressService problemProgressService;

    public AnimationWebController(AnimationCatalogService catalogService, CodeExecutionService codeExecutionService,
            WebAnimationService webAnimationService, ProblemSubmitService problemSubmitService,
            ProblemProgressService problemProgressService) {
        this.catalogService = catalogService;
        this.codeExecutionService = codeExecutionService;
        this.webAnimationService = webAnimationService;
        this.problemSubmitService = problemSubmitService;
        this.problemProgressService = problemProgressService;
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

    @GetMapping("/problems")
    public List<Map<String, Object>> problems(@RequestParam(name = "difficulty", required = false) String difficulty,
            @RequestParam(name = "q", required = false) String q) {
        return catalogService.listProblems(difficulty, q).stream().map(this::toSummary).collect(Collectors.toList());
    }

    @GetMapping("/problems/{id}")
    public ResponseEntity<Map<String, Object>> problem(@PathVariable("id") String id) {
        CatalogItem item = catalogService.findProblemById(id);
        if (item == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error("未找到题目: " + id));
        }
        return ResponseEntity.ok(toDetail(item));
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

    @GetMapping("/visualize/{id}")
    public ResponseEntity<Map<String, Object>> visualize(@PathVariable("id") String id) {
        CatalogItem item = catalogService.findProblemById(id);
        if (item == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error("未找到题目: " + id));
        }
        return ResponseEntity.ok(webAnimationService.buildAnimation(item));
    }

    @PostMapping("/launch")
    public ResponseEntity<Map<String, Object>> launch(@RequestBody LaunchRequest request) {
        String id = request == null ? null : request.getName();
        if (id == null || id.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error("id 不能为空"));
        }
        CatalogItem item = catalogService.findProblemById(id.trim());
        if (item == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error("未找到题目: " + id));
        }
        return ResponseEntity.ok(webAnimationService.buildAnimation(item));
    }

    @PostMapping("/run")
    public ResponseEntity<Map<String, Object>> run(@RequestBody RunCodeRequest request) {
        String code = request == null ? null : request.getCode();
        RunCodeResult result = codeExecutionService.execute(code);
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("success", result.isSuccess());
        map.put("output", result.getOutput());
        map.put("error", result.getError());
        return ResponseEntity.ok(map);
    }

    @PostMapping("/submit")
    public ResponseEntity<Map<String, Object>> submit(@RequestBody SubmitRequest request) {
        String id = request == null ? null : request.getId();
        String code = request == null ? null : request.getCode();
        if (id == null || id.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error("id 不能为空"));
        }
        CatalogItem item = catalogService.findProblemById(id.trim());
        if (item == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error("未找到题目: " + id));
        }
        Map<String, Object> result = problemSubmitService.submit(item.getId(), code);
        return ResponseEntity.ok(result);
    }

    private Map<String, Object> error(String message) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("ok", false);
        map.put("error", message);
        return map;
    }

    private Map<String, Object> toSummary(CatalogItem item) {
        Map<String, Object> map = new LinkedHashMap<>();
        ProblemProgressService.ProblemProgress progress = problemProgressService.snapshot(item.getId());
        map.put("id", item.getId());
        map.put("name", item.getName());
        map.put("difficulty", item.getDifficulty());
        map.put("technique", item.getTechnique());
        map.put("categoryPath", item.getCategoryPath());
        map.put("launchable", item.isLaunchable());
        map.put("launchName", item.getLaunchName());
        map.put("solved", progress.isAccepted());
        map.put("progress", progress);
        return map;
    }

    private Map<String, Object> toDetail(CatalogItem item) {
        Map<String, Object> map = toSummary(item);
        map.put("description", item.getDescription());
        map.put("code", item.getCode());
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

    public static class RunCodeRequest {
        private String code;

        public RunCodeRequest() {
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

    public static class SubmitRequest {
        private String id;
        private String code;

        public SubmitRequest() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
