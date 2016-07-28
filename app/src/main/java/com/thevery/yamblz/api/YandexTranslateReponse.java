package com.thevery.yamblz.api;

/*
{
    "code": 200,
    "lang": "en-ru",
    "text": [
        "Здравствуй, Мир!"
    ]
}
*/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class YandexTranslateReponse {
    public int code;
    public String lang;
    public String[] text;
}
