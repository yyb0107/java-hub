package com.bingo.sentinel.standalone;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Bingo
 * @title: Demo
 * @projectName java-hub
 * @description: TODO
 * @date 2019/8/23  23:30
 */
public class Demo {

    static Logger log = LoggerFactory.getLogger(Demo.class);
    public static void main(String[] args) throws InterruptedException {
        initRules();
        Entry entry = null;
        while(true){
            try {
                entry = SphU.entry("Bingo");
                log.info("this is logical log");

            } catch (BlockException e) {
                log.error("this is flow control info");
            } finally {
                if (entry != null) {
                    entry.exit();
                }
            }

            TimeUnit.SECONDS.sleep(1);
        }



    }

    public static void initRules(){
        List<FlowRule> rules = new ArrayList<>();

        FlowRule rule = new FlowRule();
        rule.setRefResource("Bingo");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(10);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }
}
