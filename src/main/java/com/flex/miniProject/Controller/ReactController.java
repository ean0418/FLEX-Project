package com.flex.miniProject.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Arrays;

@Controller
public class ReactController {

    // JSP 파일을 반환하는 메서드 (기존 메서드 유지)
    @GetMapping("/api/data")
    public String getMapPage() {
        return "map/map";  // map.jsp 파일을 반환
    }
}

// RestController로 추가 API 제공
@RestController
class BoundaryDataController {

    // React에서 경계 데이터를 요청할 때 호출되는 API
    @GetMapping("/api/boundary")
    public List<String> getBoundaryData() {
        // 임시로 경계 데이터를 리스트로 반환 (예시)
        return Arrays.asList("Boundary 1", "Boundary 2", "Boundary 3");
    }
}
