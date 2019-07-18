package com.bingo.format;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * @author Bingo
 * @title: XmlFormatterService
 * @projectName java-hub
 * @description: TODO
 * @date 2019/7/17  22:58
 */
public class XmlFormatterService implements IFormatterService {
    @Override
    public <T> String output(T t) throws JsonProcessingException {
        XmlMapper mapper =  new XmlMapper();
        return mapper.writeValueAsString(t);
    }
}
