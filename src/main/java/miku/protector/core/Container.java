package miku.protector.core;

import java.util.Arrays;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.DummyModContainer;

public class Container extends DummyModContainer {
    private Logger log;

    public Container(){
        super(new ModMetadata());
        ModMetadata meta = getMetadata();
        meta.modId = "protector";
        meta.name = "Protector";
        meta.version = "1.2";
        meta.authorList = Arrays.asList("mcst12345");
        meta.description = "Protect your game";
        meta.url = "https://mcst12345.top:8443";
    }
}
