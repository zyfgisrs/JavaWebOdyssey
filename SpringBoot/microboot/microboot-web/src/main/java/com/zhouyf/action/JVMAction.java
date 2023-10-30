package com.zhouyf.action;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.RuntimeMXBean;
import java.util.List;

@RestController
@RequestMapping("/jvm/*")
public class JVMAction {
    @GetMapping(value = "show", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getJVMArgument() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        return runtimeMXBean.getInputArguments();
    }

    @GetMapping(value = "memory-info", produces = MediaType.APPLICATION_JSON_VALUE)
    public MemoryInfo getMemory() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
        return new MemoryInfo(
                heapMemoryUsage.getInit(),
                heapMemoryUsage.getUsed(),
                heapMemoryUsage.getCommitted(),
                heapMemoryUsage.getMax(),
                nonHeapMemoryUsage.getInit(),
                nonHeapMemoryUsage.getUsed(),
                nonHeapMemoryUsage.getCommitted(),
                nonHeapMemoryUsage.getMax()
        );
    }

    @Data
    @AllArgsConstructor
    public static class MemoryInfo {
        public long heapInit;
        public long heapUsed;
        public long heapCommitted;
        public long heapMax;
        public long nonHeapInit;
        public long nonHeapUsed;
        public long nonHeapCommitted;
        public long nonHeapMax;


    }
}
