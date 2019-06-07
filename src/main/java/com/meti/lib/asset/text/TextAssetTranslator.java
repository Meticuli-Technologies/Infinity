package com.meti.lib.asset.text;

import com.meti.lib.asset.Asset;
import com.meti.lib.asset.AssetTranslator;
import com.meti.lib.asset.properties.InlineAssetProperties;
import com.meti.lib.asset.source.Source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/6/2019
 */
public class TextAssetTranslator implements AssetTranslator<Asset<?, StringBuilder>> {
    @Override
    public Asset<?, StringBuilder> read(Source source) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(source.newInputStream()));
        StringBuilder builder = new StringBuilder();
        String line;
        do{
            line = reader.readLine();
            if(line != null){
                builder.append(line);
            }
        } while(line != null);
        return new TextAsset(new InlineAssetProperties(source.getName()), builder);
    }

    @Override
    public void write(Source source, Asset<?, StringBuilder> asset) throws IOException {
        PrintWriter writer = new PrintWriter(source.newOutputStream());
        writer.print(asset.getValue());
        writer.flush();
        writer.flush();
    }

    @Override
    public boolean canBuild(Source source) {
        return source.getName().endsWith("txt");
    }
}
