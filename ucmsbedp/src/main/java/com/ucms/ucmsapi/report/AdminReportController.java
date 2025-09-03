
package com.ucms.ucmsapi.report;

import com.ucms.ucmsapi.report.dto.AdminReportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/report")
public class AdminReportController {

    private final AdminReportService service;

    @Autowired
    public AdminReportController(AdminReportService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public AdminReportDto report() {
        return service.build();
    }
}
