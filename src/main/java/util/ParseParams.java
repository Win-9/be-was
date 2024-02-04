package util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ParseParams {
    private Map<String, String> paramMap;

    public ParseParams(Map<String, String> paramMap) {
        this.paramMap = paramMap;
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    public static ParseParams from(String query) {
        return new ParseParams(
                Arrays.stream(query.split("&"))
                        .map(Param::from)
                        .collect(Collectors.toMap(Param::getKey, Param::getValue)));
    }

    public static ParseParams from(String body, String query) {
        ParseParams bodyParseParam = new ParseParams(
                Arrays.stream(body.split("&"))
                        .map(Param::from)
                        .collect(Collectors.toMap(Param::getKey, Param::getValue)));

        ParseParams queryParseParam = new ParseParams(
                Arrays.stream(query.split("&"))
                        .map(Param::from)
                        .collect(Collectors.toMap(Param::getKey, Param::getValue)));

        return combineMap(bodyParseParam, queryParseParam);
    }

    private static ParseParams combineMap(ParseParams bodyParseParam, ParseParams queryParseParam) {
        Map<String, String> combinedParams = new HashMap<>();
        combinedParams.putAll(bodyParseParam.getParamMap());
        combinedParams.putAll(queryParseParam.getParamMap());
        ParseParams combinedParseParam = new ParseParams(combinedParams);
        return combinedParseParam;
    }
}
