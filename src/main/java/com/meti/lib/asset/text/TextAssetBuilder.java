package com.meti.lib.asset.text;

import com.meti.lib.asset.AssetBuilder;
import com.meti.lib.asset.TextAsset;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/13/2019
 */
public class TextAssetBuilder implements AssetBuilder<TextAsset> {
    @Override
    public TextAsset apply(InputStream inputStream, String s) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        List<String> lines = reader.lines().collect(Collectors.toList());

        try {
            reader.close();
        } catch (IOException e) {
            //TODO: do something here
            e.printStackTrace();
        }

        return new TextAsset(lines) {
            @Override
            public String getName() {
                return s;
            }
        };
    }

    @Override
    public boolean test(String s) {
        return s.endsWith("txt");
    }
}
