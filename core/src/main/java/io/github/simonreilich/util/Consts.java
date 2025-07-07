package io.github.simonreilich.util;

public class Consts {
    // the probability of an elemental monster being small
    public static final double smallProb = 0.66;

    // the probability of a zombie being active in a single turn
    public static final double zombieActiveProb = 0.4;

    // the maximum range, a zombie can detect the player from
    public static final int zombieRange = 4;

    // the maximum range, an orc can detect the player from
    public static final int orcRange = 10;

    // the margin of elements in divs, multiple of the size
    public static final float margin = 0.5f;

    // timings for the startup animation
    public final static float libLogoFadeInStart = 0.0f;
    public final static float libLogoFadeInEnd = 0.5f;
    public final static float libLogoFadeOutStart = 1.5f;
    public final static float libLogoFadeOutEnd = 2.0f;
    public final static float gameLogoFadeInStart = 2.5f;
    public final static float animationEnd = 3.0f;

    // map-layers to render as foreground and background
    public final static int[] backgroundLayer = new int[]{4, 5};
    public final static int[] foregroundLayer = new int[]{6};

    // layer that has the spawnXi and spawnYi properties
    public static final int spawnLayer = 0;
}
