package com.agacostays.room.client;

import com.agacostays.room.config.FeignClientConfig;
import com.agacostays.room.dto.response.BranchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "branch-service", url = "${services.branch.url}", configuration = FeignClientConfig.class)
public interface BranchServiceClient {
    @GetMapping("/api/hotel-branches/{branchId}")
    BranchResponse getBranch(@PathVariable Long branchId);
}
