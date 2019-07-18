package com.bingo.springboot.autoconfigure.format;

import com.bingo.format.IFormatterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Bingo
 * @title: FormatService
 * @projectName java-hub
 * @description: TODO
 * @date 2019/7/17  23:22
 */
@Service
public class FormatService {
    @Autowired
    IFormatterService formatterService;

    public <T> String format(T t) throws Exception {
        return formatterService.output(t);

    }
}
