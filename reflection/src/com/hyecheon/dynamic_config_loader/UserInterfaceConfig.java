package com.hyecheon.dynamic_config_loader;

import java.util.Arrays;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/20
 */
public class UserInterfaceConfig {
    private String titleColor;
    private String footerText;
    private short titleFontSize;
    private short footerFontSize;
    private String[] titleFonts;

    public String getTitleColor() {
        return titleColor;
    }

    public String getFooterText() {
        return footerText;
    }

    public String[] getTitleFonts() {
        return titleFonts;
    }

    public short getTitleFontSize() {
        return titleFontSize;
    }

    public short getFooterFontSize() {
        return footerFontSize;
    }

    @Override
    public String toString() {
        return "UserInterfaceConfig{" +
                "titleColor='" + titleColor + '\'' +
                ", footerText='" + footerText + '\'' +
                ", titleFontSize=" + titleFontSize +
                ", footerFontSize=" + footerFontSize +
                ", titleFonts=" + Arrays.toString(titleFonts) +
                '}';
    }
}
