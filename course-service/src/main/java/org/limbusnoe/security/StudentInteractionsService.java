package org.limbusnoe.security;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "student-interactions-service", path = "/api/student-interactions", url = "student-interactions-service:8083")
public interface StudentInteractionsService {
    @GetMapping("is-page-read/{page}/{student}")
    boolean isPageRead(@PathVariable UUID page, @PathVariable Long student);

    @GetMapping("is-assigned/{course}/{student}")
    boolean isAssigned(@PathVariable UUID course, @PathVariable Long student);
}
