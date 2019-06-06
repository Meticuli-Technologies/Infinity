package com.meti.lib.asset;

import com.meti.lib.asset.source.Source;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/6/2019
 */
public class TextAssetTranslator implements AssetTranslator<Asset<?, StringBuilder>> {
    @Override
    public Asset<?, StringBuilder> read(Source source) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(source.getInputStream()));
        String lines = reader.lines().collect(Collectors.joining());
        return new TextAsset(source.getName(), lines);
    }

    @Override
    public void write(Source source, Asset<?, StringBuilder> asset) {
        PrintWriter writer = new PrintWriter(source.getOutputStream());
        writer.print(asset.getValue());
        writer.flush();
    }

    @Override
    public boolean canBuild(Source source) {
        return source.getName().endsWith("txt");
    }
}
