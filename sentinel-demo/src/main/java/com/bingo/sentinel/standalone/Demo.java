package com.bingo.sentinel.standalone;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bingo
 * @title: Demo
 * @projectName java-hub
 * @description: TODO
 * @date 2019/8/23  23:30
 */
public class Demo {

    public static void main(String[] args) {
        initRules();



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
