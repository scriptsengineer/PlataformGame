package br.com.expressobits.games.plataformer.resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import net.dermetfan.gdx.assets.AnnotationAssetManager;
import net.dermetfan.gdx.assets.AnnotationAssetManager.Asset;

public class Assets {

    public static final AnnotationAssetManager manager = new AnnotationAssetManager(new InternalFileHandleResolver());

    //BLOCKS
    @Asset public static AssetDescriptor<Texture> dirt;
    @Asset public static AssetDescriptor<Texture> cobblestone;
    @Asset public static AssetDescriptor<Texture> obsidian;
    @Asset public static AssetDescriptor<Texture> grass;

    //ENTITIES
    @Asset public static AssetDescriptor<Texture> player;

    public static void loadMod(){

        FileHandle fileHandle;

        if(Gdx.files.local("mods").isDirectory()){
            Gdx.app.log("Mod","Existe mod!");
            fileHandle = new FileHandle(Gdx.files.getLocalStoragePath()).child("mods");
        }else{
            Gdx.app.log("Mod","Não existe mod!");
            fileHandle = new FileHandle("");
        }

        dirt = new AssetDescriptor<Texture>(fileHandle.child("blocks/dirt.png"), Texture.class);
        cobblestone = new AssetDescriptor<Texture>(fileHandle.child("blocks/cobblestone.png"), Texture.class);
        obsidian = new AssetDescriptor<Texture>(fileHandle.child("blocks/obsidian.png"), Texture.class);
        grass = new AssetDescriptor<Texture>(fileHandle.child("blocks/grass.png"), Texture.class);
        player = new AssetDescriptor<Texture>(fileHandle.child("player/player.png"), Texture.class);
    }

    public static void load(){
        loadMod();
        manager.load(Assets.class);

    }

}
