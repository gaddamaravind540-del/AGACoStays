package com.agacostays.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.gateway.rate-limit")
public class RateLimitConfig {

    private int replenishRate = 20;
    private int burstCapacity = 40;
    private boolean failOpen = true;

    public int getReplenishRate() { return replenishRate; }
    public void setReplenishRate(int replenishRate) { this.replenishRate = replenishRate; }

    public int getBurstCapacity() { return burstCapacity; }
    public void setBurstCapacity(int burstCapacity) { this.burstCapacity = burstCapacity; }

    public boolean isFailOpen() { return failOpen; }
    public void setFailOpen(boolean failOpen) { this.failOpen = failOpen; }
}
