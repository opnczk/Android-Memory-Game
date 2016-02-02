package com.olivierpanczuk.simpleframework.implementation;

        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.OutputStream;

        import android.content.Context;
        import android.content.SharedPreferences;
        import android.content.res.AssetManager;
        import android.os.Environment;
        import android.preference.PreferenceManager;

        import com.olivierpanczuk.simpleframework.FileIO;

public class AndroidFileIO implements FileIO {
    Context context;
    AssetManager assets;
    String externalStoragePath;
    String internalStoragePath;
    String preferredStoragePath;

    public AndroidFileIO(Context context) {
        this.context = context;
        this.assets = context.getAssets();
        this.externalStoragePath = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + File.separator;
        /*Added This to store in Internal Storage 20/01/2016*/
        this.internalStoragePath = context.getFilesDir().getAbsolutePath()+File.separator;
        this.preferredStoragePath = this.internalStoragePath;
    }

    @Override
    public InputStream readAsset(String file) throws IOException {
        return assets.open(file);
    }

    @Override
    public InputStream readFile(String file) throws IOException {
        return new FileInputStream(preferredStoragePath + file);
    }

    @Override
    public OutputStream writeFile(String file) throws IOException {
        return new FileOutputStream(preferredStoragePath + file);
    }

    public SharedPreferences getSharedPref() {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
