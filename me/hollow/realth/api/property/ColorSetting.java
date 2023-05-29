package me.hollow.realth.api.property;

import me.hollow.realth.api.property.Setting;
import java.awt.*;
import java.util.function.*;

public class ColorSetting extends Setting<Color>
{
    public ColorSetting(final String name, final Color value) {
        super(name, value);
    }

    public ColorSetting(final String name, final Color value, final Predicate<Color> shown) {
        super(name, value, shown);
    }

    public void setColor(final Color value) {
        this.value = value;
    }
}