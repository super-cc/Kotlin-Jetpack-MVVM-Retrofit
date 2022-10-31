package love.isuper.core.glide;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

/**
 * Created by shichao on 2022/6/6.
 * Company: isuper
 * E-mail: 1169380200@qq.com
 */
@GlideModule
public class GlideConfigModule extends AppGlideModule {

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        //修改默认配置，如缓存配置
//        //磁盘缓存配置（默认缓存大小250M，默认保存在内部存储中）
//
//        //设置磁盘缓存保存在外部存储，且指定缓存大小
//        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, diskCacheSize);
//        //设置磁盘缓存保存在自己指定的目录下，且指定缓存大小
//        builder.setDiskCache(new DiskLruCacheFactory(new DiskLruCacheFactory.CacheDirectoryGetter() {
//            @Override
//            public File getCacheDirectory() {
//                return diskCacheFolder;
//            }
//        }, diskCacheSize);
//
//
//        //内存缓存配置（不建议配置，Glide会自动根据手机配置进行分配）
//
//        //设置内存缓存大小
//        builder.setMemoryCache(new LruResourceCache(memoryCacheSize));
//        //设置Bitmap池大小
//        builder.setBitmapPool(new LruBitmapPool(bitmapPoolSize));
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        //替换组件，如网络请求组件

    }
}