package com.zhouyf.banner;

import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

public class Mybanner implements Banner {
    private static final String[] ZHOUYF_BANNER = {
        "    .                  .-.",
        "    |                  |  ",
        "---.|--. .-. .  . .  .-|- ",
        " .' |  |(   )|  | |  | |  ",
        "'---'  `-`-' `--`-`--| '",
        "                     ; ",
        "                   `-'"

    };
    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        for (String line : ZHOUYF_BANNER) {
            out.println(line);
        }
        out.println();
        out.flush();
    }
}
