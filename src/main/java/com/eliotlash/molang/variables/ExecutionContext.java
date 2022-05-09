package com.eliotlash.molang.variables;

import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap;

import java.util.HashMap;
import java.util.Map;

public class ExecutionContext {
    private final Object2DoubleMap<RuntimeVariable> variableMap = new Object2DoubleOpenHashMap<>();
    private final Map<String, RuntimeVariable> variableCache = new HashMap<>();

    public Object2DoubleMap<RuntimeVariable> getVariableMap() {
        return variableMap;
    }

    public RuntimeVariable getCachedVariable(String var) {
        return variableCache.computeIfAbsent(var, name -> {
            String[] split = name.split("\\.", 2);

            VariableFlavor flavor = VariableFlavor.parse(split[0]);
            if (split.length == 2 && flavor != null) {
                return new RuntimeVariable(split[1], flavor);
            } else {
                return new RuntimeVariable(name, null);
            }
        });
    }

    public void setQuery(String var, double value) {
        RuntimeVariable cachedVariable = getCachedVariable(var);
        variableMap.put(cachedVariable, value);
    }
}
