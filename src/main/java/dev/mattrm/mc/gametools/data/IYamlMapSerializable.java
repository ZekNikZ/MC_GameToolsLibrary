package dev.mattrm.mc.gametools.data;

import java.util.Map;

public interface IYamlMapSerializable {
    Map<?, ?> serialize();

    void deserialize(Map<?, ?> map);
}
